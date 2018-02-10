package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.audio.SoundMaster;
import com.madcoatgames.newpong.enemy.Enemy;
import com.madcoatgames.newpong.enemy.FollowsBall;
import com.madcoatgames.newpong.play.Ball;
import com.madcoatgames.newpong.play.CloneBall;
import com.madcoatgames.newpong.powerups.electricity.LightningManager;
import com.madcoatgames.newpong.util.BallUpdateable;
import com.madcoatgames.newpong.util.EnemyUpdateable;

public class EnemyBallMaster implements EnemyUpdateable, BallUpdateable {
	private Array<Enemy> enemies;
	private Ball ball;
	
	public LightningManager lightningManager;
	
	public EnemyBallMaster(Ball ball) {
		lightningManager = new LightningManager(ball);
	}
	
	public void update(Ball ball, Array<Enemy> enemies){
		for (Enemy e : enemies){
			testCollision(ball, e);
			if (e instanceof FollowsBall) {
				((FollowsBall)e).followBall(ball);
			}
		}
		if (lightningManager.getActive()) {
			lightningManager.update(enemies, Gdx.graphics.getDeltaTime());
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
			SoundMaster.acquireq = true;
		}
		
	}
	public void testCloneBallCollisions(Array<CloneBall> cloneBalls, Array<Enemy> enemies) {
		for (int i = 0; i < cloneBalls.size; i++) {
			for (int j = 0; j < enemies.size; j++) {
				testCollision(cloneBalls.get(i), enemies.get(j));
			}
		}
	}

	

	@Override
	public void setUpdateableEnemies(Array<Enemy> enemies) {
		this.enemies = enemies;
	}

	@Override
	public void setUpdateableBall(Ball ball) {
		this.ball = ball;
	}
	
	public LightningManager getLightningManager() {
		return this.lightningManager;
	}

}
