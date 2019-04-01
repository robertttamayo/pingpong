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

public class NetworkCheckUsername {
	
	public void fetch(final AsyncHandler<Boolean> handler) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", Global.TEMP_USERNAME);
 
		HttpRequest httpGet = new HttpRequest(HttpMethods.GET);
		
		String url = "https://www.roberttamayo.com/revolve/checkusername.php";
		if (Gdx.app.getType() == ApplicationType.Desktop) {
			url = "http://www.roberttamayo.com/revolve/checkusername.php";
		}
		
		httpGet.setUrl(url);
		httpGet.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		
		Gdx.net.sendHttpRequest (httpGet, new HttpResponseListener() {
	        public void handleHttpResponse(HttpResponse httpResponse) {
	                String status = httpResponse.getResultAsString();
	                if (status.equals("valid")) {
	                		handler.handle(new Boolean(true));
	                } else {
	                		handler.handle(new Boolean(false));
	                }
	        }
	 
	        public void failed(Throwable t) {
	                handler.handle(new Boolean(false));
	                System.out.println("ScoreMenuHUD::failed, status: " + t.getMessage());
	        }

			@Override
			public void cancelled() {
				String status = "cancelled";
				handler.handle(new Boolean(false));
				System.out.println("ScoreMenuHUD::cancelled, status: " + status);
			}
	 });
	}
	
}
