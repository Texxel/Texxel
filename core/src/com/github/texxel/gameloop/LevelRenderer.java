package com.github.texxel.gameloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.actors.Actor;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.Theme;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.sprites.api.CustomRenderer;
import com.github.texxel.sprites.api.EmptyTexture;
import com.github.texxel.sprites.api.Visual;
import com.github.texxel.sprites.api.WorldVisual;
import com.github.texxel.sprites.imp.AbstractVisual;
import com.github.texxel.tiles.Tile;

import java.util.List;

public class LevelRenderer implements GameRenderer {

    private class TileRenderer extends AbstractVisual implements CustomRenderer {

        {
            setDepth( 100 );
        }

        @Override
        public boolean render( Batch batch ) {
            TileMap tileMap = level.getTileMap();
            Theme theme = level.getTheme();
            for ( int i = tileMap.width() - 1; i >= 0; i-- ) {
                for ( int j = tileMap.height() - 1; j >= 0; j-- ) {
                    Tile tile = tileMap.getTile( i, j );
                    TextureRegion region = theme.image( tile, tileMap, i, j );
                    batch.draw( region, i, j, 1, 1 );
                }
            }
            return false;
        }

        @Override
        public TextureRegion getRegion() {
            return EmptyTexture.instance();
        }
    }

    private class FogRenderer extends AbstractVisual implements CustomRenderer {

        {
            setDepth( -100 );
        }

        @Override
        public boolean render( Batch batch ) {
            level.getFogOfWar().render( batch );
            return false;
        }

        @Override
        public TextureRegion getRegion() {
            return EmptyTexture.instance();
        }
    }

    private final GameBatcher batch;
    private final Visual[] specialVisuals;

    Level level;

    public LevelRenderer( OrthographicCamera camera ) {
        batch = new GameBatcher( camera );

        specialVisuals = new Visual[] {
                new TileRenderer(),
                new FogRenderer()
        };
    }

    @Override
    public void render( Level level, float dt ) {
        this.level = level;

        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // draw the actors
        List<Actor> actors = level.getActors();
        for ( int i = actors.size() - 1; i >= 0; i-- ) {
            Actor actor = actors.get( i );
            if ( actor instanceof WorldVisual ) {
                WorldVisual wv = (WorldVisual) actor;
                Visual v = wv.getVisual();
                batch.draw( v );
            }
        }

        // draw the special visuals
        for ( Visual visual : specialVisuals )
            batch.draw( visual );

        batch.flush( dt );
    }

}
