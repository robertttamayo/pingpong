package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.audio.SoundMaster;
import com.madcoatgames.newpong.enemy.Enemy;
import com.madcoatgames.newpong.enemy.FollowsBall;
import com.madcoatgames.newpong.play.Ball;

public class EnemyBallMaster {
	public void update(Ball ball, Array<Enemy> enemies){
		for (Enemy e : enemies){
			testCollision(ball, e);
			if (e instanceof FollowsBall) {
				((FollowsBall)e).followBall(ball);
			}
		}
	}
	public void testCollision(Ball ball, Enemy enemy){
		if (!ball.isLive() || enemy.isDead() || enemy.isHit()){
			return;
		}
		if (ball.getBounds().overlaps(enemy)){
			if (!enemy.isHit()){
				enemy.damage();
			}
			enemy.setHit(true);
			SoundMaster.enemyDeadq = true;
		}
		
	}

}
