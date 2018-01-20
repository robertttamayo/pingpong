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
import com.madcoatgames.newpong.play.Button;
import com.madcoatgames.newpong.rule.GameMaster;
import com.madcoatgames.newpong.rule.InputMaster;
import com.madcoatgames.newpong.rule.LogicMaster;
import com.madcoatgames.newpong.rule.StarBackgroundMaster;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.TriColorChanger;

public class HomeScreen implements Screen {
	public enum Action {
		START, STEADY
	}
	private Action action = Action.STEADY;
	
	protected Game game;
	private HomeMenuHUD hud;
	private TriColorChanger tcc;
	
	private ShapeRenderer shaper;
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private MusicMaster mm;
	private SoundMaster soundMaster;
	
	private StarBackgroundMaster starBg;
	
	private InputMaster input;
	
	public HomeScreen (Game game, MusicMaster musicMaster){
		this.game = game;
		this.mm = musicMaster;
		
		tcc = new TriColorChanger(new Color(.25f, .64f, 1, 1)
		, new Color(.64f, 1, .25f, 1)
		, new Color(1, .25f, .64f, 1));
		
		hud = new HomeMenuHUD();
		input = new InputMaster();
		
		cam = new OrthographicCamera();
		shaper = new ShapeRenderer();
		batch = new SpriteBatch();
		
		cam.setToOrtho(false, (int)Global.width(), (int)Global.height());
		shaper.setProjectionMatrix(cam.combined);
		batch.setProjectionMatrix(cam.combined);
		
		Gdx.input.setInputProcessor(input);
		
		soundMaster = new SoundMaster();
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
		input.updateHomeScreen(this);
		
		cam.update();
		cam.position.x = 0;
		cam.position.y = 0;
		cam.update();
		
		tcc.update(delta);
		
		starBg.update(delta);
		starBg.setColor(tcc.c2);
		
		shaper.begin(ShapeType.Filled);
		starBg.renderFilled(shaper);
		shaper.end();
		
		hud.draw(tcc, batch, shaper);
		
		if (action == Action.START){
			System.out.println("HomeScreen::setting screen to MissionSelectScreen");
			MissionSelectScreen mss = new MissionSelectScreen(this.game, this.mm);
			mss.setStarBg(this.starBg);
			SoundMaster.specialq = true;
			game.setScreen(mss);
		}
		mm.update(delta);
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
		return hud.getButtons();
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
}
