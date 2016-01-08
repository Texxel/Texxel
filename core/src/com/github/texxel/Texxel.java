package com.github.texxel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.github.texxel.modloader.Mod;
import com.github.texxel.modloader.ModLoader;
import com.github.texxel.scenes.GameScene;

import java.util.List;

public class Texxel extends Game {

    public static Texxel getInstance() {
        return instance;
    }
    private static Texxel instance;

    private ModLoader loader;
    private List<Mod> modList;

    public Texxel( ModLoader loader ) {
        instance = this;
        this.loader = loader;
    }

    @Override
    public void create() {

        modList = loader.loadMods();

        GameState state;
        try {
            state = GameState.resume( 1 );
            if ( state == null )
                state = GameState.newGame( 1 );
        } catch ( Exception e ) {
            throw new RuntimeException( "Broken load", e );
        }

        setScreen( new GameScene( state ) );

        for ( Mod mod : modList ) {
            mod.onGameStart( state );
        }
    }

    @Override
    public void render() {
        super.render();
        if ( Gdx.graphics.getFrameId() % 100 == 0 )
            System.out.println( "FPS: " + Gdx.graphics.getFramesPerSecond() );
    }
}
