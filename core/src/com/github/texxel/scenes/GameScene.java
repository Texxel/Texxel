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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameScene implements Screen {

    // world things
    private final Dungeon dungeon;
    private final Level level;
    private GameUpdater updater;
    private GameRenderer renderer;
    private Hero player;
    private OrthographicCamera gameCamera;

    // ui things
    private Stage ui;
    private ScreenViewport viewport;

    /**
     * Creates a visual display for the given level
     * @param dungeon the dungeon the game scene is for
     * @param level the level to display
     * @param player the hero to track
     */
    public GameScene( Dungeon dungeon, Level level, Hero player ) {
        if ( dungeon == null )
            throw new NullPointerException( "dungeon cannot be null" );
        if ( level == null )
            throw new NullPointerException( "level cannot be null" );
        if ( player == null )
            throw new NullPointerException( "player cannot be null" );

        this.dungeon = dungeon;
        this.level = level;
        this.player = player;
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
        // limit the tick time to assist in debugging
        delta = Math.min( delta, 0.1f );

        Gdx.gl.glViewport( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        updater.update( level, delta );
        renderer.render( level, delta );

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

    /**
     * Sets what player should get tracked. This is the character that the user sees. Texxel will
     * never change this - but who knows what a mod will do.
     * @param player the tracked player
     */
    public void setPlayer( Hero player ) {
        if ( player == null )
            throw new NullPointerException( "'player' cannot be null" );
        this.player = player;
    }

    /**
     * Gets the Camera that is displaying the world
     * @return the worlds camera
     */
    public OrthographicCamera getGameCamera() {
        return gameCamera;
    }

    /**
     * Gets the level this GameScene is for
     * @return the level
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Gets the dungeon this GameScene is for
     * @return the dungeon
     */
    public Dungeon getDungeon() {
        return dungeon;
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
        try {
            dungeon.save();
            dungeon.save( level );
            level.destroy();
        } catch ( IOException e ) {
            // TODO handle error while saving
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {

    }
}
