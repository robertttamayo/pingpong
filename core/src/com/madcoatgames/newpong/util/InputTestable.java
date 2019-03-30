package com.madcoatgames.newpong.util;

import com.badlogic.gdx.math.Rectangle;

public interface InputTestable {
	public Rectangle getBounds();
	public ActionCode getActionCode();
	public boolean isEnabled();
	public void setEnabled(boolean enabled);
}
