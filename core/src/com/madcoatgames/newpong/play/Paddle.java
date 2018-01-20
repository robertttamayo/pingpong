package com.madcoatgames.newpong.play;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.madcoatgames.newpong.util.HitTimer;
import com.madcoatgames.newpong.util.TouchTarget;

public class Paddle extends TouchTarget{
	public final static int RIGHT = 0;
	public final static int LEFT = 1;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Color color = new Color();
	
	private HitTimer hitTimer = new HitTimer(.4f);
	private boolean hit = false;
	
	private float touchHeight = 0;
	private boolean ballCollision = false;

	public Paddle(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}
	public void update(float delta){
		if (hit){
			hit = hitTimer.update(delta);
		}
	}
	public float getTouchHeight() {
		return touchHeight;
	}
	
	public void setTouchHeight(float touchHeight) {
		this.touchHeight = touchHeight;
	}

	public boolean isBallCollision() {
		return ballCollision;
	}

	public void setBallCollision(boolean ballCollision) {
		this.ballCollision = ballCollision;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	public void setHit(boolean hit){
		this.hit = hit;
		hitTimer.setHit(hit);
	}
	public boolean isHit(){
		return hit;
	}

}
