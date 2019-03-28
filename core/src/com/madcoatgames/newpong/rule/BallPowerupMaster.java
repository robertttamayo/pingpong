package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.audio.SoundMaster;
import com.madcoatgames.newpong.play.Ball;
import com.madcoatgames.newpong.play.CloneBall;
import com.madcoatgames.newpong.powerups.bombacity.BombMaster;
import com.madcoatgames.newpong.powerups.electricity.LightningManager;
import com.madcoatgames.newpong.powerups.radial.RadialMaster;
import com.madcoatgames.newpong.powerups.virus.VirusMaster;
import com.madcoatgames.newpong.util.Global;

public class BallPowerupMaster {
	
	private int previousCount = 0;
	private int count = 0;
	private int previousPowerup = -1;
	
	private BallPaddleMaster ballPaddleMaster;
	private BallMaster ballMaster;
	private EnemyBallMaster enemyBallMaster;
	private BombMaster bombMaster;
	private LightningManager lightningManager;
	private VirusMaster virusMaster;
	private RadialMaster radialMaster;
	
	public Type type = Type.ELECTRICITY;
	public PowerLevel powerLevel = PowerLevel.UNCHARGED;
	
	public enum PowerLevel {
		UNCHARGED, LOW, SUPER, ULTRAMEGA
	}
	
	public enum Type {
		ELECTRICITY, DUPLICITY, BOMB, VIRUS, RADIAL
	}
	public BallPowerupMaster(BallPaddleMaster ballPaddleMaster, BallMaster ballMaster, EnemyBallMaster enemyBallMaster) {
		this.ballPaddleMaster = ballPaddleMaster;
		this.ballMaster = ballMaster;
		this.enemyBallMaster = enemyBallMaster;
		this.virusMaster = enemyBallMaster.getVirusMaster();
		this.radialMaster = new RadialMaster();
		this.lightningManager = enemyBallMaster.getLightningManager();
		this.bombMaster = enemyBallMaster.getBombMaster();
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
		if (previousCount != 0 && powerLevel != PowerLevel.UNCHARGED) {
			SoundMaster.losePowerupq = true;
		}
		previousCount = 0;
		count = 0;
		powerLevel = PowerLevel.UNCHARGED;
		lightningManager.setActive(false);
		lightningManager.orbs.clear();
		bombMaster.setActive(false);
		virusMaster.setActive(false);
		ballMaster.getBall().setInfector(false);
	}
	
