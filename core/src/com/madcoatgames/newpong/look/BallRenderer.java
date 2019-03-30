package com.madcoatgames.newpong.look;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.play.Ball;
import com.madcoatgames.newpong.util.FilledShapeRenderable;
import com.madcoatgames.newpong.util.Global;
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
	private Array<Particle> particles;
	private float particleTime = 0f;
	
	private boolean darkMode = false;
	private int angleHalf, angleFull;
	
	private float cr, cg, cb;
	
	public BallRenderer (Ball ball){
		this.angleHalf = 45;
		this.angleFull = 90;
		this.ball = ball;
		this.texture = new Texture(Gdx.files.internal("img/balldraft.png"));
		if (Global.getGameMode() == Global.MISSIONS) {
			particles = new Array<Particle>();
		}
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
		
		if (!ball.hasInfector()) {
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
			if (ball.hasInfector()) {
//				shaper.setColor(color.r * .75f, color.g * .75f, color.b * .75f, 1);
				shaper.setColor(0f, .8f, 0f, 1);
			} else {
				shaper.setColor(color);
			}
		} else {
			shaper.setColor(Color.BLACK);
		}
		shaper.circle(shape.getCenter().x, shape.getCenter().y, shape.getRadiusYolk());
		
		if (ball.isLive()) {
			if (ball.hasInfector()) {
				shaper.setColor(.8f, .8f, .8f, 1f);
			} else {
				if (darkMode) {
					shaper.setColor(color);
				} else {
					shaper.setColor(Color.WHITE);
				}
			}
		} else {
			if (darkMode) {
				shaper.setColor(color);
			} else {
				shaper.setColor(Color.WHITE);
			}
		}
		for (int i = 0; i < shape.getAngles().length; i++){
			shaper.arc(shape.getArcs().get(i).x, shape.getArcs().get(i).y
					, shape.getRadiusShell(), shape.getAngles()[i] - angleHalf, angleFull);
		}
		if (Global.getGameMode() == Global.MISSIONS) {
			if (Global.infectedLevel == 1) {
				this.cr = 0f;
				this.cg = .8f;
				this.cb = 0f;
			} else if (Global.infectedLevel == 2) {
				this.cr = .6f;
				this.cg = 1f;
				this.cb = 0f;
			} else {
				cr = .8f;
				cg = .5f;
				cb = 1f;
			}
			renderParticles(shaper);
		}
	}
	public void update(float delta) {
		if (ball.hasInfector()) {
			particleTime += delta;
		}
		if (particleTime > .1f) {
			addParticles(ball.getPos().x, ball.getPos().y);
			particleTime = 0f;
		}
		updateParticles(delta);
	}
	public void renderParticles(ShapeRenderer shaper) {
//		Gdx.gl.glEnable(GL20.GL_BLEND);
		for(int i = 0; i < particles.size; i++) {
			particles.get(i).render(shaper);
		}
//		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	@Override
	public void renderLine(ShapeRenderer shaper) {
		shape = ball.getBallShape();
		
//		shaper.setColor(C);
//		shaper.circle(shape.getCenter().x, shape.getCenter().y, shape.getRadiusYolk());
		
		shaper.setColor(Color.BLACK);
		for (int i = 0; i < shape.getAngles().length; i++){
			shaper.arc(shape.getArcs().get(i).x, shape.getArcs().get(i).y
					, shape.getRadiusShell(), shape.getAngles()[i] - angleHalf, angleFull);
		}
		
	}
	private void addParticles(float x, float y) {
		float range = 50f;
		for (int i = 0; i < 5; i++) {
			particles.add(new Particle(x + range * (float)Math.random() - range/2f, 
					y + range * (float)Math.random() - range/2f));
		}
	}
	private void updateParticles(float delta) {
		for (int i = 0; i < particles.size; i++) {
			particles.get(i).update(delta);
			if (particles.get(i).alpha <= 0f) {
				particles.removeIndex(i);
			}
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
	private class Particle {
		public float stateTime = 0f;
		public float alpha = 1f;
		private float x, y, radius, rise;
		public Particle (float x, float y) {
			radius = 25f * (float)Math.random() * .5f;
			this.x = x;
			this.y = y;
			this.rise = (float) Math.random() * 10f + 20f;
		}
		public void update(float delta) {
			stateTime += delta;
			alpha = Math.max(.5f - stateTime/2f, 0f);
			y += rise*delta;
		}
		public void render(ShapeRenderer shaper) {
			shaper.setColor(cr, cg, cb, alpha);
			shaper.circle(x, y, radius);
		}
	}

}
