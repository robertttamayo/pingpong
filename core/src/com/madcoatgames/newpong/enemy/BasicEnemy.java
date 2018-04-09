package com.madcoatgames.newpong.enemy;

import com.madcoatgames.newpong.enemy.brain.BasicBrain;
import com.madcoatgames.newpong.enemy.brain.Brain;
import com.madcoatgames.newpong.play.Ball;
import com.madcoatgames.newpong.util.Global;

public class BasicEnemy extends Enemy implements FollowsBall{
	private BasicBrain brain;
	
	private int dir = RIGHT;
	private float renderWidth = 60f;
	private float renderHeight = 60f;
	
	private final int type = EnemyType.UFO_MEDIUM;
	private boolean dead = false;
	private boolean isHit = false;
	
	public boolean electricuted;
	public float electricutedTime = 0;
	public float electricutedPeriod = .45f;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float stateTime = 0;
	private float originY;
	
	private boolean directionChangeDisabled = false;
	
	public BasicEnemy(int maxHealth){
		super(maxHealth);
		health = maxHealth;
		
		
		
		width = 60f;
		height = 60f;
		x = Global.centerWidth() - width/2f;
		y = Global.centerHeight() - height/2f;
		
		setOriginY(y);
		
		brain = new BasicBrain(this);
	}

	@Override
	public void update(float delta) {
		stateTime += delta;
		brain.update(delta);
		updateElectricuted(delta);
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
		if (!directionChangeDisabled) {
			if (ball.getPos().x > x + width/2f){
				dir = RIGHT; 
			} else {
				dir = LEFT;
			}
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
		return null;
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

	@Override
	public float getTravelY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getReversed() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public float getDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getAttackInterval() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateElectricuted(float delta) {
		if (electricuted) {
			electricutedTime -= delta;
			if (electricutedTime <= 0) {
				this.damage();
				this.setHit(true);
			}
		}
	}

	@Override
	public boolean isElectricuted() {
		// TODO Auto-generated method stub
		return this.electricuted;
	}

	@Override
	public void setElectricuted(boolean electricuted) {
		this.electricuted = electricuted;
		this.electricutedTime = this.electricutedPeriod;
	}

	@Override
	public float getElectricutedTime() {
		// TODO Auto-generated method stub
		return this.electricutedTime;
	}
	@Override
	public float getElectricutedPeriod() {
		return this.electricutedPeriod;
	}

	@Override
	public boolean getElectricContact() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setElectricContact(boolean electricContact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disableDirectionChange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableDirectionChange() {
		// TODO Auto-generated method stub
		
	}
}
