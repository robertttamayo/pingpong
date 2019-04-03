package com.madcoatgames.newpong.records;

import java.io.File;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.files.FileHandle; // comment out this line for html builds
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;
import com.madcoatgames.newpong.NewPong;
import com.madcoatgames.newpong.util.Global;

public class SaveDataProcessor {
	public static String externalSaveDataPathPrefix = "MadCoatPingPong/gamedata/";
	public static String internalSaveDataPathPrefix = "";

	private SaveDataProcessor() {}
	
	public static void eraseScoreData() {
//		System.out.println("SaveDataProcessor::Erasing score data");
		SaveData restartData = new SaveData();
		restartData.username = Global.USER_NAME;
		
		FileHandle file;
		file = Gdx.files.local(NewPong.jsonFile + ".txt");
		file.delete();
		if (!file.exists()){
//			System.out.println("SaveDataProcessor::file has been erased");
			File maker = file.file();
			try {
				maker.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			file = new FileHandle(maker);
		}
		Json json = new Json();
		String saveScores = json.toJson(restartData, SaveData.class);
		file.writeString(saveScores, false);
		if (!file.exists()){
//			System.out.println("SaveDataProcessor::unable to write file");
		} else {
//			System.out.println("SaveDataProcessor::file written successfully");
		}
	}
	public static void processToFile(){
//		System.out.println("SaveDataProcessor::Writing to file");
		SaveData saveData = SaveDataCache.saveData;
//		/* // comment out this line for non-html builds
		if (Gdx.app.getType() == ApplicationType.WebGL) {
			return;
		}
		Array<Score> scores = new Array<Score>();
//		System.out.println("SaveDataProcessor::Save data cache size of scores: " + SaveDataCache.getScores().size);
		scores.addAll(SaveDataCache.getScores());
		
		Array<Score> enemyScores = new Array<Score>();
//		System.out.println("SaveDataProcessor::Save data cache size of enemyscores: " + SaveDataCache.getEnemyScores().size);
		enemyScores.addAll(SaveDataCache.getEnemyScores());
		
		String username = SaveDataCache.getUsername();
		SaveDataCache.getScores().clear();
		SaveDataCache.getEnemyScores().clear();
		
		FileHandle file;
		file = Gdx.files.local(NewPong.jsonFile + ".txt");
		file.delete();
		if (!file.exists()){
//			System.out.println("SaveDataProcessor::file deleted successfully");
			File maker = file.file();
			try {
				maker.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			file = new FileHandle(maker);
		}
		
		Array<Score> badScores = new Array<Score>();
		for (Score score : scores) {
			if (score.getName().equals("default value")) {
				badScores.add(score);
			} else if (score.getPoints() == 0) {
				badScores.add(score);
			}
		}
		scores.removeAll(badScores, true);
		
		saveData.scores.addAll(scores);
		saveData.scores.sort();
		
		Array<Score> enemyBadScores = new Array<Score>();
		for (Score score : enemyScores) {
			if (score.getName().equals("default value")) {
				enemyBadScores.add(score);
			} else if (score.getPoints() == 0) {
				enemyBadScores.add(score);
			}
		}
		enemyScores.removeAll(enemyBadScores, true);

		saveData.enemyScores.addAll(enemyScores);
		saveData.enemyScores.sort();
		
		if (!Global.USER_NAME.equals("")) {
			saveData.username = Global.USER_NAME;
		}
		
		Json json = new Json();
		String saveScores = json.toJson(saveData, SaveData.class);
		file.writeString(saveScores, false);
		if (!file.exists()){
//			System.out.println("SaveDataProcessor::unable to write file");
		} else {
//			System.out.println("SaveDataProcessor::file written successfully");
		}
//		System.out.println(file.path());
//		System.out.println(json.prettyPrint(saveData));
//		*/ // comment out this line for non-html builds
	}
	public static void writeUsername(String username) {
		processToFile();
	}
	public static SaveData generateScores(){
//		System.out.println("SaveDataProcessor::generating scores");
		boolean isDesktop = (Gdx.app.getType() == ApplicationType.Desktop);

		Array<Score> scores = new Array<Score>();
		Array<Score> enemyScores = new Array<Score>();
		String username = "";
		SaveData saveData;
		if (Gdx.app.getType() == ApplicationType.WebGL) {
			saveData = new SaveData();
		} else {
			saveData = new SaveData();
		}
//		/* // comment out this line for non-html builds
		
		Json json = new Json();

		FileHandle file = Gdx.files.local(NewPong.jsonFile + ".txt");
		
		if (!file.exists()){
			File tempFile = file.file();
			try {
				tempFile.createNewFile();
				file = new FileHandle(tempFile);
				SaveData errorData = new SaveData();
				String errorString = json.toJson(errorData, SaveData.class); //this will write an empty (default) Json file with no game data
				file.writeString(errorString, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String data = file.readString();
//		System.out.println(file.readString());
		saveData = json.fromJson(
				SaveData.class
				, data);

		if (saveData == null) {
			saveData = new SaveData();
			saveData.scores = new Array<Score>();
			saveData.enemyScores = new Array<Score>();
			saveData.username = Global.USER_NAME;
		} else {
			if (saveData.scores != null) {
//				System.out.println("SaveDataProcessor::Scores size: " + saveData.scores.size);
				while(saveData.scores.size > 10) { // fix for bug in earlier release
					saveData.scores.removeIndex(0);
				}
				scores = saveData.scores;
			} else {
				scores = new Array<Score>();
			}
			if (saveData.enemyScores != null) {
//				System.out.println("SaveDataProcessor::Enemy Scores size: " + saveData.enemyScores.size);
				while(saveData.enemyScores.size > 10) {
					saveData.enemyScores.removeIndex(0);
				}
				enemyScores = saveData.enemyScores;
			} else {
				enemyScores = new Array<Score>();
			}
			if (!saveData.username.equals("")) {
				username = saveData.username;
				Global.USER_NAME = saveData.username;
			}
		}
		//*/
		return saveData;
	}

}
