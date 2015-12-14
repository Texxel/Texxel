package com.github.texxel.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.github.texxel.Dungeon;
import com.github.texxel.gameloop.GameInput;
import com.github.texxel.gameloop.GameRenderer;
import com.github.texxel.gameloop.GameUpdater;
import com.github.texxel.gameloop.LevelInput;
import com.github.texxel.gameloop.LevelRenderer;
import com.github.texxel.gameloop.LevelUpdater;
import com.github.texxel.levels.Level;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.BundleWriter;
import com.github.texxel.utils.GameTimer;

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
        BundleGroup group = BundleGroup.newGroup();
        group.putBundlable( "level", level );
        BundleWriter.write( BundleWriter.file( "level-" + level.id() + ".json" ), group );
        dungeon.save();
    }

    @Override
    public void dispose() {

    }
}