	public void action(float delta) {
		switch (powerLevel) {
			case UNCHARGED:
				if (count >= 3) { // 3
					if (previousCount == 2) {
						SoundMaster.powerup1q = true;
					}
					int random = 0;
					while (true) {
						random = (int) Math.floor(Math.random() * 4f);
						if (random != previousPowerup) {
							previousPowerup = random;
							break;
						}
					}
					switch (random) {
					case 0: 
						type = Type.DUPLICITY;
						break;
					case 1: 
						type = Type.ELECTRICITY;
						break;
					case 2: 
						type = Type.BOMB;
						break;
					case 3:
						type = Type.VIRUS;
						break;
					case 4:
						type = Type.RADIAL;
						break;
					}
//					type = Type.ELECTRICITY;
					powerLevel = PowerLevel.LOW;
					actionLow(delta);
				}
				break;
			case LOW:
				if (type == Type.DUPLICITY) {
					if (count >= 5) {
						if (previousCount == 4) {
							SoundMaster.powerup2q = true;
						}
						powerLevel = PowerLevel.SUPER;
						actionSuper(delta);
					} else {
						actionLow(delta);
					}
				} else {
					if (count >= 7) { // 7
						if (previousCount == 6) {
							SoundMaster.powerup2q = true;
						}
						powerLevel = PowerLevel.SUPER;
						actionSuper(delta);
					} else {
						actionLow(delta);
					}
				}
				break;
			case SUPER:
				if (type == Type.DUPLICITY) {
					if (count >= 7) {
						if (previousCount == 6) {
							SoundMaster.powerup3q = true;
						}
						powerLevel = PowerLevel.SUPER;
						actionUltraMega(delta);
					} else {
						actionSuper(delta);
					}
				} else {
					if (count >= 12) { // 12
						if (previousCount == 11) {
							SoundMaster.powerup3q = true;
						}
						powerLevel = PowerLevel.ULTRAMEGA;
						actionUltraMega(delta);
					} else {
						actionSuper(delta);
					}
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
			Global.electricutedPeriod = 1f;
			break;
		case BOMB:
			bombMaster.setActive(true);
			bombMaster.statusLevel = 1;
			bombMaster.update(delta);
			break;
		case VIRUS:
			virusMaster.setActive(true);
			virusMaster.statusLevel = 1;
			Global.infectedPeriod = Global.infectedPeriod1;
			Global.infectedLevel = 1;
			ball.setInfector(true);
		}
	}
	public void actionSuper(float delta) {
		switch (type) {
		case DUPLICITY:

			Ball ball = ballMaster.getBall();
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
			}
			if (angle > 150 && angle <= 210) {
				// create 1 ball above and 1 ball below
				float angle1 = angle - 30f;
				float angle2 = angle + 30f;

				cloneBall.getVel().setAngle(angle1);
				cloneBall2.getVel().setAngle(angle2);
			}
			if (angle > 210 && angle <= 270) {
				// create 2 balls above
				float angle1 = angle - 30f;
				float angle2 = angle - 60f;

				cloneBall.getVel().setAngle(angle1);
				cloneBall2.getVel().setAngle(angle2);
			}
			// check right side
			if (angle > 270 && angle <= 330) {
				// create 2 balls below
				float angle1 = angle + 30f;
				float angle2 = angle + 60f;

				cloneBall.getVel().setAngle(angle1);
				cloneBall2.getVel().setAngle(angle2);
			}
			if ( (angle > 330 && angle <= 360) || (angle <= 30) ) {
				// create 2 balls below
				float angle1 = angle + 30f;
				float angle2 = angle + 60f;

				cloneBall.getVel().setAngle(angle1);
				cloneBall2.getVel().setAngle(angle2);
			}
			if (angle > 30 && angle <= 90) {
				// create 2 balls below
				float angle1 = angle + 30f;
				float angle2 = angle + 60f;

				cloneBall.getVel().setAngle(angle1);
				cloneBall2.getVel().setAngle(angle2);
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
			break;
		case ELECTRICITY:
			lightningManager.setActive(true);
			lightningManager.createHeroNormal();
			LightningManager.boltCount = 2;
			Global.electricutedPeriod = .8f;
			break;
		case BOMB:
			bombMaster.setActive(true);
			bombMaster.statusLevel = 2;
			bombMaster.update(delta);
			break;
		case VIRUS:
			virusMaster.setActive(true);
			virusMaster.statusLevel = 2;
			Global.infectedLevel = 2;
			Global.infectedPeriod = Global.infectedPeriod2;
			ballMaster.getBall().setInfector(true);
			break;
		}
	}
	public void actionUltraMega(float delta) {
		switch (type) {
		case DUPLICITY:
			
			Ball ball = ballMaster.getBall();
			
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
			}
			break;
		case ELECTRICITY:
			lightningManager.setActive(true);
			lightningManager.createHeroNormal();
			LightningManager.boltCount = 5;
			Global.electricutedPeriod = .6f;
			break;
		case BOMB:
			bombMaster.setActive(true);
			bombMaster.statusLevel = 3;
			bombMaster.update(delta);
			break;
		case VIRUS:
			virusMaster.setActive(true);
			virusMaster.statusLevel = 3;
			Global.infectedLevel = 3;
			Global.infectedPeriod = Global.infectedPeriod3;
			ballMaster.getBall().setInfector(true);
			break;
		}
	}
	public VirusMaster getVirusMaster() {
		return this.virusMaster;
	}
}
