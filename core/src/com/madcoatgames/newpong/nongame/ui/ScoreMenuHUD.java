package com.madcoatgames.newpong.nongame.ui;


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
import com.madcoatgames.newpong.records.SaveDataProcessor;
import com.madcoatgames.newpong.records.Score;
import com.madcoatgames.newpong.util.FontSizeTimer;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.TouchTarget;
import com.madcoatgames.newpong.util.TriColorChanger;

public class ScoreMenuHUD implements Disposable{
	private ScoreMenuHUDPlacement placement;
	private Rectangle menu;
	private Array<Button> buttons = new Array<Button>();
	private Array<Button> globalButtons = new Array<Button>();
	private Array<Button> localButtons = new Array<Button>();
	private BitmapFont font;
	private BitmapFontCache fontCache;
	private Color color = new Color();
	private GlyphLayout gl = new GlyphLayout();
	
	private Button titleButton;
	private Button arcadeButton;
	private Button battleButton;
	private Button exitScoreButton;
	private Button localScoresButton;
	private Button globalScoresButton;
	private Button selectScoreButton;
	
	private final String titleMessage = "Scores";
	private final String localTitleMessage = "Your Scores";
	private final String globalTitleMessage = "Global Scores";
	private final String arcadeMessage = "Solo";
	private final String battleMessage = "Enemies";
	private final String exitScoreMessage = "Back";
	
	private final String localScoresMessage = "Your Scores";
	private final String globalScoresMessage = "Global Scores";
	
	private final String moreInfoMessage = "For more scores, visit https://www.roberttamayo.com/revolve/scores";
	
	private float scale = 1f;
	private float scoreScale = .6f;
	
	private SaveDataCache saveDataCache;
	private SaveDataProcessor saveDataProcessor;
	private String soloMessage;
	private String enemiesMessage;
//	private RemoteScores remoteScores;
	
	private FontSizeTimer fst = new FontSizeTimer();
	
	public enum Scope {
		GLOBAL, LOCAL
	}
	public enum Mode {
		SOLO, ENEMIES
	}

	public Scope scope = Scope.LOCAL;
	public Mode mode = Mode.SOLO;
	
	public boolean selectScreen = true;
	
	public ScoreMenuHUD(){
		saveDataCache = new SaveDataCache();
		saveDataProcessor = new SaveDataProcessor();
		
		soloMessage = "Solo\n\n";
		Array<Score> scores = SaveDataCache.getScores();
		for (int i = scores.size - 1, j = 1; i >= scores.size - 5; i--, j++) {
			soloMessage += Integer.toString(j) + ".    " + scores.get(i).getPoints() + "\n";
		}
		
		enemiesMessage = "Enemies\n\n";
		Array<Score> enemyScores = SaveDataCache.getEnemyScores();
		for (int i = enemyScores.size - 1, j = 1; i >= enemyScores.size - 5; i--, j++) {
			enemiesMessage += Integer.toString(j) + ".    " + enemyScores.get(i).getPoints() + "\n";
		}
		
		System.out.println(soloMessage);
		System.out.println(enemiesMessage);
		titleButton = new Button(ButtonType.TITLE);
		arcadeButton = new Button(ButtonType.MODE_ARCADE);
		battleButton = new Button(ButtonType.MODE_BATTLE);
		exitScoreButton = new Button(ButtonType.EXIT_SCORE);
		localScoresButton = new Button(ButtonType.SCORE_SCOPE_LOCAL);
		globalScoresButton = new Button(ButtonType.SCORE_SCOPE_GLOBAL);
		selectScoreButton = new Button(ButtonType.SELECT_SCORE);
		
		buttons.add(titleButton);
		buttons.add(localScoresButton);
		buttons.add(globalScoresButton);
		buttons.add(exitScoreButton);
		
		localButtons.add(selectScoreButton);
		localButtons.add(titleButton);
		
		placement = new ScoreMenuHUDPlacement();
		placement.settle(buttons);
		placement.settle(localButtons);
		
		font = new BitmapFont(Gdx.files.internal("font/swhite.fnt"));
		fontCache = new BitmapFontCache(font);
		fontCache.getFont().setFixedWidthGlyphs("0123456789");
		
	}

