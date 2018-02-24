package com.oim.core.common.config.data;

import com.oim.core.common.AppConstant;

/**
 * @author XiaHui
 * @date 2017年10月21日 下午1:27:57
 */
public class Theme {

	public static final String config_file_path = AppConstant.getUserAppPath() + "Config/Application/Theme.xml";
	private String backgroundImage="Resources/Images/Wallpaper/default.jpg";
	private double opacity = 0.3D;
	private double red = 1.0D;
	private double green = 1.0D;
	private double blue = 1.0D;

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	public double getRed() {
		return red;
	}

	public void setRed(double red) {
		this.red = red;
	}

	public double getGreen() {
		return green;
	}

	public void setGreen(double green) {
		this.green = green;
	}

	public double getBlue() {
		return blue;
	}

	public void setBlue(double blue) {
		this.blue = blue;
	}
}
