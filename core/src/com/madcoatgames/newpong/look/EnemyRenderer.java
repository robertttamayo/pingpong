package com.madcoatgames.newpong.look;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.madcoatgames.newpong.enemy.Enemy;
import com.madcoatgames.newpong.enemy.EnemyType;

public class EnemyRenderer implements Disposable{
	private Animation ufoMedium_r, ufoMedium_l;
	private Animation anim;
	
	private float scale = 8;
	private float margin = 1;
	private float pixel = 1;
	private int length = 10;
	
	private Texture t;
	private TextureRegion[] split, mirror;
	
	private Color originalColor;
	private Color randomColor = new Color();
	
	public EnemyRenderer(){
		t = new Texture(Gdx.files.internal("img/bot2may31.png"));
		loadUfoMedium();
	}
	public void loadUfoMedium(){
		split = TextureRegion.split(t, length, length)[1];
		mirror = TextureRegion.split(t, length, length)[1];
		for (TextureRegion r : mirror) r.flip(true, false);
		
		ufoMedium_r 	= new Animation(.125f, 	mirror[12]);
		ufoMedium_l 	= new Animation(.125f, 	split[12]);
	}
	/**
	 * batch.begin() must be called first
	 * @param batch
	 * @param e
	 */
	public void drawEnemy(SpriteBatch batch, Enemy e){
		switch(e.getType()){
		case(EnemyType.UFO_MEDIUM):
			prep_UFO_MEDIUM(batch, e);
			break;
			
		}
	}
	private void prep_UFO_MEDIUM(SpriteBatch batch, Enemy e){
		originalColor = batch.getColor();
		
//		if (e.isHit() || (e.isInfected() && e.getInfectedTime() > 0f)){
		if (e.getStateTime() < 1f) {
			batch.setColor(originalColor.r, originalColor.g, originalColor.b, Math.min(e.getStateTime()*.75f, 1f));
		}
		if (e.isHit()) {
			batch.setColor((float) Math.random(), (float) Math.random(), (float) Math.random()/3f, 1f);
		}
		
		if (e.getDir() > 0) {
			anim = ufoMedium_r;
		} else {
			anim = ufoMedium_l;
		}
		
		drawAnim(batch, e);
		if (e.isHit()) {
			batch.setColor(originalColor);
		}
	}
	private void drawAnim(SpriteBatch batch, Enemy enemy){
		float angriness = 0f;
		if (enemy.isAngry()) {
			angriness = 20f;
		}
		batch.draw(
				(TextureRegion) anim
				.getKeyFrame(
						enemy
						.getStateTime(), true)
				, enemy.x - margin*scale - angriness/2f
				, enemy.y - margin*scale - angriness/2f
				, enemy.getRenderWidth() + angriness, enemy.getRenderHeight() + angriness);
	}
	@Override
	public void dispose() {
		t.dispose();
	}
}
