package com.madcoatgames.newpong.rule;

public class GameStartCountdown {
	private float duration = 3f;
	private float position;
	
	public GameStartCountdown () {
		position = duration;
	}
	public void reset() {
		position = duration;
	}
	public boolean update(float delta) {
		position -= delta;
		return position < 0f;
	}
	public float getPosition() {
		return this.position;
	}
}
