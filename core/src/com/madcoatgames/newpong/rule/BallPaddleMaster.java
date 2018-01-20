package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.audio.SoundMaster;
import com.madcoatgames.newpong.look.BallShape;
import com.madcoatgames.newpong.look.MenuOperator;
import com.madcoatgames.newpong.play.Ball;
import com.madcoatgames.newpong.play.Paddle;
import com.madcoatgames.newpong.util.Global;

public class BallPaddleMaster {
	private static int numHits = 0;
	
	public void testCollision(Ball ball, Paddle paddle){
		if (ball.getPush() == Global.ballDefaultPush()){
			numHits = 0;
		}
		if (ball.getVel().x > 0){
			testRight(ball, paddle);
		} else {
			testLeft(ball, paddle);
		}
	}
	public void testCollisions(Ball ball, Paddle... paddles){
		for (Paddle paddle : paddles){
			testCollision(ball, paddle);
		}
	}
	public void testCollisions(Ball ball, Array<Paddle> paddles){
		for (Paddle paddle : paddles){
			testCollision(ball, paddle);
		}
	}
	private void testRight(Ball ball, Paddle paddle){
		if (ball.getBounds().overlaps(paddle)){
			if (paddle.isBallCollision()) {
				return;
			} /*else {
				paddle.setBallCollision(true);
				ball.setLive(true);
			}*/
			if (ball.getBounds().x > paddle.x){ //it's behind the paddle, no collision
//				ball.setLive(false);
			} else {
				ball.setVel(reboundRightPaddle(ball, paddle));
				paddle.setBallCollision(true);
				ball.setLive(true);
			}
		} else {
			paddle.setBallCollision(false);
		}
	}
	private void testLeft(Ball ball, Paddle paddle){
		if (ball.getBounds().overlaps(paddle)){
			if (paddle.isBallCollision()) {
				return;
			} /*else {
				paddle.setBallCollision(true);
				ball.setLive(true);
			}*/
			if (ball.getBounds().x + ball.getBounds().width < paddle.x + paddle.width){ //it's behind the paddle, no collision
//				ball.setLive(false);
			} else {
				ball.setVel(reboundLeftPaddle(ball, paddle));
				paddle.setBallCollision(true);
				ball.setLive(true);
			}
		} else {
			paddle.setBallCollision(false);
		}
	}
	private Vector2 reboundRightPaddle(Ball ball, Paddle paddle){
		float angle;
		Vector2 vel = new Vector2(100, 100);
		float push = ball.getPush();
		
		float yContact = ball.getPos().y;
		if (yContact > paddle.y + paddle.height) {
			yContact = paddle.y + paddle.height;
		}
		if (yContact < paddle.y) {
			yContact = paddle.y;
		}
		float paddleCenter = paddle.y + paddle.height/2f;
		float dif = yContact - paddleCenter;
		float maxDif = paddle.height/2f;
		float angleMod = (dif/maxDif);
		
		angle = 180 - angleMod*60;
		vel.setAngle(angle);
		
		sound(ball);
		checkGame(ball);
		ball.setHit(true);
		paddle.setHit(true);
		
		if (vel.y > 0 ){
			BallShape.addLeft();
		} else {
			BallShape.addRight();
		}
		
		return vel.nor().scl(push);
	}
	private Vector2 reboundLeftPaddle(Ball ball, Paddle paddle){
		float angle;
		Vector2 vel = new Vector2(100, 100);
		float push = ball.getPush();
		
		float yContact = ball.getPos().y;
		if (yContact > paddle.y + paddle.height) {
			yContact = paddle.y + paddle.height;
		}
		if (yContact < paddle.y) {
			yContact = paddle.y;
		}
		float paddleCenter = paddle.y + paddle.height/2f;
		float dif = yContact - paddleCenter;
		float maxDif = paddle.height/2f;
		float angleMod = (dif/maxDif);
		
		angle = 0 + angleMod*60;
		vel.setAngle(angle);
		
		sound(ball);
		checkGame(ball);
		ball.setHit(true);
		paddle.setHit(true);
		
		if (vel.y > 0 ){
			BallShape.addRight();
		} else {
			BallShape.addLeft();
		}
		
		return vel.nor().scl(push);
	}
	private void sound(Ball ball){
		if (ball.isLive()) {
			SoundMaster.paddleq = true;
		} else {
			SoundMaster.paddleq = true;
		}
	}
	private void checkGame(Ball ball){
		if (Global.getGameMode() == Global.ARCADE){
			ball.setPush(ball.getPush() + 10);
			numHits++;
			MenuOperator.start();
			System.out.println("Ball Push: " + ball.getPush());
			System.out.println("Num Hits" + numHits);
		}
	}
	public static int getNumHits(){ //should not be set externally
		return numHits;
	}
}
