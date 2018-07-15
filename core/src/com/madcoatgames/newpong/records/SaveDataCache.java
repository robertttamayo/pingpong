package com.madcoatgames.newpong.records;

import com.badlogic.gdx.utils.Array;

public class SaveDataCache {
	private static Array<Score> scores;
	private static int highestScoreThisGame = 0;
	private static int currentPoints = 0;
	
	public SaveDataCache(){
		scores = new Array<Score>();
		scores.add(new Score(0, "default value"));
		scores.addAll(SaveDataProcessor.generateScores());
		
//		enemyScores = new Array<Score>();
	}
	
	public static void addScore(Score score){
		scores.add(score);
		currentPoints = score.getPoints();
		if (currentPoints >= highestScoreThisGame){
			highestScoreThisGame = currentPoints;
		}
		scores.sort();
	}
	public static void addEnemyScore(Score score){
//		enemyScores.add(score);
//		currentPoints = score.getPoints();
//		if (currentPoints >= highestScoreThisGame){
//			highestScoreThisGame = currentPoints;
//		}
//		scores.sort();
	}
	public static Score createScore(int score, String name){
		return new Score(score, name);
	}
	public static int getCurrentPoints(){
		return currentPoints;
	}
	public static String getHighestString(){
		return String.valueOf(scores.peek().getPoints());
	}
	static Array<Score> getScores(){
		return scores;
	}

	public static int getHighestScoreThisGame() {
		return highestScoreThisGame;
	}
}
