package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.enemy.BasicShootingEnemy;
import com.madcoatgames.newpong.enemy.Enemy;
import com.madcoatgames.newpong.util.Global;

public class EnemyGroup {

	private Array<Enemy> enemies = new Array<Enemy>();

	public Array<Enemy> getEnemies() {
		return this.enemies;
	}
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}

	public void createRandomEnemyGroup(int difficulty){
//		System.out.println(difficulty);
		int totalMaxCount = 8;
		int adjustment = difficulty / 3;
//		System.out.println("adjustment: " + adjustment);
		int maxHealth = 4 + adjustment;
		int minHealth = 1 + adjustment;
		int maxCount = Math.min(3 + adjustment, totalMaxCount);
		int minCount = Math.min(1 + adjustment, 5);
//		System.out.println("minCount: " + minCount);
//		System.out.println("maxCount: " + maxCount);
		int count = minCount + (int) (Math.random() * (maxCount - minCount) + 1);
//		System.out.println("Creating " + count + " enemies");
		Enemy enemy;
		for (int i = 0; i < count; i++) {
			int health = minHealth + (int) (Math.random() * (maxHealth - minHealth)) + 1;
//			System.out.println("health: " + health);
			float x,y;
			x = Global.width()*.25f + (float)(Math.random() * Global.width()/2f);
			y = Global.height()*.35f + (float)(Math.random() * Global.height()*.3f);
			float travel = (float) (Global.height()*(Math.random()*.35f) + Global.height() * .15f);
			float delay = (float) Math.random() * i;
			float interval = (float) Math.max(Math.random()*2f + 1f - difficulty * .05f, .25f);
			int yDir = Math.random() > .5f ? 1 : -1;
//			System.out.println("x: " + x);
//			System.out.println("y: " + y);
//			System.out.println("yDir: " + yDir);
//			System.out.println("travel: " + travel);
//			System.out.println("delay: " + delay);
//			System.out.println("interval: " + interval);
//			System.out.println("delay: " + delay);
			enemy = new BasicShootingEnemy(health, x, y, travel, delay, interval, yDir);
			enemies.add(enemy);
		}
	}
}
