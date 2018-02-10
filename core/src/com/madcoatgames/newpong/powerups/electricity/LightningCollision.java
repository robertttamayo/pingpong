package com.madcoatgames.newpong.powerups.electricity;

import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.enemy.Enemy;
import com.madcoatgames.newpong.play.Ball;
import com.madcoatgames.newpong.rule.BattlePaddleMaster;

public class LightningCollision {
	public Ball ball;
	public BattlePaddleMaster battlePaddleMaster;
	
	public LightningManager lightningManager;
	public LightningOrb tOrb;
	public Enemy tEnemy;
	
	private boolean hit;
	
	public LightningCollision (LightningManager lightningManager, Ball ball){
		this.battlePaddleMaster = battlePaddleMaster;
		this.lightningManager = lightningManager;
	}
	
	public void update(int boltCount, Array<Enemy> enemies){
		
		for (int i = 0; i < lightningManager.orbs.size; i++){
			tOrb = lightningManager.orbs.get(i);
			testEnemy(boltCount, tOrb, enemies);
		}
		/*
		for (LightningOrb orb : lightningManager.orbs){
			if (orb.friendly) testEnemy(orb);
			else testHero(orb);
		}
		*/
		
	}
	public void testHero(LightningOrb orb){
//		if (playScreen.hero.bounds.overlaps(orb.bounds)) {
//			//playScreen.lightningManager.orbs.removeValue(orb, true);
//			playScreen.heroHitManager.heroHit();
//			/*
//			 * Sound here
//			 */
//			playScreen.sound.heroHitq = true;
//		}
	}
	public void testEnemy(int boltCount, LightningOrb orb, Array<Enemy> enemies){
		for (int i = 0; i < enemies.size; i++){
			tEnemy = enemies.get(i);
//			if (tEnemy.isElectricuted()) continue;
			if (tEnemy.getElectricContact()) continue;
			hit = false;
			
			if (tEnemy.overlaps(orb.bounds) || orb.bounds.contains(tEnemy)) {
				if (!orb.targetA  && boltCount >= 1 && lightningManager.addToElecs(tEnemy)) {
					orb.targetA = true;
					tEnemy.setElectricContact(true);
					hit = true;
					orb.setEnemyA(tEnemy);
					//lightningManager.elecs.add(tEnemy);
				}
				else if (!orb.targetB && boltCount >= 2 && lightningManager.addToElecs(tEnemy)) {
					orb.targetB = true;
					tEnemy.setElectricContact(true);
					hit = true;
					orb.setEnemyB(tEnemy);
					//lightningManager.elecs.add(tEnemy);
				}
				else if (!orb.targetC && boltCount >= 3 && lightningManager.addToElecs(tEnemy)) {
					orb.targetC = true;
					tEnemy.setElectricContact(true);
					hit = true;
					orb.setEnemyC(tEnemy);
					//lightningManager.elecs.add(tEnemy);
				}
				else if (!orb.targetD && boltCount >= 4 && lightningManager.addToElecs(tEnemy)) {
					orb.targetD = true;
					tEnemy.setElectricContact(true);
					hit = true;
					orb.setEnemyD(tEnemy);
					//lightningManager.elecs.add(tEnemy);
				}
				else if (!orb.targetE && boltCount >= 5 && lightningManager.addToElecs(tEnemy)) {
					orb.targetE = true;
					tEnemy.setElectricContact(true);
					hit = true;
					orb.setEnemyE(tEnemy);
					//lightningManager.elecs.add(tEnemy);
				}
				if (hit) {
//					tEnemy.elecTime = .45f;
//					if (!tEnemy.isHit()){
//						tEnemy.damage();
//						tEnemy.setHit(true);
//					}
				}
			}
		}
	}

}