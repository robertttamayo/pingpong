package com.madcoatgames.newpong.powerups.electricity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class LightningBolt {
	public ShapeRenderer shaper;
	public Point start, finish;
	public Array<Point> points = new Array<Point>();
	public int numPoints;
	public float stateTime = 0, flickerTime = 0;
	public boolean dead = false;
	public float offX;
	
	private class Point {
		public float x, y/*, thickness*/;
		public Point (float x, float y){
			this.x = x;
			this.y = y;
		}
	}
	public LightningBolt(float x1, float y1, float x2, float y2){
		start = new Point(x1, y1);
		finish = new Point(x2, y2);
		
		createBolt();
	}
	public void update(float delta){
		stateTime += delta;
		flickerTime += delta;
		
		
		
		if (stateTime > .45f)	dead = true;
	}
	/*
	 * WARNING: HIGHLY INEFFICIENT CODE HERE!!
	 */
	public void render(ShapeRenderer shaper){
		//shaper.setColor(Color.CYAN);
		
		
		if (flickerTime > .05f) {
			flickerTime = 0;
			points.clear();
			createBolt();
			offX = 0;
		}
		
		//shaper.line(start.x, start.y, points.first().x, points.first().y);
		for(int i = 0; i < points.size - 1; i++){
			
			//if (Gdx.app.getType() != ApplicationType.Android) Gdx.gl.glLineWidth(points.get(i).thickness);
			
			shaper.line(points.get(i).x /*+ offX*/
					, points.get(i).y
					, points.get(i + 1).x /*+ offX*/
					, points.get(i + 1).y
					, Color.CYAN, Color.YELLOW);
			
		}
		//if (Gdx.app.getType() != ApplicationType.Android) Gdx.gl.glLineWidth(1);	
		//shaper.line(points.peek().x, points.peek().y, finish.x, finish.y);
	}
	public void render2(ShapeRenderer shaper){
		
		if (flickerTime > .15f) {
			flickerTime = 0;
			points.clear();
			createBolt();
			offX = 0;
		}
		for(int i = 0; i < points.size - 1; i++){
			shaper.line(points.get(i).x /*+ offX*/
					, points.get(i).y
					, points.get(i + 1).x /*+ offX*/
					, points.get(i + 1).y);
		}
	}
	public void createBolt(){
		numPoints = 15;
		
		//create 15 random x points
		for (int i = 0; i < numPoints; i++){
			points.add(new Point(start.x + (float)(Math.random()*(finish.x - start.x))
					, start.y));
		}
		
		//sort them in the array
		for (int i = 0; i < points.size; i++){
			for (int j = 0; j < points.size; j++){
				if (points.get(i).x > points.get(j).x) points.swap(i, j);
			}
		}
		
		//find the slope
		float slope = ((finish.y - start.y)/(finish.x - start.x));
		
		//set all y points along the slope
		for (int i = 0; i < points.size; i++){
			points.get(i).y += slope * (points.get(i).x - start.x);
		}
		
		//get the angle, no need to rotate it
		float angle = (float)Math.atan((finish.y - start.y)/(finish.x - start.x));
		angle += Math.PI/2d;
		//create a random variable
		float random;
		
		//for each point...
		for (int i = 0; i < points.size; i++){
			
		//set the random equal to 1.25/5 the distance from start to finish times a random number between -.5 and .5
			random = (float)(.8f/5f*(Math.sqrt(Math.pow(finish.y - start.y, 2) + Math.pow(finish.x - start.x, 2))
					*(Math.random()-.5d)));
			//random = (float)(2f/5f*(finish.y-start.y)*(Math.random()-.5d));
			
		//finally set it down
			points.get(i).y += Math.sin(angle)*random;
			points.get(i).x += Math.cos(angle)*random;
			
//			if (i == points.size - 1) {
//				points.get(i).y = finish.y;
//			}
			
			//if (Gdx.app.getType()!=ApplicationType.Android)points.get(i).thickness = (float)Math.random()*2.5f + .01f;
			//else points.get(i).thickness = 1;
		}
		
		//swap the x's only
		/*
		for (int i = 0; i < points.size; i++){
			for (int j = 0; j < points.size; j++){
				if (points.get(i).x > points.get(j).x) {
					points.get(i).x += points.get(j).x;
					points.get(j).x = points.get(i).x - points.get(j).x;
					points.get(i).x = points.get(i).x - points.get(j).x;
				}
			}
		}*/
		
	}
	public void setStart(Vector2 pos){
		for (int i = 0; i < points.size; i++){
			points.get(i).x += pos.x - start.x;
		}
		this.start.x = pos.x;
		this.start.y = pos.y;
	}
	public void setFinish(Vector2 target){
		this.finish.x = target.x;
		this.finish.y = target.y;
	}
	public void setOffX(float posX){
		this.offX = posX - start.x;
	}

}
