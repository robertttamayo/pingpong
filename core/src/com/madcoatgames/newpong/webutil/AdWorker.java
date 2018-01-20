package com.madcoatgames.newpong.webutil;

public class AdWorker {
	private float adTimer = 0;
	private int playCount = 0;
	private boolean adReady = false;
	private final float AD_INTERVAL = 30f;
	private final int MIN_PLAY_COUNT = 3;
	
	public boolean checkIfAdReady(int increment, float delta) {
		adTimer += delta;
		playCount += increment;
		if (adTimer >= AD_INTERVAL && playCount >= MIN_PLAY_COUNT) {
			adReady = true;
			adTimer = 0f;
			playCount = 0;
		} else {
			adReady = false;
		}
		return adReady;
	}

}
