package com.github.texxel.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.github.texxel.GameState;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.mechanics.FieldOfVision;
import com.github.texxel.mechanics.FogOfWar;
import com.github.texxel.mechanics.SimpleFog;
import com.github.texxel.tiles.Tile;
import com.github.texxel.utils.ColorMaths;

public class FogActor extends Actor {

    final GameState state;
    final FogOfWar fog;
    final Camera gameCamera, uiCamera;

    public FogActor( GameState state, Camera gameCamera, Camera uiCamera ) {
        this.state = state;
        this.gameCamera = gameCamera;
        this.uiCamera = uiCamera;
        Level level = state.getLevel();
        fog = new SimpleFog( level.width(), level.height() );
        toBack();
    }

    @Override
    public void act( float delta ) {
        updateFog();
    }

    @Override
    public void draw( Batch batch, float parentAlpha ) {
        batch.setProjectionMatrix( gameCamera.combined );
        fog.render( batch );
        batch.setProjectionMatrix( uiCamera.combined );
    }

    private void updateFog() {
        Level level = state.getLevel();
        TileMap tileMap = level.getTileMap();
        FogOfWar fog = this.fog;
        FieldOfVision fov = state.getPlayer().getVision();

        // can't see
        for ( int i = 0; i < tileMap.width(); i++ ) {
            for ( int j = 0; j < tileMap.height(); j++ ) {
                fog.setColorAroundTile( i, j, ColorMaths.toRGBA( 0, 0, 0, 1f ) );
            }
        }
        // discovered
        for ( int i = 0; i < tileMap.width(); i++ ) {
            for ( int j = 0; j < tileMap.height(); j++ ) {
                Tile tile = tileMap.getTile( i, j );
                if ( fov.isKnown( i, j ) && !tile.isOpaque() )
                    fog.setColorAroundTile( i, j, ColorMaths.toRGBA( 0f, 0f, 0f, 0.6f ) );
            }
        }
        // visible
        for ( int i = 0; i < tileMap.width(); i++ ) {
            for ( int j = 0; j < tileMap.height(); j++ ) {
                Tile tile = tileMap.getTile( i, j );
                if ( fov.isVisible( i, j ) && !tile.isOpaque() )
                    fog.setColorAroundTile( i, j, ColorMaths.toRGBA( 0f, 0f, 0f, 0f ) );
            }
        }
    }

}
