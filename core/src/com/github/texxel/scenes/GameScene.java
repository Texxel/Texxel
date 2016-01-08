package com.github.texxel.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.texxel.GameState;
import com.github.texxel.gameloop.GameRenderer;
import com.github.texxel.gameloop.GameUpdater;
import com.github.texxel.gameloop.LevelRenderer;
import com.github.texxel.gameloop.LevelUpdater;
import com.github.texxel.ui.BackPackWindow;
import com.github.texxel.ui.CameraControl;
import com.github.texxel.ui.DepthMover;
import com.github.texxel.ui.HeroControl;
import com.github.texxel.ui.HeroFollower;
import com.github.texxel.ui.PixelSkin;
import com.github.texxel.ui.StatusPane;
import com.github.texxel.utils.Assert;

import java.io.IOException;

public class GameScene implements Screen {

    // world things
    private GameState state;
    private GameUpdater updater;
    private GameRenderer renderer;
    private OrthographicCamera gameCamera;

    // ui things
    private Game app;
    private Stage ui;
    private ScreenViewport viewport;

    /**
     * Creates a visual display for the given game state
     * @param state the state of the game
     */
    public GameScene( Game app, GameState state ) {
        this.app = Assert.nonnull( app, "App cannot be null" );
        this.state = Assert.nonnull( state, "Game state cannot be null" );
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

        ui.addListener( new HeroControl( state, ui.getCamera(), gameCamera ) );
        ui.addActor( new DepthMover( app, state ) );
        ui.addListener( new CameraControl( gameCamera, ui.getCamera() ) );
        ui.addActor( new HeroFollower( state, gameCamera ) );

        ui.addActor( new StatusPane( state ) );

        ui.addActor( new TextButton( "Click me", PixelSkin.chrome() ) {{
            addListener( new ChangeListener() {
                @Override
                public void changed( ChangeEvent event, Actor actor ) {
                    ui.addActor( new BackPackWindow( state.getPlayer().getInventory() ) );
                }
            } );
        }});
    }

    @Override
    public void render( float delta ) {
        // limit the tick time to assist in debugging
        delta = Math.min( delta, 0.1f );

        Gdx.gl.glViewport( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        updater.update( state.getLevel(), delta );
        renderer.render( state.getLevel(), delta );

        ui.getViewport().apply();
        ui.act( delta );
        ui.draw();
    }

    public Stage getUserInterface() {
        return ui;
    }

    public Game getApp() {
        return app;
    }

    public GameState getState() {
        return state;
    }

    /**
     * Gets the Camera that is displaying the world
     * @return the worlds camera
     */
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
        try {
            state.save();
        } catch ( IOException e ) {
            throw new RuntimeException( "Couldn't save level", e );
        }
        state.getLevel().destroy();
    }

    @Override
    public void dispose() {

    }
}
