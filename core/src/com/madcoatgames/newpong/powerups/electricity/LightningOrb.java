package com.madcoatgames.newpong.powerups.electricity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.enemy.Enemy;

public class LightningOrb {
	public LightningBolt uBolt;
	public Array<LightningBolt> bolts = new Array<LightningBolt>();
	public int numBolts = 5;

	public static final int NORMAL = 0;
	
	public int type;
	public boolean friendly;
	public int dir;
	public Rectangle bounds;
	
	public Vector2 a = new Vector2();
	public Vector2 b = new Vector2(), c = new Vector2(), d = new Vector2(), e = new Vector2();
	public Vector2 da = new Vector2();
	public Vector2 db = new Vector2();
	public Vector2 dc = new Vector2();
	public float width, height;
	public Vector2 center = new Vector2();
	public Vector2 pos = new Vector2();
	public Vector2 vel = new Vector2();
	public Vector2 target = new Vector2();
	public boolean acquired = false;
	public boolean hyper = false;
	public float stateTime = 0;
	public float dstA, dstB, dstC, dstD, dstE;
	public float ar, ag, ab, br, bg, bb;
	public Color colorA = new Color();
	public Color colorB = new Color();
	public float angleA, angleB, angleC, angleD, angleE;
	public boolean targetA = false, targetB = false, targetC = false
				, targetD = false, targetE = false;
	public float targetAX, targetAY, targetBX, targetBY, targetCX, targetCY
				, targetDX, targetDY, targetEX, targetEY;
	public Enemy enemyA, enemyB, enemyC, enemyD, enemyE;
	
