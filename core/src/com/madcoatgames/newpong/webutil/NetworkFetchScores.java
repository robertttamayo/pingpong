package com.madcoatgames.newpong.webutil;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.madcoatgames.newpong.records.RemoteScore;
import com.madcoatgames.newpong.records.RemoteScores;
import com.madcoatgames.newpong.util.Global;

public class NetworkFetchScores {
	private String enemiesScore = "0";
	private String soloScore = "0";
	
	public void fetch(final AsyncHandler<Array<RemoteScore>> handler) {
		Map<String, String> parameters = new HashMap<String, String>();
		
		if (!enemiesScore.equals("0")) {
			parameters.put("enemies_score", enemiesScore);
		}
		if (!soloScore.equals("0")) {
			parameters.put("solo_score", soloScore);
		}
		if (!soloScore.equals("0") || !enemiesScore.equals("0")) {
			parameters.put("app_version", Global.APP_VERSION);
			parameters.put("username", Global.USER_NAME);
			parameters.put("device", Gdx.app.getType().toString());
		}
 
		HttpRequest httpGet = new HttpRequest(HttpMethods.GET);
		
		String url = "https://www.roberttamayo.com/revolve/getscores.php";
		if (Gdx.app.getType() == ApplicationType.Desktop) {
			url = "http://www.roberttamayo.com/revolve/getscores.php";
		}
		
		httpGet.setUrl(url);
		httpGet.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		
		Gdx.net.sendHttpRequest (httpGet, new HttpResponseListener() {
	        public void handleHttpResponse(HttpResponse httpResponse) {
	                String status = httpResponse.getResultAsString();
	                Json json = new Json();
	                RemoteScores remoteScores = json.fromJson(RemoteScores.class, status);
	                Array<RemoteScore> scores = remoteScores.remoteScores;
	                handler.handle(scores);
	        }
	 
	        public void failed(Throwable t) {
	                String status = "failed";
	                System.out.println("ScoreMenuHUD::failed, status: " + t.getMessage());
	        }

			@Override
			public void cancelled() {
				String status = "cancelled";
				System.out.println("ScoreMenuHUD::cancelled, status: " + status);
			}
		});
	}
	public void setEnemiesScore(String enemiesScore) {
		this.enemiesScore = enemiesScore;
	}
	public void setSoloScore(String soloScore) {
		this.soloScore = soloScore;
	}
}
