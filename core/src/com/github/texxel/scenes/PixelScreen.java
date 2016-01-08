package com.github.texxel.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.texxel.utils.Assert;

public class PixelScreen extends ScreenAdapter {

    // ui things
    private Game app;
    private Stage ui;
    private ScreenViewport viewport;

    public PixelScreen( Game app ) {
        this.app = Assert.nonnull( app, "App cannot be null" );
    }

    public Game getApp() {
        return app;
    }

    @Override
    public void show() {
        viewport = new ScreenViewport();
        ui = new Stage( viewport );
        Gdx.input.setInputProcessor( ui );
    }

    @Override
    public void render( float delta ) {

        ui.getViewport().apply();
        ui.act( delta );
        ui.draw();
    }

    @Override
    public void resize( int width, int height ) {
        // set the viewport so there are 160 pixels along the smallest axis
        float unitPerPixels = 160f / Math.min( width, height );
        viewport.setUnitsPerPixel( unitPerPixels );
        viewport.update( width, height, true );
    }

    public Stage getUserInterface() {
        return ui;
    }

    @Override
    public void dispose() {
        ui.dispose();
    }
}
