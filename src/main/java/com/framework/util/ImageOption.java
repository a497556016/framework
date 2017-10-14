package com.framework.util;

import java.awt.Color;
import java.awt.Font;

public class ImageOption {
	/**
	 * 字体风格 Font.PLAIN, Font.BOLD, Font.ITALIC……
	 */
	private int fontStyle = Font.BOLD;
	/**
	 * 字号大小
	 */
	private int fontSize = 50;
	/**
	 * 水印的宽度
	 */
	private int markWaterPicWidth = 600;
	/**
	 * 水印缩小比例
	 */
	private int markWaterPicZoomRatio = 3;
	/**
	 * 水印文字的透明度 0-1
	 */
	private float alpha = 0.4f;
	/**
	 * 水印文字颜色；
	 */
	private Color color = Color.RED;

	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public void setMarkWaterPicWidth(int markWaterPicWidth) {
		this.markWaterPicWidth = markWaterPicWidth;
	}

	public void setMarkWaterPicZoomRatio(int markWaterPicZoomRatio) {
		this.markWaterPicZoomRatio = markWaterPicZoomRatio;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public int getFontStyle() {
		return fontStyle;
	}

	public int getFontSize() {
		return fontSize;
	}

	public int getMarkWaterPicWidth() {
		return markWaterPicWidth;
	}

	public int getMarkWaterPicZoomRatio() {
		return markWaterPicZoomRatio;
	}

	public float getAlpha() {
		return alpha;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	

}
