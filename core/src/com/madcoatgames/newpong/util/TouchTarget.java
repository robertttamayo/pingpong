package com.madcoatgames.newpong.util;

import com.badlogic.gdx.math.Rectangle;

public class TouchTarget extends Rectangle{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean touched = false;

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}
}
