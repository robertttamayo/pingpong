package com.madcoatgames.newpong.nongame.ui;


import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.madcoatgames.newpong.play.Button;
import com.madcoatgames.newpong.play.Button.ButtonType;
import com.madcoatgames.newpong.records.RemoteScore;
import com.madcoatgames.newpong.records.SaveDataCache;
import com.madcoatgames.newpong.records.SaveDataProcessor;
import com.madcoatgames.newpong.records.Score;
import com.madcoatgames.newpong.util.FontSizeTimer;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.TouchTarget;
import com.madcoatgames.newpong.util.TriColorChanger;
import com.madcoatgames.newpong.webutil.AsyncHandler;
import com.madcoatgames.newpong.webutil.NetworkFetchScores;

public class ScoreMenuHUD implements Disposable, AsyncHandler<Array<RemoteScore>>{
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
	private Button subtitleButton;
	
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
	
	private String globalSoloMessage = "";
	private String globalEnemiesMessage = "";
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
		fetchScores();
		
		saveDataCache = new SaveDataCache();
		saveDataProcessor = new SaveDataProcessor();
		
		soloMessage = "Solo\n\n";
		Array<Score> scores = SaveDataCache.getScores();
		for (int i = scores.size - 1, j = 1; i >= scores.size - 10; i--, j++) {
			if (i < 0) {
				break;
			}
			soloMessage += Integer.toString(j) + ".    " + scores.get(i).getPoints() + "\n";
		}
		
		enemiesMessage = "Enemies\n\n";
		Array<Score> enemyScores = SaveDataCache.getEnemyScores();
		for (int i = enemyScores.size - 1, j = 1; i >= enemyScores.size - 10; i--, j++) {
			if (i < 0) {
				break;
			}
			enemiesMessage += Integer.toString(j) + ".    " + enemyScores.get(i).getPoints() + "\n";
		}

		titleButton = new Button(ButtonType.TITLE);
		arcadeButton = new Button(ButtonType.MODE_ARCADE);
		battleButton = new Button(ButtonType.MODE_BATTLE);
		exitScoreButton = new Button(ButtonType.EXIT_SCORE);
		localScoresButton = new Button(ButtonType.SCORE_SCOPE_LOCAL);
		globalScoresButton = new Button(ButtonType.SCORE_SCOPE_GLOBAL);
		selectScoreButton = new Button(ButtonType.SELECT_SCORE);
		subtitleButton = new Button(ButtonType.SUBTITLE);
		
		buttons.add(titleButton);
		buttons.add(localScoresButton);
		buttons.add(globalScoresButton);
		buttons.add(exitScoreButton);
		
		localButtons.add(selectScoreButton);
		localButtons.add(subtitleButton);
		
		globalButtons.add(selectScoreButton);
		globalButtons.add(subtitleButton);
		
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
			if (b.getType() == ButtonType.SUBTITLE) {
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
		
		fontCache.getFont().getData().setScale(1f);
		fontCache.setColor(tcc.c2);

		String title = localTitleMessage;
		if (!Global.USER_NAME.equals("")) {
			title = Global.USER_NAME;
		}
		gl.reset();
		gl.setText(fontCache.getFont(), title);
		fontCache.setText(
				title, 
				subtitleButton.getCenterPaddingX() - (gl.width / 2f), 
				subtitleButton.getCenterPaddingY() + (gl.height / 2f)
				);
		fontCache.draw(batch);
		
		fontCache.getFont().getData().setScale(.62f);
		fontCache.setColor(tcc.c3);
		
		fontCache.setText(soloMessage, Global.width()/2f - 150f, Global.centerHeight() + 180f);
		fontCache.draw(batch);
		fontCache.setText(enemiesMessage, Global.width()/2f + 50f, Global.centerHeight() + 180f);
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
		shaper.begin(ShapeType.Filled);
		for (Button b : globalButtons){
//			if (b.getType() == ButtonType.TITLE || b.getType() == ButtonType.SCORE) {
			if (b.getType() == ButtonType.SUBTITLE) {
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
		
		fontCache.getFont().getData().setScale(1f);
		fontCache.setColor(tcc.c2);

		
		gl.reset();
		gl.setText(fontCache.getFont(), globalTitleMessage);
		fontCache.setText(
				globalTitleMessage, 
				subtitleButton.getCenterPaddingX() - (gl.width / 2f), 
				subtitleButton.getCenterPaddingY() + (gl.height / 2f)
				);
		fontCache.draw(batch);
		
		fontCache.getFont().getData().setScale(.62f);
		fontCache.setColor(tcc.c3);
		
		fontCache.setText(globalSoloMessage, Global.width()/2f - 250f, Global.centerHeight() + 180f);
		fontCache.draw(batch);
		fontCache.setText(globalEnemiesMessage, Global.width()/2f + 100f, Global.centerHeight() + 180f);
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
		for (int i = 0; i < localButtons.size; i++) {
			localButtons.get(i).visible = false;
			localButtons.get(i).enabled = false;
		}
		for (int i = 0; i < globalButtons.size; i++) {
			globalButtons.get(i).visible = true;
			globalButtons.get(i).enabled = false;
		}
	}
	public void setLocalScope() {
		scope = Scope.LOCAL;
		selectScreen = false;
		for (int i = 0; i < buttons.size; i++) {
			buttons.get(i).visible = false;
			buttons.get(i).enabled = false;
		}
		for (int i = 0; i < globalButtons.size; i++) {
			globalButtons.get(i).visible = false;
			globalButtons.get(i).enabled = false;
		}
		for (int i = 0; i < localButtons.size; i++) {
			localButtons.get(i).visible = true;
			localButtons.get(i).enabled = false;
		}
	}
	public void setSelectScreen() {
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

	@Override
	public void handle(Array<RemoteScore> array) {
		Json json = new Json();
		globalEnemiesMessage = "Enemies\n\n";
		globalSoloMessage = "Solo\n\n";
		int enemiesPlace = 0;
		int soloPlace = 0;
		int currentEnemiesScore = -1;
		int currentSoloScore = -1;
		int enemiesCount = 1;
		int soloCount = 1;
		for (int i = 0; i < array.size; i++) {
			RemoteScore score = array.get(i);
			if (score.mode.equals("enemies")) {
				if (enemiesCount >= 10) {
					continue;
				}
				if (currentEnemiesScore != score.score) {
					currentEnemiesScore = score.score;
					enemiesPlace++;
				}
				globalEnemiesMessage += enemiesPlace + ". " + score.score + " – " + score.username + "\n";
				enemiesCount++;
			} else {
				if (soloCount >= 10) {
					continue;
				}
				if (currentSoloScore != score.score) {
					currentSoloScore = score.score;
					soloPlace++;
				}
				globalSoloMessage += soloPlace + ". " + score.score + " – " + score.username + "\n";
				soloCount = 1;
			}
		}
//		System.out.println("ScoreMenuHUD::Global solo scores: " + globalSoloMessage);
//		System.out.println("ScoreMenuHUD::Global enemies scores: " + globalEnemiesMessage);
	}
	public void fetchScores() {
		NetworkFetchScores fetcher = new NetworkFetchScores();
		fetcher.fetch(this);
	}
	public void fetchUploadScores() {
		String enemiesScore = SaveDataCache.getHighestString(Global.MISSIONS);
		String soloScore = SaveDataCache.getHighestString(Global.ARCADE);
		NetworkFetchScores fetcher = new NetworkFetchScores();
		fetcher.setEnemiesScore(enemiesScore);
		fetcher.setSoloScore(soloScore);
		fetcher.fetch(this);
	}
}
