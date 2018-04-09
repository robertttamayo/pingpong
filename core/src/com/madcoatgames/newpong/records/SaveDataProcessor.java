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

public class SaveDataProcessor {
	public static String externalSaveDataPathPrefix = "MadCoatPingPong/gamedata/";
	public static String internalSaveDataPathPrefix = "";

	public static void processToFile(SaveData saveData){
		/* // comment in this line for html builds
		if (Gdx.app.getType() == ApplicationType.WebGL) {
			return;
		}
		Array<Score> scores = SaveDataCache.getScores();
		FileHandle file;
		file = Gdx.files.local(NewPong.jsonFile + ".txt");
		file.delete();
		if (!file.exists()){
			System.out.println("file no exist");
			File maker = file.file();
			try {
				maker.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			file = new FileHandle(maker);
		}
		
		saveData.scores.addAll(scores);
		saveData.scores.sort();
		
		Json json = new Json();
		String saveScores = json.toJson(saveData);
		file.writeString(saveScores, false);
		if (!file.exists()){
			System.out.println("file still no exist");
		} else {
			System.out.println(file.readString());
		}
		System.out.println(file.path());
		*/ // comment in this line for html builds
	}
	public static Array<Score> generateScores(){
		boolean isDesktop = (Gdx.app.getType() == ApplicationType.Desktop);

		Array<Score> scores = new Array<Score>();
		/* // comment in this line for html builds
		SaveData saveData;
		
		Json json = new Json();

		FileHandle file = Gdx.files.local(NewPong.jsonFile + ".txt");
		
		if (!file.exists()){
			File tempFile = file.file();
			try {
				tempFile.createNewFile();
				file = new FileHandle(tempFile);
				SaveData errorData = new SaveData();
				String errorString = json.toJson(errorData); //this will write an empty (default) Json file with no game data
				file.writeString(errorString, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String data = file.readString();
		System.out.println(file.readString());
		saveData = json.fromJson(
				SaveData.class
				, data);
		
		if (saveData.scores != null) {
			scores = saveData.scores;
		} else {
			scores = new Array<Score>();
		}
		/*
		try {
			saveData = json.fromJson(
					SaveData.class
					, file);
			if (saveData.scores != null) {
				scores = saveData.scores;
			} else {
				scores = new Array<Score>();
			}
		} catch (SerializationException e){
			//saveData = new SaveData();
			//json.toJson(saveData, file);
			//SaveData saveData2 = json.fromJson(SaveData.class, file);
			//scores = saveData2.scores;
			//scores.add(new Score(100, "testscore"));
			//scores = new Array<Score>();
			System.out.println("bad file");
			scores = new Array<Score>();
		}
		*/
		
		return scores;
	}

}
