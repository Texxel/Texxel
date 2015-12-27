package com.github.texxel.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.texxel.Dungeon;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.gameloop.GameRenderer;
import com.github.texxel.gameloop.GameUpdater;
import com.github.texxel.gameloop.LevelRenderer;
import com.github.texxel.gameloop.LevelUpdater;
import com.github.texxel.levels.Level;
import com.github.texxel.ui.BackPackWindow;
import com.github.texxel.ui.CameraControl;
import com.github.texxel.ui.HeroControl;
import com.github.texxel.ui.HeroFollower;
import com.github.texxel.ui.PixelSkin;
import com.github.texxel.ui.StatusPane;
import com.github.texxel.utils.GameTimer;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameScene implements Screen {

    private final Dungeon dungeon;
    private final Level level;
    private GameUpdater updater;
    private GameRenderer renderer;
    private Hero player;
    private OrthographicCamera gameCamera;

    private Stage ui;
    private ScreenViewport viewport;


    public GameScene( Dungeon dungeon, Level level ) {
        this.dungeon = dungeon;
        this.level = level;

        // super hacky method to find hero
        for ( Char c : level.getCharacters() ) {
            if ( c instanceof Hero ) {
                setPlayer( (Hero)c );
                break;
            }
        }
        assert getPlayer() != null;
    }

    @Override
    public void show() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        float aspectRatio = h / w;

        // set up the world
        OrthographicCamera gameCamera = this.gameCamera = new OrthographicCamera();
        gameCamera.setToOrtho( false, 34, 34 * aspectRatio );
        gameCamera.position.set( 17, 17, 0 );
        gameCamera.update();

        updater = new LevelUpdater();
        renderer = new LevelRenderer( gameCamera );


        // Set up the ui
        viewport = new ScreenViewport();
        ui = new Stage( viewport );
        Gdx.input.setInputProcessor( ui );

        ui.addListener( new HeroControl( this ) );
        ui.addListener( new CameraControl( gameCamera, ui.getCamera() ) );
        ui.addActor( new HeroFollower( this ) );

        ui.addActor( new StatusPane( this ) );

        ui.addActor( new TextButton( "Click me", PixelSkin.chrome() ) {{
            addListener( new ChangeListener() {
                @Override
                public void changed( ChangeEvent event, Actor actor ) {
                    ui.addActor( new BackPackWindow( getPlayer().getInventory() ) );
                }
            } );
        }});
    }

    @Override
    public void render( float delta ) {
        GameTimer.update();

        Gdx.gl.glViewport( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        updater.update( level );
        renderer.render( level );

        ui.getViewport().apply();
        ui.act( delta );
        ui.draw();
    }

    public Stage getUserInterface() {
        return ui;
    }

    public Hero getPlayer() {
        return player;
    }

    public void setPlayer( Hero player ) {
        if ( player == null )
            throw new NullPointerException( "'player' cannot be null" );
        this.player = player;
    }

    public OrthographicCamera getGameCamera() {
        return gameCamera;
    }

    @Override
    public void resize( int width, int height ) {
        gameCamera.viewportHeight = gameCamera.viewportWidth * height / width;
        gameCamera.update();

        // set the viewport so there are 160 pixels along the smallest axis
        float unitPerPixels = 160f / Math.min( width, height );
        viewport.setUnitsPerPixel( unitPerPixels );
        viewport.update( width, height, true );
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        ui.dispose();
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
