package com.madcoatgames.newpong.play;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.look.BallShape;
import com.madcoatgames.newpong.util.HitTimer;
import com.madcoatgames.newpong.util.Point;
import com.madcoatgames.newpong.util.Trail;

public class Ball {
	private BallShape ballShape;
	private Vector2 pos = new Vector2();
	private Vector2 vel = new Vector2();
	private Rectangle bounds = new Rectangle();
	private Array<Trail> trail = new Array<Trail>();
	
	private HitTimer hitTimer = new HitTimer(.4f);
	private boolean hit = false;
	
	private float radius = 16f;
	private float push = 720f;
	private boolean live = false;
	
	private boolean isDead = false;
	
	public Type type = Type.NORMAL;
	
	public enum Type {
		NORMAL, ELECTRIC
	}
	
	public Ball(){
		ballShape = new BallShape();
	}
	
	public Ball(Vector2 pos) {
		this.setPos(pos);
		ballShape = new BallShape();
	}
	public void update(float delta){
		ballShape.setLive(live);
		ballShape.setCenter(pos);
		ballShape.update(delta);
		updateStates(delta);
	}
	private void updateStates(float delta){
		if (hit){
			hit = hitTimer.update(delta);
		}
		
	}
	public Vector2 getPos() {
		return pos;
	}
	public void setPos(Vector2 pos) {
		this.pos = pos;
	}

	public Vector2 getVel() {
		return vel;
	}

	public void setVel(Vector2 vel) {
		this.vel = vel;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float getPush() {
		return push;
	}

	public void setPush(float push) {
		this.push = push;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public void setBounds(float x, float y, float width, float height) {
		bounds.set(x, y, width, height);
	}
	public void moveBounds(){
		bounds.x = pos.x - radius;
		bounds.y = pos.y - radius;
	}

	public Array<Trail> getTrail() {
		return trail;
	}

	public void setTrail(Array<Trail> trail) {
		this.trail = trail;
	}
	public void setHit(boolean hit){
		this.hit = hit;
		hitTimer.setHit(true);
	}
	public boolean isHit(){
		return hit;
	}
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	public BallShape getBallShape(){
		return ballShape;
	}
	public boolean isDead() {
		return this.isDead;
	}
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
}
