package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.enemy.BasicEnemy;
import com.madcoatgames.newpong.enemy.BasicShootingEnemy;
import com.madcoatgames.newpong.enemy.Enemy;
import com.madcoatgames.newpong.play.Hazard;
import com.madcoatgames.newpong.util.FilledShapeRenderable;

public class EnemyMaster {
	private Array<Enemy> enemies = new Array<Enemy>();
	private Array<Hazard> hazards = new Array<Hazard>();
	
	public EnemyMaster(){
		enemies.add(new BasicShootingEnemy(3));
	}
	
	public void update(float delta){
		for (Enemy e : enemies) {
			e.update(delta);
			if (!e.isDead()){
				if (e.getBrain().isAttackReady()){
					hazards.add(e.getBrain().getHazard());
				}
			}
			if (e.isDead()){
				e.reset();
			}
		}
		for (Hazard hazard : hazards){
			hazard.update(delta);
			if (hazard.isDead()){
				hazards.removeValue(hazard, true);
			}
			// don't forget to remove them when off screen!
			//if (hazard.getCollisionBounds().x < 0 || hazard.getCollisionBounds().x > )
		}
	}

	public Array<Enemy> getEnemies() {
		return enemies;
	}
	public Array<Hazard> getHazards() {
		return hazards;
	}

}
