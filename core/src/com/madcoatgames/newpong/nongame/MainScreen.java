package com.madcoatgames.newpong.nongame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.madcoatgames.newpong.audio.MusicMaster;
import com.madcoatgames.newpong.audio.SoundMaster;
import com.madcoatgames.newpong.rule.GameMaster;
import com.madcoatgames.newpong.rule.ScreenMaster;
import com.madcoatgames.newpong.rule.StarBackgroundMaster;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.TriColorChanger;

public class MainScreen extends ScreenMaster {
	public static final float WIDTH = 720;
	public static final float HEIGHT = 480;
	
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private ShapeRenderer shaper;
	
	private FontMaster fontMaster;
	private TriColorChanger tcc;
	
	private GameSelectScreen gameSel;
	private EnterNameScreen enterName;
	private SplashScreen splash;
	private MissionSelectScreen missions;
	private OptionsScreen options;
	private HomeScreen homeScreen;
	
	private MusicMaster musicMaster;
	private SoundMaster soundMaster;
	
	private ActiveViewType activeView;
	
	private StarBackgroundMaster starBg;
	
	public MainScreen(){
		activeView = ActiveViewType.HOME;
		fontMaster = new FontMaster();
		Color c1 = new Color(.89f, .15f, .21f, 1);
		Color c2 = new Color(c1.g, c1.b, c1.r, 1);
		Color c3 = new Color(c1.b, c1.r, c1.g, 1);
		tcc = new TriColorChanger(c1, c2, c3);
		
		starBg = new StarBackgroundMaster((int) Global.width(), (int) Global.height());
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Global.width(), Global.height());
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		shaper = new ShapeRenderer();
		shaper.setProjectionMatrix(cam.combined);
		
		gameSel = new GameSelectScreen();
		enterName = new EnterNameScreen();
		splash = new SplashScreen();
		options = new OptionsScreen();
		
		musicMaster = new MusicMaster(MusicMaster.Track.MENU);
		soundMaster = new SoundMaster();
	}
	public MainScreen(Game game){
		this();
		this.game = game;
		homeScreen = new HomeScreen(this.game, this.musicMaster);
	}
	public void dispose(){
		batch.dispose();
		shaper.dispose();
		soundMaster.dispose();
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
		
		cam.update();
		cam.position.x = 0;
		cam.position.y = 0;
		cam.update();
		tcc.update(delta);
		
		switch(activeView){
		case GAME:
			System.out.println("MainScreen::Changing to GameMaster");
			GameMaster gm = new GameMaster(game, this.musicMaster);
			gm.setStarBg(this.starBg);
			game.setScreen(gm);
			break;
		case SPLASH:
			break;
		case HOME:
			System.out.println("MainScreen::Changing to HomeScreen");
			HomeScreen hs = new HomeScreen(game, this.musicMaster);
			hs.setStarBg(this.starBg);
			game.setScreen(hs);
			break;
		case GAME_SELECT:
			System.out.println("MainScreen::draw viewables and update input");
			drawViewable(gameSel);
			updateInput(gameSel);
			break;
		case OPTIONS:
			break;
		case MISSION_SELECT:
			break;
		}
		
		Gdx.gl.glDisable(GL20.GL_BLEND);
		soundMaster.update();
	}
	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = Global.width();
		cam.viewportHeight = Global.height();
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
	private void drawViewable(Viewable view){
		
	}
	private void updateInput(InputTestable test){
		
	}
	public StarBackgroundMaster getStarBg() {
		return this.starBg;
	}
	public void setStarBg(StarBackgroundMaster starBg) {
		this.starBg = starBg;
	}
}
