package com.madcoatgames.newpong.nongame.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.madcoatgames.newpong.play.Button;
import com.madcoatgames.newpong.play.Button.ButtonType;
import com.madcoatgames.newpong.records.SaveDataCache;
import com.madcoatgames.newpong.util.FontSizeTimer;
import com.madcoatgames.newpong.util.Global;
import com.madcoatgames.newpong.util.TouchTarget;
import com.madcoatgames.newpong.util.TriColorChanger;

public class ModeMenuHUD implements Disposable{
	private ModeMenuHUDPlacement placement;
	private Rectangle menu;
	private Array<Button> buttons = new Array<Button>();
	private Array<Button> activeButtons = new Array<Button>();
	private BitmapFont font;
	private BitmapFontCache fontCache;
	private Color color = new Color();
	private GlyphLayout gl = new GlyphLayout();
	
	private Button titleButton;
	private Button arcadeButton;
	private Button battleButton;
	
	private final String titleMessage = "Game Mode";
	private final String arcadeMessage = "Solo";
	private final String battleMessage = "Enemies";
	
	private float scale = 1f;
	
	private FontSizeTimer fst = new FontSizeTimer();
	
	public ModeMenuHUD(){
		titleButton = new Button(ButtonType.TITLE);
		arcadeButton = new Button(ButtonType.MODE_ARCADE);
		battleButton = new Button(ButtonType.MODE_BATTLE);
		
		buttons.add(titleButton);
		buttons.add(arcadeButton);
//		buttons.add(battleButton);
		
		placement = new ModeMenuHUDPlacement();
		placement.settle(buttons);
		
		font = new BitmapFont(Gdx.files.internal("font/swhite.fnt"));
		fontCache = new BitmapFontCache(font);
		fontCache.getFont().setFixedWidthGlyphs("0123456789");
		
	}

	public void draw(TriColorChanger tcc, SpriteBatch batch, ShapeRenderer shaper){
		shaper.begin(ShapeType.Filled);
		for (Button b : buttons){
			if (b.getType() == ButtonType.TITLE) {
				continue;
			}
			shaper.setColor(tcc.c1);
			shaper.rect(b.x, b.y, b.width, b.height);
			shaper.setColor(Color.BLACK);
			shaper.rect(
					b.x + b.getPaddingLeft(), b.y + b.getPaddingBottom(), 
					b.width - b.getPaddingWidth(), b.height - b.getPaddingHeight()
					);
		}
		shaper.end();
	
		batch.begin();
		
		fontCache.clear();
		
		fontCache.getFont().getData().setScale(2f);
		fontCache.setColor(tcc.c2);

		
		gl.reset();
		gl.setText(fontCache.getFont(), titleMessage);
		fontCache.setText(
				titleMessage, 
				titleButton.getCenterPaddingX() - (gl.width / 2f), 
				titleButton.getCenterPaddingY() + (gl.height / 2f)
				);
		fontCache.draw(batch);
		
		fontCache.getFont().getData().setScale(1f);
		fontCache.setColor(tcc.c3);
		
		gl.setText(fontCache.getFont(), arcadeMessage);
		fontCache.setText(
				arcadeMessage, 
				arcadeButton.getCenterPaddingX() - (gl.width / 2f),
				arcadeButton.getCenterPaddingY() + (gl.height / 2f)
				);
		fontCache.draw(batch);
		
//		gl.setText(fontCache.getFont(), battleMessage);
//		fontCache.setText(
//				battleMessage, 
//				battleButton.getCenterPaddingX() - (gl.width / 2f),
//				battleButton.getCenterPaddingY() + (gl.height / 2f)
//				);
//		fontCache.draw(batch);
//		gl.reset();
		
		batch.end();
		
		
	}
	public Array<Button> getButtons(){
		return buttons;
	}
	
	public void calcFontLook(TriColorChanger tcc){
		float delta = Gdx.graphics.getDeltaTime();
		float alpha = 0f;
		if (fst.isActive()){
			alpha = (1f - fst.getTimer()/fst.getCycle());
			fst.update(delta);
		}
		color.a = alpha;
		color.r = tcc.c2.r;
		color.g = tcc.c2.g;
		color.b = tcc.c2.b;
	}
	@Override
	public void dispose() {
		font.dispose();
	}
	
	

}
