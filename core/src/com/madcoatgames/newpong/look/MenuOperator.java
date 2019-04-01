package com.madcoatgames.newpong.look;

import com.madcoatgames.newpong.records.SaveData;
import com.madcoatgames.newpong.records.SaveDataCache;
import com.madcoatgames.newpong.records.SaveDataProcessor;
import com.madcoatgames.newpong.records.Score;
import com.madcoatgames.newpong.util.Global;

public class MenuOperator {
	public static final int GAMEOVER = 0;
	public static final int PLAY = 1;
	public static final int OPTIONS = 3;
	public static final int SHUTDOWN = 4;
	public static final int SPLAIN_ARCADE = 4;
	public static final int SPLAIN_BATTLE = 5;
	public static final int GAMEOVER_NO_CONTINUES = 6;
	
	public static boolean failTriggered = true;
	public static boolean countdownEnabled = true;
	
	private static int type = PLAY;
	private static boolean firstGameOver = false;

	
	public static int getType(){
		return type;
	}
	public static void failArcade(int points){
		firstGameOver = true;
		type = GAMEOVER;
		countdownEnabled = true;
		Score score = new Score(points, "name", Global.ARCADE);
		SaveDataCache.addScore(score);
	}
	public static void failMissions(int points){
		firstGameOver = true;
		type = GAMEOVER;
		Score score = new Score(points, "name", Global.MISSIONS);
		SaveDataCache.addScore(score);
	}
	public static void gameOverMissions(int points) {
		firstGameOver = true;
		type = GAMEOVER_NO_CONTINUES;
		Score score = new Score(points, "name", Global.MISSIONS);
		SaveDataCache.addScore(score);
	}
	public static void start(){
		type = PLAY;
	}
	public static boolean firstGameOverCheck() {
		boolean toReturn = firstGameOver;
		firstGameOver = false;
		return toReturn;
	}
	public static void shutdown() {
		type = SHUTDOWN;
	}
}
