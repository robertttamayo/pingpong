package com.madcoatgames.newpong.audio;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class MusicMaster implements Disposable{
	public enum Track {
		MENU, PLAY, ALT
	}
	
	private String track;
	private Music music;
	
	private float fadeInterval = 2f;
	private float fadeTimer = 0f;
	private boolean changingTracks = false;
	private float volume = 0f;
	
	private Track nextTrack;
	
//	public MusicMaster (){
//		track = "Exist in Sound Boy Girl.mp3";
//		music = Gdx.audio.newMusic(Gdx.files.internal("music/" + track));
//		music.setLooping(true);
//		music.play();
//	}
	public MusicMaster (Track track){
		String trackName = getTrackName(track);
		music = Gdx.audio.newMusic(Gdx.files.internal("music/" + trackName));
		music.setLooping(true);
		music.play();
	}
	public String getTrack() {
		return track;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	public Music getMusic() {
		return music;
	}
	public void setMusic(Music music) {
		this.music = music;
	}
	@Override
	public void dispose() {
		music.dispose();
	}
	public void stopAndLoadNewTrack(Track track) {
		this.nextTrack = track;
		this.changingTracks = true;
	}
	public void update(float delta) {
		if (this.changingTracks) {
			System.out.println("changing tracks");
			fadeTimer += delta;
			if (fadeTimer >= fadeInterval) {
				fadeTimer = 0f;
				changingTracks = false;
				// start playing new track
				music.stop();
				music.dispose();
				String trackName = getTrackName(nextTrack);
				System.out.println("trackName: " + trackName);
				music = Gdx.audio.newMusic(Gdx.files.internal("music/" + trackName));
				music.play();
			} else {
				volume = (fadeInterval - fadeTimer) / (fadeInterval);
				volume = Math.min(1f, Math.max(0f, volume));
				music.setVolume(volume);
			}
		}
	}
	private String getTrackName(Track track) {
		String trackName = "";
		switch(track) {
		case MENU: 
			trackName = "menu.mp3";
			break;
		case PLAY: 
			trackName = "game.mp3";
			break;
		default: ;
		}
		return trackName;
	}

}
