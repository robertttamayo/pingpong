package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.enemy.BasicEnemy;
import com.madcoatgames.newpong.enemy.BasicShootingEnemy;
import com.madcoatgames.newpong.enemy.Enemy;
import com.madcoatgames.newpong.play.Hazard;
import com.madcoatgames.newpong.util.FilledShapeRenderable;
import com.madcoatgames.newpong.util.Global;

public class EnemyMaster {
	private Array<Enemy> enemies = new Array<Enemy>();
	private Array<Hazard> hazards = new Array<Hazard>();
	public Color color1 = new Color(), color2 = new Color(), color3 = new Color();
	
	public Array<EnemyGroup> enemyGroups = new Array<EnemyGroup>();
	
	public int groupNumber = 0;
	
	private EnemyRazzleMaster enemyRazzleMaster;
	private EnemyAttackBalancer enemyAttackBalancer;
	
	public static int enemiesDefeated = 0;
	
	public EnemyMaster(){
		enemyRazzleMaster = new EnemyRazzleMaster(this);
		enemyAttackBalancer = new EnemyAttackBalancer();
		
		EnemyGroup firstGroup = new EnemyGroup();
		firstGroup.addEnemy(new BasicShootingEnemy(1, Global.width()*.5f, Global.height()*.5f, Global.height()/8f, 0f, 3f, -1));
		enemyGroups.add(firstGroup);
		
//		firstGroup = new EnemyGroup();
//		firstGroup.addEnemy(new BasicShootingEnemy(2, Global.width()*.5f, Global.height()*.5f, Global.height()/3f, 0f, 3f, -1));
//		enemyGroups.add(firstGroup);
//		
//		EnemyGroup thirdGroup = new EnemyGroup();
//		thirdGroup.addEnemy(new BasicShootingEnemy(2, Global.width()*.45f, Global.height()*.55f, Global.height()/5f, 0f, 3f, -1));
//		thirdGroup.addEnemy(new BasicShootingEnemy(2, Global.width()*.55f, Global.height()*.45f, Global.height()/5f, 0f, 3f, 1));
//		enemyGroups.add(thirdGroup);
//		
//		EnemyGroup secondGroup = new EnemyGroup();
//		secondGroup.addEnemy(new BasicShootingEnemy(2, Global.width()*.5f, Global.height()*.5f, Global.height()/3f, 0f, 3f, -1));
//		secondGroup.addEnemy(new BasicShootingEnemy(2, Global.width()*.35f, Global.height()*.65f, Global.height()/5f, 2f, 3f, 1));
//		secondGroup.addEnemy(new BasicShootingEnemy(2, Global.width()*.65f, Global.height()*.35f, Global.height()/5f, 0f, 3f, -1));
//		enemyGroups.add(secondGroup);
//		
//		secondGroup = new EnemyGroup();
//		secondGroup.addEnemy(new BasicShootingEnemy(3, Global.width()*.5f, Global.height()*.5f, Global.height()/3f, 0f, 3f, 1));
//		secondGroup.addEnemy(new BasicShootingEnemy(3, Global.width()*.35f, Global.height()*.65f, Global.height()/3f, 1f, 3f, -1));
//		secondGroup.addEnemy(new BasicShootingEnemy(3, Global.width()*.65f, Global.height()*.35f, Global.height()/5f, 2f, 3f, 1));
//		enemyGroups.add(secondGroup);
//		
//		secondGroup = new EnemyGroup();
//		secondGroup.addEnemy(new BasicShootingEnemy(3, Global.width()*.45f, Global.height()*.5f, Global.height()/3f, 0f, 3f, 1));
//		secondGroup.addEnemy(new BasicShootingEnemy(3, Global.width()*.3f, Global.height()*.65f, Global.height()/4f, 0f, 3f, -1));
//		secondGroup.addEnemy(new BasicShootingEnemy(3, Global.width()*.6f, Global.height()*.35f, Global.height()/5f, 0f, 3f, 1));
//		secondGroup.addEnemy(new BasicShootingEnemy(3, Global.width()*.7f, Global.height()*.5f, Global.height()/3f, 0f, 3f, -1));
//		enemyGroups.add(secondGroup);
//		
//		secondGroup = new EnemyGroup();
//		secondGroup.addEnemy(new BasicShootingEnemy(3, Global.width()*.45f, Global.height()*.5f, Global.height()/3f, 0f, 1f, 1));
//		secondGroup.addEnemy(new BasicShootingEnemy(3, Global.width()*.3f, Global.height()*.65f, Global.height()/4f, 0f, 2f, -1));
//		secondGroup.addEnemy(new BasicShootingEnemy(5, Global.width()*.55f, Global.height()*.65f, Global.height()/4f, 0f, 1f, -1));
//		secondGroup.addEnemy(new BasicShootingEnemy(3, Global.width()*.62f, Global.height()*.35f, Global.height()/5f, 0f, 2f, 1));
//		secondGroup.addEnemy(new BasicShootingEnemy(3, Global.width()*.75f, Global.height()*.5f, Global.height()/3f, 0f, 2f, -1));
//		enemyGroups.add(secondGroup);
		
		enemies.addAll(enemyGroups.get(groupNumber).getEnemies());
		
	}
	
	public void update(float delta){
		enemyRazzleMaster.update(delta);
		Enemy e;
		for (int i = 0; i < enemies.size; i++) {
			e = enemies.get(i);
			if (!e.isDead()){
				e.update(delta);
				if (e.getBrain().isAttackReady()){
					if (enemyAttackBalancer.testAttack(hazards, e.x + e.height / 2f)) {
						hazards.add(e.getBrain().getHazard());
						System.out.println("Attack is valid");
					} else {
						System.out.println("Attack is invalid");
					}
				}
			}
			if (e.isDead()){
				float x, y;
				x = enemies.get(i).x + enemies.get(i).width / 2f;
				y = enemies.get(i).y + enemies.get(i).height / 2f;
				enemyRazzleMaster.newDazzler(x, y);
				
				enemies.removeIndex(i);
				enemiesDefeated++;
				System.out.println("Enemies defeated: " + enemiesDefeated);
				if (enemies.size <= 0) {
					groupNumber++;
					EnemyGroup group = new EnemyGroup();
					group.createRandomEnemyGroup(groupNumber);
					enemies.addAll(group.getEnemies());
					return;
//					if (groupNumber < enemyGroups.size) {
//						enemies.addAll(enemyGroups.get(groupNumber).getEnemies());
//						return;
//					} else {
//						EnemyGroup group = new EnemyGroup();
//						group.createRandomEnemyGroup(groupNumber);
//						enemies.addAll(group.getEnemies());
//					}
				}
//				e.reset();
			}
		}
		for (Hazard hazard : hazards){
			hazard.setColors(color2, color2, color3);
			hazard.update(delta);
			if (hazard.isDead()){
				hazards.removeValue(hazard, true);
			}
			
			// don't forget to remove them when off screen!
			//if (hazard.getCollisionBounds().x < 0 || hazard.getCollisionBounds().x > )
		}
	}

	public Array<EnemyRazzle> getEnemyRazzles() {
		return enemyRazzleMaster.erd;
	}
	public Array<Enemy> getEnemies() {
		return enemies;
	}
	public Array<Hazard> getHazards() {
		return hazards;
	}

}
