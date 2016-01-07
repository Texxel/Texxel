package com.github.texxel.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.github.texxel.Dungeon;
import com.github.texxel.Texxel;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.LevelDescriptor;

public class IntervalLevelScreen implements Screen {

    private final Dungeon dungeon;
    private final Level previousLevel;
    private final LevelDescriptor nextLevel;
    private final Hero player;

    public IntervalLevelScreen( Dungeon dungeon, Level currentLevel, LevelDescriptor nextLevel, Hero player ) {
        if ( dungeon == null )
            throw new NullPointerException( "'dungeon' cannot be null" );
        if ( nextLevel == null )
            throw new NullPointerException( "'nextLevel' cannot be null" );
        if ( player == null )
            throw new NullPointerException( "player cannot be null" );
        if ( currentLevel == null )
            throw new NullPointerException( "currentLevel cannot be null" );

        this.dungeon = dungeon;
        this.nextLevel = nextLevel;
        this.player = player;
        this.previousLevel = currentLevel;
    }

    @Override
    public void show() {
    }

    @Override
    public void render( float delta ) {
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        Level level = dungeon.loadLevel( nextLevel );
        level.addActor( player );

        Texxel.getInstance().setScreen( new GameScene( dungeon, level, player ) );
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
