package com.github.texxel.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.github.texxel.Dungeon;
import com.github.texxel.levels.Level;
import com.github.texxel.gameloop.LevelRenderer;
import com.github.texxel.gameloop.LevelUpdater;
import com.github.texxel.utils.GameTimer;

public class GameScene implements Screen {

    private LevelUpdater updater;
    private LevelRenderer renderer;

    @Override
    public void show() {
        Dungeon.goTo( 1 );
        updater = new LevelUpdater();
        renderer = new LevelRenderer();
        Gdx.input.setOnscreenKeyboardVisible( true );
    }

    @Override
    public void render( float delta ) {
        GameTimer.update();
        Level level = Dungeon.level();
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
    }

    @Override
    public void dispose() {
        Dungeon.gameEnd();
    }
}
