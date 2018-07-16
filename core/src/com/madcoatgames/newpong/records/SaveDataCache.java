package com.madcoatgames.newpong.records;

import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.util.Global;

public class SaveDataCache {
	private static Array<Score> scores;
	private static Array<Score> enemyScores;
	private static int highestScoreThisGameArcade = 0;
	private static int highestScoreThisGameEnemies = 0;
	private static int currentPoints = 0;
	
	public SaveDataCache(){
		scores = new Array<Score>();
		enemyScores = new Array<Score>();
		SaveData saveData = SaveDataProcessor.generateScores();
		scores.addAll(saveData.scores);
		enemyScores.addAll(saveData.enemyScores);
	}
	
	public static void addScore(Score score){
		currentPoints = score.getPoints();
		if (score.getType() == Global.ARCADE) {
			scores.add(score);
			scores.sort();
			if (currentPoints >= highestScoreThisGameArcade){
				highestScoreThisGameArcade = currentPoints;
			}
		} else {
			enemyScores.add(score);
			enemyScores.sort();
			if (currentPoints >= highestScoreThisGameEnemies){
				highestScoreThisGameEnemies = currentPoints;
			}
		}
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
			highest = String.valueOf(scores.peek().getPoints());
			break;
		case Global.MISSIONS:
			highest = String.valueOf(enemyScores.peek().getPoints());
			break;
		default:
			highest = String.valueOf(scores.peek().getPoints());
		}

		return highest;
	}
	static Array<Score> getScores(){
		return scores;
	}
	static Array<Score> getEnemyScores(){
		return enemyScores;
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
}
