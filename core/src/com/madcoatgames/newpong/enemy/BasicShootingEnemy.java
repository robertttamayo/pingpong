package com.madcoatgames.newpong.enemy;

import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.audio.SoundMaster;
import com.madcoatgames.newpong.enemy.brain.BasicBrain;
import com.madcoatgames.newpong.enemy.brain.BattleBrain;
import com.madcoatgames.newpong.enemy.brain.Brain;
import com.madcoatgames.newpong.play.Ball;
import com.madcoatgames.newpong.play.Hazard;
import com.madcoatgames.newpong.util.Global;

public class BasicShootingEnemy extends Enemy implements FollowsBall{
	private BattleBrain brain;
	private int dir = RIGHT;
	private float renderWidth = 80f;
	private float renderHeight = 80f;
	
	private final int type = EnemyType.UFO_MEDIUM;
	private boolean dead = false;
	
	public boolean isDying = false;
	public float dieTime = 0f;
	public float diePeriod = 1f;
	
	private boolean isHit = false;
	
	public boolean electricuted;
	public float electricutedTime = 0;
	public float electricutedPeriod = Global.electricutedPeriod; //.45f
	public boolean electricContact = false;
	
	private float travelY = Global.height()/3f;
	private float travelX = Global.width()/20f;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float stateTime = 0;
	private float originY;
	private float originX;
	
	private float delay = 0;
	private int yDir = 1;
	
	private float attackInterval = 5f;
	
	private boolean directionChangeDisabled = false;
	
	private boolean angry = false;
	private float angryTime = 15f;
	
	public BasicShootingEnemy(int maxHealth){
		super(maxHealth);
		health = maxHealth;
		
		width = 80f;
		height = 80f;
		x = Global.centerWidth() - width/2f;
		y = Global.centerHeight() - height/2f;
		
		setOriginY(y);
		setOriginX(x);
		
		travelX *= Math.random() > .5f ? 1 : -1;
		
		brain = new BattleBrain(this);
	}
	public BasicShootingEnemy(int maxHealth, float x, float y, float travelY, float delay, float interval, int yDir) {
		this(maxHealth);
		this.x = x;
		this.y = y;
		setOriginY(y);
		setOriginX(x);
		this.travelY = travelY;
		this.delay = delay;
		this.yDir = yDir;
		this.attackInterval = interval;
	}

	@Override
	public boolean isAngry() {
		return this.angry;
	}
	@Override
	public void update(float delta) {
		stateTime += delta;
		brain.update(delta);
		updateElectricuted(delta);
//		if (stateTime >= angryTime) {
//			if (!this.angry) {
//				this.width += 20f;
//				this.height += 20f;
//				this.originX -= 10f;
//				this.originY -= 10f;
//			}
//			this.angry = true;
//		}
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
	
	private void setOriginX(float originX) {
		this.originX = originX;
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
			SoundMaster.enemyDeadq = true;
		} else {
//			SoundMaster.enemyHitq = true;
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
		return this.originX;
	}

	@Override
	public void reset() {
		health = maxHealth;
		
	}
	public float getTravelY() {
		return this.travelY;
	}
	@Override
	public int getReversed() {
		// TODO Auto-generated method stub
		return this.yDir;
	}
	@Override
	public float getDelay() {
		// TODO Auto-generated method stub
		return this.delay;
	}
	public float getAttackInterval() {
		return this.attackInterval;
	}
	@Override
	public void updateElectricuted(float delta) {
		if (electricuted) {
			if (electricContact) {
				electricutedTime -= delta;
			} else {
				electricutedTime += delta*.1f;
			}
			if (electricutedTime <= 0) {
				this.damage();
				this.setHit(true);
				electricuted = false;
				electricContact = false;
			} else if (electricutedTime > Global.electricutedPeriod) {
				electricutedTime = Global.electricutedPeriod;
				electricuted = false;
				electricContact = false;
			}
		}
	}
	@Override
	public boolean isElectricuted() {
		return this.electricuted;
	}
	@Override
	public void setElectricuted(boolean electricuted) {
		if (electricuted) {
			if (!this.electricuted) {
				this.electricutedTime = Global.electricutedPeriod;
			}
			this.electricuted = electricuted;
		}
	}
	@Override
	public float getElectricutedTime() {
		return this.electricutedTime;
	}
	@Override
	public float getElectricutedPeriod() {
		return Global.electricutedPeriod;
	}
	public boolean getElectricContact() {
		return this.electricContact;
	}
	public void setElectricContact(boolean electricContact) {
		this.electricContact = electricContact;
		if (electricContact) {
			setElectricuted(true);
		}
	}
	@Override
	public void disableDirectionChange() {
		this.directionChangeDisabled = true;
	}
	@Override
	public void enableDirectionChange() {
		this.directionChangeDisabled = false;
		
	}
	@Override
	public float getTravelX() {
		return this.travelX;
	}

}
