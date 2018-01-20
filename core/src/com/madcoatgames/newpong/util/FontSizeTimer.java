package com.madcoatgames.newpong.util;

public class FontSizeTimer {
	private final float cycle = 1f;
	private float timer = 0f;
	private boolean isActive = false;
	
	public boolean update(float delta){
		timer += delta;
		if (timer >= cycle) {
			reset();
			return true;
		} else {
			return false;
		}
	}
	public void setActive(boolean isActive){
		this.isActive = isActive;
	}
	public float getTimer(){
		return timer;
	}
	public float getCycle(){
		return cycle;
	}
	public boolean isActive(){
		return isActive;
	}
	public void reset(){
		timer = 0f;
		isActive = false;
	}

}
