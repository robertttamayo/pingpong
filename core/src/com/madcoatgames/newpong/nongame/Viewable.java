package com.madcoatgames.newpong.nongame;

import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.utils.Array;
import com.madcoatgames.newpong.util.TouchTarget;

public interface Viewable {
	public Array<TouchTarget> getTargets();
	public void arrangeText(BitmapFontCache cache);
	public BitmapFontCache drawText();

}
