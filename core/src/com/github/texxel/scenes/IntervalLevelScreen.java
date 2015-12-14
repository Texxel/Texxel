package com.github.texxel.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.github.texxel.Dungeon;
import com.github.texxel.Texxel;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.LevelDescriptor;

public class IntervalLevelScreen implements Screen {

    public interface TransitionReason {
        String getText();
    }

    public enum StandardTransitionReason implements TransitionReason {

        STAIRS_ASCEND( "ASCENDING" ),
        STAIRS_DESCENDING( "DESCENDING" ),
        STAIRS_FALLING( "FALLING" );

        private final String text;
        StandardTransitionReason( String text ) {
            this.text = text;
        }

        @Override
        public String getText() {
            return text;
        }
    }

    private final Dungeon dungeon;
    private final LevelDescriptor nextLevel;
    private final TransitionReason reason;

    public IntervalLevelScreen( Dungeon dungeon, LevelDescriptor nextLevel, TransitionReason reason ) {
        if ( dungeon == null )
            throw new NullPointerException( "'dungeon' cannot be null" );
        if ( nextLevel == null )
            throw new NullPointerException( "'nextLevel' cannot be null" );
        if ( reason == null )
            throw new NullPointerException( "'reason' cannot be null" );
        this.dungeon = dungeon;
        this.nextLevel = nextLevel;
        this.reason = reason;
    }

    @Override
    public void show() {
    }

    @Override
    public void render( float delta ) {
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        Level level = dungeon.loadLevel( nextLevel );
        Texxel.getInstance().setScreen( new GameScene( dungeon, level ) );
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
