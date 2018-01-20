package com.madcoatgames.newpong.look;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.madcoatgames.newpong.play.Paddle;
import com.madcoatgames.newpong.util.FilledShapeRenderable;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.TextureRenderable;

public class PaddleRenderer implements FilledShapeRenderable, TextureRenderable{
	private Paddle paddle;
	private Rectangle render = new Rectangle();
	private Texture texture;
	//private Color c;
	
	public PaddleRenderer (Paddle paddle){
		this.paddle = paddle;
		this.texture = new Texture(Gdx.files.internal("img/paddledraft.png"));
	}

	@Override
	public void renderFilled(ShapeRenderer shaper) {
		
		//shaper.setColor(paddle.getColor().r/2f, paddle.getColor().g/2f, paddle.getColor().b/2f, 1);
		//shaper.rect(paddle.x, paddle.y, paddle.width, paddle.height);
		float percent = 2f/5f;
		float padding;
		//padding = Math.min(paddle.width - percent*paddle.width, paddle.height - percent*paddle.height);
		//padding /= 2f;
		
		//shaper.setColor(Color.BLACK);
		//shaper.rect(paddle.x + padding, paddle.y + padding, paddle.width - padding * 2f, paddle.height - padding * 2f);
		
		if (paddle.x > Global.centerWidth()){ //left paddle
			render.set(paddle.x, paddle.y, paddle.width/3f, paddle.height);
		} else {
			render.set(paddle.x + 2f*paddle.width/3f, paddle.y, paddle.width/3f, paddle.height);
		}
		shaper.setColor(paddle.getColor());
		if (paddle.isHit()){
			shaper.setColor(Color.WHITE);
		}
		shaper.rect(render.x, render.y, render.width, render.height);
		
		percent = 3f/5f;
		padding = Math.min(render.width - percent*render.width, render.height - percent*render.height);
		padding /= 3f;
		
		shaper.setColor(Color.BLACK);
		shaper.rect(render.x + padding, render.y + padding, render.width - padding * 2f, render.height - padding * 2f);
	}

	@Override
	public void renderTexture(SpriteBatch batch) {
		if (paddle.x > Global.centerWidth()){ //left paddle
			render.set(paddle.x, paddle.y, paddle.width/3f, paddle.height);
		} else {
			render.set(paddle.x + 2f*paddle.width/3f, paddle.y, paddle.width/3f, paddle.height);
		}
		render.x -= render.height/2f;
		batch.draw(texture, render.x, render.y
				, render.height , render.height);
	}

}
