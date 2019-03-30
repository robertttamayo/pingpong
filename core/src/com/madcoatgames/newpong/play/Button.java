package com.madcoatgames.newpong.play;

import com.badlogic.gdx.graphics.Color;
import com.madcoatgames.newpong.util.TouchTarget;

public class Button extends TouchTarget{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float padding;
	private float paddingTop, paddingLeft, paddingRight, paddingBottom;
	
	public enum ButtonType {
		QUIT, BACK, PLAY_AGAIN, START, TITLE, OPTIONS, SCORE, MODE_ARCADE, MODE_BATTLE, CONT, 
		EXIT_SCORE, SELECT_SCORE,
		SCORE_MODE_SOLO, SCORE_MODE_ENEMIES,
		SCORE_SCOPE_GLOBAL, SCORE_SCOPE_LOCAL
	}
	private ButtonType type;
	private Color color;
	
	public boolean enabled = true;
	public boolean visible = true;
	
	public Button(ButtonType type) {
		this.type = type;
		init();
	}
	private void init(){
		switch (type) {
		case QUIT:
			setColor(new Color(1, 94f/255f, 0, 1));
			break;
		case BACK:
			setColor(new Color(0, 135f/255f, 159f/255f, 1));
			break;
		case PLAY_AGAIN:
			setColor(new Color(0, 217f/255f, 0, 1));
			break;
		case START:
			setColor(new Color(0, 217f/255f, 0, 1));
			break;
		case TITLE:
			setColor(new Color(0, 135f/255f, 159f/255f, 1));
			break;
		case OPTIONS:
			setColor(new Color(199f/255f, 52f/255f, 120f/255f, 1));
			break;
		case SCORE:
			setColor(new Color(199f/255f, 52f/255f, 120f/255f, 1));
			break;
		default:
			break;
		}
	}
	public ButtonType getType() {
		return type;
	}
	public void setType(ButtonType type) {
		this.type = type;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void move(float x, float y){
		x += x;
		y += y;
	}
	public float getPaddingTop() {
		return paddingTop;
	}
	public void setPaddingTop(float paddingTop) {
		this.paddingTop = paddingTop;
	}
	public float getPaddingLeft() {
		return paddingLeft;
	}
	public void setPaddingLeft(float paddingLeft) {
		this.paddingLeft = paddingLeft;
	}
	public float getPaddingRight() {
		return paddingRight;
	}
	public void setPaddingRight(float paddingRight) {
		this.paddingRight = paddingRight;
	}
	public float getPaddingBottom() {
		return paddingBottom;
	}
	public void setPaddingBottom(float paddingBottom) {
		this.paddingBottom = paddingBottom;
	}
	public float getPaddingWidth(){
		return paddingLeft + paddingRight;
	}
	public float getPaddingHeight(){
		return paddingTop + paddingBottom;
	}
	public float getCenterX(){
		return x + width/2f;
	}
	public float getCenterY(){
		return y + height/2f;
	}
	public float getPadding() {
		return padding;
	}
	public void setPadding(float padding) {
		this.padding = padding;
	}
	public float getCenterPaddingX(){
		return ((x + paddingLeft) + (width - getPaddingWidth()) / 2f);
				
	}
	public float getCenterPaddingY(){
		return ((y + paddingBottom) + (height - getPaddingHeight()) / 2f);
	}
}
