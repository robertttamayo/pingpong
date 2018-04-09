package com.madcoatgames.newpong.rule;

import java.util.Comparator;

import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.enemy.Enemy;
import com.madcoatgames.newpong.play.Hazard;
import com.madcoatgames.newpong.util.Global;

public class EnemyAttackBalancer {
	private float minAttackInterval = 2f; // at most one attack per x seconds
	private boolean attackSent = false;
	private float attackTime = 0f;
	
	private float bufferHeight = 200f;
	private float totalHeight = Global.height();
	
	private Array<Float> yValues = new Array<Float>();
	
	public EnemyAttackBalancer() {
		
	}
	public void update(float delta) {
		
	}
	public boolean testAttack(Array<Hazard> hazards, float prospectY) {
		yValues.clear();
		// organize enemies from highest y to lowest y
		boolean attackValid = true;
		
		yValues.add(prospectY);
		for (int i = 0; i < hazards.size; i++) {
			yValues.add(hazards.get(i).getCollisionBounds().x + hazards.get(i).getCollisionBounds().height);
		}
		
		yValues.sort();
		float currentY = totalHeight;
		for (int i = 0; i < yValues.size; i++) {
			if (currentY - bufferHeight > yValues.get(i)) {
				attackValid = true;
				break;
			}
		}
		
		return attackValid;
	}
}
