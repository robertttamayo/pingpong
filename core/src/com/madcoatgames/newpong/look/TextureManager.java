package com.madcoatgames.newpong.look;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureManager {
	private Texture ball;
	private Texture paddle;
	
	public TextureManager(){
		ball = new Texture(Gdx.files.internal("img/balldraft.png"));
		paddle = new Texture(Gdx.files.internal("img/paddledraft.png"));
	}
	public Texture getBall(){
		return this.ball;
	}
	public Texture getPaddle(){
		return this.paddle;
	}

}
