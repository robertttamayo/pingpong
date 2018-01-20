package com.madcoatgames.newpong.enemy;

import com.madcoatgames.newpong.enemy.brain.BasicBrain;
import com.madcoatgames.newpong.enemy.brain.BattleBrain;
import com.madcoatgames.newpong.enemy.brain.Brain;
import com.madcoatgames.newpong.play.Ball;
import com.madcoatgames.newpong.util.Global;

public class BasicShootingEnemy extends Enemy implements FollowsBall{
	private BattleBrain brain;
	private int dir = RIGHT;
	private float renderWidth = 120f;
	private float renderHeight = 120f;
	
	private final int type = EnemyType.UFO_MEDIUM;
	private boolean dead = false;
	private boolean isHit = false;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float stateTime = 0;
	private float originY;
	
	public BasicShootingEnemy(int maxHealth){
		super(maxHealth);
		health = maxHealth;
		
		
		
		width = 120f;
		height = 120f;
		x = Global.centerWidth() - width/2f;
		y = Global.centerHeight() - height/2f;
		
		setOriginY(y);
		
		brain = new BattleBrain(this);
	}

	@Override
	public void update(float delta) {
		stateTime += delta;
		brain.update(delta);
		if (dead) {
			//should ignore collision at this point.
			// enemies don't actually 'die', they instead reset their health and respawn.
			// once you have killed enough of them, you win.
		}
	}
	public int getDir(){
		return dir;
	}
	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public float getOriginY() {
		return originY;
	}

	private void setOriginY(float originY) {
		this.originY = originY;
	}

	@Override
	public void setHit(boolean isHit) {
		this.isHit = isHit;
	}
	public void setDead(boolean dead){
		this.dead = dead;
	}
	public boolean isDead(){
		return dead;
	}

	public int getType() {
		return type;
	}

	public float getRenderWidth() {
		return renderWidth;
	}

	public void setRenderWidth(float renderWidth) {
		this.renderWidth = renderWidth;
	}

	public float getRenderHeight() {
		return renderHeight;
	}

	public void setRenderHeight(float renderHeight) {
		this.renderHeight = renderHeight;
	}

	@Override
	public void followBall(Ball ball) {
		if (ball.getPos().x > x + width/2f){
			dir = RIGHT; 
		} else {
			dir = LEFT;
		}
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isHit() {
		// TODO Auto-generated method stub
		return isHit;
	}

	@Override
	public void damage() {
		damage(1);
	}

	@Override
	public void damage(int damage) {
		health -= damage;
		if (health <= 0) {
			health = 0;
			setDead(true);
		}
	}

	@Override
	public Brain getBrain() {
		// TODO Auto-generated method stub
		return brain;
	}


	@Override
	public float getOriginX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void reset() {
		health = maxHealth;
		
	}

}
