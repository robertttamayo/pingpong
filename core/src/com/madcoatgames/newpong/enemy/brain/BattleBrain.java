package com.madcoatgames.newpong.enemy.brain;

import com.madcoatgames.newpong.enemy.BasicEnemy;
import com.madcoatgames.newpong.enemy.Enemy;
import com.madcoatgames.newpong.play.Bullet;
import com.madcoatgames.newpong.play.Hazard;
import com.madcoatgames.newpong.play.Star;
import com.madcoatgames.newpong.util.Global;

public class BattleBrain extends Brain{
	
	private Enemy enemy;
	
	private float deadTime = 0;
	private float deadPeriod = 2f;
	
	private float hitTime = 0f;
	private float hitPeriod = 2f;
	
	private float attackTime = 0f;
	private float attackPeriod = 2f;
	private boolean attackReady = false;
	private float attackChargeTime = 0f;
	private float attackChargePeriod = 1f;
	private boolean attackCharging = false;
	
	public BattleBrain(Enemy enemy){
		this.enemy = enemy;
		this.attackPeriod = enemy.getAttackInterval();
	}
	@Override
	public void update(float delta) {
		if (!attackCharging && enemy.getStateTime() > enemy.getDelay()) {
			attackTime += delta;
		}
		if (attackCharging) {
			attackChargeTime += delta;
			if (attackChargeTime >= attackChargePeriod) {
				attackChargeTime = 0f;
				attackCharging = false;
				attackReady = true;
			}
		}
		if (attackTime >= attackPeriod){
			attackTime = 0f;
			attackCharging = true;
		}
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
		enemy.y = (float) (enemy.getOriginY() - enemy.getReversed() * enemy.getTravelY() * Math.sin(enemy.getStateTime()*1f));
	}
	@Override
	public Hazard getHazard() {
		// TODO Auto-generated method stub
//		return new Bullet(enemy.x + enemy.width/2f, enemy.y + enemy.height/2f, enemy.getDir());
		return new Star(enemy.x + enemy.width/2f, enemy.y + enemy.height/2f, enemy.getDir());
	}
	@Override
	public boolean isAttackReady() {
		// TODO Auto-generated method stub
		if (attackReady){
			attackReady = false;
			return true;
		}
		return false;
	}

}
