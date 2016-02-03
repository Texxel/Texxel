package com.github.texxel;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;

import org.mockito.Mockito;

public class TestUtils {
    static private boolean isGdxInitialised = false;

    /**
     * Initialises a headless backend for LibGdx. If Gdx is already initialised, this will do nothing.
     * Use this method whenever interacting with Gdx code during testing.
     */
    public static void initGdx() {
        if ( isGdxInitialised )
            return;
        isGdxInitialised = true;
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        config.renderInterval = 1/60f;
        new HeadlessApplication( new ApplicationAdapter() {}, config );

        // won't work for every thing, but should be okay for most things
        Gdx.gl = Gdx.gl20 = Mockito.mock( GL20.class );
    }

}
