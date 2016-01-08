package com.github.texxel.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.github.texxel.GameState;
import com.github.texxel.levels.components.LevelDescriptor;
import com.github.texxel.utils.Assert;

import java.io.IOException;

public class IntervalLevelScreen implements Screen {

    private final Game app;
    private final GameState state;
    private final LevelDescriptor nextLevel;

    public IntervalLevelScreen( Game app, GameState state, LevelDescriptor nextLevel ) {
        this.app = Assert.nonnull( app, "App cannot be null");
        this.state = Assert.nonnull( state, "Game State cannot be null" );
        this.nextLevel = Assert.nonnull( nextLevel, "next level cannot be null" );
    }

    @Override
    public void show() {
    }

    @Override
    public void render( float delta ) {
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        try {
            state.progress( nextLevel );
        } catch ( IOException e ) {
            throw new RuntimeException( "Failed to load level", e );
        }
        app.setScreen( new GameScene( app, state ) );

    }

    @Override
    public void resize( int width, int height ) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
