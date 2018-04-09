package com.madcoatgames.newpong.look;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.TriColorChanger;

public class HealthRenderer {
	public Rectangle frame = new Rectangle();
	
	public HealthRenderer(){
		frame.width = Global.width()/3f;
		frame.height = 30f;
		frame.x = Global.width()/2f - frame.width/2f;
		frame.y = 0f;
	}
	
	public void draw(ShapeRenderer shaper, TriColorChanger tcc, int health, int maxHealth){
		float padding = 4f;
		float width = (frame.width - padding*4f)/(float)maxHealth;
		
		shaper.begin(ShapeType.Filled);
		
		shaper.setColor(tcc.c1);
		shaper.rect(frame.x, frame.y, frame.width, frame.height);
		
		shaper.setColor(Color.BLACK);
		for(int i = 0; i < maxHealth; i++){
			shaper.rect(
					frame.x + padding*(i + 1) + width*i, 
					frame.y + padding,
					width,
					frame.height - padding*2f
					);
		}
		shaper.setColor(tcc.c3);
		for(int i = 0; i < health; i++){
			shaper.rect(
					frame.x + padding*(i + 1) + width*i, 
					frame.y + padding,
					width,
					frame.height - padding*2f
					);
		}
		shaper.end();
	}
	public void drawHit(ShapeRenderer shaper, TriColorChanger tcc, int health, int maxHealth){
		float padding = 8f;
		float width = (frame.width - padding*4f)/(float)maxHealth;
		
		shaper.begin(ShapeType.Filled);
		
		shaper.setColor((float)Math.random(), (float)Math.random(), (float)Math.random(), 1f);
		shaper.rect(frame.x, frame.y, frame.width, frame.height);
		
		shaper.setColor(Color.BLACK);
		for(int i = 0; i < maxHealth; i++){
			shaper.rect(
					frame.x + padding*(i + 1) + width*i, 
					frame.y + padding,
					width,
					frame.height - padding*2f
					);
		}
		shaper.setColor(Color.WHITE);
		for(int i = 0; i < health; i++){
			shaper.rect(
					frame.x + padding*(i + 1) + width*i, 
					frame.y + padding,
					width,
					frame.height - padding*2f
					);
		}
		shaper.end();
	}

}
