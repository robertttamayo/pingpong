package com.madcoatgames.newpong.powerups.bombacity;

import com.badlogic.gdx.math.Vector2;

public class Satellite {
	private Bomb bomb;
	public float x, y;
	public float radius = 2f;
	private float stateTime = 0f;
	private Vector2 pos = new Vector2();
	private Vector2 origin = new Vector2();

	public float angle = 0f;
	
	public Satellite(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
		System.out.println("stateTime: " + stateTime);
	}
	public void update(float angle, float width, float height, float originX, float originY, float delta) {
		stateTime += delta;
		this.angle = angle;
		x = originX + (float) (width * Math.cos(7f*stateTime));
		y = originY - (float) (height * Math.sin(7f*stateTime));
		pos.x = x;
		pos.y = y;
		origin.x = originX;
		origin.y = originY;
		pos.sub(origin);
		pos.rotateRad(angle);
		pos.add(origin);
		x = pos.x;
		y = pos.y;
	}
}
