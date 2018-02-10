package com.madcoatgames.newpong.util;

import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.enemy.Enemy;

public interface EnemyUpdateable {
	
	public void setUpdateableEnemies(Array<Enemy> enemies);

}
