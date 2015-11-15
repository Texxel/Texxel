package com.github.texxel.gameloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.texxel.actors.Actor;
import com.github.texxel.event.events.input.CellSelectedEvent;
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
        private final TileMap tileMap;

        public TileRenderer( TileMap tileMap ) {
            this.tileMap = tileMap;
        }

        @Override
        public void onDraw( SpriteBatch batch ) {
            TileMap tileMap = this.tileMap;
            for ( int i = tileMap.width() - 1; i >= 0; i-- ) {
                for ( int j = tileMap.height() - 1; j >= 0; j-- ) {
                    Tile tile = tileMap.getTile( i, j );
                    batch.draw( tile.getDefaultImage(), i, j, 1, 1 );
                }
            }
        }
    }

    private class ClickHandler implements CameraMover.ClickHandler {
        @Override
        public void onClick( int x, int y ) {
            xTouches[touchTop] = x;
            yTouches[touchTop] = y;
            touchTop = (touchTop+1) % MAX_TOUCHES;
        }
    }

    private static class FogRenderer implements GameBatcher.OptimisedDrawer {
        private final FogOfWar fog;

        public FogRenderer( FogOfWar fog ) {
            this.fog = fog;
        }

        @Override
        public void onDraw( SpriteBatch batch ) {
            fog.onDraw( batch );
        }
    }

    private static final int MAX_TOUCHES = 5;
    private final OrthographicCamera camera;
    private final GameBatcher batch;
    private final int[] xTouches = new int[MAX_TOUCHES];
    private final int[] yTouches = new int[MAX_TOUCHES];
    private int touchIndex = 0;
    private int touchTop = 0;

    public LevelRenderer( Level level ) {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera( 20, 20 * (h / w) );
        camera.position.set( 6, 6, 0 );
        camera.update();

        batch = new GameBatcher( camera );
        batch.addOptimisedDrawer( 0, new TileRenderer( level.getTileMap() ) );
        batch.addOptimisedDrawer( 1000, new FogRenderer( level.getFogOfWar() ) );
        new CameraMover( camera, new ClickHandler() );
    }

    public void render( Level level ) {
        processInput( level );

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

    private void processInput( Level level ) {
        int touchIndex = this.touchIndex;
        int touchTop   = this.touchTop;
        int[] xTouches = this.xTouches;
        int[] yTouches = this.yTouches;
        for ( ; touchIndex != touchTop; touchIndex = (touchIndex+1)%MAX_TOUCHES ) {
            level.getCellSelectHandler().dispatch(
                    new CellSelectedEvent( level, xTouches[touchIndex], yTouches[touchIndex] ) );
        }
        this.touchIndex = touchIndex;
    }

}
