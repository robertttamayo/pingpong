package com.madcoatgames.newpong.look;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.play.Button;
import com.madcoatgames.newpong.play.Button.ButtonType;
import com.madcoatgames.newpong.util.Global;

public class HUDPlacement {
	public void settle(Array<Button> buttons) {
		float paddingBase = 7.5f;

		Rectangle frame = new Rectangle();

		frame.setSize(4f * Global.width() / 5f, .8f * Global.height());
		frame.setPosition(0 + (Global.width() - frame.width) / 2f, 0 + (Global.height() - frame.height) / 2f);

		Button score, back, again, cont;
		score = getButtonByType(buttons, ButtonType.SCORE);
		back = getButtonByType(buttons, ButtonType.BACK);
		again = getButtonByType(buttons, ButtonType.PLAY_AGAIN);
		cont = getButtonByType(buttons, ButtonType.CONT);

		score.setSize(frame.width, .8f * frame.height);
		back.setSize(frame.width / 2f, .2f * frame.height);
		again.setSize(frame.width / 2f, .2f * frame.height);

		score.setPosition(frame.x, frame.y + back.height);
		back.setPosition(frame.x, frame.y);
		again.setPosition(frame.x + back.width, frame.y);

		for (Button b : buttons) {
			// check left
			b.setPadding(paddingBase);
			if (b.x == frame.x) {
				b.setPaddingLeft(paddingBase);
			} else {
				b.setPaddingLeft(paddingBase / 2f);
			}
			// check bottom
			if (b.y == frame.y) {
				b.setPaddingBottom(paddingBase);
			} else {
				b.setPaddingBottom(paddingBase / 2f);
			}
			// check right
			if ((b.x + b.width) == (frame.x + frame.width)) {
				b.setPaddingRight(paddingBase);
			} else {
				b.setPaddingRight(paddingBase / 2f);
			}
			// check top
			if ((b.y + b.height) == (frame.y + frame.height)) {
				b.setPaddingTop(paddingBase);
			} else {
				b.setPaddingTop(paddingBase / 2f);
			}
		}
		for (Button b : buttons) {
			if (b.getType() != ButtonType.SCORE) {
				b.setPaddingTop(paddingBase);
			}
		}
	}

	private Button getButtonByType(Array<Button> buttons, ButtonType type) {
		Button b = null;
		for (Button button : buttons) {
			if (button.getType() == type) {
				b = button;
				return b;
			}
		}
		return b;
	}
}