	public void draw(TriColorChanger tcc, SpriteBatch batch, ShapeRenderer shaper){
		if (selectScreen) {
			drawSelectScreen(tcc, batch, shaper);
		} else {
			if (scope == Scope.GLOBAL) {
				drawGlobalScreen(tcc, batch, shaper);
			} else {
				drawLocalScreen(tcc, batch, shaper);
			}
		}
	}
	public Array<Button> getButtons(){
		if (!selectScreen) {
			if (scope == Scope.LOCAL) {
				return localButtons;
			} else {
				return globalButtons;
			}
		}
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
	
	public void drawSelectScreen(TriColorChanger tcc, SpriteBatch batch, ShapeRenderer shaper) {
		shaper.begin(ShapeType.Filled);
		for (Button b : buttons){
//			if (b.getType() == ButtonType.TITLE || b.getType() == ButtonType.SCORE) {
			if (b.getType() == ButtonType.TITLE) {
				continue;
			}
			shaper.setColor(tcc.c1);
			shaper.rect(b.x, b.y, b.width, b.height);
			shaper.setColor(Color.BLACK);
			shaper.rect(
					b.x + b.getPaddingLeft(), b.y + b.getPaddingBottom(), 
					b.width - b.getPaddingWidth(), b.height - b.getPaddingHeight()
					);
		}
		shaper.end();
	
		batch.begin();
		
		fontCache.clear();
		
		fontCache.getFont().getData().setScale(2f);
		fontCache.setColor(tcc.c2);

		
		gl.reset();
		gl.setText(fontCache.getFont(), titleMessage);
		fontCache.setText(
				titleMessage, 
				titleButton.getCenterPaddingX() - (gl.width / 2f), 
				titleButton.getCenterPaddingY() + (gl.height / 2f)
				);
		fontCache.draw(batch);
		
		fontCache.getFont().getData().setScale(1f);
		fontCache.setColor(tcc.c3);
		
		gl.setText(fontCache.getFont(), localScoresMessage);
		fontCache.setText(
				localScoresMessage, 
				localScoresButton.getCenterPaddingX() - (gl.width / 2f),
				localScoresButton.getCenterPaddingY() + (gl.height / 2f)
				);
		fontCache.draw(batch);
		
		gl.setText(fontCache.getFont(), globalScoresMessage);
		fontCache.setText(
				globalScoresMessage, 
				globalScoresButton.getCenterPaddingX() - (gl.width / 2f),
				globalScoresButton.getCenterPaddingY() + (gl.height / 2f)
				);
		fontCache.draw(batch);
		
		gl.setText(fontCache.getFont(), exitScoreMessage);
		fontCache.setText(
				exitScoreMessage, 
				exitScoreButton.getCenterPaddingX() - (gl.width / 2f),
				exitScoreButton.getCenterPaddingY() + (gl.height / 2f)
				);
		fontCache.draw(batch);
		gl.reset();
		
		batch.end();
	}
	public void drawLocalScreen(TriColorChanger tcc, SpriteBatch batch, ShapeRenderer shaper) {
		shaper.begin(ShapeType.Filled);
		for (Button b : localButtons){
//			if (b.getType() == ButtonType.TITLE || b.getType() == ButtonType.SCORE) {
			if (b.getType() == ButtonType.TITLE) {
				continue;
			}
			shaper.setColor(tcc.c1);
			shaper.rect(b.x, b.y, b.width, b.height);
			shaper.setColor(Color.BLACK);
			shaper.rect(
					b.x + b.getPaddingLeft(), b.y + b.getPaddingBottom(), 
					b.width - b.getPaddingWidth(), b.height - b.getPaddingHeight()
					);
		}
		shaper.end();
	
		batch.begin();
		
		fontCache.clear();
		
		fontCache.getFont().getData().setScale(2f);
		fontCache.setColor(tcc.c2);

		
		gl.reset();
		gl.setText(fontCache.getFont(), localTitleMessage);
		fontCache.setText(
				localTitleMessage, 
				titleButton.getCenterPaddingX() - (gl.width / 2f), 
				titleButton.getCenterPaddingY() + (gl.height / 2f)
				);
		fontCache.draw(batch);
		
		fontCache.getFont().getData().setScale(.62f);
		fontCache.setColor(tcc.c3);
		
		fontCache.setText(soloMessage, Global.width()/2f - 150f, titleButton.getCenterPaddingY() + (gl.height / 2f) - 100f);
		fontCache.draw(batch);
		fontCache.setText(enemiesMessage, Global.width()/2f + 50f, titleButton.getCenterPaddingY() + (gl.height / 2f) - 100f);
		fontCache.draw(batch);
		
		fontCache.getFont().getData().setScale(1f);
		gl.setText(fontCache.getFont(), exitScoreMessage);
		fontCache.setText(
				exitScoreMessage, 
				exitScoreButton.getCenterPaddingX() - (gl.width / 2f),
				exitScoreButton.getCenterPaddingY() + (gl.height / 2f)
				);
		fontCache.draw(batch);
		gl.reset();
		
		batch.end();
	}
	public void drawGlobalScreen(TriColorChanger tcc, SpriteBatch batch, ShapeRenderer shaper) {
		
	}
	public void setAllButtonsVisible(boolean visible) {
		for (int i = 0; i < buttons.size; i++) {
			buttons.get(i).visible = visible;
		}
		for (int i = 0; i < globalButtons.size; i++) {
			globalButtons.get(i).visible = visible;
		}
		for (int i = 0; i < localButtons.size; i++) {
			localButtons.get(i).visible = visible;
		}
	}
	public void setGlobalScope() {
		scope = Scope.GLOBAL;
		selectScreen = false;
		for (int i = 0; i < buttons.size; i++) {
			buttons.get(i).visible = false;
			buttons.get(i).enabled = false;
		}
		for (int i = 0; i < globalButtons.size; i++) {
			globalButtons.get(i).visible = true;
			globalButtons.get(i).enabled = false;
		}
		for (int i = 0; i < localButtons.size; i++) {
			localButtons.get(i).visible = false;
			localButtons.get(i).enabled = false;
		}
	}
	public void setLocalScope() {
		scope = Scope.LOCAL;
		selectScreen = false;
		for (int i = 0; i < buttons.size; i++) {
			buttons.get(i).visible = false;
			buttons.get(i).enabled = false;
		}
		for (int i = 0; i < localButtons.size; i++) {
			localButtons.get(i).visible = true;
			localButtons.get(i).enabled = false;
		}
		for (int i = 0; i < globalButtons.size; i++) {
			globalButtons.get(i).visible = false;
			globalButtons.get(i).enabled = false;
		}
	}
	public void setSelectScreen() {
		System.out.println("setting select screen");
		selectScreen = true;
		for (int i = 0; i < buttons.size; i++) {
			buttons.get(i).visible = true;
			buttons.get(i).enabled = false;
		}
		for (int i = 0; i < localButtons.size; i++) {
			localButtons.get(i).visible = false;
			localButtons.get(i).enabled = false;
		}
		for (int i = 0; i < globalButtons.size; i++) {
			globalButtons.get(i).visible = false;
			globalButtons.get(i).enabled = false;
		}
	}
}
