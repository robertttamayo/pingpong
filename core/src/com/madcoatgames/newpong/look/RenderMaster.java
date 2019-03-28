package com.madcoatgames.newpong.look;

import com.badlogic.gdx.Gdx;	
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.madcoatgames.newpong.enemy.Enemy;
import com.madcoatgames.newpong.play.Hazard;
import com.madcoatgames.newpong.util.BatchDrawable;
import com.madcoatgames.newpong.util.FilledShapeRenderable;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.LineShapeRenderable;
import com.madcoatgames.newpong.util.TextureRenderable;
import com.madcoatgames.newpong.util.TriColorChanger;

public class RenderMaster implements Disposable{
	private Array<Enemy> enemies = new Array<Enemy>();
	private Array<BatchDrawable> batchDrawables = new Array<BatchDrawable>();
	private BackgroundMaster backgroundMaster;
	private EnemyRenderer er;
	private HealthRenderer hr;
	
	public RenderMaster(){
		backgroundMaster = new BackgroundMaster("citymoon3.jpg");
		batchDrawables.add(backgroundMaster);
		er = new EnemyRenderer();
		hr = new HealthRenderer();
	}
	public void renderFilledBackground(ShapeRenderer shaper, Color color){
		shaper.begin(ShapeType.Filled);
		shaper.setColor(color);
		shaper.rect(0, 0, Global.width(), Global.height());
		shaper.end();
	}
	public void drawBackground(SpriteBatch batch){
		batch.begin();
		
		backgroundMaster.drawBatched(batch);
		
		batch.end();
	}
	public void drawBatched(SpriteBatch batch, Array<BatchDrawable> batches){
		batch.begin();
		
		for (BatchDrawable batched : batches){
			batched.drawBatched(batch);
		}
		
		batch.end();
	}
	public void renderFilled(ShapeRenderer shaper, Array<FilledShapeRenderable> renders){
		Gdx.gl.glEnable(GL20.GL_BLEND);
		
		shaper.begin(ShapeType.Filled);
		for (FilledShapeRenderable fsr : renders){
			fsr.renderFilled(shaper);
		}
		shaper.end();
		
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	public void renderTextures(SpriteBatch batch, Array<TextureRenderable> renders){
		for (TextureRenderable textureRenderable : renders) {
			textureRenderable.renderTexture(batch);
		}
	}
	public void renderLine(ShapeRenderer shaper, Array<LineShapeRenderable> renders){
		Gdx.gl.glEnable(GL20.GL_BLEND);
		
		shaper.begin(ShapeType.Line);
		for (LineShapeRenderable lsr : renders){
			lsr.renderLine(shaper);
		}
		shaper.end();
		
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	public void renderBatchEnemies(SpriteBatch batch, Array<Enemy> enemies, Color color){
		batch.begin();
		
		for (Enemy e : enemies){
			float shade = e.getShade();
//			System.out.println("RenderMaster::" + r + ", " + g + ", " + b + ". shade: " + shade);
			batch.setColor(color.r * shade, color.g * shade, color.b * shade, color.a);
			if (e.isDead()) {
				continue;
			}
			er.drawEnemy(batch, e);
		}
		
		batch.end();
	}
	public void renderHazards(ShapeRenderer shaper, Array<Hazard> hazards){
		for (Hazard hazard : hazards) {
			hazard.draw(shaper);
		}
	}
	public void renderShapeEnemies(ShapeRenderer shaper, Array<Enemy> enemies){
		shaper.begin(ShapeType.Line);
		for (Enemy e : enemies){
			if (e.isDead()) {
				continue;
			}
			shaper.rect(e.x, e.y,  e.width, e.height);
		}
		shaper.end();
	}
	public void renderHealth(ShapeRenderer shaper, TriColorChanger tcc, int health, int maxHealth, boolean isHit){
		if (isHit){
			hr.drawHit(shaper, tcc, health, maxHealth);
		} else {
			hr.draw(shaper, tcc, health, maxHealth);
		}
	}
	public BackgroundMaster getBackgroundMaster(){
		return backgroundMaster;
	}
	@Override
	public void dispose() {
		er.dispose();
	}
}
