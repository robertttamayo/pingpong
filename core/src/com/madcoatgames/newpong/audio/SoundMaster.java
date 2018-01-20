package com.madcoatgames.newpong.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class SoundMaster implements Disposable{	
	//anything can access these.
	private Sound 	jump, 	land, 	normal, 	acquire, 	enemyDead, 	enemyHit, 	dash,
					hyper,		elec,		charge;
	private Sound paddle_1, paddle_2, paddle_3, paddle_4, paddle_5;
	private Sound special; // menu select
	private Sound heroHit; // game over (alt_gameover)
	private Sound heroDead; // quit game
	
	public static boolean 	jumpq, 	landq, 	normalq,	 acquireq, 	enemyDeadq, heroDeadq, 	enemyHitq, 	heroHitq, 	dashq,
					specialq,		hyperq,		elecq,		chargeq;
	public static boolean paddleq;
	public static boolean	unleashq, tokenq, fireworksq, abductionq;
	
	public static boolean landDisabled = true, dashDisabled = false, heroDeadDisabled = false, enemyDeadDisabled = false;
	
	private Array<Sound> paddleSounds = new Array<Sound>(); 
	
	public SoundMaster(){
		loadPlay();
		resetPlay();
		paddleSounds.addAll(paddle_1, paddle_2, paddle_3, paddle_4, paddle_5);
	}
	
	private void loadPlay(){
		jump 		= Gdx.audio.newSound(Gdx.files.internal	("sound/Jump4.wav")); //paddle not powerup
		land 		= Gdx.audio.newSound(Gdx.files.internal	("sound/Hit_Hurt6.wav"));
		normal 		= Gdx.audio.newSound(Gdx.files.internal	("sound/Laser_Shoot8.wav"));//ping pong hit wall vert
		acquire 	= Gdx.audio.newSound(Gdx.files.internal	("sound/Pickup_Coin24.wav"));
		enemyDead 	= Gdx.audio.newSound(Gdx.files.internal	("sound/Explosion11.wav"));
		heroDead 	= Gdx.audio.newSound(Gdx.files.internal	("sound/game_over.wav"));
		enemyHit 	= Gdx.audio.newSound(Gdx.files.internal	("sound/Explosion15.wav"));
		heroHit 	= Gdx.audio.newSound(Gdx.files.internal	("sound/alt_gameover.wav")); //lose juice
		dash		= Gdx.audio.newSound(Gdx.files.internal	("sound/Powerup2.wav"));
		special		= Gdx.audio.newSound(Gdx.files.internal	("sound/select.wav")); //acquired star //hit paddle
		hyper		= Gdx.audio.newSound(Gdx.files.internal	("sound/Laser_Shoot21.wav")); //hyper star
		elec		= Gdx.audio.newSound(Gdx.files.internal	("sound/Laser_Shoot24.wav"));
		charge		= Gdx.audio.newSound(Gdx.files.internal	("sound/Laser_Shoot19.wav"));
		paddle_1 = Gdx.audio.newSound(Gdx.files.internal	("sound/1_paddle.wav"));
		paddle_2 = Gdx.audio.newSound(Gdx.files.internal	("sound/2_paddle.wav"));
		paddle_3 = Gdx.audio.newSound(Gdx.files.internal	("sound/3_paddle.wav"));
		paddle_4 = Gdx.audio.newSound(Gdx.files.internal	("sound/4_paddle.wav"));
		paddle_5 = Gdx.audio.newSound(Gdx.files.internal	("sound/5_paddle.wav"));
	}
	public void disposePlay(){
		jump.dispose();
		land.dispose();
		normal.dispose();
		acquire.dispose();
		enemyDead.dispose();
		heroDead.dispose();
		enemyHit.dispose();
		heroDead.dispose();
		dash.dispose();
		special.dispose();
		hyper.dispose();
		elec.dispose();
		charge.dispose();
	}
	public void update(){
		playOnQueue();
		resetPlay();
	}
	public void playOnQueue(){
		if (paddleq) {
			queuePaddle();
			paddleq = false;
		}
		if 	(jumpq) 			jump.play();
		if 	(landq && !landDisabled)	{
								land.play();
								landDisabled = true;
		}
		if	(hyperq)			{
								hyper.stop();
								hyper.play();
								specialq = false;
								normalq = false;
		}
		if 	(specialq)			{
								special.stop();
								special.play();
								normalq = false;
		}
		if 	(normalq) 			{
								normal.stop();
								normal.play();
		}
		
		
		if 	(acquireq) 			{
								enemyHitq = false;
								acquire.play();
		}
		if 	(enemyDeadq)		{
								enemyDead.stop();
								enemyDead.play();
		}
		if 	(heroDeadq) {
								heroDead.play();
								heroDeadq = false;
		}
		if 	(enemyHitq)			{
								enemyHit.stop();
								enemyHit.play();
		}
		if 	(heroHitq) 			heroHit.play();
		if 	(dashq && !dashDisabled)	{
								dash.play();
								dashDisabled = true;
		}
		if	(elecq)				{
								elec.stop();
								elec.play();
		}
		if	(chargeq)			{
								charge.stop();
								charge.play();
		}
		if	(unleashq)			{
								charge.stop();
								hyper.play(1, .3f, 0);
		}
		if 	(tokenq)			acquire.play(1, 1.5f, 0);
		if 	(fireworksq)		{
								heroHit.stop();
								heroHit.play(1, .65f, 0);
								//fireworks.play(1, .5f, 0);
		}
		if 	(abductionq)		{
								heroDead.stop();
								heroDead.play(1, 1.5f, 0);
								//fireworks.play(1, .5f, 0);
		}
	}
	private void resetPlay(){
		jumpq 		= false;
		landq 		= false;
		normalq 	= false;
		acquireq 	= false;
		enemyDeadq 	= false;
		heroDeadq	= false;
		enemyHitq 	= false;
		heroHitq 	= false;
		dashq 		= false;
		specialq 	= false;
		hyperq 		= false;
		elecq 		= false;
		chargeq 	= false;
		
		paddleq = false;
		
		unleashq	= false;
		tokenq 		= false;
		fireworksq 	= false;
		abductionq 	= false;
	}

	@Override
	public void dispose() {
		disposePlay();
	}
	private void queuePaddle() {
		int index = (int) Math.floor(Math.random() * 5f);
		paddleSounds.get(index).play();
	}

}
