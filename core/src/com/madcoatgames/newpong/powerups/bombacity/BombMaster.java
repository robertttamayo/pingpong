package com.madcoatgames.newpong.powerups.bombacity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.play.Ball;
import com.madcoatgames.newpong.powerups.bombacity.Satellite;

public class BombMaster {
	private Ball ball;
	private Bomb bomb;
	private Array<Bomb> bombs;
	private float bombPeriod = .7f;
	private float bombTime = 0f;
	private boolean isActive = false;
	private float bombRadius = 10f;
	
	public int statusLevel = 0;
	
	public static int bombCount = 10;
	
	public BombMaster (Ball ball) {
		this.ball = ball;
		bombs = new Array<Bomb>();
	}
	
	public void update(float delta) {
		if (!isActive) {
			reset();
			return;
		}
		bombTime += delta;
		if (bombTime >= bombPeriod - statusLevel*.125f) {
			bombTime = 0f;
			bomb = new Bomb(ball.getPos().x, ball.getPos().y, bombRadius, bombRadius);
			bombs.add(bomb);
			if (bombs.size > bombCount) {
				bombs.removeIndex(0);
			}
		}
		for (int i = 0; i < bombs.size; i++) {
			bombs.get(i).update(statusLevel, delta);
		}
	}

	private void reset() {
		bombTime = 0f;
		bombs.clear();
	}
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
		if (!isActive) {
			this.reset();
		}
	}
	
	public void render(ShapeRenderer shaper) {
		Bomb bomb;
		Array<Satellite> satellites;
		Satellite satellite;
		//render bombs
		shaper.begin(ShapeType.Filled);
		
		float r = 0f, g = 0f, b = 0f;
		if (statusLevel < 2) {
			r = .5f;
			g = 1f;
			b = 1f;
		} else if (statusLevel < 3) {
			r = .7f;
			g = 1f;
			b = .5f;
		} else {
			r = 1f;
			g = .7f;
			b = .7f;
		}
		shaper.setColor(r, g, b, 1f);
		for (int i = 0; i < bombs.size; i++) {
			bomb = bombs.get(i);
			if (!bomb.isLive()) {
				continue;
			}
			shaper.circle(bomb.x + bomb.radius/2f, bomb.y + bomb.radius/2f, bomb.radius);
		}
		shaper.end();
		// render satellites
		shaper.begin(ShapeType.Filled);
		shaper.setColor(r, g, b, 1f);
		for (int i = 0; i < bombs.size; i++) {
			bomb = bombs.get(i);
			if (!bomb.isLive()) {
				continue;
			}
			satellites = bomb.getSatellites();
			for (int j = 0; j < satellites.size; j++) {
				satellite = satellites.get(j);
				shaper.circle(satellite.x, satellite.y, satellite.radius);
			}
		}
		shaper.end();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		shaper.begin(ShapeType.Line);
		shaper.setColor(r, g, b, .25f);
		for (int i = 0; i < bombs.size; i++) {
			bomb = bombs.get(i);
			if (!bomb.isLive()) {
				continue;
			}
			satellites = bomb.getSatellites();
			for (int j = 0; j < satellites.size; j++) {
				satellite = satellites.get(j);
				shaper.ellipse(bomb.x + bomb.radius/2f - 18f, bomb.y + bomb.radius/2f - 6f, 36f, 12f, (float)Math.toDegrees(satellite.angle));
			}
		}
		shaper.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	public Array<Bomb> getBombs(){
		return this.bombs;
	}

}
