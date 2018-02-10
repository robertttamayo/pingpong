package com.madcoatgames.newpong.powerups;

public abstract class Powerup {
	
	public int duration = 5;
	
	public abstract void update(float delta);
	
}
