package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.play.Ball;
import com.madcoatgames.newpong.play.CloneBall;
import com.madcoatgames.newpong.powerups.electricity.LightningManager;
import com.madcoatgames.newpong.util.Global;

public class BallPowerupMaster {
	private LightningManager lightningManager;
	
	private int previousCount = 0;
	private int count = 0;
	
	private BallPaddleMaster ballPaddleMaster;
	private BallMaster ballMaster;
	private EnemyBallMaster enemyBallMaster;
	
	public Type type = Type.ELECTRICITY;
	public PowerLevel powerLevel = PowerLevel.UNCHARGED;
	
	public enum PowerLevel {
		UNCHARGED, LOW, SUPER, ULTRAMEGA
	}
	
	public enum Type {
		ELECTRICITY, DUPLICITY, BOMB
	}
	public BallPowerupMaster(BallPaddleMaster ballPaddleMaster, BallMaster ballMaster, EnemyBallMaster enemyBallMaster) {
		this.ballPaddleMaster = ballPaddleMaster;
		this.ballMaster = ballMaster;
		this.enemyBallMaster = enemyBallMaster;
		this.lightningManager = enemyBallMaster.getLightningManager();
	}
	
	public void update(float delta) {
		if (BallPaddleMaster.getNumHits() == 0) {
			reset();
		}
		count = BallPaddleMaster.getNumHits();
		
		if (count > previousCount) {
			action(delta);
		}
		
		previousCount = BallPaddleMaster.getNumHits();
	}
	public void reset() {
		previousCount = 0;
		count = 0;
		powerLevel = PowerLevel.UNCHARGED;
		lightningManager.setActive(false);
		lightningManager.orbs.clear();
	}
	
