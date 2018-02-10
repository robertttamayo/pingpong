package com.madcoatgames.newpong.look;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;	
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.play.Ball;
import com.madcoatgames.newpong.play.CloneBall;
import com.madcoatgames.newpong.util.FilledShapeRenderable;
import com.madcoatgames.newpong.util.LineShapeRenderable;
import com.madcoatgames.newpong.util.Point;
import com.madcoatgames.newpong.util.TextureRenderable;

public class CloneBallRenderer implements FilledShapeRenderable, LineShapeRenderable {
	private Array<CloneBall> cloneBalls = new Array<CloneBall>();
	private CloneBall cloneBall;
	private Color color = new Color();
	private Color color2 = new Color();
	private Color color3 = new Color();
	private BallShape shape;
	
	public CloneBallRenderer (){
		
	}
	/**
	 * ShapeRenderer.begin() must be called first
	 */
	public void setCloneBalls(Array<CloneBall> cloneBalls) {
		this.cloneBalls = cloneBalls;
	}
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
		
		for (int index = 0; index < cloneBalls.size; index++) {
			cloneBall = cloneBalls.get(index);
	
			boolean live = cloneBall.isLive();
			
			for (int i = cloneBall.getTrail().size - 1; i >= 0; i--){
				if (live){
					shaper.setColor(0, 0, 0, (20-i)/40f);
				} else {
					shaper.setColor(.25f, .25f, .25f, (20-i)/40f);
				}
				if (cloneBall.getTrail().get(i).isSpecial()){
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
				shaper.setColor(color.r, color.g, color.b, (20-i)/40f);
				shaper.circle(cloneBall.getTrail().get(i).x(), cloneBall.getTrail().get(i).y(), cloneBall.getRadius()*((20-i)/20f));
			}
			
			shaper.setColor(color);
			shaper.circle(cloneBall.getPos().x, cloneBall.getPos().y, cloneBall.getRadius());
		
			//////////////////////////////////////////////////////////////
			
			shape = cloneBall.getBallShape();
			
			if (cloneBall.isLive()) {
				shaper.setColor(color);
			} else {
				shaper.setColor(Color.BLACK);
			}
			shaper.circle(shape.getCenter().x, shape.getCenter().y, shape.getRadiusYolk());
			
			if (cloneBall.isLive()) {
				shaper.setColor(Color.BLACK);
			} else {
				shaper.setColor(color);
			}
			for (int i = 0; i < shape.getAngles().length; i++){
//				shaper.arc(shape.getArcs().get(i).x, shape.getArcs().get(i).y
//						, shape.getRadiusShell(), shape.getAngles()[i] - 45, 90);
			}
		}
	
	}
	@Override
	public void renderLine(ShapeRenderer shaper) {
		shape = cloneBall.getBallShape();
		
//		shaper.setColor(C);
//		shaper.circle(shape.getCenter().x, shape.getCenter().y, shape.getRadiusYolk());
		
		shaper.setColor(Color.WHITE);
		for (int i = 0; i < shape.getAngles().length; i++){
			shaper.arc(shape.getArcs().get(i).x, shape.getArcs().get(i).y
					, shape.getRadiusShell(), shape.getAngles()[i] - 45, 90);
		}
		
	}
	public BallShape getBallShape(){
		return shape;
	}

}
