package com.madcoatgames.newpong.util;

public class Trail extends Point{
	private boolean special = false;

	public Trail(float x, float y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	public void special(){
		special = true;
	}
	public boolean isSpecial(){
		return special;
	}

}
