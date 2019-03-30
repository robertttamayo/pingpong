package com.madcoatgames.newpong.nongame.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.play.Button;
import com.madcoatgames.newpong.play.Button.ButtonType;
import com.madcoatgames.newpong.util.Global;

public class ScoreMenuHUDPlacement {
	
	public void settle(Array<Button> buttons){
		float padding = 7.5f;
		
		Rectangle frame = new Rectangle();
		
		frame.setSize(4f*Global.width()/5f, .8f*Global.height());
		frame.setPosition(0 + (Global.width() - frame.width)/2f, 60f + (Global.height() - frame.height)/2f);
		
		Button title, arcade, battle, score;
		
		title = getButtonByType(buttons, ButtonType.TITLE);
		
		if (title != null) {
			title.setSize(frame.width, .8f*frame.height);
			title.setPosition(frame.x, frame.y + .2f*frame.height);
			
			title.setPaddingLeft(0f);
			title.setPaddingRight(0f);
			title.setPaddingTop(0f);
			title.setPaddingBottom(0f);
		}
		
		arcade = getButtonByType(buttons, ButtonType.SCORE_SCOPE_LOCAL);
		if (arcade != null) {
			arcade.setSize(frame.width/2f, .2f * frame.height);
			arcade.setPosition(frame.x, frame.y);
			
			arcade.setPaddingLeft(padding);
			arcade.setPaddingRight(padding);
			arcade.setPaddingTop(padding);
			arcade.setPaddingBottom(padding);
		}
		
		battle = getButtonByType(buttons, ButtonType.SCORE_SCOPE_GLOBAL);
		if (battle != null) {
			battle.setSize(frame.width/2f, .2f * frame.height);
			battle.setPosition(frame.x + .5f*frame.width, frame.y);
			
			battle.setPaddingLeft(padding/2f);
			battle.setPaddingRight(padding);
			battle.setPaddingTop(padding);
			battle.setPaddingBottom(padding);
		}
		
		score = getButtonByType(buttons, ButtonType.EXIT_SCORE);
		if (score != null) {
			score.setSize(frame.width/3f, .15f * frame.height);
			score.setPosition(Global.width()/2f - frame.width/6f, 20f);
			
			score.setPaddingLeft(padding/2f);
			score.setPaddingRight(padding/2f);
			score.setPaddingTop(padding/2f);
			score.setPaddingBottom(padding/2f);
		}
		
		score = getButtonByType(buttons, ButtonType.SELECT_SCORE);
		if (score != null) {
			score.setSize(frame.width/3f, .15f * frame.height);
			score.setPosition(Global.width()/2f - frame.width/6f, 20f);
			
			score.setPaddingLeft(padding/2f);
			score.setPaddingRight(padding/2f);
			score.setPaddingTop(padding/2f);
			score.setPaddingBottom(padding/2f);
		}
		
	}
	private Button getButtonByType(Array<Button> buttons, ButtonType type){
		Button b = null;
		for (Button button : buttons){
			if (button.getType() == type){
				b = button;
				return b;
			}
		}
		return b;
	}
}
