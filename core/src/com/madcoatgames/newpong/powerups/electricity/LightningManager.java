package com.madcoatgames.newpong.powerups.electricity;

import com.badlogic.gdx.Gdx;		
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.audio.SoundMaster;
import com.madcoatgames.newpong.enemy.Enemy;
import com.madcoatgames.newpong.play.Ball;
import com.madcoatgames.newpong.rule.BattlePaddleMaster;


public class LightningManager {
	public Ball ball;
	public static int boltCount = 1;
	
	public Array<LightningBolt> bolts = new Array<LightningBolt>();
	public Array<LightningOrb> orbs = new Array<LightningOrb>();
	public OrthographicCamera cam;
	public float stateTime = 0;
	public boolean acquired = false;
	public Vector2 target = new Vector2();
	public float targetWidth, targetHeight;
	public LightningOrb nOrb;
	public LightningCollision lightningCollision;
	public Array<Enemy> elecs = new Array<Enemy>();
	public Enemy de; //for draw enemy
	
	public int dmg = 2;
	public float offx, offy;
	
	private boolean active = false;
	
	public LightningManager(Ball ball){
		this.ball = ball;
		lightningCollision = new LightningCollision(this, ball);
	}
	
	public void createHeroNormal(){
		if (orbs.size >= 1) {
			return;
		}
		nOrb = new LightningOrb(ball.getPos().x, ball.getPos().y
				, 1, LightningOrb.NORMAL, true);
		if (acquired) {
			nOrb.setTarget(target.x, target.y);
			nOrb.acquired = true;
		}
		/*
		 * Sound here
		 */
		
		SoundMaster.elecq = true;
		
		orbs.add(nOrb);
		System.out.println("orbs: " + orbs.size);
	}
	public void update(Array<Enemy> enemies, float delta){
//		if (playScreen.heroMaster.heroMoveManager.weapon != Weapon.LIGHTNING) {
//			orbs.clear();
//			return;
//		}
		if (!active) {
			orbs.clear();
			return;
		}
		
		for(LightningOrb orb : orbs){
			orb.pos.set(ball.getPos().x, ball.getPos().y); 
			
//			if (Math.abs(orb.pos.x - orb.center.x) > 550) {
//				if (orb.enemyA != null) orb.enemyA.setElectricuted(false);
//				if (orb.enemyB != null) orb.enemyB.setElectricuted(false);
//				if (orb.enemyC != null) orb.enemyC.setElectricuted(false);
//				if (orb.enemyD != null) orb.enemyD.setElectricuted(false);
//				if (orb.enemyE != null) orb.enemyE.setElectricuted(false);
//				orbs.removeValue(orb, true);
//			}
		}
//		render(shaper, delta);
		updateElecs();
		lightningCollision.update(boltCount, enemies);
		
		
		
	}
	public void setAcquired(boolean acquired){
		this.acquired = acquired;
	}
	public void render(ShapeRenderer shaper, float delta){
//		shaper.setProjectionMatrix(cam.combined);
		renderOrb(shaper, delta);
//		renderTargetCircle(shaper, delta);
	}
	public void renderOrb(ShapeRenderer shaper, float delta){
//		shaper.begin(ShapeType.);
		
		for(LightningOrb orb : orbs){
				orb.update(ball.getPos(), delta);
//				orb.rotate(delta);
				orb.render(shaper);
		}
		
//		shaper.end();
	}
	public void renderTargetCircle(Array<Enemy> enemies, ShapeRenderer shaper, float delta){
		Enemy enemy;
		float radius;
		float start;
		float degrees;
		// render the charge time for the electric attack
		for (int i = 0; i < enemies.size; i++) {
			enemy = enemies.get(i);
			if (!enemy.isElectricuted()) {
				continue;
			}
			degrees = enemy.getElectricutedTime()/enemy.getElectricutedPeriod()*360f;
			radius = enemy.getWidth()/2f; // (float) Math.max(enemy.width, enemy.height), can even optimize by initializing max in enemy constructor
			shaper.begin(ShapeType.Line);
			shaper.setColor(1f, 1f, .5f, 1f);
			shaper.circle(enemy.getX() + enemy.getWidth()/2f, enemy.getY() + enemy.getHeight()/2f, radius);
			shaper.end();
			
			Gdx.gl.glEnable(GL20.GL_BLEND);
			shaper.begin(ShapeType.Filled);
			
			shaper.setColor(1f, 1f, .5f, .2f);
	
			shaper.arc(enemy.getX() + enemy.getWidth()/2f, enemy.getY() + enemy.getHeight()/2f, radius, 0, degrees);
			shaper.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
	}
	public void updateElecs(){
		for (int i = 0; i < elecs.size; i++){
			if (elecs.get(i).getHealth() <= 0 || !elecs.get(i).isElectricuted()) elecs.removeIndex(i);
		}
	}
	public boolean addToElecs(Enemy enemy){
		boolean valid = true;
		for (int i = 0; i < elecs.size; i++){
			if (enemy == elecs.get(i)) valid = false;
		}
		if (valid) {
//			enemy.setElectricuted(true);
			enemy.setElectricContact(true);
			elecs.add(enemy);
		}
		return valid;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean getActive() {
		return this.active;
	}

}