	public void action(float delta) {
		switch (powerLevel) {
			case UNCHARGED:
				if (count >= 3) {
					float random = (float)Math.random();
					if (random > .5f) {
						type = Type.DUPLICITY;
					} else {
						type = Type.ELECTRICITY;
					}
					powerLevel = PowerLevel.LOW;
					actionLow(delta);
				}
				break;
			case LOW:
				if (count >= 7) { // 10
					powerLevel = PowerLevel.SUPER;
					actionSuper(delta);
				} else {
					actionLow(delta);
				}
				break;
			case SUPER:
				if (count >= 12) { // 22
					powerLevel = PowerLevel.ULTRAMEGA;
					actionUltraMega(delta);
				} else {
					actionSuper(delta);
				}
				break;
			case ULTRAMEGA:
				actionUltraMega(delta);
				break;
		}
	}
	public void actionLow(float delta) {
		Ball ball = ballMaster.getBall();
		switch (type) {
		case DUPLICITY:
			CloneBall cloneBall = new CloneBall();
//			cloneBall.getVel().x = ballMaster.getBall().getVel().x;
//			cloneBall.getVel().y = -1f * ball.getVel().y;
			cloneBall.getVel().x = ball.getVel().x;
			cloneBall.getVel().y = ball.getVel().y;
			if (ball.getVel().y >= 0) {
				if (ball.getVel().x >= 0) {
					cloneBall.getVel().setAngle(ball.getVel().angle() - 30f);
				} else {
					cloneBall.getVel().setAngle(ball.getVel().angle() + 30f);
				}
			} else {
				if (ball.getVel().x >= 0) {
					cloneBall.getVel().setAngle(ball.getVel().angle() + 30f);
				} else {
					cloneBall.getVel().setAngle(ball.getVel().angle() - 30f);
				}
			}
			cloneBall.setPos(new Vector2(ball.getPos().x, ball.getPos().y));
			cloneBall.setBounds(ball.getBounds().x, ball.getBounds().y, ball.getBounds().width, ball.getBounds().height);
			cloneBall.setPush(ball.getPush());
			cloneBall.setLive(true);
			ballMaster.addCloneBall(cloneBall);
			break;
		case ELECTRICITY:
			lightningManager.setActive(true);
			lightningManager.createHeroNormal();
			LightningManager.boltCount = 1;
			Global.electricutedPeriod = 1.2f;
			break;
		}
	}
	public void actionSuper(float delta) {
		switch (type) {
		case DUPLICITY:
			System.out.println("DUPLICITY IS CHARGED TO SUPER LEVEL!");
			
			Ball ball = ballMaster.getBall();
			System.out.println("angle: " + ball.getVel().angle());
			CloneBall cloneBall = new CloneBall();
			CloneBall cloneBall2 = new CloneBall();
			cloneBall.setVel(new Vector2(ball.getVel().x, ball.getVel().y));
			cloneBall2.setVel(new Vector2(ball.getVel().x, ball.getVel().y));
			float angle = cloneBall.getVel().angle();
			//check left side
			if (angle > 90 && angle <= 150) {
				// create 2 balls below
				float angle1 = angle + 30f;
				float angle2 = angle + 60f;
				if (angle2 > 255f) {
					angle2 = 255f;
				}
				cloneBall.getVel().setAngle(angle1);
				cloneBall2.getVel().setAngle(angle2);
				System.out.println("Angle, Angle1, Angle2: " + angle + ", " + angle1 + ", " + angle2);
			}
			if (angle > 150 && angle <= 210) {
				// create 1 ball above and 1 ball below
				float angle1 = angle - 30f;
				float angle2 = angle + 30f;

				cloneBall.getVel().setAngle(angle1);
				cloneBall2.getVel().setAngle(angle2);
				System.out.println("Angle, Angle1, Angle2: " + angle + ", " + angle1 + ", " + angle2);
			}
			if (angle > 210 && angle <= 270) {
				// create 2 balls above
				float angle1 = angle - 30f;
				float angle2 = angle - 60f;

				cloneBall.getVel().setAngle(angle1);
				cloneBall2.getVel().setAngle(angle2);
				System.out.println("Angle, Angle1, Angle2: " + angle + ", " + angle1 + ", " + angle2);
			}
			// check right side
			if (angle > 270 && angle <= 330) {
				// create 2 balls below
				float angle1 = angle + 30f;
				float angle2 = angle + 60f;

				cloneBall.getVel().setAngle(angle1);
				cloneBall2.getVel().setAngle(angle2);
				System.out.println("Angle, Angle1, Angle2: " + angle + ", " + angle1 + ", " + angle2);
			}
			if ( (angle > 330 && angle <= 360) || (angle <= 30) ) {
				// create 2 balls below
				float angle1 = angle + 30f;
				float angle2 = angle + 60f;

				cloneBall.getVel().setAngle(angle1);
				cloneBall2.getVel().setAngle(angle2);
				System.out.println("Angle, Angle1, Angle2: " + angle + ", " + angle1 + ", " + angle2);
			}
			if (angle > 30 && angle <= 90) {
				// create 2 balls below
				float angle1 = angle + 30f;
				float angle2 = angle + 60f;

				cloneBall.getVel().setAngle(angle1);
				cloneBall2.getVel().setAngle(angle2);
				System.out.println("Angle, Angle1, Angle2: " + angle + ", " + angle1 + ", " + angle2);
			}
			cloneBall.setPos(new Vector2(ball.getPos().x, ball.getPos().y));
			cloneBall.setBounds(ball.getBounds().x, ball.getBounds().y, ball.getBounds().width, ball.getBounds().height);
			cloneBall.setPush(ball.getPush());
			cloneBall.setLive(true);
			cloneBall2.setPos(new Vector2(ball.getPos().x, ball.getPos().y));
			cloneBall2.setBounds(ball.getBounds().x, ball.getBounds().y, ball.getBounds().width, ball.getBounds().height);
			cloneBall2.setPush(ball.getPush());
			cloneBall2.setLive(true);
			ballMaster.addCloneBall(cloneBall);
			ballMaster.addCloneBall(cloneBall2);
//			System.out.println("Clone Ball vel x: " + cloneBall.getVel().x);
//			System.out.println("Clone Ball vel y: " + cloneBall.getVel().y);
			break;
		case ELECTRICITY:
			lightningManager.setActive(true);
			lightningManager.createHeroNormal();
			LightningManager.boltCount = 2;
			Global.electricutedPeriod = 1f;
			break;
		}
	}
	public void actionUltraMega(float delta) {
		switch (type) {
		case DUPLICITY:
			System.out.println("DUPLICITY IS CHARGED TO SUPER LEVEL!");
			
			Ball ball = ballMaster.getBall();
			System.out.println("angle: " + ball.getVel().angle());
			
			float angle = ball.getVel().angle();
			float _angle = angle;
			int dir = 1;
			for (int i = 1; i <= 4; i++) {
				dir *= -1;
				if (angle > 90 && angle <= 270f) {
					if (angle + 20f * i * dir > 270f) {
						dir *= -1;
					}
				} else {
					float test = angle + 20f * i * dir;
					if (test > 90f ) {
						dir *= -1;
					}
				}
				
				_angle = angle + 20f * i * dir;
				CloneBall cloneBall = new CloneBall();
				
				cloneBall.setVel(new Vector2(ball.getVel().x, ball.getVel().y));
				cloneBall.getVel().setAngle(_angle);
				cloneBall.setPos(new Vector2(ball.getPos().x, ball.getPos().y));
				cloneBall.setBounds(ball.getBounds().x, ball.getBounds().y, ball.getBounds().width, ball.getBounds().height);
				cloneBall.setPush(ball.getPush());
				cloneBall.setLive(true);
				
				ballMaster.addCloneBall(cloneBall);
				System.out.println("_angle" + i + ": " + _angle);
			}
			System.out.println("angle: " + angle);
			break;
		case ELECTRICITY:
			lightningManager.setActive(true);
			lightningManager.createHeroNormal();
			LightningManager.boltCount = 5;
			Global.electricutedPeriod = .8f;
			break;
		}
	}

}
