package com.github.texxel.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.github.texxel.GameState;
import com.github.texxel.levels.components.LevelDescriptor;
import com.github.texxel.utils.Assert;

public class IntervalLevelScreen implements Screen {

    private final GameState state;
    private final LevelDescriptor nextLevel;

    public IntervalLevelScreen( GameState state, LevelDescriptor nextLevel ) {
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

        state.progress( nextLevel );
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
