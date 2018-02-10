package com.madcoatgames.newpong.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.madcoatgames.newpong.util.Global;

public class Star extends Hazard{
	public static final int NORMAL = 0;
	
	public int type;
	public boolean friendly;
	public int dir;
	public Rectangle bounds;
	
	public Vector2 a = new Vector2();
	public Vector2 b = new Vector2();
	public Vector2 c = new Vector2();
	public Vector2 a90, b90, c90, a180, b180, c180, a270, b270, c270;
	public Vector2 da = new Vector2();
	public Vector2 db = new Vector2();
	public Vector2 dc = new Vector2();
	public float width, height;
	public Vector2 center = new Vector2();
	public Vector2 pos = new Vector2();
	public Vector2 vel = new Vector2();
	public Vector2 target = new Vector2();
	public boolean acquired = false;
	public boolean hyper = true;
	public float stateTime = 0;
	private float rotateTime = 0;
	public float dstA, dstB, dstC;
	public float ar, ag, ab, br, bg, bb;
	public Color colorA = new Color();
	public Color colorB = new Color();
	public Color colorC = new Color();
	
	//public LightningBolt bolt;
	public boolean hasBolt = false;
	
	public boolean isDead = false;
	
	//public float dstA, dstB, dstC, dstA90, dstB90, dstC90
				//, dstA180, dstB180, dstC180, dstA270, dstB270, dstC270;
	
