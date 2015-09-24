package com.github.texxel.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.github.texxel.Texxel;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.LevelDescriptor;

public class IntervalLevelScreen implements Screen{

    public static String DESCENDING_TEXT = "DESCENDING";
    public static String FALLING_TEXT = "FALLING";
    public static String ASCENDING_TEXT = "ASCENDING";

    private final LevelDescriptor nextLevel;
    private final String text;

    public IntervalLevelScreen( LevelDescriptor nextLevel, String text ) {
        this.nextLevel = nextLevel;
        this.text = text;
    }

    @Override
    public void show() {
    }

    @Override
    public void render( float delta ) {
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        Level level = nextLevel.constructLevel(  );
        Texxel.getInstance().setScreen( new GameScene() );
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
