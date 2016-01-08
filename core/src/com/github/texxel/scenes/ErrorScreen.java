package com.github.texxel.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.github.texxel.ui.GameWindow;
import com.github.texxel.ui.PixelSkin;
import com.github.texxel.utils.Assert;

/**
 * The error screen displays an error message to the user.
 */
public class ErrorScreen extends PixelScreen {

    private final Throwable throwable;

    public ErrorScreen( Game app, Throwable error ) {
        super( app );
        this.throwable = Assert.nonnull( error, "Cannot display a null error" );
    }

    @Override
    public void show() {
        super.show();
        Label area = new Label( throwable.getMessage(), PixelSkin.chrome() );
        GameWindow window = new GameWindow( "EEK! An error!", area );
        getUserInterface().addActor( window );

    }

    @Override
    public void render( float delta ) {
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        super.render( delta );
    }
}
