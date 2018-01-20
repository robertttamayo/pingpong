package com.madcoatgames.newpong.look;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;	
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.madcoatgames.newpong.play.Ball;
import com.madcoatgames.newpong.util.FilledShapeRenderable;
import com.madcoatgames.newpong.util.LineShapeRenderable;
import com.madcoatgames.newpong.util.Point;
import com.madcoatgames.newpong.util.TextureRenderable;

public class BallRenderer implements FilledShapeRenderable, LineShapeRenderable, TextureRenderable{
	private Ball ball;
	private Color color = new Color();
	private Color color2 = new Color();
	private Color color3 = new Color();
	private BallShape shape;
	private Texture texture;
	
	public BallRenderer (Ball ball){
		this.ball = ball;
		this.texture = new Texture(Gdx.files.internal("img/balldraft.png"));
	}
	/**
	 * ShapeRenderer.begin() must be called first
	 */
	public Color getColor(){
		return color;
	}
	public void setColor(Color color){
		this.color = color;
	}
	public Color getColor2(){
		return color2;
	}
	public void setColor2(Color color){
		this.color2 = color;
	}
	public Color getColor3(){
		return color3;
	}
	public void setColor3(Color color){
		this.color3 = color;
	}
	@Override
	public void renderFilled(ShapeRenderer shaper) {
		boolean live = ball.isLive();
		
		for (int i = ball.getTrail().size - 1; i >= 0; i--){
			if (live){
				shaper.setColor(0, 0, 0, (20-i)/40f);
			} else {
				shaper.setColor(.25f, .25f, .25f, (20-i)/40f);
			}
			if (ball.getTrail().get(i).isSpecial()){
				float offr, offg, offb;
				offr = (float) (Math.random()*.4f) -.2f;
				offg = (float) (Math.random()*.4f) -.2f;
				offb = (float) (Math.random()*.4f) -.2f;
				float r, g, b;
				//r = (float) Math.random();
				//g = (float) Math.random();
				//b = (float) Math.random();
				r = color.r + offr;
				g = color.g + offg;
				b = color.b + offb;
				shaper.setColor(new Color(r, g, b, 1));
			}
			//shaper.setColor(color.r, color.g, color.b, (20-i)/40f);
			shaper.circle(ball.getTrail().get(i).x(), ball.getTrail().get(i).y(), ball.getRadius()*((20-i)/20f));
		}
		/*
		for (int i = 0; i < 8; i++){
			shaper.setColor(color.r, color.g, color.b, 1-i*.125f);
			shaper.circle(ball.getPos().x, ball.getPos().y, i*4);
		}
		*/
		shaper.setColor(color);
		shaper.circle(ball.getPos().x, ball.getPos().y, ball.getRadius());
		
		
		//////////////////////////////////////////////////////////////
		
		shape = ball.getBallShape();
		
		if (ball.isLive()) {
			shaper.setColor(color);
		} else {
			shaper.setColor(Color.BLACK);
		}
		shaper.circle(shape.getCenter().x, shape.getCenter().y, shape.getRadiusYolk());
		
		if (ball.isLive()) {
			shaper.setColor(Color.WHITE);
		} else {
			shaper.setColor(Color.WHITE);
		}
		for (int i = 0; i < shape.getAngles().length; i++){
			shaper.arc(shape.getArcs().get(i).x, shape.getArcs().get(i).y
					, shape.getRadiusShell(), shape.getAngles()[i] - 45, 90);
		}
	
	}
	@Override
	public void renderLine(ShapeRenderer shaper) {
		shape = ball.getBallShape();
		
//		shaper.setColor(C);
//		shaper.circle(shape.getCenter().x, shape.getCenter().y, shape.getRadiusYolk());
		
		shaper.setColor(Color.BLACK);
		for (int i = 0; i < shape.getAngles().length; i++){
			shaper.arc(shape.getArcs().get(i).x, shape.getArcs().get(i).y
					, shape.getRadiusShell(), shape.getAngles()[i] - 45, 90);
		}
		
	}
	public BallShape getBallShape(){
		return shape;
	}
	@Override
	public void renderTexture(SpriteBatch batch) {
		batch.draw(texture, ball.getBounds().x - ball.getBounds().width/2f, ball.getBounds().y - ball.getBounds().height/2f
				, 90, 90);
	}

}
