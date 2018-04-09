package com.madcoatgames.newpong.play;

import com.badlogic.gdx.math.Vector2;
import com.madcoatgames.newpong.look.BallShape;

public class CloneBall extends Ball {
	public int maxVerticalCollisions = 3;
	
	public CloneBall(){
		super();
		this.type = Type.CLONE;
	}
	
	public CloneBall(Vector2 pos) {
		super();
		this.type = Type.CLONE;
	}
	public void update(float delta){
		super.update(delta);
		if (verticalCollisionCount >= maxVerticalCollisions) {
			this.setDead(true);
		}
	}
}
