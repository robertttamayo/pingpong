package com.madcoatgames.newpong.play;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class Hazard {
	
public abstract Rectangle getCollisionBounds();
public abstract void update(float delta);
public abstract void draw(ShapeRenderer shaper);
public abstract boolean isDead();

}
