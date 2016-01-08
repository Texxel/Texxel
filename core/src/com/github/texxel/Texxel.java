package com.github.texxel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.github.texxel.modloader.Mod;
import com.github.texxel.modloader.ModLoader;
import com.github.texxel.scenes.ErrorScreen;
import com.github.texxel.scenes.GameScene;

import java.util.List;

public class Texxel extends Game {

    private ModLoader loader;
    private List<Mod> modList;

    public Texxel( ModLoader loader ) {
        this.loader = loader;
    }

    @Override
    public void create() {

        try {
            modList = loader.loadMods();

            GameState state = GameState.resume( 1 );
            if ( state == null )
                state = GameState.newGame( 1 );

            setScreen( new GameScene( this, state ) );

            for ( Mod mod : modList ) {
                mod.onGameStart( state );
            }

        } catch ( Throwable e ) {
            crash( "Crash while creating game", e );
        }
    }

    private void crash( String message, Throwable e ) {
        Gdx.app.error( "Texxel", message, e );
        // manually switch the screen since setScreen() would probably crash when disabling the old screen
        screen = new ErrorScreen( this, e );
        screen.show();
        screen.resize( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
    }

    @Override
    public void render() {
        try {
            super.render();
        } catch ( Throwable error ) {
            crash( "Crash while rendering", error );
        }
    }

    @Override
    public void resume() {
        try {
            super.resume();
        } catch ( Throwable e ) {
            crash( "Crash while resuming", e );
        }
    }

    @Override
    public void pause() {
        try {
            super.pause();
        } catch ( Throwable e ) {
            crash( "Crash while pausing", e );
        }
    }

    @Override
    public void resize( int width, int height ) {
        try {
            super.resize( width, height );
        } catch ( Throwable e ) {
            crash( "Crash while resizing", e );
        }
    }

    @Override
    public void dispose() {
        try {
            super.dispose();
        } catch ( Throwable e ) {
            crash( "Crash while disposing", e );
        }
    }

}
