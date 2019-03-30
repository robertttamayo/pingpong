package com.madcoatgames.newpong.records;

import com.badlogic.gdx.Gdx;
import com.madcoatgames.newpong.nongame.RevolveTextInputer;
import com.madcoatgames.newpong.util.Global;

public class RemoteScore {
	private final String username = "username";
	private final String app_version = "1.2";
	private final int score = 0;
	private final String sector = "RT29";
	private final String score_date = "";
	private final String device = Gdx.app.getType().toString();
	
	public RemoteScore() {
		
		RevolveTextInputer listener = new RevolveTextInputer();
		Gdx.input.getTextInput(listener, "Enter your name", Global.USER_NAME, "Your name");
		
	}
}
