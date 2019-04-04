package com.madcoatgames.newpong.nongame;

import com.badlogic.gdx.Input.TextInputListener;
import com.madcoatgames.newpong.util.Global;

public class RevolveTextInputer implements TextInputListener {
		private TextInputHandler handler;
		public RevolveTextInputer() {
			System.out.println("RevolveTextInputer::username before: " + Global.USER_NAME);
		}
		
		public RevolveTextInputer(TextInputHandler handler) {
			this.handler = handler;
		}
		@Override
		public void input (String text) {
			Global.textInputActive = false;
			Global.USER_NAME = text;
			System.out.println("RevolveTextInputer::username after: " + Global.USER_NAME);
			if (handler != null) {
				handler.handleTextInput(text);
			}
		}

		@Override
		public void canceled () {
			Global.textInputActive = false;
		}
}