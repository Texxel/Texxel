package com.github.texxel.actors.heroes;

import com.github.texxel.Dungeon;
import com.github.texxel.actors.AbstractChar;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.brains.HeroHuntAI;
import com.github.texxel.actors.ai.brains.HeroIdleAI;
import com.github.texxel.actors.ai.brains.HeroMoveAI;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.events.input.CellSelectedEvent;
import com.github.texxel.event.listeners.input.CellSelectedListener;
import com.github.texxel.event.listeners.level.TileSetListener;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.mechanics.FieldOfVision;
import com.github.texxel.mechanics.FogOfWar;
import com.github.texxel.saving.Bundle;
import com.github.texxel.tiles.Tile;
import com.github.texxel.utils.ColorMaths;
import com.github.texxel.utils.Point2D;

public abstract class AbstractHero extends AbstractChar implements Hero, CellSelectedListener, TileSetListener {

    public AbstractHero( Point2D spawn ) {
        super( spawn, 10 );
        updateFog();
        setBrain( new HeroIdleAI( this ) );
        Dungeon.level().getCellSelectHandler().addListener( this, EventHandler.LATE );
        Dungeon.level().getTileMap().getTileSetHandler().addListener( this, EventHandler.VERY_LATE );
    }

    protected AbstractHero( Bundle bundle ) {
        super( bundle );
    }

    @Override
    public void onCellSelected( CellSelectedEvent e ) {
        if ( e.isCancelled() )
            return;
        int x = e.getX();
        int y = e.getY();
        Level level = Dungeon.level();
        for ( Char c : level.getCharacters()) {
            if ( c.isOver( x, y ) ) {
                setBrain( new HeroHuntAI( this, c ) );
                return;
            }
        }
        if ( x >= 0 && x < level.width() && y >= 0 && y < level.height()  )
            setBrain( new HeroMoveAI( this, new Point2D( x, y ) ) );
    }

    @Override
    public HeroFOV getVision() {
        FieldOfVision fov = super.getVision();
        if ( fov instanceof HeroFOV )
            return (HeroFOV)fov;
        else
            throw new ClassCastException( "Hero's FOV must be instance of HeroFOV" );
    }

    private void updateFog() {
        TileMap tileMap = Dungeon.level().getTileMap();
        FogOfWar fog = Dungeon.level().getFogOfWar();
        HeroFOV fov = getVision();
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
                if ( fov.isDiscovered( i, j ) && !tile.isOpaque() )
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

    @Override
    public Point2D setLocation( Point2D location ) {
        location = super.setLocation( location );
        updateFog();
        return location;
    }

    @Override
    public boolean onTileSet( TileMap tileMap, Tile tile, int x, int y ) {
        updateFog();
        return false;
    }

    @Override
    protected FieldOfVision makeFOV() {
        return new BasicHeroFOV( Dungeon.level().getTileMap().getLosBlocking(), getLocation() );
    }

    @Override
    public boolean isUserControlled() {
        return true;
    }

    @Override
    public Side getSide() {
        return Side.GOOD;
    }
}
