package com.madcoatgames.newpong.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.madcoatgames.newpong.webutil.DesktopActionResolver;
import com.madcoatgames.newpong.NewPong;
import com.madcoatgames.newpong.util.Global;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) Global.width();
		config.height = (int) Global.height();
		new LwjglApplication(new NewPong(new DesktopActionResolver()), config);
	}
}
