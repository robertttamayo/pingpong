package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.audio.SoundMaster;
import com.madcoatgames.newpong.look.BallRenderer;
import com.madcoatgames.newpong.look.BallShape;
import com.madcoatgames.newpong.look.CloneBallRenderer;
import com.madcoatgames.newpong.look.MenuOperator;
import com.madcoatgames.newpong.play.Ball;
import com.madcoatgames.newpong.play.Ball.Type;
import com.madcoatgames.newpong.play.CloneBall;
import com.madcoatgames.newpong.play.Table;
import com.madcoatgames.newpong.util.FilledShapeRenderable;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.LineShapeRenderable;
import com.madcoatgames.newpong.util.Point;
import com.madcoatgames.newpong.util.TextureRenderable;
import com.madcoatgames.newpong.util.Trail;

public class BallMaster {
	private Ball ball;
	private Array<CloneBall> cloneBalls = new Array<CloneBall>();
	private CloneBall cloneBall;
	private CloneBallRenderer cloneBallRenderer;
	private Table table;
	private BallRenderer br;
	private Color color = new Color();
	private Color color2 = new Color();
	private Color color3 = new Color();
	
	private Array<FilledShapeRenderable> filled = new Array<FilledShapeRenderable>();
	
	public BallMaster (Table table){
		ball = new Ball();
		resetBall();
		
		this.table = table;
		
		br = new BallRenderer(ball);
		cloneBallRenderer = new CloneBallRenderer();
		filled.add(br);
		filled.add(cloneBallRenderer);
	}
	public void update(float delta){
		update(delta, ball);
		ball.update(delta);
		if (Global.getGameMode() == Global.MISSIONS) {
			updateCloneBalls(delta);
		}
	}
	private void update(float delta, Ball ball) {
		//y collision
		moveY(delta, ball);
		if (ball.getVel().y > 0) {
			vertUp(ball);
		} else {
			vertDown(ball);
		}
		//x collision
		moveX(delta, ball);
		if (ball.getVel().x > 0) {
			if (ball instanceof CloneBall) {
				horRightClone((CloneBall) ball);
			} else {
				horRight();
			}
		} else {
			if (ball instanceof CloneBall) {
				horLeftClone((CloneBall) ball);
			} else {
				horLeft();
			}
		}
		ball.setVel(ball.getVel().nor().scl(ball.getPush()));
		ball.moveBounds();
		if (ball.isLive()) {
			br.setColor(color);
			br.setColor2(color2);
			br.setColor3(color3);
			cloneBallRenderer.setColor(color);
			cloneBallRenderer.setColor2(color2);
			cloneBallRenderer.setColor3(color3);
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

		
	}
	private void updateCloneBalls(float delta) {
		for (int i = 0; i < cloneBalls.size; i++ ) {
			cloneBall = cloneBalls.get(i);
			update(delta, (Ball) cloneBall);
			cloneBall.update(delta);
			if (cloneBall.isDead()) {
				cloneBalls.removeIndex(i);
			}
		}
		cloneBallRenderer.setCloneBalls(cloneBalls);
	}
	public void reset() {
		resetBall();
	}
	private void resetBall(){
		ball.reset();
		ball.setBounds(ball.getPos().x - ball.getRadius(), ball.getPos().y - ball.getRadius(),
				ball.getRadius()*2f, ball.getRadius() * 2f);
		ball.setPos(Global.ballDefaultPos());
		ball.setVel(Global.ballDefaultVel());
		ball.setPush(Global.ballDefaultPush());
	}
	private void vertUp(Ball ball){
		float ballTop = ball.getPos().y + ball.getRadius();
		if (ballTop > table.top()){
			float yMod = ballTop - table.top();
			ball.getPos().y -= yMod * 2;
			ball.getVel().y *= -1;
			//sound
			SoundMaster.paddleq = true;
			if (ball.type == Type.NORMAL) {
				if (ball.getVel().x > 0){
					BallShape.addLeft();
				} else {
					BallShape.addRight();
				}
			} else if (ball.type == Type.CLONE) {
				ball.verticalCollisionCount++;
			}
		}
	}
	private void vertDown(Ball ball){
		float ballBottom = ball.getPos().y - ball.getRadius();
		if (ballBottom < table.bottom()){
			float yMod = table.bottom() - ballBottom;
			ball.getPos().y += yMod * 2;
			ball.getVel().y *= -1;
			SoundMaster.paddleq = true;
			
			if (ball.type == Type.NORMAL) {
				if (ball.getVel().x > 0){
					BallShape.addRight();
				} else {
					BallShape.addLeft();
				}
			} else if (ball.type == Type.CLONE) {
				ball.verticalCollisionCount++;
			}
			
		}
	}
	private void moveY(float delta, Ball ball){
		ball.getPos().y += ball.getVel().y * delta;
	}
	private void horRight(){
		float ballRight = ball.getPos().x + ball.getRadius();
		if (ballRight > table.right()) {
			float xMod = ballRight - table.right();
			ball.getPos().x -= xMod * 2; //1 xMod would be point of contact, btw
			ball.getVel().x *= -1;
			if (ball.isLive() && Global.getGameMode() != Global.MISSIONS) {
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
	private void horRightClone(CloneBall ball){
		float ballRight = ball.getPos().x + ball.getRadius();
		if (ballRight > table.right()) {
//			float xMod = ballRight - table.right();
//			ball.getPos().x -= xMod * 2; //1 xMod would be point of contact, btw
//			ball.getVel().x *= -1;
			ball.setLive(false); 
			ball.setDead(true);

		}
	}
	private void horLeftClone(CloneBall ball){
		float ballLeft = ball.getPos().x - ball.getRadius();
		if (ballLeft < table.left()){
//			float xMod = table.left() - ballLeft;
//			ball.getPos().x += xMod * 2;
//			ball.getVel().x *= -1;
			
			ball.setLive(false);
			ball.setDead(true);
			
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
		} else if (Global.getGameMode() == Global.MISSIONS) {
			BallPaddleMaster.resetHits();
		}
	}
	private void moveX(float delta, Ball ball){
		ball.getPos().x += ball.getVel().x * delta;
	}
	public Array<FilledShapeRenderable> getFilled(){
		
		return filled;
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
	public void addCloneBall(CloneBall cloneBall) {
		cloneBalls.add(cloneBall);
	}
	public Array<CloneBall> getCloneBalls(){
		return this.cloneBalls;
	}
	public BallRenderer getBallRenderer() {
		return this.br;
	}
}
