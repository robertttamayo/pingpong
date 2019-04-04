package com.madcoatgames.newpong.nongame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.audio.MusicMaster;
import com.madcoatgames.newpong.audio.SoundMaster;
import com.madcoatgames.newpong.look.RenderMaster;
import com.madcoatgames.newpong.nongame.ui.HomeMenuHUD;
import com.madcoatgames.newpong.nongame.ui.ModeMenuHUD;
import com.madcoatgames.newpong.nongame.ui.ScoreMenuHUD;
import com.madcoatgames.newpong.play.Button;
import com.madcoatgames.newpong.records.SaveDataCache;
import com.madcoatgames.newpong.records.SaveDataProcessor;
import com.madcoatgames.newpong.rule.GameMaster;
import com.madcoatgames.newpong.rule.InputMaster;
import com.madcoatgames.newpong.rule.LogicMaster;
import com.madcoatgames.newpong.rule.StarBackgroundMaster;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.TriColorChanger;
import com.madcoatgames.newpong.webutil.AsyncHandler;
import com.madcoatgames.newpong.webutil.NetworkCheckUsername;

public class MissionSelectScreen implements Screen, TextInputHandler, AsyncHandler<Boolean> {
	public enum Action {
		ARCADE, BATTLE, STEADY, SCORE, EXIT_SCORE, SCORE_SCOPE_LOCAL, SCORE_SCOPE_GLOBAL, SELECT_SCORE
	}
	private enum HUD {
		NORMAL, SCORES
	}
	private Action action = Action.STEADY;
	
	protected Game game;
	private ModeMenuHUD hud;
	private TriColorChanger tcc;
	
	private ShapeRenderer shaper;
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private SoundMaster sm;
	
	private InputMaster input;
	
	private StarBackgroundMaster starBg;
	
	private MusicMaster musicMaster;
	private SoundMaster soundMaster;
	
	private ScoreMenuHUD scoreHUD;
	private HUD activeHUD;
	
	public MissionSelectScreen (Game game, MusicMaster musicMaster){
		this.game = game;
		
		tcc = new TriColorChanger(new Color(.25f, .64f, 1, 1)
		, new Color(.64f, 1, .25f, 1)
		, new Color(1, .25f, .64f, 1));
		
		hud = new ModeMenuHUD();
		scoreHUD = new ScoreMenuHUD();
		input = new InputMaster();
		
		cam = new OrthographicCamera();
		shaper = new ShapeRenderer();
		batch = new SpriteBatch();
		
		cam.setToOrtho(false, (int)Global.width(), (int)Global.height());
		shaper.setProjectionMatrix(cam.combined);
		batch.setProjectionMatrix(cam.combined);
		
		Gdx.input.setInputProcessor(input);
		
		this.musicMaster = musicMaster;
		this.soundMaster = new SoundMaster();
		this.activeHUD = HUD.NORMAL;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		input.update();
		input.updateMissionSelectScreen(this);
		
		cam.update();
		cam.position.x = 0;
		cam.position.y = 0;
		cam.update();
		
		tcc.update(delta);
		
		starBg.update(delta);
		starBg.setColor(tcc.c3);
		
		shaper.begin(ShapeType.Filled);
		starBg.renderFilled(shaper);
		shaper.end();
		
		if (!Global.textInputActive) {
			if (activeHUD == HUD.NORMAL) {
				hud.draw(tcc, batch, shaper);
			} else {
				scoreHUD.draw(tcc, batch, shaper);
			}
		}
		
		if (action == Action.ARCADE){
			Global.setGameModeArcade();
			GameMaster gm = new GameMaster(this.game, this.musicMaster);
			gm.setStarBg(this.starBg);
			game.setScreen(gm);
			SoundMaster.specialq = true;
		} else if (action == Action.BATTLE) {
			Global.setGameModeMissions();
			GameMaster gm = new GameMaster(this.game, this.musicMaster);
			gm.setStarBg(this.starBg);
			game.setScreen(gm);
			SoundMaster.specialq = true;
		} else if (action == Action.SCORE) {
			System.out.println("MISSION SELECT SCREEN:: score");
			SoundMaster.specialq = true;
			action = Action.STEADY;
			activeHUD = HUD.SCORES;
			scoreHUD.setSelectScreen();
			hud.setAllButtonsVisible(false);
			if (Global.USER_NAME.equals("")) {
				initInput();
			}
		} else if (action == Action.EXIT_SCORE) {
			System.out.println("MISSION SELECT SCREEN::Exit score");
			scoreHUD.setAllButtonsVisible(false);
			hud.setAllButtonsVisible(true);
			SoundMaster.specialq = true;
			action = Action.STEADY;
			activeHUD = HUD.NORMAL;
		} else if (action == Action.SCORE_SCOPE_GLOBAL) {
			SoundMaster.specialq = true;
			action = Action.STEADY;
			scoreHUD.setGlobalScope();
		} else if (action == Action.SCORE_SCOPE_LOCAL) {
			SoundMaster.specialq = true;
			action = Action.STEADY;
			scoreHUD.setLocalScope();
		} else if (action == Action.SELECT_SCORE) {
			SoundMaster.specialq = true;
			action = Action.STEADY;
			scoreHUD.setSelectScreen();
		}
		soundMaster.update();
	}

	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = (int)Global.width();
		cam.viewportHeight = (int)Global.height();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		shaper.dispose();
		batch.dispose();
		soundMaster.dispose();
		//mm.getMusic().stop();
		//mm.dispose();
		//sm.dispose();
		hud.dispose();
	}
	public Array<Button> getButtons(){
		if (activeHUD == HUD.NORMAL) {
			return hud.getButtons();
		} else {
			return scoreHUD.getButtons();
		}
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public StarBackgroundMaster getStarBg() {
		return this.starBg;
	}
	public void setStarBg(StarBackgroundMaster starBg) {
		this.starBg = starBg;
	}
	
	private void initInput() {
		RevolveTextInputer listener = new RevolveTextInputer(this);
		Gdx.input.getTextInput(listener, "Enter your name", "", "Your name");
	}
	private void initInputInvalid() {
		RevolveTextInputer listener = new RevolveTextInputer(this);
		Gdx.input.getTextInput(listener, "Name already taken. Please try a different one", "", "Choose a different name");
	}
	@Override
	public void handleTextInput(String text) {
		System.out.println(text.length());
		if (text.length() > 12) {
			initInputTooLong();
			return;
		}
		Global.TEMP_USERNAME = text;
		NetworkCheckUsername checker = new NetworkCheckUsername();
		checker.fetch(this);
	}
	private void initInputTooLong() {
		RevolveTextInputer listener = new RevolveTextInputer(this);
		Gdx.input.getTextInput(listener, "Name too long. Must be 12 characters or less", "", "Your name. 12 characters or less");
	}
	@Override
	public void handle(Boolean isValid) {
		if (isValid) {
			Global.USER_NAME = Global.TEMP_USERNAME;
			SaveDataCache.setUsername(Global.USER_NAME);
			SaveDataProcessor.writeUsername(Global.USER_NAME);
			scoreHUD.fetchUploadScores();
		} else {
			// TODO: Handle invalid username
			System.out.println("NOT VALID");
			initInputInvalid();
		}
	}
}
