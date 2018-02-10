package com.madcoatgames.newpong.play;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.madcoatgames.newpong.util.Global;

public class Bullet extends Hazard{
	private Rectangle bounds = new Rectangle();
	
	private int dir;
	
	private float stateTime = 0f;
	
	private boolean isDead = false;
	
	public Bullet(float x, float y, int dir){
		bounds.width = 60f;
		bounds.height = 60f;
		bounds.x = x - bounds.width/2f;
		bounds.y = y - bounds.height/2f;
		
		this.dir = dir;
	}

	@Override
	public Rectangle getCollisionBounds() {
		return bounds;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void update(float delta) {
		stateTime += delta;
		bounds.x += dir * stateTime * stateTime * 8f;
		if (dir > 0) {
			if (bounds.x + bounds.width > Global.width()){
				isDead = true;
			}
		} else {
			if (bounds.x < 0) {
				isDead = true;
			}
		}
	}
	public void draw(ShapeRenderer shaper){
		shaper.begin(ShapeType.Filled);
		
		shaper.setColor(Color.RED);
		shaper.rect(bounds.x, bounds.y, bounds.width, bounds.height);
		
		
		
		shaper.end();
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return isDead;
	}

	@Override
	public void setColors(Color colorA, Color colorB, Color colorC) {
		// TODO Auto-generated method stub
		
	}

}
