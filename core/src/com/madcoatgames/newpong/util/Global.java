package com.madcoatgames.newpong.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Global {
	public static final int ARCADE = 0;
	public static final int MISSIONS = 1;
	
	private static float width, height;
	private static float nativeWidth, nativeHeight;

	private static int gameMode;
	
	private static float velX = 100f;
	private static float velY = 100f;
	private static float push = 400f;//860f; //720
	
	public static final Color c1 = new Color();
	public static final Color c2 = new Color();
	public static final Color c3 = new Color();
	public static final Color c4 = new Color();
	
	private static boolean canInitialize = true;
	
	public static float electricutedPeriod = 1.2f;

	public static float width() {
		return width;
	}
	public static float height() {
		return height;
	}
	public static float centerWidth() {
		return width/2f;
	}
	public static float centerHeight() {
		return height/2f;
	}
	public static float nativeWidth(){
		return nativeWidth;
	}
	public static float nativeHeight(){
		return nativeHeight;
	}
	public static float gameToNativeX(float gameX){
		return 0;
	}
	public static void initialize() {
		if (!canInitialize) {
			return;
		} else {
			canInitialize = false; //now locked as false forever in this JVM
		}
		nativeWidth = Gdx.graphics.getWidth();
		nativeHeight = Gdx.graphics.getHeight();
		width = 1024;
		height = 576;
		gameMode = ARCADE;
	}
	public static int getGameMode(){
		return gameMode;
	}
	public static void setGameModeArcade(){
		Global.gameMode = Global.ARCADE;
	}
	public static void setGameModeMissions(){
		Global.gameMode = Global.MISSIONS;
	}
	public static Vector2 ballDefaultPos(){
		return new Vector2(centerWidth(), centerHeight());
	}
	public static Vector2 ballDefaultVel(){
		return new Vector2(velX, velY);
	}
	public static float ballDefaultPush(){
		return push;
	}
}
