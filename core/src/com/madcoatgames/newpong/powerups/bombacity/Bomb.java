package com.madcoatgames.newpong.powerups.bombacity;

import com.badlogic.gdx.math.Rectangle;

public class Bomb extends Rectangle{
	private float stateTime = 0f;
	public boolean alive = true;
	
	public Bomb (float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public void update(float delta) {
		stateTime += delta;
	}
	
}
