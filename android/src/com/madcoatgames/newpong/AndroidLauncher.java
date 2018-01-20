package com.madcoatgames.newpong;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.madcoatgames.newpong.NewPong;
import com.madcoatgames.newpong.webutil.DesktopActionResolver;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new NewPong(new DesktopActionResolver()), config);
	}
}