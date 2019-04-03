package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.audio.SoundMaster;
import com.madcoatgames.newpong.play.Hazard;
import com.madcoatgames.newpong.play.Paddle;
import com.madcoatgames.newpong.util.Global;

public class BattlePaddleMaster {
	
	private int playerHealth = 1;
	private final int maxPlayerHealth = 5;
	private boolean playerLose = false;
	
	private boolean isHit = false;
	private float hitTimer = 0f;
	private final float hitCycle = 3f;
	
	public void update(float delta){
		Global.playerHealth = playerHealth;
		if (isHit){
			hitTimer += delta;
			if (hitTimer >= hitCycle){
				hitTimer = 0f;
				isHit = false;
			}
		}
	}
	
	public void testCollision (Array<Hazard> hazards, Array<Paddle> paddles){
		if (isHit) {
			return;
		}
		for(Paddle paddle : paddles) {
			for(Hazard hazard : hazards){
				if (hazard.getCollisionBounds().overlaps(paddle)){
					playerDamage();
					hazards.removeValue(hazard, true);
					BallPaddleMaster.resetHits();
					if (playerHealth <= 0) {
						SoundMaster.heroHitq = true;
					} else {
						SoundMaster.losePowerupq = true;
					}
				}
			}
		}
	}
	private void playerDamage(){
		isHit = true;
		playerHealth--;
		if (playerHealth <= 0){
			playerLose = true;
		}
		Global.playerHealth = playerHealth;
	}
	public boolean isPlayerLose(){
		return playerLose;
	}
	public int getMaxPlayerHealth() {
		return maxPlayerHealth;
	}

	public int getPlayerHealth() {
		return playerHealth;
	}

	public void setPlayerHealth(int playerHealth) {
		this.playerHealth = playerHealth;
	}
	public void reset(){
		isHit = false;
		playerLose = false;
		playerHealth = maxPlayerHealth;
	}
	public boolean isHit(){
		return isHit;
	}
}
