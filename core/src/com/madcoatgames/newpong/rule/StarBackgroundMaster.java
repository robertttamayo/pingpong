package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.util.FilledShapeRenderable;

public class StarBackgroundMaster implements FilledShapeRenderable{
	private enum MoveType {
		LEFT, RIGHT, FORWARD, BACKWARD
	}
	private MoveType type;
	private Array<Star> stars = new Array<Star>();
	
	private int width, height;
	private float centerX, centerY;
	
	private float zDist = 1000f;
	
	private int count = 1500;
	
	private float r = 1, g = 1, b = 1;
	
	private float xyRatio;
	
	public StarBackgroundMaster(int width, int height) {
		this.width = width;
		this.height = height;
		centerX = width/2f;
		centerY = height/2f;
		xyRatio = (float) width/height;
		
		type = MoveType.FORWARD;
		
		Star star;
		Vector2 v = new Vector2();
		for (int i = 0; i < count; i++) {
			star = new Star((int) (Math.random() * width), (int) (Math.random() * height), (float) (Math.random() * zDist));
			v.x = star.x() - centerX;
			v.y = star.y() - centerY;
			v.nor();
			star.setVelX(v.x);
			star.setVelY(v.y);
			stars.add(star);
		}
	}
	public void update(float delta){ 
		for (Star star : stars) {
			star.update(type, delta);
			if ((star.x() > width || star.x() < 0) || (star.y() > height || star.y() < 0)) {
				stars.removeValue(star, true);
			}
		}
		Star star;
		Vector2 v = new Vector2();
		for (int i = 0; i < (count - stars.size); i++) {
//			star = new Star((int) (Math.random() * width), (int) (Math.random() * height), (float)Math.random() * 20f);
			star = new Star((int) (Math.random() * width), (int) (Math.random() * height), 1f);
			v.x = star.x() - centerX;
			v.y = star.y() - centerY;
			v.nor();
			star.setVelX(v.x);
			star.setVelY(v.y);
//			star.setAliveTime(2f);
			stars.add(star);
		}
	}

	private class Star {
		private float x, y;
		private float velX, velY;
		private float accel;
		private float aliveTime = 0f;
		private float distance = 0f;
		private Star(float x, float y, float accel) {
			this.x = x;
			this.y = y;
			this.accel = accel;
		}
		public void update(MoveType type, float delta) {
			aliveTime += delta;
			accel += delta * aliveTime * .5f;
//			accel += delta * .5f;
			x += delta * velX * accel;// * xyRatio;
			y += delta * velY * accel;
		}
		public float x(){
			return this.x;
		}
		public float y(){
			return this.y;
		}
		public float getVelX() {
			return this.velX;
		}
		public float getVelY() {
			return this.velY;
		}
		public void setVelX(float velX){
			this.velX = velX;
		}
		public void setVelY(float velY){
			this.velY = velY;
		}
		public float getAccel(){
			return accel;
		}
		public float getAliveTime(){
			return aliveTime;
		}
		public void setAliveTime(float aliveTime){
			this.aliveTime = aliveTime;
		}
	}	
	public FilledShapeRenderable getFilled(){
		return this;
	}
	public void setColor(float r, float g, float b) {
		this.r = r * 1.3f;
		this.g = g * 1.3f;
		this.b = b * 1.3f;
	}
	public void setColor(Color color) {
		this.r = color.r * 1.3f;
		this.g = color.g * 1.3f;
		this.b = color.b * 1.3f;
	}
	@Override
	public void renderFilled(ShapeRenderer shaper) {
//		System.out.println("Rendering the stars");
		shaper.setColor(Color.WHITE);
		Star star;
		for (int i = 0; i < stars.size; i++) {
			star = stars.get(i);
			if (i == 0) {
//				System.out.println("render radius: " + star.getAccel() / 20f);
//				shaper.circle(star.x(), star.y(), Math.max(star.getAccel() / 5f, .5f));
			}
			shaper.setColor(r, g, b, star.getAliveTime() / 10f);
//			shaper.setColor(1f, 1f, 1f, star.getAccel());
			shaper.circle(star.x(), star.y(), Math.min(2f, Math.max(star.getAccel() / 50f, 1)));
//			shaper.circle(star.x(), star.y(), Math.min(2f, Math.max(star.getAliveTime() / 20f, 1)));
		}
	}
}
