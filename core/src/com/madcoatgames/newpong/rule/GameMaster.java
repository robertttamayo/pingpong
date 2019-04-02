package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.NewPong;
import com.madcoatgames.newpong.audio.MusicMaster;
import com.madcoatgames.newpong.audio.SoundMaster;
import com.madcoatgames.newpong.look.HUDRenderer;
import com.madcoatgames.newpong.look.MenuOperator;
import com.madcoatgames.newpong.look.RenderMaster;
import com.madcoatgames.newpong.look.TextureManager;
import com.madcoatgames.newpong.nongame.HomeScreen;
import com.madcoatgames.newpong.nongame.MissionSelectScreen;
import com.madcoatgames.newpong.nongame.RevolveTextInputer;
import com.madcoatgames.newpong.nongame.TextInputHandler;
import com.madcoatgames.newpong.records.RemoteScore;
import com.madcoatgames.newpong.records.SaveDataCache;
import com.madcoatgames.newpong.records.SaveDataProcessor;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.StateUpdateable;
import com.madcoatgames.newpong.util.TriColorChanger;
import com.madcoatgames.newpong.webutil.AsyncHandler;
import com.madcoatgames.newpong.webutil.NetworkCheckUsername;
import com.madcoatgames.newpong.webutil.NetworkFetchScores;

public class GameMaster extends ScreenMaster implements TextInputHandler, AsyncHandler<Boolean> {
	protected Game game;
	private ShapeRenderer shaper;
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private LogicMaster logic;
	private RenderMaster rm;
	private TextureManager textureManager;
	
	private MusicMaster mm;
	private SoundMaster sm;
	private HUDRenderer hr;
	
	private SaveDataCache saveDataCache;
	private SaveDataProcessor saveDataProcessor;
	
	private StarBackgroundMaster starBg;
	
	private boolean renderTextures = false;
	private int score = 0;
	
