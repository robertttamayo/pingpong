package com.madcoatgames.newpong.records;

import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.util.Global;

public class SaveDataCache {
	private static Array<Score> scores;
	private static Array<Score> enemyScores;
	private static int highestScoreThisGameArcade = 0;
	private static int highestScoreThisGameEnemies = 0;
	private static int currentPoints = 0;
	private static String username = "";
	public static SaveData saveData;
	
	private SaveDataCache(){}
	
	public static void init() {
		saveData = SaveDataProcessor.generateScores();
		scores = saveData.scores;
		enemyScores = saveData.enemyScores;
	}
	
	public static void addScore(Score score){
		System.out.println("SaveDataCache::Adding score");
		currentPoints = score.getPoints();
		
		if (score.getType() == Global.ARCADE) {
			saveData.scores.add(score);
			saveData.scores.sort();
			if (currentPoints >= highestScoreThisGameArcade){
				highestScoreThisGameArcade = currentPoints;
			}
		} else {
			saveData.enemyScores.add(score);
			saveData.enemyScores.sort();
			if (currentPoints >= highestScoreThisGameEnemies){
				highestScoreThisGameEnemies = currentPoints;
			}
		}
		while(saveData.scores.size > 10) {
			saveData.scores.removeIndex(0);
		}
		while(saveData.enemyScores.size > 10) {
			saveData.enemyScores.removeIndex(0);
		}
		
		SaveDataProcessor.processToFile();
	}
	public static void setUsername(String _username) {
		username = _username;
	}
	public static Score createScore(int score, String name, int type){
		return new Score(score, name, type);
	}
	public static int getCurrentPoints(int gameMode){
		return currentPoints;
	}
	public static String getHighestString(int gameMode){
		String highest = "";
		
		switch (gameMode) {
		case Global.ARCADE:
			if (saveData.scores.size != 0) {
				highest = String.valueOf(saveData.scores.peek().getPoints());
			} else {
				highest = "0";
			}
			break;
		case Global.MISSIONS:
			if (enemyScores.size != 0) {
				highest = String.valueOf(saveData.enemyScores.peek().getPoints());
			} else {
				highest = "0";
			}
			break;
		default:
			if (saveData.scores.size != 0) {
				highest = String.valueOf(scores.peek().getPoints());
			} else {
				highest = "0";
			}
		}

		return highest;
	}
	public static Array<Score> getScores(){
		return saveData.scores;
	}
	public static Array<Score> getEnemyScores(){
		return saveData.enemyScores;
	}

	public static int getHighestScoreThisGame(int gameMode) {
		int gameModeHighestScoreThisGame = 0;
		switch (gameMode) {
		case Global.ARCADE: 
			gameModeHighestScoreThisGame = highestScoreThisGameArcade;
			break;
		case Global.MISSIONS:
			gameModeHighestScoreThisGame = highestScoreThisGameEnemies;
			break;
		}
		return gameModeHighestScoreThisGame;
	}
	public static String getUsername() {
		return username;
	}
}
