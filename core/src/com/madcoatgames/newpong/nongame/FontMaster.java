package com.madcoatgames.newpong.nongame;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.utils.Disposable;

public class FontMaster implements Disposable{
	private BitmapFont titleFont, bigFont, medFont, smallFont;
	private BitmapFontCache titleFontCache, bigFontCache, medFontCache, smallFontCache;
	
	public FontMaster (){
		
	}
	@Override
	public void dispose() {
		titleFont.dispose();
		bigFont.dispose();
		medFont.dispose();
		smallFont.dispose();
	}

}
