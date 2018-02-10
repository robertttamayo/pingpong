package com.madcoatgames.newpong.nongame.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.play.Button;
import com.madcoatgames.newpong.play.Button.ButtonType;
import com.madcoatgames.newpong.util.Global;

public class ModeMenuHUDPlacement {
	
	public void settle(Array<Button> buttons){
		float padding = 7.5f;
		
		Rectangle frame = new Rectangle();
		
		frame.setSize(4f*Global.width()/5f, .8f*Global.height());
		frame.setPosition(0 + (Global.width() - frame.width)/2f, 0 + (Global.height() - frame.height)/2f);
		
		Button title, arcade, battle;
		
		title = getButtonByType(buttons, ButtonType.TITLE);
		
		title.setSize(frame.width, .8f*frame.height);
		title.setPosition(frame.x, frame.y + .2f*frame.height);
		
		title.setPaddingLeft(0f);
		title.setPaddingRight(0f);
		title.setPaddingTop(0f);
		title.setPaddingBottom(0f);
		
		arcade = getButtonByType(buttons, ButtonType.MODE_ARCADE);
		if (arcade != null) {
			arcade.setSize(frame.width/2f, .2f * frame.height);
			arcade.setPosition(frame.x, frame.y);
			
			arcade.setPaddingLeft(padding);
			arcade.setPaddingRight(padding);
			arcade.setPaddingTop(padding);
			arcade.setPaddingBottom(padding);
		}
		
		battle = getButtonByType(buttons, ButtonType.MODE_BATTLE);
		if (battle != null) {
			battle.setSize(frame.width/2f, .2f * frame.height);
			battle.setPosition(frame.x + .5f*frame.width, frame.y);
			
			battle.setPaddingLeft(padding/2f);
			battle.setPaddingRight(padding);
			battle.setPaddingTop(padding);
			battle.setPaddingBottom(padding);
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
