package com.github.texxel.gameloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.texxel.Dungeon;
import com.github.texxel.actors.Actor;
import com.github.texxel.items.Heap;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.mechanics.FogOfWar;
import com.github.texxel.sprites.api.Visual;
import com.github.texxel.sprites.api.WorldVisual;
import com.github.texxel.tiles.Tile;

import java.util.List;

public class LevelRenderer {

    private static class TileRenderer implements GameBatcher.OptimisedDrawer {
        @Override
        public void onDraw( SpriteBatch batch ) {
            TileMap tileMap = Dungeon.level().getTileMap();
            for ( int i = tileMap.width() - 1; i >= 0; i-- ) {
                for ( int j = tileMap.height() - 1; j >= 0; j-- ) {
                    Tile tile = tileMap.getTile( i, j );
                    batch.draw( tile.getImage(), i, j, 1, 1 );
                }
            }
        }
    }

    private static class FogRenderer implements GameBatcher.OptimisedDrawer {
        @Override
        public void onDraw( SpriteBatch batch ) {
            FogOfWar fogOfWar = Dungeon.level().getFogOfWar();
            fogOfWar.onDraw( batch );

        }
    }

    private final OrthographicCamera camera;
    private final GameBatcher batch;

    public LevelRenderer() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera( 20, 20 * (h / w) );
        camera.position.set( 6, 6, 0 );
        camera.update();

        batch = new GameBatcher( camera );
        batch.addOptimisedDrawer( 0, new TileRenderer());
        batch.addOptimisedDrawer( 1000, new FogRenderer() );
        new CameraMover( camera );
    }

    public void render( Level level ) {
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        List<Heap> heaps = level.getHeaps();
        for ( int i = heaps.size() - 1; i >= 0; i-- ) {
            Heap heap = heaps.get( i );
            heap.getVisual().render( batch );
        }

        List<Actor> actors = level.getActors();
        for ( int i = actors.size() - 1; i >= 0; i-- ) {
            Actor actor = actors.get( i );
            if ( actor instanceof WorldVisual ) {
                WorldVisual wv = (WorldVisual) actor;
                Visual v = wv.getVisual();
                v.render( batch );
            }
        }

        batch.flush();
    }

    public void resize( int width, int height ) {
        camera.viewportHeight = camera.viewportWidth * height / width;
        camera.update();
    }

}
