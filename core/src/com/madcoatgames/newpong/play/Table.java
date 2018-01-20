package com.madcoatgames.newpong.play;

import com.badlogic.gdx.math.Rectangle;

public class Table extends Rectangle{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Table (Rectangle r){
		this.set(r);
	}
	public Table (float x, float y, float width, float height){
		this.set(x, y, width, height);
	}
	public float left(){
		return this.x;
	}
	public float right(){
		return this.x + this.width;
	}
	public float top(){
		return this.y + this.height;
	}
	public float bottom(){
		return this.y;
	}
}
