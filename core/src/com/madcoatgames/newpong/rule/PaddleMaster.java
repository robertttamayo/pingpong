package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.look.PaddleRenderer;
import com.madcoatgames.newpong.play.Paddle;
import com.madcoatgames.newpong.util.FilledShapeRenderable;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.TextureRenderable;

public class PaddleMaster {
	private Paddle rPaddle, lPaddle;
	private PaddleRenderer rpr, lpr;
	private Array<FilledShapeRenderable> fsr = new Array<FilledShapeRenderable>();
	private Array<TextureRenderable> textureRenderables = new Array<TextureRenderable>();
	private Array<Paddle> paddles = new Array<Paddle>();
	private Color color = new Color();

	public PaddleMaster() {
		rPaddle = new Paddle(Paddle.RIGHT);
		lPaddle = new Paddle(Paddle.LEFT);
		rpr = new PaddleRenderer(rPaddle);
		lpr = new PaddleRenderer(lPaddle);
		initPaddleDimensions();
		initPaddlePosition();
		paddles.addAll(rPaddle, lPaddle);
		textureRenderables.add(rpr);
		textureRenderables.add(lpr);
	}

	public void update(Color color, float delta) {
		this.color = color;
		rPaddle.setColor(color);
		lPaddle.setColor(color);
		rPaddle.update(delta);
		lPaddle.update(delta);
	}

	public Array<FilledShapeRenderable> getFilled() {
		fsr.clear();
		fsr.addAll(rpr, lpr);
		return fsr;
	}

	public Array<TextureRenderable> getTextureRenderables() {
		return this.textureRenderables;
	}

	public Array<Paddle> getPaddles() {
		return paddles;
	}

	private void initPaddleDimensions() {
		if (Global.getGameMode() == Global.MISSIONS) {
			rPaddle.setSize(135, 160);
			lPaddle.setSize(135, 160);
		} else {
			rPaddle.setSize(150, 180);
			lPaddle.setSize(150, 180);
		}
	}

	private void initPaddlePosition() {
		rPaddle.setPosition(Global.width() - rPaddle.width,
				Global.centerHeight() - rPaddle.height/2f);
		lPaddle.setPosition(0, Global.centerHeight() - lPaddle.height/2f);
	}

}
