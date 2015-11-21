package com.github.texxel.actors.heroes;

import com.github.texxel.actors.AbstractChar;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.brains.HeroHuntAI;
import com.github.texxel.actors.ai.brains.HeroIdleAI;
import com.github.texxel.actors.ai.brains.HeroInteractAI;
import com.github.texxel.actors.ai.brains.HeroMoveAI;
import com.github.texxel.actors.ai.sensors.HeroDangerSensor;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.events.input.CellSelectedEvent;
import com.github.texxel.event.listeners.input.CellSelectedListener;
import com.github.texxel.event.listeners.level.TileSetListener;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.mechanics.FieldOfVision;
import com.github.texxel.mechanics.FogOfWar;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.tiles.Interactable;
import com.github.texxel.tiles.Tile;
import com.github.texxel.utils.ColorMaths;
import com.github.texxel.utils.Point2D;

public abstract class AbstractHero extends AbstractChar implements Hero {

    private static class Listener implements CellSelectedListener, TileSetListener {

        static {
            ConstructorRegistry.put( Listener.class, new Constructor<Listener>() {
                @Override
                public Listener newInstance( Bundle bundle ) {
                    return new Listener( null );
                }
            } );
        }

        private AbstractHero hero;

        public Listener( AbstractHero hero ) {
            this.hero = hero;
        }

        @Override
        public void onCellSelected( CellSelectedEvent e ) {
            if ( e.isCancelled() )
                return;
            int x = e.getX();
            int y = e.getY();
            Level level = e.getLevel();
            for ( Char c : level.getCharacters()) {
                if ( c.isOver( x, y ) ) {
                    hero.setBrain( new HeroHuntAI( hero, c ) );
                    return;
                }
            }
            if ( x >= 0 && x < level.width() && y >= 0 && y < level.height()  ) {
                Tile tile = level.getTileMap().getTile( x, y );
                if ( tile instanceof Interactable )
                    hero.setBrain( new HeroInteractAI( hero, new Point2D( x, y ) ) );
                else
                    hero.setBrain( new HeroMoveAI( hero, new Point2D( x, y ) ) );
            }
        }

        @Override
        public boolean onTileSet( TileMap tileMap, Tile tile, int x, int y ) {
            hero.updateFog();
            return false;
        }

        @Override
        public Bundle bundle( BundleGroup topLevel ) {
            Bundle bundle = topLevel.newBundle();
            bundle.putBundlable( "hero", hero );
            return bundle;
        }

        @Override
        public void restore( Bundle bundle ) {
            hero = bundle.getBundlable( "hero" );
        }
    }

    public AbstractHero( Level level, Point2D spawn ) {
        super( level, spawn, 100 );
        updateFog();
        setBrain( new HeroIdleAI( this ) );
        addSensor( new HeroDangerSensor( this ) );
        Listener listener = new Listener( this );
        level.getCellSelectHandler().addListener( listener, EventHandler.LATE );
        level.getTileMap().getTileSetHandler().addListener( listener, EventHandler.VERY_LATE );
    }

    protected AbstractHero( Bundle bundle ) {
        super( bundle );
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
        TileMap tileMap = level().getTileMap();
        FogOfWar fog = level().getFogOfWar();
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
    protected FieldOfVision makeFOV() {
        return new BasicHeroFOV( level().getTileMap().getLosBlocking(), getLocation() );
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
