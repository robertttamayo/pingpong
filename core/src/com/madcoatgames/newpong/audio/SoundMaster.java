package com.madcoatgames.newpong.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.madcoatgames.newpong.util.Global;

public class SoundMaster implements Disposable{	
	//anything can access these.
	private Sound 	jump, 	land, 	normal, 	acquire, 	enemyDead, 	enemyHit, 	dash,
					hyper,		elec,		charge;
	private Sound paddle_1, paddle_2, paddle_3, paddle_4, paddle_5;
	private Sound special; // menu select
	private Sound heroHit; // game over (alt_gameover)
	private Sound heroDead; // quit game
	private Sound powerup1, powerup2, powerup3, losePowerup;
	
	public static boolean 	jumpq, 	landq, 	normalq,	 acquireq, 	enemyDeadq, heroDeadq, 	enemyHitq, 	heroHitq, 	dashq,
					specialq,		hyperq,		elecq,		chargeq;
	public static boolean powerup1q, powerup2q, powerup3q, losePowerupq;
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
		jump 		= Gdx.audio.newSound(Gdx.files.internal	("sound/Jump4.mp3")); //paddle not powerup
		land 		= Gdx.audio.newSound(Gdx.files.internal	("sound/Hit_Hurt6.mp3"));
		normal 		= Gdx.audio.newSound(Gdx.files.internal	("sound/Laser_Shoot8.mp3"));//ping pong hit wall vert
		acquire 	= Gdx.audio.newSound(Gdx.files.internal	("sound/enemy_hit.mp3"));

		    //lose juice
		dash		= Gdx.audio.newSound(Gdx.files.internal	("sound/Powerup2.mp3"));
		special		= Gdx.audio.newSound(Gdx.files.internal	("sound/select.mp3")); //acquired star //hit paddle
		hyper		= Gdx.audio.newSound(Gdx.files.internal	("sound/Laser_Shoot21.mp3")); //hyper star
		elec		= Gdx.audio.newSound(Gdx.files.internal	("sound/Laser_Shoot24.mp3"));
		charge		= Gdx.audio.newSound(Gdx.files.internal	("sound/Laser_Shoot19.mp3"));

//				losePowerup = Gdx.audio.newSound(Gdx.files.internal("sound/lose_powerup.mp3"));
		losePowerup = Gdx.audio.newSound(Gdx.files.internal("sound/new_PlyrHit.mp3"));
//				powerup1 = Gdx.audio.newSound(Gdx.files.internal("sound/power_up_level_1.mp3"));
//				powerup2 = Gdx.audio.newSound(Gdx.files.internal("sound/power_up_level_2.mp3"));
//				powerup3 = Gdx.audio.newSound(Gdx.files.internal("sound/power_up_level_3.mp3"));
		powerup1 = Gdx.audio.newSound(Gdx.files.internal("sound/new_PwrUp1.mp3"));
		powerup2 = Gdx.audio.newSound(Gdx.files.internal("sound/new_PwrUp2.mp3"));
		powerup3 = Gdx.audio.newSound(Gdx.files.internal("sound/new_PwrUp3.mp3"));
//				enemyDead 	= Gdx.audio.newSound(Gdx.files.internal	("sound/enemy_dead.mp3"));
//				enemyHit 	= Gdx.audio.newSound(Gdx.files.internal	("sound/enemy_hit.mp3"));
		enemyDead 	= Gdx.audio.newSound(Gdx.files.internal	("sound/new_enemy_die.mp3"));
		enemyHit 	= Gdx.audio.newSound(Gdx.files.internal	("sound/new_enemy_die.mp3"));
		heroHit 	= Gdx.audio.newSound(Gdx.files.internal	("sound/alt_gameover.mp3"));
		heroDead 	= Gdx.audio.newSound(Gdx.files.internal	("sound/game_over.mp3"));
		paddle_1 = Gdx.audio.newSound(Gdx.files.internal	("sound/1_paddle.mp3"));
		paddle_2 = Gdx.audio.newSound(Gdx.files.internal	("sound/2_paddle.mp3"));
		paddle_3 = Gdx.audio.newSound(Gdx.files.internal	("sound/3_paddle.mp3"));
		paddle_4 = Gdx.audio.newSound(Gdx.files.internal	("sound/4_paddle.mp3"));
		paddle_5 = Gdx.audio.newSound(Gdx.files.internal	("sound/5_paddle.mp3"));
		
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
		paddle_1.dispose();
		paddle_2.dispose();
		paddle_3.dispose();
		paddle_4.dispose();
		paddle_5.dispose();
		powerup1.dispose();
		powerup2.dispose();
		powerup3.dispose();
		losePowerup.dispose();
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
								long soundId = enemyDead.play();
								enemyDead.setVolume(soundId, .4f);
		}
		if 	(heroDeadq) {
								heroDead.play();
								heroDeadq = false;
		}
		if 	(enemyHitq)			{
//								enemyHit.stop();
//								enemyHit.play();
		}
		
		if 	(heroHitq) 			{
			if (Global.getGameMode() == Global.MISSIONS && Global.playerHealth == 0) {
				heroHit.play();
			} else if (Global.getGameMode() == Global.ARCADE) {
				heroHit.play();
			}
		}
		if 	(powerup1q) 			powerup1.play();
		if 	(powerup2q) 			powerup2.play();
		if 	(powerup3q) 			powerup3.play();
		if 	(losePowerupq) 		losePowerup.play();
		
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
		
		powerup1q = false;
		powerup2q = false;
		powerup3q = false;
		losePowerupq = false;
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
