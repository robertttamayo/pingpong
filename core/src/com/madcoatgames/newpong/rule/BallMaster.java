package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.audio.SoundMaster;
import com.madcoatgames.newpong.look.BallRenderer;
import com.madcoatgames.newpong.look.BallShape;
import com.madcoatgames.newpong.look.MenuOperator;
import com.madcoatgames.newpong.play.Ball;
import com.madcoatgames.newpong.play.Table;
import com.madcoatgames.newpong.util.FilledShapeRenderable;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.LineShapeRenderable;
import com.madcoatgames.newpong.util.Point;
import com.madcoatgames.newpong.util.TextureRenderable;
import com.madcoatgames.newpong.util.Trail;

public class BallMaster {
	private Ball ball;
	private Table table;
	private BallRenderer br;
	private Color color = new Color();
	private Color color2 = new Color();
	private Color color3 = new Color();
	
	public BallMaster (Table table){
		ball = new Ball();
		resetBall();
		
		this.table = table;
		
		br = new BallRenderer(ball);
	}
	public void update(float delta){
		//y collision
		moveY(delta);
		if (ball.getVel().y > 0) {
			vertUp();
		} else {
			vertDown();
		}
		//x collision
		moveX(delta);
		if (ball.getVel().x > 0) {
			horRight();
		} else {
			horLeft();
		}
		ball.setVel(ball.getVel().nor().scl(ball.getPush()));
		ball.moveBounds();
		if (ball.isLive()) {
			br.setColor(color);
			br.setColor2(color2);
			br.setColor3(color3);
		} else {
			br.setColor(Color.GRAY);
		}
		
		Trail trail = new Trail(ball.getPos().x, ball.getPos().y);
		if (ball.isHit()){
			trail.special();
		}
		ball.getTrail().insert(0, trail);
		if (ball.getTrail().size > 20) {
			ball.getTrail().pop();
		}
		ball.update(delta);
	}
	private void resetBall(){
		ball.setBounds(ball.getPos().x - ball.getRadius(), ball.getPos().y - ball.getRadius(),
				ball.getRadius()*2f, ball.getRadius() * 2f);
		ball.setPos(Global.ballDefaultPos());
		ball.setVel(Global.ballDefaultVel());
		ball.setPush(Global.ballDefaultPush());
	}
	private void vertUp(){
		float ballTop = ball.getPos().y + ball.getRadius();
		if (ballTop > table.top()){
			float yMod = ballTop - table.top();
			ball.getPos().y -= yMod * 2;
			ball.getVel().y *= -1;
			//sound
			SoundMaster.paddleq = true;
			
			if (ball.getVel().x > 0){
				BallShape.addLeft();
			} else {
				BallShape.addRight();
			}
			
		}
	}
	private void vertDown(){
		float ballBottom = ball.getPos().y - ball.getRadius();
		if (ballBottom < table.bottom()){
			float yMod = table.bottom() - ballBottom;
			ball.getPos().y += yMod * 2;
			ball.getVel().y *= -1;
			SoundMaster.paddleq = true;
			
			if (ball.getVel().x > 0){
				BallShape.addRight();
			} else {
				BallShape.addLeft();
			}
			
		}
	}
	private void moveY(float delta){
		ball.getPos().y += ball.getVel().y * delta;
	}
	private void horRight(){
		float ballRight = ball.getPos().x + ball.getRadius();
		if (ballRight > table.right()) {
			float xMod = ballRight - table.right();
			ball.getPos().x -= xMod * 2; //1 xMod would be point of contact, btw
			ball.getVel().x *= -1;
			if (ball.isLive()) {
				SoundMaster.heroHitq = true;
			} else {
				SoundMaster.paddleq = true;
			}
			ball.setLive(false);
			
			checkGame();
			
			if (ball.getVel().y > 0){
				BallShape.addRight();
			} else {
				BallShape.addLeft();
			}
		}
	}
	private void horLeft(){
		float ballLeft = ball.getPos().x - ball.getRadius();
		if (ballLeft < table.left()){
			float xMod = table.left() - ballLeft;
			ball.getPos().x += xMod * 2;
			ball.getVel().x *= -1;
			
			if (ball.isLive()) {
				SoundMaster.heroHitq = true;
			} else {
				SoundMaster.paddleq = true;
			}
			
			ball.setLive(false);
			checkGame();
			
			if (ball.getVel().y > 0){
				BallShape.addLeft();
			} else {
				BallShape.addRight();
			}
		}
	}
	private void checkGame(){
		if (BallPaddleMaster.getNumHits() == 0) {
			return;
		}
		if (Global.getGameMode() == Global.ARCADE){
			System.out.println("Score: " + BallPaddleMaster.getNumHits());
			MenuOperator.failArcade(BallPaddleMaster.getNumHits());
			resetBall();
		}
	}
	private void moveX(float delta){
		ball.getPos().x += ball.getVel().x * delta;
	}
	public FilledShapeRenderable getFilled(){
		return br;
	}
	public Ball getBall(){
		return ball;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor2() {
		return color2;
	}
	public void setColor2(Color color) {
		this.color2 = color;
	}
	public Color getColor3() {
		return color3;
	}
	public void setColor3(Color color) {
		this.color3 = color;
	}
	public LineShapeRenderable getLine(){
		return (LineShapeRenderable) br;
	}
	public TextureRenderable getTextureRenderable(){
		return (TextureRenderable) br;
	}
}
