package com.madcoatgames.newpong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.madcoatgames.newpong.nongame.MainScreen;
import com.madcoatgames.newpong.records.SaveData;
import com.madcoatgames.newpong.rule.GameMaster;
import com.madcoatgames.newpong.rule.ScreenMaster;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.StateUpdateable;
import com.madcoatgames.newpong.webutil.ActionResolver;
import com.madcoatgames.newpong.webutil.AdWorker;

public class NewPong extends Game implements StateUpdateable{
	public static final String preferenceTitle = "com.madcoatgames.np.preferences";
	public static final String jsonFile = "savedata";
	public ScreenMaster gm;
	public MainScreen ms;
	private SaveData saveData;
	private ActionResolver actionResolver;
	private AdWorker adWorker;
	
	public NewPong(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
		this.adWorker = new AdWorker();
	}
	@Override
	public void create () {
		System.out.println("NewPong::create() called");
		Global.initialize();
//		saveData = new SaveData();
		//gm = new GameMaster(this);
		ms = new MainScreen(this);
		setScreen(ms);
	}
	public SaveData getSaveData(){
		return null;
	}
	@Override
	public void onGameOver(){
		adWorker.checkIfAdReady(1, Gdx.graphics.getDeltaTime());
	}

}