	public GameMaster (Game game, MusicMaster mm){
		this.game = game;
		
		shaper = new ShapeRenderer();
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		rm = new RenderMaster();
		logic = new LogicMaster();
//		mm = new MusicMaster();
		this.mm = mm;
		mm.stopAndLoadNewTrack(MusicMaster.Track.PLAY);
		sm = new SoundMaster();
		hr = new HUDRenderer();
		textureManager = new TextureManager();
		saveDataCache = new SaveDataCache();
		saveDataProcessor = new SaveDataProcessor();
		
		cam.setToOrtho(false, (int)Global.width(), (int)Global.height());
		shaper.setProjectionMatrix(cam.combined);
		batch.setProjectionMatrix(cam.combined);
	}
	@Override
	public void dispose(){
		rm.dispose();
		shaper.dispose();
		batch.dispose();
//		mm.getMusic().stop();
		mm.dispose();
		sm.dispose();
		hr.dispose();
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		if (MenuOperator.getType() == MenuOperator.PLAY) {
			logic.update(delta, hr, batch);
			if (Global.getGameMode() == Global.MISSIONS) {
				logic.getBallRenderer().update(delta);
			}
		} else if (MenuOperator.getType() == MenuOperator.GAMEOVER){
			logic.updatePaused(delta, hr);
			if (MenuOperator.firstGameOverCheck()) {
				StateUpdateable st = (StateUpdateable)(game);
				st.onGameOver();
				
				int currentPoints = SaveDataCache.getCurrentPoints(Global.getGameMode());
				int personalRecord = Integer.parseInt(SaveDataCache.getHighestString(Global.getGameMode()));
				if (currentPoints >= personalRecord) {
					save();
					if (Global.USER_NAME.equals("")) {
						initTextInput("New Personal Record: " + currentPoints + "!");
					} else {
						uploadScores();
					}
				}
			}
		} else if (MenuOperator.getType() == MenuOperator.SHUTDOWN) {
			MenuOperator.start();
			System.out.println("GameScreen::setting screen to HomeScreen");
			mm.stopAndLoadNewTrack(MusicMaster.Track.MENU);
			HomeScreen mss = new HomeScreen(this.game, this.mm);
			mss.setStarBg(new StarBackgroundMaster((int) Global.width(), (int) Global.height()));
			SoundMaster.heroDeadq = true;
			game.setScreen(mss);
		}
		rm.getBackgroundMaster().setColor(logic.getTcc().c2);
		rm.drawBackground(batch);
		//rm.renderFilledBackground(shaper, logic.getTcc().c2);
		
		if (renderTextures) {
			batch.begin();
			rm.renderTextures(batch, logic.getTextureRenderables());
			batch.end();
		}
		rm.renderFilled(shaper, logic.getFilled());
		rm.renderLine(shaper, logic.getLine());
		//rm.renderShapeEnemies(shaper, logic.getEnemyMaster().getEnemies());
		if (Global.getGameMode() != Global.ARCADE) {
			rm.renderBatchEnemies(batch, logic.getEnemyMaster().getEnemies(), logic.getTcc().c1);
			rm.renderHazards(shaper, logic.getEnemyMaster().getHazards());
			rm.renderHealth(shaper, logic.getTcc(), logic.getHealth(), logic.getMaxHealth(), logic.isHit());
			// lightning
			logic.getLightningManager().render(shaper, delta);
			logic.getLightningManager().renderTargetCircle(logic.getEnemyMaster().getEnemies(), shaper, delta);
			logic.getVirusMaster().renderTargetCircle(logic.getEnemyMaster().getEnemies(), shaper, delta);
			// bombs
			logic.getBombMaster().render(shaper);
		}
		
		sm.update();
		
		score = Global.getGameMode() == Global.ARCADE ? BallPaddleMaster.getNumHits() : Global.enemiesDefeated;
		hr.draw(logic.getTcc(), batch, shaper, score, logic.isCountdownInProgress());
		
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
		mm.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = (int)Global.width();
		cam.viewportHeight = (int)Global.height();
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		SaveDataProcessor.processToFile(((NewPong) game).getSaveData());
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	public void save(){
		SaveDataProcessor.processToFile(((NewPong) game).getSaveData());
	}
	public TriColorChanger getTriColorChanger(){
		return logic.getTcc();
	}
	public StarBackgroundMaster getStarBg() {
		return this.starBg;
	}
	public void setStarBg(StarBackgroundMaster starBg) {
		this.starBg = starBg;
		logic.setStarBg(this.starBg);
	}
	@Override
	public void handleTextInput(String text) {
		Global.TEMP_USERNAME = text;
		NetworkCheckUsername checker = new NetworkCheckUsername();
		checker.fetch(this);
	}
	@Override
	public void handle(Boolean isValid) {
		if (isValid) {
			Global.USER_NAME = Global.TEMP_USERNAME;
			SaveDataCache.setUsername(Global.USER_NAME);
			SaveDataProcessor.writeUsername(Global.USER_NAME);
			uploadScores();
		} else {
			// TODO: Handle invalid username
			initInputInvalid();
		}
	}
	private void initTextInput(String message) {
		RevolveTextInputer listener = new RevolveTextInputer(this);
		Gdx.input.getTextInput(listener, message, "", "Enter your name to save online");
	}
	private void initInputInvalid() {
		RevolveTextInputer listener = new RevolveTextInputer(this);
		Gdx.input.getTextInput(listener, "Name already taken. Please try a different one", "", "Enter a different name");
	}
	private void uploadScores() {
		String enemiesScore = SaveDataCache.getHighestString(Global.MISSIONS);
		String soloScore = SaveDataCache.getHighestString(Global.ARCADE);
		NetworkFetchScores fetcher = new NetworkFetchScores();
		fetcher.setEnemiesScore(enemiesScore);
		fetcher.setSoloScore(soloScore);
		fetcher.fetch(new AsyncHandler<Array<RemoteScore>>() {

			@Override
			public void handle(Array<RemoteScore> t) {
				// assume it went well
			}
			
		});
	}
}

