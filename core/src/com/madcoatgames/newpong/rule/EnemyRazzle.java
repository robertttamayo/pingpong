package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.util.FilledShapeRenderable;

public class EnemyRazzle implements FilledShapeRenderable {
	public Vector2 center = new Vector2();
	public float force;
	public float radius;
	public Array<Spark> sparks = new Array<Spark>();
	public float r, g, b;
	public float alpha = 1f;
	public boolean dead = false;
	
	public EnemyRazzle(float rx, float ry){
		
		setColor();
		
		center.set(rx, ry);
		
		for (int i = 0; i < 20; i++){
			sparks.add(new Spark(center.x, center.y));
		}
		
		force = 200;
		float maxf = 60, minf = 5;
		int j;
		float angle;
		for (int i = 0; i < 20; i++){
			force = (float) (Math.random()*maxf + minf);
			sparks.get(i).vx = force;
			sparks.get(i).vy = force;
			
			
			
			angle = (float) ((i)*2*Math.PI/20f);
			
			sparks.get(i).vx *= Math.sin(angle);
			sparks.get(i).vy *= Math.cos(angle);
		}
	}
	public void setColor(){
		int numColors = 7;
		int c = (int)(Math.random()*numColors);
		r = 0;
		g = 0;
		b = 0;
		switch(c){
		case 0:
			//yellow
			r = 1;
			g = 1;
			break;
		case 1:
			//cyan
			g = 1;
			b = 1;
			break;
		case 2:
			//magenta
			r = 1;
			b = 1;
			break;
		case 3:
			//red
			r = 1;
			break;
		case 4:
			//green
			g = 1;
			break;
		case 5:
			//blue
			b = 1;
			break;
		case 6:
			//orange
			g = .5f;
			r = 1;
			break;
		}
	}
	@Override
	public void renderFilled(ShapeRenderer shaper){
		float delta = Gdx.graphics.getDeltaTime();
		for (Spark spark : sparks){
			spark.update(delta);
		}
		
		alpha -= delta*.5f;
		if (alpha < 0) {
			dead = true;
			return;
		}
		shaper.end();
		Gdx.gl.glEnable(GL20.GL_BLEND);
		shaper.begin(ShapeType.Filled);
		
		for (int i = 0; i < sparks.size; i++){
			//shaper.setColor(r, g, b, alpha);
			//shaper.circle(sparks.get(i).x, sparks.get(i).y, 4);
			shaper.setColor(1f, 1f, 1f, alpha);
			shaper.circle(sparks.get(i).x, sparks.get(i).y, sparks.get(i).radius);
		}
		
		shaper.end();
		
		Gdx.gl.glDisable(GL20.GL_BLEND);
		shaper.begin(ShapeType.Filled);
	}
	
	private class Spark{
		public float x, y;
		public float vx, vy;
		public float radius;
		
		public Spark(float x, float y){
			this.x = x;
			this.y = y;
			radius = (float) (Math.random()*8+3);
		}
		public void update(float delta){
			x += delta*vx;
			y += delta*vy;
		}
	}

}

