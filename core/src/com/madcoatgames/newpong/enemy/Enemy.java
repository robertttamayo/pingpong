package com.madcoatgames.newpong.enemy;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.enemy.brain.Brain;
import com.madcoatgames.newpong.play.Hazard;
 
public abstract class Enemy extends Rectangle{
	int RIGHT = 1;
	int LEFT = -1;
	
	int maxHealth;
	int health;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public abstract void update(float delta);

	public abstract int getType();
	public abstract int getDir();
	
	public abstract void setHit(boolean b);
	public abstract boolean isHit();
	public abstract boolean isDead();
	public abstract void setDead(boolean dead);
	
	public abstract float getStateTime();
	
	public abstract float getRenderWidth();
	public abstract float getRenderHeight();
	
	public abstract Brain getBrain();
	
	public abstract void damage();
	public abstract void damage(int damage);
	public abstract int getHealth();
	public abstract int getMaxHealth();
	public abstract void reset();
	
	// position
	public abstract float getOriginY();
	public abstract float getOriginX();
	
	public abstract float getTravelY();
	public abstract float getTravelX();
	public abstract int getReversed();
	public abstract float getDelay();
	public abstract float getAttackInterval();
	
	public abstract void updateElectricuted(float delta);
	public abstract boolean isElectricuted();
	protected abstract void setElectricuted(boolean electricuted);
	public abstract float getElectricutedTime();
	public abstract float getElectricutedPeriod();
	public abstract boolean getElectricContact();
	public abstract void setElectricContact(boolean electricContact);
	
	public abstract void disableDirectionChange();
	public abstract void enableDirectionChange();


	protected Enemy(int maxHealth){
		this.maxHealth = maxHealth;
	}

}
