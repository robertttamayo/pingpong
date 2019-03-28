package com.madcoatgames.newpong.powerups.virus;

import javax.swing.text.Position;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.enemy.Enemy;
import com.madcoatgames.newpong.play.Ball;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.TriColorChanger;

public class VirusMaster {
	private Ball ball;
	private Rectangle infectionBounds = new Rectangle();
	private Array<Particle> particles = new Array<Particle>();
	
	private boolean active = false;
	public int statusLevel = 1;
	
	private float r, g, b;

	private float particleTime = 0f;
	
	public VirusMaster(Ball ball) {
		this.ball = ball;
	}
	public void update(Array<Enemy> enemies, float delta) {
		particleTime += delta;
		if (active) {
			Enemy e;
			// test ball collision with each enemy
			for (int i = 0; i < enemies.size; i++) {
				 e = enemies.get(i);
				 
				 if (ball.getBounds().overlaps(e)) {
					 if (!e.isHit()) {
	//					 e.setHit(true);
					 }
					 if (!e.isInfected()) {
						 e.infect(true);
					 }
				 }
				 if (e.isInfected()) {
					 infectionBounds.width = e.width * 1.5f  + e.width * .25f * statusLevel;
					 infectionBounds.height = e.height * 1.5f  + e.height * .25f * statusLevel;
					 infectionBounds.x = e.x - infectionBounds.width/2f;
					 infectionBounds.y = e.y - infectionBounds.height/2f;
					 for (int j = 0; j < enemies.size; j++ ) {
						 if (enemies.get(j).isInfected() || enemies.get(j).isImmune()) {
							 continue;
						 }
						// check virus collision for each enemy against every other non-infected enemy
						 if (infectionBounds.overlaps(enemies.get(j))) {
							 enemies.get(j).infect(false);
						 }
					 }
					 if (particleTime > .5f) {
						 addParticles(e.x + e.width/2f, e.y + e.height/2f, e.infectedByDirectContact());
					 }
				 }
			}
		}
		if (particleTime > .5f) {
			particleTime = 0f;
		}
		updateParticles(delta);
	}
	private void updateParticles(float delta) {
		for (int i = 0; i < particles.size; i++) {
			particles.get(i).update(delta);
			if (particles.get(i).alpha <= 0f) {
				particles.removeIndex(i);
			}
		}
	}
	private void addParticles(float x, float y, boolean directContact) {
		float range = 70f + 15f * statusLevel;
		for (int i = 0; i < (5 + statusLevel*2); i++) {
			particles.add(new Particle(x + range * (float)Math.random() - range/2f, 
					y + range * (float)Math.random() - range/2f));
		}
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isActive() {
		return this.active;
	}
	public void renderTargetCircle(Array<Enemy> enemies, ShapeRenderer shaper, float delta){
		float infectedPeriod = Global.infectedPeriod;
		Enemy enemy;
		float radius;
		float start;
		float degrees;
		// render the charge time for the electric attack
		for (int i = 0; i < enemies.size; i++) {
			enemy = enemies.get(i);
			if (!enemy.isInfected() || enemy.getInfectedTime() < 0) {
				continue;
			}
			degrees = enemy.getInfectedTime()/infectedPeriod*360f;
			radius = enemy.getWidth()/2f; // (float) Math.max(enemy.width, enemy.height), can even optimize by initializing max in enemy constructor
			shaper.begin(ShapeType.Line);
			shaper.setColor(0f, 1f, 0f, 1f);
			
//			shaper.circle(enemy.getX() + enemy.getWidth()/2f, enemy.getY() + enemy.getHeight()/2f, radius);
			shaper.end();
			
			Gdx.gl.glEnable(GL20.GL_BLEND);
			shaper.begin(ShapeType.Filled);
			
			if (enemy.infectedByDirectContact()) {
				shaper.setColor(0f, 1f, 0f, .35f);
			} else {
				shaper.setColor(0f, 1f, 0f, .35f);
			}
	
//			shaper.arc(enemy.getX() + enemy.getWidth()/2f, enemy.getY() + enemy.getHeight()/2f, radius, 0, degrees);
			
			shaper.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
		switch(statusLevel) {
		case 1:
			r = 0f;
			g = .8f;
			b = 0f;
			break;
		case 2: 
			r = .6f;
			g = 1f;
			b = 0f;
			break;
		case 3:
			r = .8f;
			g = .5f;
			b = 1f;
			break;
		default:
				r = 0f;
				g = 0f;
				b = 0f;
		}
		Gdx.gl.glEnable(GL20.GL_BLEND);
		shaper.begin(ShapeType.Filled);
		
		for (int j = 0; j < particles.size; j++) {
			particles.get(j).render(shaper);
		}
		
		shaper.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	private class Tail {
		
	}
	private class Particle {
		public float stateTime = 0f;
		public float alpha = 0f;
		private float x, y, radius, rise;
		public Particle (float x, float y) {
			radius = 25f * (float)Math.random() * .5f;
			this.x = x;
			this.y = y;
			this.rise = (float) Math.random() * 10f + 20f;
		}
		public void update(float delta) {
			stateTime += delta;
			alpha = Math.max(.6f - stateTime/2f, 0f);
			y += rise*delta;
		}
		public void render(ShapeRenderer shaper) {
			shaper.setColor(0f, .8f, 0f, alpha);
			shaper.setColor(r, g, b, alpha);
			
			shaper.circle(x, y, radius);
		}
	}
}
