package com.madcoatgames.newpong.enemy.brain;

import com.madcoatgames.newpong.play.Hazard;

public abstract class Brain {
	public abstract void update(float delta);
	public abstract Hazard getHazard();
	public abstract boolean isAttackReady();

}