	public Star (float x, float y, int dir) {	
		width = 12; //8
		height = 24; //16
 		
		bounds = new Rectangle(x, y, 4f*width, 2*height);
		
		a.set(x, y);
		b.set(a);
		b.add(width/2f, -height);
		c.set(b);
		c.sub(width, 0);
		
		da.set(a);
		db.set(b);
		dc.set(c);
		
		center.x = a.x;
		center.y = a.y - height;
		pos.set(center);
		
		dstA = (float)a.dst(center);
		dstB = (float)b.dst(center);
		dstC = (float)c.dst(center);
		
		set90();
		set180();
		set270();
		
		colorA.set(1, .5f, .5f, 1);
		colorB.set(.5f, .8f, 1, 1);
		
		this.dir = dir;
	}
	public void update(float delta){

		stateTime += delta;
		
		pos.x += dir * stateTime * stateTime * 8f;
		bounds.setPosition(pos);
		bounds.x -= bounds.width/2f;
		bounds.y -= bounds.height/2f;
		
		if (dir > 0) {
			if (bounds.x + bounds.width > Global.width()){
				isDead = true;
			}
		} else {
			if (bounds.x < 0) {
				isDead = true;
			}
		}
		rotate(delta);
	}
	public void rotate(float delta){
		rotateTime += 8f*delta;
		if (dir < 0) {
		a.x = (float)(center.x - dstA*Math.cos(rotateTime + Math.PI/2d));
		a.y = (float)(center.y + dstA*Math.sin(rotateTime + Math.PI/2d));
		b.x = (float)(center.x - dstB*Math.cos(rotateTime));
		b.y = (float)(center.y + dstB*Math.sin(rotateTime));
		c.x = (float)(center.x + dstC*Math.cos(rotateTime));
		c.y = (float)(center.y - dstC*Math.sin(rotateTime));
		
		a90.x = (float)(center.x - dstA*Math.cos(rotateTime + Math.PI));
		a90.y = (float)(center.y + dstA*Math.sin(rotateTime + Math.PI));
		b90.x = (float)(center.x - dstB*Math.cos(rotateTime + Math.PI/2d));
		b90.y = (float)(center.y + dstB*Math.sin(rotateTime + Math.PI/2d));
		c90.x = (float)(center.x + dstC*Math.cos(rotateTime + Math.PI/2d));
		c90.y = (float)(center.y - dstC*Math.sin(rotateTime + Math.PI/2d));
		
		a180.x = (float)(center.x - dstA*Math.cos(rotateTime + 3d*Math.PI/2d));
		a180.y = (float)(center.y + dstA*Math.sin(rotateTime + 3d*Math.PI/2d));
		b180.x = (float)(center.x - dstB*Math.cos(rotateTime + Math.PI));
		b180.y = (float)(center.y + dstB*Math.sin(rotateTime + Math.PI));
		c180.x = (float)(center.x + dstC*Math.cos(rotateTime + Math.PI));
		c180.y = (float)(center.y - dstC*Math.sin(rotateTime + Math.PI));
		
		a270.x = (float)(center.x - dstA*Math.cos(rotateTime));
		a270.y = (float)(center.y + dstA*Math.sin(rotateTime));
		b270.x = (float)(center.x - dstB*Math.cos(rotateTime + 3d*Math.PI/2d));
		b270.y = (float)(center.y + dstB*Math.sin(rotateTime + 3d*Math.PI/2d));
		c270.x = (float)(center.x + dstC*Math.cos(rotateTime + 3d*Math.PI/2d));
		c270.y = (float)(center.y - dstC*Math.sin(rotateTime + 3d*Math.PI/2d));
		}
		else {
		a.x = (float)(center.x - dstA*Math.sin(rotateTime + Math.PI/2d));
		a.y = (float)(center.y + dstA*Math.cos(rotateTime + Math.PI/2d));
		b.x = (float)(center.x - dstB*Math.sin(rotateTime));
		b.y = (float)(center.y + dstB*Math.cos(rotateTime));
		c.x = (float)(center.x + dstC*Math.sin(rotateTime));
		c.y = (float)(center.y - dstC*Math.cos(rotateTime));
		
		a90.x = (float)(center.x - dstA*Math.sin(rotateTime + Math.PI));
		a90.y = (float)(center.y + dstA*Math.cos(rotateTime + Math.PI));
		b90.x = (float)(center.x - dstB*Math.sin(rotateTime + Math.PI/2d));
		b90.y = (float)(center.y + dstB*Math.cos(rotateTime + Math.PI/2d));
		c90.x = (float)(center.x + dstC*Math.sin(rotateTime + Math.PI/2d));
		c90.y = (float)(center.y - dstC*Math.cos(rotateTime + Math.PI/2d));
		
		a180.x = (float)(center.x - dstA*Math.sin(rotateTime + 3d*Math.PI/2d));
		a180.y = (float)(center.y + dstA*Math.cos(rotateTime + 3d*Math.PI/2d));
		b180.x = (float)(center.x - dstB*Math.sin(rotateTime + Math.PI));
		b180.y = (float)(center.y + dstB*Math.cos(rotateTime + Math.PI));
		c180.x = (float)(center.x + dstC*Math.sin(rotateTime + Math.PI));
		c180.y = (float)(center.y - dstC*Math.cos(rotateTime + Math.PI));
		
		a270.x = (float)(center.x - dstA*Math.sin(rotateTime));
		a270.y = (float)(center.y + dstA*Math.cos(rotateTime));
		b270.x = (float)(center.x - dstB*Math.sin(rotateTime + 3d*Math.PI/2d));
		b270.y = (float)(center.y + dstB*Math.cos(rotateTime + 3d*Math.PI/2d));
		c270.x = (float)(center.x + dstC*Math.sin(rotateTime + 3d*Math.PI/2d));
		c270.y = (float)(center.y - dstC*Math.cos(rotateTime + 3d*Math.PI/2d));
		}
		
		a.x += (pos.x - center.x);
		b.x += (pos.x - center.x);
		c.x += (pos.x - center.x);
		a90.x += (pos.x - center.x);
		b90.x += (pos.x - center.x);
		c90.x += (pos.x - center.x);
		a180.x += (pos.x - center.x);
		b180.x += (pos.x - center.x);
		c180.x += (pos.x - center.x);
		a270.x += (pos.x - center.x);
		b270.x += (pos.x - center.x);
		c270.x += (pos.x - center.x);
		
		if (acquired) {
			adjustY();
			pos.y += vel.y*Gdx.graphics.getDeltaTime()*(hyper ? 10.8f : 1.8f);
		}
		
		a.y += (pos.y - center.y);
		b.y += (pos.y - center.y);
		c.y += (pos.y - center.y);
		a90.y += (pos.y - center.y);
		b90.y += (pos.y - center.y);
		c90.y += (pos.y - center.y);
		a180.y += (pos.y - center.y);
		b180.y += (pos.y - center.y);
		c180.y += (pos.y - center.y);
		a270.y += (pos.y - center.y);
		b270.y += (pos.y - center.y);
		c270.y += (pos.y - center.y);
	}
	public void render(ShapeRenderer shaper, Color color1, Color color2){
		shaper.begin(ShapeType.Filled);
		//if (hasBolt) bolt.update(Gdx.graphics.getDeltaTime(), shaper);

		shaper.triangle(a.x, a.y, b.x, b.y, c.x, c.y, colorA, colorB, colorB);
		shaper.triangle(a90.x, a90.y, b90.x, b90.y, c90.x, c90.y, colorA, colorB, colorB);
		shaper.triangle(a180.x, a180.y, b180.x, b180.y, c180.x, c180.y, colorA, colorB, colorB);
		shaper.triangle(a270.x, a270.y, b270.x, b270.y, c270.x, c270.y, colorA, colorB, colorB);
		//shaper.triangle(da.x, da.y, db.x, db.y, dc.x, dc.y, Color.RED, Color.BLUE, Color.GREEN);
		shaper.end();
	}
	public void set90(){
		a90 = new Vector2();
		b90 = new Vector2();
		c90 = new Vector2();
		a90.x = (float)(center.x - dstA*Math.cos(Math.PI));
		a90.y = (float)(center.y + dstA*Math.sin(Math.PI));
		b90.x = (float)(center.x - dstB*Math.cos(Math.PI/2d));
		b90.y = (float)(center.y + dstB*Math.sin(Math.PI/2d));
		c90.x = (float)(center.x + dstC*Math.cos(Math.PI/2d));
		c90.y = (float)(center.y - dstC*Math.sin(Math.PI/2d));
	}
	public void set180(){
		a180 = new Vector2();
		b180 = new Vector2();
		c180 = new Vector2();
		a180.x = (float)(center.x - dstA*Math.cos(Math.PI));
		a180.y = (float)(center.y + dstA*Math.sin(Math.PI));
		b180.x = (float)(center.x - dstB*Math.cos(Math.PI/2d));
		b180.y = (float)(center.y + dstB*Math.sin(Math.PI/2d));
		c180.x = (float)(center.x + dstC*Math.cos(Math.PI/2d));
		c180.y = (float)(center.y - dstC*Math.sin(Math.PI/2d));
	}
	public void set270(){
		a270 = new Vector2();
		b270 = new Vector2();
		c270 = new Vector2();
		a270.x = (float)(center.x - dstA*Math.cos(3d*Math.PI/2d));
		a270.y = (float)(center.y + dstA*Math.sin(3d*Math.PI/2d));
		b270.x = (float)(center.x - dstB*Math.cos(Math.PI));
		b270.y = (float)(center.y + dstB*Math.sin(Math.PI));
		c270.x = (float)(center.x + dstC*Math.cos(Math.PI));
		c270.y = (float)(center.y - dstC*Math.sin(Math.PI));
	}
	public void setColorA(float r, float g, float b){
		colorA.set(r, g, b, 1);
	}
	public void setColorB(float r, float g, float b){
		colorB.set(r, g, b, 1);
	}
	public void setTarget(float x, float y){
		target.x = x;
		target.y = y;
	}
	public void updateTarget(Vector2 target){
		this.target = target;
	}
	public void adjustY(){
		float change = target.y - pos.y;
		float distance = target.x - pos.x;
		//float dividend = distance == 0 ? change/distance : 0;
		
		if (dir > 0 && distance <= 0) {
			acquired = false;
			return;
		}
		if (dir < 0 && distance >= 0) {
			acquired = false;
			return;
		}
		vel.y = change;
		
		
	}
	@Override
	public Rectangle getCollisionBounds() {
		return bounds;
	}
	@Override
	public void draw(ShapeRenderer shaper) {
		render(shaper, this.colorA, this.colorB);
		
	}
	@Override
	public void setColors(Color colorA, Color colorB, Color colorC) {
		this.colorA = colorA;
		this.colorB = colorB;
		this.colorC = colorC;
	}
	@Override
	public boolean isDead() {
		return isDead;
	}

}
