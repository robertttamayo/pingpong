package com.madcoatgames.newpong.nongame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.util.Global;

public class CircleMachine {
	public Color light, dark;
	public Array<Vector2> centers = new Array<Vector2>();
	public Array<Radius> radii = new Array<Radius>();
	
	public float w, h;
	public float spread;
	public int max;
	public float edge;
	public float f;
	public boolean even;
	
	public CircleMachine(Color light){
		this.light = new Color(light);
		this.dark = new Color(light.r/2f, light.g/2f, light.b/2f, 1);
		this.w = Global.width();
		this.h = Global.height();
		this.max = 8;
		this.spread = (Global.width()/15f);
		this.edge = max*spread;
		setCenters();
		setRadii();
		this.even = true;
	}
	public void setColors(Color light){
		this.light.set(light.r, light.g, light.b, 1);
		this.dark.set(light.r/1.5f, light.g/1.5f, light.b/1.5f, 1);
	}
	public void setColors(Color light, Color dark){
		this.light.set(light.r, light.g, light.b, 1);
		this.dark.set(dark.r/1.5f, dark.g/1.5f, dark.b/1.5f, 1);
	}
	public void setCenters(){
		float tenth = w/10f;
		float fifth = h/5f;
		
		centers.add(new Vector2(tenth*2f, fifth*2.5f));
		centers.add(new Vector2(tenth*6.5f, fifth));
		centers.add(new Vector2(tenth*9f, fifth*3.5f));
	}
	public void setRadii(){
		for (int i = 0; i < max; i++){
			radii.add(new Radius(spread + i*spread, even));
			even = !even;
		}
	}	
	public void update(float delta, ShapeRenderer shaper){
		for (int i = 0; i < max; i++){
			radii.get(i).r += spread*delta*.75f;
		}
		
		shaper.begin(ShapeType.Filled);
		//shaper.setColor(light);
		
		for (int i = max - 1; i >= 0; i--){
			if (radii.get(i).even) shaper.setColor(light);
			else shaper.setColor(dark);
			shaper.circle(centers.first().x, centers.first().y, radii.get(i).r);
			shaper.circle(centers.get(1).x, centers.get(1).y, radii.get(i).r);
			if (radii.get(i).even) shaper.setColor(dark);
			else shaper.setColor(light);
			shaper.circle(centers.get(2).x, centers.get(2).y, radii.get(i).r);
		}
		
		
		shaper.end();
		
		
		//check remove
		for (int i = 0; i < max; i++){
			if (radii.get(i).r > edge) radii.removeIndex(i);
		}
		if (radii.size < max) {
			//radii.reverse();
			radii.insert(0, new Radius(0f, even));
			even = !even;
			//radii.reverse();
		}
	}
	private class Radius{
		float r;
		boolean even;
		public Radius(float r, boolean even){
			set(r);
			this.even = even;
		}
		public void set(float r){
			this.r = r;
		}
	}

}
