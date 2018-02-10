package com.madcoatgames.newpong.rule;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.look.HUDRenderer;
import com.madcoatgames.newpong.look.MenuOperator;
import com.madcoatgames.newpong.play.Button;
import com.madcoatgames.newpong.play.Table;
import com.madcoatgames.newpong.powerups.electricity.LightningManager;
import com.madcoatgames.newpong.util.FilledShapeRenderable;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.LineShapeRenderable;
import com.madcoatgames.newpong.util.TextureRenderable;
import com.madcoatgames.newpong.util.TriColorChanger;

public class LogicMaster {
	private Array<FilledShapeRenderable> filled = new Array<FilledShapeRenderable>();
	private Array<LineShapeRenderable> line = new Array<LineShapeRenderable>();
	private Array<TextureRenderable> textureRenderables = new Array<TextureRenderable>();
	private Array<Button> buttons;
	private BallMaster bm;
	private Table table;
	private PaddleMaster pm;
	private InputMaster im;
	private BallPaddleMaster bpm;
	private EnemyBallMaster ebm;
	private TriColorChanger tcc;
	private EnemyMaster em;
	private BattlePaddleMaster battleMaster;
	private StarBackgroundMaster starBg;
	private BallPowerupMaster ballPowerupMaster;
	
	private GameStartCountdown gameStartCountdown;
	
	public TriColorChanger getTcc() {
		return tcc;
	}
	public void setTcc(TriColorChanger tcc) {
		this.tcc = tcc;
	}
	public LogicMaster(){
		gameStartCountdown = new GameStartCountdown();
		
		table = new Table(0, 0, Global.width(), Global.height());
		bm = new BallMaster(table);
		pm = new PaddleMaster();
		im = new InputMaster();
		bpm = new BallPaddleMaster();
		ebm = new EnemyBallMaster(bm.getBall());
		battleMaster = new BattlePaddleMaster();
//		tcc = new TriColorChanger(new Color(1, .5f, .5f, 1), new Color(.5f, 1, .5f, 1), new Color(.5f, .5f, 1, 1));
		tcc = new TriColorChanger(new Color(.25f, .64f, 1, 1)
								, new Color(.64f, 1, .25f, 1)
								, new Color(1, .25f, .64f, 1));
		em = new EnemyMaster();
		ballPowerupMaster = new BallPowerupMaster(bpm, bm, ebm);
	}
	public void updateCountdownMode(float delta, HUDRenderer hr, SpriteBatch batch) {
		if (MenuOperator.countdownEnabled && !gameStartCountdown.update(delta)) {
			gameStartCountdown.update(delta * .8f);
			updatePaused(delta * 3f, hr);
			hr.drawCountdown(tcc, batch, gameStartCountdown.getPosition());
		} else {
			MenuOperator.countdownEnabled = false;
			gameStartCountdown.reset();
		}
	}
	public void updateArcade(float delta, HUDRenderer hr, SpriteBatch batch){
		if (MenuOperator.countdownEnabled) {
			updateCountdownMode(delta, hr, batch);
			return;
		}
		
		bm.update(delta);
		pm.update(tcc.c3, delta);
		bpm.testCollisions(bm.getBall(), pm.getPaddles());
		
		starBg.update(delta * 3f);
		
		textureRenderables.clear();
//		textureRenderables.addAll(bm.getTextureRenderable());
//		textureRenderables.addAll(pm.getTextureRenderables());
		
		tcc.update(delta);
		bm.setColor(tcc.c1);
		bm.setColor2(tcc.c2);
		bm.setColor3(tcc.c3);
		
		im.update();
		im.updatePaddles(pm.getPaddles());//maybe memory intensive?
		
		starBg.setColor(tcc.c2);
		filled.clear();
		filled.addAll(starBg.getFilled());
		filled.addAll(bm.getFilled());
		filled.addAll(pm.getFilled());
		
		line.clear();
		line.addAll(bm.getLine());
		
	}
	public void update(float delta, HUDRenderer hr, SpriteBatch batch){
		if (Global.getGameMode() == Global.ARCADE){
			updateArcade(delta, hr, batch);
			return;
		}
		bm.update(delta);
		pm.update(tcc.c3, delta);
		bpm.testCollisions(bm.getBall(), pm.getPaddles());
		ballPowerupMaster.update(delta);;
		
		starBg.update(delta);
		
		em.color1 = tcc.c1;
		em.color2 = tcc.c2;
		em.color3 = tcc.c3;
		em.update(delta);
		ebm.update(bm.getBall(), em.getEnemies());
		ebm.testCloneBallCollisions(bm.getCloneBalls(), em.getEnemies());
		battleMaster.update(delta);
		battleMaster.testCollision(em.getHazards(), pm.getPaddles());
		if (battleMaster.isPlayerLose()){
			reset();
		}
		
		tcc.update(delta);
		bm.setColor(tcc.c1);
		
		im.update();
		im.updatePaddles(pm.getPaddles());//maybe memory intensive?
		
		filled.clear();
		filled.addAll(starBg.getFilled());
		if (!ebm.getLightningManager().getActive()) {
			filled.addAll(bm.getFilled());
		}
		filled.addAll(pm.getFilled());
		
	}
	public void updatePaused(float delta, HUDRenderer hud){
		tcc.update(delta);
		
		starBg.update(delta);
		
		im.update();
		im.updateHUD(hud);
		
		filled.clear();
		filled.addAll(starBg.getFilled());
		//filled.addAll(bm.getFilled());
		//filled.addAll(pm.getFilled());
	}
	public Array<FilledShapeRenderable> getFilled(){
		return filled;
	}
	public Array<LineShapeRenderable> getLine(){
		return line;
	}
	public Array<TextureRenderable> getTextureRenderables(){
		return this.textureRenderables;
	}
	public EnemyMaster getEnemyMaster(){
		return em;
	}
	private void reset(){
		battleMaster.reset();
		MenuOperator.failMissions(0);
	}
	public int getHealth(){
		return battleMaster.getPlayerHealth();
	}
	public int getMaxHealth(){
		return battleMaster.getMaxPlayerHealth();
	}
	public boolean isHit(){
		return battleMaster.isHit();
	}
	public StarBackgroundMaster getStarBg() {
		return this.starBg;
	}
	public void setStarBg(StarBackgroundMaster starBg) {
		this.starBg = starBg;
	}
	public boolean isCountdownInProgress() {
		return MenuOperator.countdownEnabled;
	}
	public LightningManager getLightningManager() {
		if (Global.getGameMode() == Global.MISSIONS) {
			return ebm.getLightningManager();
		} else {
			return null;
		}
	}
}
