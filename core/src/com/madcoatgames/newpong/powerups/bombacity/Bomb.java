package com.madcoatgames.newpong.powerups.bombacity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Bomb extends Rectangle{
	private float stateTime = 0f;
	private boolean alive = true;
	public float radius = 4f;
	private Array<Satellite> satellites = new Array<Satellite>();
	private float satellitePeriod = 7f;
	private float startingAngle;
	
	private Rectangle bounds = new Rectangle();
	
	public Bomb (float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		bounds.x = this.x + this.width/2f - 18f;
		bounds.y = this.y + this.height/2f - 6f;
		bounds.width = 36f;
		bounds.height = 12f;
		
		this.startingAngle = (float) (Math.random() * Math.PI);
		
		Satellite satellite;
		int count = 3;
		for (int i = 0; i < count; i++) {
			satellite = new Satellite(x + width/2f - 2f, y + height/2f - 2f);
			satellites.add(satellite);
		}
	}
	public void update(int statusLevel, float delta) {
		if (!alive) {
			delta *= .25f;
		}
		stateTime += delta;
		for (int i = 0; i < satellites.size; i++) {
			satellites.get(i).update(this.startingAngle + i*2f*(float)Math.PI/3f, 
					18f, 6f, 
					x + radius/2f, y + radius/2f, 
					delta * (statusLevel / 2f + .5f)
			);
		}
	}
	public boolean isLive() {
		return this.alive;
	}
	public void setLive(boolean live) {
		this.alive = live;
	}
	public Array<Satellite> getSatellites(){
		return this.satellites;
	}
	public Rectangle getBounds() {
		return this.bounds;
	}
}
