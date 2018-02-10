package com.madcoatgames.newpong.enemy.brain;

import com.madcoatgames.newpong.enemy.BasicEnemy;
import com.madcoatgames.newpong.play.Hazard;
import com.madcoatgames.newpong.util.Global;

public class BasicBrain extends Brain{
	
	BasicEnemy enemy;
	
	private float deadTime = 0;
	private float deadPeriod = 2f;
	
	private float hitTime = 0f;
	private float hitPeriod = 2f;
	
	private boolean attackReady = false;
	
	public BasicBrain(BasicEnemy enemy){
		this.enemy = enemy;
	}
	@Override
	public void update(float delta) {
		
		if (enemy.isDead()) {
			deadTime += delta;
			if (deadTime > deadPeriod){
				deadTime = 0f;
				enemy.setDead(false);
			}
		}
		if (enemy.isHit()) {
			hitTime += delta;
			if (hitTime > hitPeriod){
				hitTime = 0f;
				enemy.setHit(false);
			}
		}
		enemy.y = (float) (enemy.getOriginY() - Global.height()/4f*Math.sin(enemy.getStateTime()*1f));
	}
	@Override
	public Hazard getHazard() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isAttackReady() {
		// TODO Auto-generated method stub
		return attackReady;
	}

}
