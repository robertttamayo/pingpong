package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.util.FilledShapeRenderable;

public class EnemyRazzleMaster implements FilledShapeRenderable {
	public EnemyMaster enemyMaster;
	public Array<EnemyRazzle> erd = new Array<EnemyRazzle>();

	public EnemyRazzleMaster(EnemyMaster enemyMaster) {
		this.enemyMaster = enemyMaster;
	}
	public void update(float delta){
		for (int i = 0; i < erd.size; i++){
//			erd.get(i).update(delta, enemyMaster.shaper);
			if (erd.get(i).dead) erd.removeIndex(i);
		}
	}
	public void newDazzler(float x, float y) {
		erd.add(new EnemyRazzle(x, y));
	}
	@Override
	public void renderFilled(ShapeRenderer shaper) {
		
		
	}

}
