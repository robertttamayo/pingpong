package com.madcoatgames.newpong.util;

public class HitTimer {
	
	private float timerHit = 0;
	private boolean hit = false;
	private float totalTimeHit;
	
	public HitTimer(){
		totalTimeHit = .4f;
	}
	public HitTimer(float totalTimeHit){
		this.totalTimeHit = totalTimeHit;
	}
	public boolean update(float delta){
		if (hit) {
			timerHit += delta;
			if (timerHit > totalTimeHit){
				hit = false;
				timerHit = 0;
			}
		}
		return hit;
	}
	public boolean isHit(){
		return hit;
	}
	public void setHit(boolean hit){
		this.hit = hit;
	}

}
