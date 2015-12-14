package com.github.texxel.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.github.texxel.Dungeon;
import com.github.texxel.gameloop.GameInput;
import com.github.texxel.gameloop.GameRenderer;
import com.github.texxel.gameloop.GameUpdater;
import com.github.texxel.gameloop.LevelInput;
import com.github.texxel.gameloop.LevelRenderer;
import com.github.texxel.gameloop.LevelUpdater;
import com.github.texxel.levels.Level;
import com.github.texxel.utils.GameTimer;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameScene implements Screen {

    private final Dungeon dungeon;
    private final Level level;
    private GameUpdater updater;
    private GameRenderer renderer;
    private GameInput input;

    public GameScene( Dungeon dungeon, Level level ) {
        this.dungeon = dungeon;
        this.level = level;
    }

    @Override
    public void show() {
        updater = new LevelUpdater();
        OrthographicCamera camera = new OrthographicCamera();
        renderer = new LevelRenderer( camera );
        input = new LevelInput( camera );

        Gdx.input.setOnscreenKeyboardVisible( false );
    }

    @Override
    public void render( float delta ) {
        GameTimer.update();
        input.process( level );
        updater.update( level );
        renderer.render( level );
    }

    @Override
    public void resize( int width, int height ) {
        renderer.resize( width, height );
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        input.onDestroy();
        ObjectOutputStream oos = null;
        try {
            dungeon.save();
            FileHandle file = Gdx.files.local( "level-" + level.id() + ".json" );
            oos = new ObjectOutputStream( new BufferedOutputStream( file.write( false ) ) );
            oos.writeObject( level );
        } catch ( IOException e ) {
            // TODO handle error while saving
            e.printStackTrace();
        } finally {
            if ( oos != null ) {
                try {
                    oos.close();
                } catch ( IOException ignored ) {
                    // just let the memory leak out
                }
            }
        }
    }

    @Override
    public void dispose() {

    }
}
