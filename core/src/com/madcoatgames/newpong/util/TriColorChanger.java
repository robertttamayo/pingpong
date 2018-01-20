package com.madcoatgames.newpong.util;

import com.badlogic.gdx.graphics.Color;

public class TriColorChanger {	
	public Color c1 = new Color(), c2 = new Color(), c3 = new Color();
	public Color t1 = new Color(), t2 = new Color(), t3 = new Color();
	public Color i1 = new Color(), i2 = new Color(), i3 = new Color();
	public Color d1 = new Color(), d2 = new Color(), d3 = new Color();
	public Color s1, s2, s3;
	
	public float changeTime = .5f;
	public float stateTime = 0;
	public boolean change = false;
	
	public TriColorChanger(Color c1, Color c2, Color c3){
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
		
		i1.set(c1);
		i2.set(c2);
		i3.set(c3);
		
		t1.set(c2);
		t2.set(c3);
		t3.set(c1);
		
		init();
	}
	public void resetColors(Color c1, Color c2, Color c3){
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
		
		i1.set(c1);
		i2.set(c2);
		i3.set(c3);
		
		t1.set(c2);
		t2.set(c3);
		t3.set(c1);
		
		init();
	}
	public void init(){
		d1.r = t1.r - i1.r;
		d1.g = t1.g - i1.g;
		d1.b = t1.b - i1.b;
		
		d2.r = t2.r - i2.r;
		d2.g = t2.g - i2.g;
		d2.b = t2.b - i2.b;
		
		d3.r = t3.r - i3.r;
		d3.g = t3.g - i3.g;
		d3.b = t3.b - i3.b;
	}
	public void storeColors(){
		s1 = c1;
		s2 = c2;
		s3 = c3;
		
		c1 = null;
		c1 = new Color(Color.WHITE);
		c2 = null;
		c2 = new Color(Color.LIGHT_GRAY);
		c3 = null;
		c3 = new Color(.9f, .9f, .9f, 1);
	}
	public void resetFromStorage(){
		c1 = s1;
		c2 = s2;
		c3 = s3;
		s1 = null;
		s2 = null;
		s3 = null;
	}
	public void update(float delta){
		stateTime += delta;
		if (stateTime > changeTime) {
			stateTime = changeTime;
			change = true;
		}
		c1.r = (stateTime/changeTime)*d1.r + i1.r;
		c1.g = (stateTime/changeTime)*d1.g + i1.g;
		c1.b = (stateTime/changeTime)*d1.b + i1.b;
		
		c2.r = (stateTime/changeTime)*d2.r + i2.r;
		c2.g = (stateTime/changeTime)*d2.g + i2.g;
		c2.b = (stateTime/changeTime)*d2.b + i2.b;
		
		c3.r = (stateTime/changeTime)*d3.r + i3.r;
		c3.g = (stateTime/changeTime)*d3.g + i3.g;
		c3.b = (stateTime/changeTime)*d3.b + i3.b;
		
		//changing
		if (!change) return;
		change = false;
		stateTime = 0;
		c1.set(t1);
		c2.set(t2);
		c3.set(t3);
		resetColors(c1, c2, c3);
	}

}

