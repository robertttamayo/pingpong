package com.madcoatgames.newpong.nongame;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.util.InputTestable;

public class HouseInputer {
	private Array<Vector2> points = new Array<Vector2>();
	
	public HouseInputer(){
		for (int i = 0; i < 2; i++){
			points.add(new Vector2());
		}
		resetPoints();
		
	}
	private void resetPoints(){
		for (Vector2 v : points){
			v.set(-1, -1);
		}
	}
	public void test(Array<InputTestable> tests){
		
	}

}
