package com.madcoatgames.newpong.records;

import com.badlogic.gdx.utils.Array;

public class SaveData {
	public SaveData(){}
	
	Array<Score> scores = new Array<Score>();
	Array<Score> enemyScores = new Array<Score>();
	String username = "";
}

