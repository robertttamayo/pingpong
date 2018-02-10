package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.enemy.Enemy;

public class EnemyGroup {
	private Array<Enemy> enemies = new Array<Enemy>();

	public Array<Enemy> getEnemies() {
		return this.enemies;
	}
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}

}
