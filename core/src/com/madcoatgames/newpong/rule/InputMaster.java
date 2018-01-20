package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.audio.SoundMaster;
import com.madcoatgames.newpong.look.HUDRenderer;
import com.madcoatgames.newpong.look.MenuOperator;
import com.madcoatgames.newpong.nongame.HomeScreen;
import com.madcoatgames.newpong.nongame.HomeScreen.Action;
import com.madcoatgames.newpong.nongame.MissionSelectScreen;
import com.madcoatgames.newpong.play.Button;
import com.madcoatgames.newpong.play.Button.ButtonType;
import com.madcoatgames.newpong.play.Paddle;
import com.madcoatgames.newpong.util.Global;

public class InputMaster implements InputProcessor{
	private Array<Vector2> touches = new Array<Vector2>();
	private boolean valid = false;
	
	public InputMaster(){
		for (int i = 0; i < 4; i++){
			touches.add(new Vector2());
		}
	}
	public void update(){
		resetTouches();
		if (!Gdx.input.isTouched()){
			valid = true;
			return;
		}
		if (!valid) {
			return;
		}
		for (int i = 0; i < touches.size; i++){
			if (Gdx.input.isTouched(i)){
				float x = (float)(Gdx.input.getX(i) / (float)Gdx.graphics.getWidth()) * Global.width();
				float y = (float)(Gdx.input.getY(i) / (float)Gdx.graphics.getHeight()) * Global.height();
				y = Global.height() - y;
				touches.get(i).set(x, y);
			}
		}
	}
	private boolean updatePaddleKeys(Paddle paddle) {
		boolean keyPressed = false;
		
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (paddle.getId() == Paddle.LEFT) {
				paddle.setPosition(paddle.x, paddle.y + 20f);
			}
			keyPressed = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (paddle.getId() == Paddle.LEFT) {
				if (paddle.getId() == Paddle.LEFT) {
					paddle.setPosition(paddle.x, paddle.y - 20f);
				}
			}
			keyPressed = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.I)) {
			if (paddle.getId() == Paddle.RIGHT) {
				paddle.setPosition(paddle.x, paddle.y + 20f);
			}
			keyPressed = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.K)) {
			if (paddle.getId() == Paddle.RIGHT) {
				paddle.setPosition(paddle.x, paddle.y - 20f);
			}
			keyPressed = true;
		}
		
		clampY(paddle);
		
		return keyPressed;
	}
	private void resetTouches(){
		for (Vector2 v2 : touches){
			v2.set(-1, -1); //somewhere off screen
		}
	}
	public void updateHomeScreen(HomeScreen screen){
		for (Button b : screen.getButtons()){
			for (int i = 0; i < touches.size; i++){
				if (b.contains(touches.get(i))){
					if (b.getType() == ButtonType.START) {
						screen.setAction(Action.START);
					} 
				}
			}
		}
	}
	public void updateMissionSelectScreen(MissionSelectScreen screen){
		for (Button b : screen.getButtons()){
			for (int i = 0; i < touches.size; i++){
				if (b.contains(touches.get(i))){
					if (b.getType() == ButtonType.MODE_ARCADE) {
						screen.setAction(MissionSelectScreen.Action.ARCADE);
					} else if (b.getType() == ButtonType.MODE_BATTLE){
						screen.setAction(MissionSelectScreen.Action.BATTLE);
					}
				}
			}
		}
	}
	public void updateHUD(HUDRenderer hud){
		if (MenuOperator.getType() != MenuOperator.GAMEOVER){
			return;
		}
		
		for (Button b : hud.getButtons()){
			for (int i = 0; i < touches.size; i++){
				if (b.contains(touches.get(i))){
					if (b.getType() == ButtonType.BACK) {
//						SoundMaster.heroDeadq = true;
						MenuOperator.shutdown();
					} else if (b.getType() == ButtonType.PLAY_AGAIN){
						SoundMaster.specialq = true;
						MenuOperator.start();
					}
				}
			}
		}
	}
	public void updatePaddle(Paddle paddle){
		if (updatePaddleKeys(paddle)) {
			paddle.setTouched(false);
			return;
		}
		if (!Gdx.input.isTouched()){
			paddle.setTouched(false);
			return;
		}
		for (Vector2 v2 : touches){
			if (v2.x < 0) {
				continue;
			}
			/*
			if (paddle.contains(v2)){
				if (paddle.isTouched()){
					paddle.setPosition(paddle.x, v2.y - paddle.getTouchHeight());
					if (paddle.y < 0) {
						paddle.y = 0;
					}
					if (paddle.y + paddle.height > Global.height()){
						paddle.y = Global.height() - paddle.height;
					}
				} else { //first touch
					paddle.setTouchHeight(v2.y - paddle.y); //difference between point of touch on paddle and bottom of paddle
					paddle.setTouched(true);
				}
			}
			*/
			if (v2.x > Global.centerWidth()){
				if (paddle.getId() == Paddle.RIGHT) {
					paddle.setPosition(paddle.x, v2.y - paddle.height/2f);
					clampY(paddle);
				}
			} else if (v2.x < Global.centerWidth()){
				if (paddle.getId() == Paddle.LEFT) {
					paddle.setPosition(paddle.x, v2.y - paddle.height/2f);
					clampY(paddle);
				}
			}
		}
		
	}
	private void clampY(Paddle paddle) {
		// clamp y
		if (paddle.y < 0f) {
			paddle.setPosition(paddle.x, 0f);
		}
		if (paddle.y > Global.height() - paddle.height) {
			paddle.setPosition(paddle.x, Global.height() - paddle.height);
		}
	}
	public void updatePaddles(Paddle... paddles){
		for (Paddle paddle : paddles){
			updatePaddle(paddle);
		}
	}
	public void updatePaddles(Array<Paddle> paddles){
		for (Paddle paddle : paddles){
			updatePaddle(paddle);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
