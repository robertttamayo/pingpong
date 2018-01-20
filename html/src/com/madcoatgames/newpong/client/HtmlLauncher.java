package com.madcoatgames.newpong.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.madcoatgames.newpong.NewPong;
import com.madcoatgames.newpong.webutil.DesktopActionResolver;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(1024, 576);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new NewPong(new DesktopActionResolver());
        }
        
        @Override
        public ApplicationListener createApplicationListener () {
        		return new NewPong(new DesktopActionResolver());
        }
}