	public LightningOrb(float x, float y, int dir, int type, boolean friendly){
		width = 8;
		height = 32;
		
		bounds = new Rectangle(x, y, 420, 420);
		
		a.set(x, y);
		b.set(a);
		c.set(b);
		d.set(c);
		e.set(d);
		
		da.set(a);
		db.set(b);
		dc.set(c);
		
		center.x = a.x;
		center.y = a.y - height;
		pos.set(center);
		
		dstA = (float)a.dst(center);
		dstB = (float)b.dst(center);
		dstC = (float)c.dst(center);
		dstD = (float)d.dst(center);
		dstE = (float)e.dst(center);
		
		angleA = (float)(2d/5d*Math.PI);
		angleB = (float)(4d/5d*Math.PI);
		angleC = (float)(6d/5d*Math.PI);
		angleD = (float)(8d/5d*Math.PI);
		angleE = (float)(2d*Math.PI);
		
		setOthers();
		
		this.dir = dir;
		this.type = type;
		this.friendly = friendly;
		setBolts();
	}
	public void setOthers(){
		a.x = (float)(center.x - dstA*Math.cos(angleA));
		a.y = (float)(center.y + dstA*Math.sin(angleA));
		b.x = (float)(center.x - dstB*Math.cos(angleB));
		b.y = (float)(center.y + dstB*Math.sin(angleB));
		c.x = (float)(center.x - dstC*Math.cos(angleC));
		c.y = (float)(center.y + dstC*Math.sin(angleC));
		d.x = (float)(center.x - dstD*Math.cos(angleD));
		d.y = (float)(center.y + dstD*Math.sin(angleD));
		e.x = (float)(center.x - dstE*Math.cos(angleE));
		e.y = (float)(center.y + dstE*Math.sin(angleE));
	}
	public void setBolts(){
		bolts.add(new LightningBolt(center.x, center.y, a.x, a.y));
		bolts.add(new LightningBolt(center.x, center.y, b.x, b.y));
		bolts.add(new LightningBolt(center.x, center.y, c.x, c.y));
		bolts.add(new LightningBolt(center.x, center.y, d.x, d.y));
		bolts.add(new LightningBolt(center.x, center.y, e.x, e.y));
	}
	public void update(Vector2 ballPos, float delta){

		checkFreeEnemies();
		//System.out.println("here at update method in orb");
		pos.set(ballPos.x, ballPos.y);
		center.set(ballPos.x, ballPos.y);
		bounds.setPosition(pos.x, pos.y);
		bounds.x -= bounds.width/2f;
		bounds.y -= bounds.height/2f;
		
		rotate(delta);
		
		for (int i = 0; i < bolts.size; i++){
			uBolt = bolts.get(i);
			uBolt.setStart(pos);
			uBolt.setOffX(pos.x);
			switch(i){
			case 0:
				uBolt.setFinish(a);
				break;
			case 1:
				uBolt.setFinish(b);
				break;
			case 2:
				uBolt.setFinish(c);
				break;
			case 3:
				uBolt.setFinish(d);
				break;
			case 4:
				uBolt.setFinish(e);
				break;
			}
			//uBolt.setFinish(target);
			uBolt.update(delta);
		}
		if (acquired) {
			//hasBolt = true;
			//bolt = new LightningBolt(pos.x, pos.y, target.x, target.y);
		}
	}
	public void render(ShapeRenderer shaper){
		//if (hasBolt) bolt.update(Gdx.graphics.getDeltaTime(), shaper);
		if (acquired) {
			colorA.set(Color.YELLOW);
			colorB.set(Color.CYAN);
		}
		if (hyper) {
			colorA.set(Color.RED);
			colorB.set(Color.ORANGE);
		}
		Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
		
		
		shaper.begin(ShapeType.Filled);
		
		if (LightningManager.boltCount == 1) {
			shaper.setColor(.5f, .5f, 1f, .25f);
		} else if (LightningManager.boltCount > 1 && LightningManager.boltCount < 5) {
			shaper.setColor(1f, 0, 1f, .25f);
		} else if (LightningManager.boltCount >= 5) {
			shaper.setColor(1f, .25f, .25f, .5f);
		}
		
		shaper.circle(pos.x, pos.y, height);
		shaper.setColor(Color.YELLOW);
		shaper.circle(pos.x, pos.y, height/5f);
		
		shaper.end();
		
		Gdx.graphics.getGL20().glDisable(GL20.GL_BLEND);
		
		shaper.begin(ShapeType.Line);
		
//		shaper.rect(bounds.x, bounds.y, bounds.width, bounds.height);
		
		
		for (int i = 0; i < bolts.size; i++){
			bolts.get(i).render(shaper);
		}
		shaper.setColor(Color.WHITE);
		
		shaper.circle(pos.x, pos.y, height);
		
		shaper.end();
		
		
	}
	public void rotate(float delta){
		stateTime += 1f*delta;
		if (dir < 0) {
			if (!targetA){
				a.x = (float)(center.x - dstA*Math.sin(stateTime + angleA));
				a.y = (float)(center.y + dstA*Math.cos(stateTime + angleA));
			}
			else a.set(enemyA.x + enemyA.width/2f
					, enemyA.y + enemyA.height/2f);
			
			if (!targetB){
				b.x = (float)(center.x - dstB*Math.sin(stateTime + angleB));
				b.y = (float)(center.y + dstB*Math.cos(stateTime + angleB));
			}
			else b.set(enemyB.x + enemyB.width/2f
					, enemyB.y + enemyB.height/2f);
			
			if (!targetC){
				c.x = (float)(center.x - dstC*Math.sin(stateTime + angleC));
				c.y = (float)(center.y + dstC*Math.cos(stateTime + angleC));
			}
			else c.set(enemyC.x + enemyC.width/2f
					, enemyC.y + enemyC.height/2f);
			
			if (!targetD){
				d.x = (float)(center.x - dstD*Math.sin(stateTime + angleD));
				d.y = (float)(center.y + dstD*Math.cos(stateTime + angleD));
			}
			else d.set(enemyD.x + enemyD.width/2f
					, enemyD.y + enemyD.height/2f);
			
			if (!targetE){
				e.x = (float)(center.x - dstE*Math.sin(stateTime + angleE));
				e.y = (float)(center.y + dstE*Math.cos(stateTime + angleE));
			}
			else e.set(enemyE.x + enemyE.width/2f
					, enemyE.y + enemyE.height/2f);
		} else {
			if (!targetA){
				a.x = (float)(center.x - dstA*Math.cos(stateTime + angleA));
				a.y = (float)(center.y + dstA*Math.sin(stateTime + angleA));
			}
			else a.set(enemyA.x + enemyA.width/2f
					, enemyA.y + enemyA.height/2f);
			
			if (!targetB){
				b.x = (float)(center.x - dstB*Math.cos(stateTime + angleB));
				b.y = (float)(center.y + dstB*Math.sin(stateTime + angleB));
			}
			else b.set(enemyB.x + enemyB.width/2f
					, enemyB.y + enemyB.height/2f);
			
			if (!targetC){
				c.x = (float)(center.x - dstC*Math.cos(stateTime + angleC));
				c.y = (float)(center.y + dstC*Math.sin(stateTime + angleC));
			}
			else c.set(enemyC.x + enemyC.width/2f
					, enemyC.y + enemyC.height/2f);
			
			if (!targetD){
				d.x = (float)(center.x - dstD*Math.cos(stateTime + angleD));
				d.y = (float)(center.y + dstD*Math.sin(stateTime + angleD));
			}
			else d.set(enemyD.x + enemyD.width/2f
					, enemyD.y + enemyD.height/2f);
			
			if (!targetE){
				e.x = (float)(center.x - dstE*Math.cos(stateTime + angleE));
				e.y = (float)(center.y + dstE*Math.sin(stateTime + angleE));
			}
			else e.set(enemyE.x + enemyE.width/2f
					, enemyE.y + enemyE.height/2f);
		}
		
//		if (!targetA) {
//			a.x += (pos.x - center.x);
//			a.y += (pos.y - center.y);
//		}
//		if (!targetB) {
//			b.x += (pos.x - center.x);
//			b.y += (pos.y - center.y);
//		}
//		if (!targetC) {
//			c.x += (pos.x - center.x);
//			c.y += (pos.y - center.y);
//		}
//		if (!targetD) {
//			d.x += (pos.x - center.x);
//			d.y += (pos.y - center.y);
//		}
//		if (!targetE) {
//			e.x += (pos.x - center.x);
//			e.y += (pos.y - center.y);
//		}
		
		if (acquired) {
			//adjustY();
			//pos.y += vel.y*Gdx.graphics.getDeltaTime()*(hyper ? 10.8f : 1.8f);
		}

	}
	public void setTarget(float x, float y) {
		// TODO Auto-generated method stub
		target.x = x;
		target.y = y;
	}
	public void setColorA(float r, float g, float b){
		colorA.set(r, g, b, 1);
	}
	public void setColorB(float r, float g, float b){
		colorB.set(r, g, b, 1);
	}
	public void updateTarget(Vector2 target){
		this.target = target;
	}
	public void setEnemyA(Enemy enemy){
		enemyA = enemy;
	}
	public void setEnemyB(Enemy enemy){
		enemyB = enemy;
	}
	public void setEnemyC(Enemy enemy){
		enemyC = enemy;
	}
	public void setEnemyD(Enemy enemy){
		enemyD = enemy;
	}
	public void setEnemyE(Enemy enemy){
		enemyE = enemy;
	}
	public void checkEnemies(){
		if (enemyA.getHealth() <= 0) targetA = false;
		if (enemyB.getHealth() <= 0) targetB = false;
		if (enemyC.getHealth() <= 0) targetC = false;
		if (enemyD.getHealth() <= 0) targetD = false;
		if (enemyE.getHealth() <= 0) targetE = false;
	}
	public boolean checkAddedEnemy(Enemy enemy){
		boolean legal = false;
		//enemy.electricuted = true;
		legal = !enemy.isElectricuted();
		//legal = (((enemyA != null && enemy != enemyA) || (enemyA == null))
		//		|| ((enemyB != null && enemy != enemyB) || (enemyB == null))
		//		|| ((enemyC != null && enemy != enemyC) || (enemyC == null))
		//		|| ((enemyD != null && enemy != enemyD) || (enemyD == null))
		//		|| ((enemyE != null && enemy != enemyE) || (enemyE == null)));
		
		
		return legal;
	}
	public void checkFreeEnemies(){
		if (targetA && enemyA != null && (!enemyA.overlaps(this.bounds) || !this.bounds.contains(enemyA))) {
			//enemyA.elecTime = 0;
			enemyA.setElectricContact(false);
			targetA = false;
			
		}
		if (targetB && enemyB != null && (!enemyB.overlaps(this.bounds) || !this.bounds.contains(enemyB))) {
			//enemyB.elecTime = 0;
			enemyB.setElectricContact(false);
			targetB = false;
		}
		if (targetC && enemyC != null && (!enemyC.overlaps(this.bounds) || !this.bounds.contains(enemyC))) {
			//enemyC.elecTime = 0;
			enemyC.setElectricContact(false);
			targetC = false;
		}
		if (targetD && enemyD != null && (!enemyD.overlaps(this.bounds) || !this.bounds.contains(enemyD))) {
			//enemyD.elecTime = 0;
			enemyD.setElectricContact(false);
			targetD = false;
		}
		if (targetE && enemyE != null && (!enemyE.overlaps(this.bounds) || !this.bounds.contains(enemyE))) {
			//enemyE.elecTime = 0;
			enemyE.setElectricContact(false);
			targetE = false;
		}
	}

}
