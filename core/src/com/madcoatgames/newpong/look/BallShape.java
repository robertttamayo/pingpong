package com.madcoatgames.newpong.look;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class BallShape {
	public static final int SHELL_SPREAD_LIVE = 8;
	public static final int SHELL_SPREAD_CLOSED = 2;
	
	public static final int RIGHT = 1;
	public static final int LEFT = -1;
	
	public static final int GEAR_NORMAL = 0;
	public static final int GEAR_FAST = 1;
	public static final int GEAR_SUPER_FAST = 2;
	public static final int GEAR_SLOW = -1;
	public static final int GEAR_SUPER_SLOW = -2;
	
	public static final float CONSTANT_SPEED = 32f;
	
	private static int gear = 0;
	private static int dir = RIGHT;
	
	private final int helperSpeed = 10;
	
	private boolean live = false;
	
	private float radiusYolk, radiusShell;
	private float shellSpread;
	private float dirSpeed;
	private Vector2 center = new Vector2();
	private int[] angles = new int[4];
	private Array<Vector2> arcs = new Array<Vector2>();
	
	private int angle = 0;
	
	public BallShape(){
		radiusYolk =  16f;
		radiusShell = 20f;
		shellSpread = SHELL_SPREAD_CLOSED;
		
		dirSpeed = 30f;
		
		for (int i = 0; i < 4; i++){
			arcs.add(new Vector2(0,0));
		}
	}
	public void update(float delta){
		if (live){
			updateLive(delta);
			shellSpread = SHELL_SPREAD_LIVE;
		} else {
			updateClosed(delta);
			shellSpread = SHELL_SPREAD_CLOSED;
		}
	}
	private void updateLive(float delta){
		addAngle(delta);
		
	}
	private void updateClosed(float delta){
		addAngle(delta);
	}
	private void addAngle(float delta){
		angle += dirSpeed * dir * delta * helperSpeed;
		generateAngles();
		placeArcs();
		
	}
	private void generateAngles(){
		for (int i = 0; i < angles.length; i++){
			angles[i] = angle + i * 90;
		}
	}
	private void placeArcs(){
		float radians = 0;
		for (int i = 0; i < angles.length; i++){
			radians = (float) Math.toRadians(angles[i]);
			arcs.get(i).x = (float) (center.x + shellSpread * Math.cos(radians));
			arcs.get(i).y = (float) (center.y + shellSpread * Math.sin(radians));
		}
	}
	public Array<Vector2> getArcs(){
		return arcs;
	}
	public float getRadiusShell(){
		return radiusShell;
	}
	public float getRadiusYolk(){
		return radiusYolk;
	}
	public int[] getAngles(){
		return angles;
	}
	public void setCenter(Vector2 center){
		this.center = center;
	}
	public Vector2 getCenter(){
		return center;
	}
	public void setLive(boolean live){
		this.live = live;
	}
	public static void addLeft(){
		if (dir == RIGHT) {
			gear++;
			if (gear >= 2) {
				gear = 2;
			}
		} else {
			dir = RIGHT;
			gear = 0;
		}
		
	}
	public static void addRight(){
		if (dir == LEFT) {
			gear++;
			if (gear >= 2) {
				gear = 2;
			}
		} else {
			dir = LEFT;
			gear = 0;
		}
		
	}
}
