package com.madcoatgames.newpong.look;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.madcoatgames.newpong.util.BatchDrawable;
import com.madcoatgames.newpong.util.Global;

public class BackgroundMaster implements BatchDrawable, Disposable{
	private TextureRegion background;
	private Texture tex;
	private Color color = new Color();
	public BackgroundMaster(String img){
//		tex = new Texture(Gdx.files.internal("img/" + img));
//		tex = new Texture(Gdx.files.internal("img/" + "winter.jpg"));
		tex = new Texture(Gdx.files.internal("img/" + "space2.jpg"));
		background = new TextureRegion(tex);
	}
	public void setColor(Color color){
		this.color = color;
	}
	/**
	 * SpriteBatch.begin() must be called first
	 * @param batch
	 */
	public void drawBatched(SpriteBatch batch){
//		batch.setColor(Color.WHITE);
//		batch.draw(background, 0, 0, Global.width(), Global.height());
	}
	@Override
	public void dispose() {
		tex.dispose();
	}

}
