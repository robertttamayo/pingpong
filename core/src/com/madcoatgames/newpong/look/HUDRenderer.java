package com.madcoatgames.newpong.look;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.madcoatgames.newpong.play.Button;
import com.madcoatgames.newpong.play.Button.ButtonType;
import com.madcoatgames.newpong.records.SaveDataCache;
import com.madcoatgames.newpong.util.FontSizeTimer;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.TouchTarget;
import com.madcoatgames.newpong.util.TriColorChanger;

public class HUDRenderer implements Disposable{
	private HUDPlacement placement;
	private Rectangle menu;
	private Array<Button> buttons = new Array<Button>();
	private Array<Button> activeButtons = new Array<Button>();
	private BitmapFont font;
	private BitmapFontCache fontCache;
	private Color color = new Color();
	private GlyphLayout gl = new GlyphLayout();
	
	private Button scoreButton;
	private Button backButton;
	private Button playButton;
	
	private final String playMessage = "Play Again";
	private final String backMessage = "Quit";
	
	private float scale = 1f;
	private int lastScore = -1;
	
	private boolean renderScoreEnabled = true;
	
	String message = "Go!";
	
	private String previousMessage = "";
	
	private FontSizeTimer fst = new FontSizeTimer();
	
	public HUDRenderer(){
		backButton = new Button(ButtonType.BACK);
		scoreButton = new Button(ButtonType.SCORE);
		playButton = new Button(ButtonType.PLAY_AGAIN);
		
		buttons.add(backButton);
		buttons.add(scoreButton);
		buttons.add(playButton);
		
		placement = new HUDPlacement();
		placement.settle(buttons);
		
		font = new BitmapFont(Gdx.files.internal("font/swhite.fnt"));
		fontCache = new BitmapFontCache(font);
		fontCache.getFont().setFixedWidthGlyphs("0123456789");
		
	}
	public void drawCountdown(TriColorChanger tcc, SpriteBatch batch, float seconds) {
		int secondsInt = (int) Math.ceil(seconds);
		message = Integer.toString(secondsInt);
		if (!message.equals(previousMessage)) {
			previousMessage = message;
			fst.reset();
			fst.setActive(true);
		}
		calcFontLook(tcc);
		scale = 1 - .5f*fst.getTimer() / fst.getCycle();
		scale = 1;
		fontCache.clear();
		fontCache.getFont().getData().setScale(scale);
		fontCache.setColor(color);
		batch.begin();
		
		//gl.reset();
		
		gl.setText(font, message);
		float x = (Global.centerWidth() - (gl.width/2f));
		float y = (Global.centerHeight() + (gl.height/2f));
		
		fontCache.addText(message, x, y);
		fontCache.draw(batch);
		
		batch.end();
	}
	private void drawPlay(TriColorChanger tcc, SpriteBatch batch, int score){
		if (score != lastScore){
			lastScore = score;
			fst.reset();
			fst.setActive(true);
		}
		calcFontLook(tcc);
		
		scale = 1 + fst.getTimer() / fst.getCycle();
		
		fontCache.clear();
		fontCache.getFont().getData().setScale(scale);
		fontCache.setColor(color);
		float x, y;
		if (message.equals("Go!") || renderScoreEnabled) {
			batch.begin();
			
			//gl.reset();
			message = score == 0 ? "Go!" : Integer.toString(score);
			
			
			gl.setText(font, message);
			x = (Global.centerWidth() - (gl.width/2f));
			y = (Global.centerHeight() + (gl.height/2f));
			
			fontCache.addText(message, x, y);
			fontCache.draw(batch);
			
			batch.end();
		}
		
		fontCache.setColor(tcc.c2);
		batch.begin();
		
		x = 10f;
		y = Global.height() - 10f;
		message = "score: " + Integer.toString(score);
		fontCache.getFont().getData().setScale(1);
		fontCache.addText(message, x, y);
		
		fontCache.draw(batch);
		
		batch.end();
		
	}
	private void drawGameOver(TriColorChanger tcc, SpriteBatch batch, ShapeRenderer shaper, int score){
		shaper.begin(ShapeType.Filled);
		for (Button b : buttons){
			if (b.getType() == ButtonType.SCORE) {
				continue;
			}
			shaper.setColor(tcc.c1);
			shaper.rect(b.x, b.y, b.width, b.height);
			shaper.setColor(0, 0, 0, 0f);
			shaper.rect(
					b.x + b.getPaddingLeft(), b.y + b.getPaddingBottom(), 
					b.width - b.getPaddingWidth(), b.height - b.getPaddingHeight()
					);
		}
		shaper.end();
	
		batch.begin();
		
		fontCache.clear();
		
		fontCache.getFont().getData().setScale(1);
		fontCache.setColor(tcc.c2);
		
		message = "Your score: " + String.valueOf(SaveDataCache.getCurrentPoints(Global.getGameMode()));
		message += "\nBest score so far: " + SaveDataCache.getHighestScoreThisGame(Global.getGameMode());
		message += "\nRecord: " + SaveDataCache.getHighestString(Global.getGameMode());
//		fontCache.addText("Your score: " + String.valueOf(SaveDataCache.getCurrentPoints()), 350, 350);
//		fontCache.addText("Best score so far: " + SaveDataCache.getHighestScoreThisGame(), 350, 300);
//		fontCache.addText("Record: " + SaveDataCache.getHighestString(), 350, 250);
		
		gl.reset();
		gl.setText(fontCache.getFont(), message);
		fontCache.setText(
				message, 
				scoreButton.getCenterPaddingX() - (gl.width / 2f), 
				scoreButton.getCenterPaddingY() + (gl.height / 2f)
				);
		fontCache.draw(batch);
//		gl.reset();
		gl.setText(fontCache.getFont(), playMessage);
		fontCache.setText(
				playMessage, 
				playButton.getCenterPaddingX() - (gl.width / 2f),
				playButton.getCenterPaddingY() + (gl.height / 2f)
				);
		fontCache.draw(batch);
//		gl.reset();
		gl.setText(fontCache.getFont(), backMessage);
		fontCache.setText(
				backMessage, 
				backButton.getCenterPaddingX() - (gl.width / 2f),
				backButton.getCenterPaddingY() + (gl.height / 2f)
				);
		fontCache.draw(batch);
		
		batch.end();
		
		
	}
	public void draw(TriColorChanger tcc, SpriteBatch batch, ShapeRenderer shaper, int score, boolean countdownInProgress){
		switch (MenuOperator.getType()){
		case (MenuOperator.GAMEOVER):
			drawGameOver(tcc, batch, shaper, score);
			break;
		case (MenuOperator.PLAY):
			if (countdownInProgress) {
//				drawGameOver(tcc, batch, shaper, score);
			} else {
				drawPlay(tcc, batch, score);
			}
			break;
//		case (MenuOperator.COUNTDOWN):
//			drawCountdown(tcc, batch, );
		}
		
	}
	public Array<Button> getButtons(){
		return buttons;
	}
	
	public void calcFontLook(TriColorChanger tcc){
		float delta = Gdx.graphics.getDeltaTime();
		float alpha = 0f;
		if (fst.isActive()){
			alpha = (1f - fst.getTimer()/fst.getCycle());
			fst.update(delta);
		}
		color.a = alpha;
		color.r = tcc.c2.r;
		color.g = tcc.c2.g;
		color.b = tcc.c2.b;
	}
	@Override
	public void dispose() {
		font.dispose();
		
	}
	
	

}
