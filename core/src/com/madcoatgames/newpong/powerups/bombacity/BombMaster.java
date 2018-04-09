package com.madcoatgames.newpong.powerups.bombacity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.play.Ball;

public class BombMaster {
	private Ball ball;
	private Bomb bomb;
	private Array<Bomb> bombs;
	private float bombPeriod = .5f;
	private float bombTime = 0f;
	private boolean isActive = false;
	private float bombRadius = 20f;
	
	public static int bombCount = 10;
	
	public BombMaster (Ball ball) {
		this.ball = ball;
	}
	
	public void update(float delta) {
		if (!isActive) {
			reset();
			return;
		}
		bombTime += delta;
		if (bombTime >= bombPeriod) {
			bomb = new Bomb(ball.getPos().x, ball.getPos().y, bombRadius, bombRadius);
			bombs.add(bomb);
			if (bombs.size > bombCount) {
				bombs.removeIndex(0);
			}
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
	}
	
	public void render(ShapeRenderer shaper) {
		shaper.begin(ShapeType.Filled);
		shaper.setColor(Color.WHITE);
		for (int i = 0; i < bombs.size; i++) {
			bomb = bombs.get(i);
			shaper.circle(bomb.x, bomb.y, bombRadius);
		}
		shaper.end();
	}

}
