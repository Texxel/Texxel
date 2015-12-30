package com.github.texxel.actors.heroes;

import com.github.texxel.actors.AbstractChar;
import com.github.texxel.actors.ai.goals.HeroIdleGoal;
import com.github.texxel.actors.ai.sensors.HeroDamageSensor;
import com.github.texxel.actors.ai.sensors.HeroDangerSensor;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.listeners.level.TileSetListener;
import com.github.texxel.items.ItemUtils;
import com.github.texxel.items.api.Weapon;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.mechanics.FieldOfVision;
import com.github.texxel.mechanics.FogOfWar;
import com.github.texxel.tiles.Tile;
import com.github.texxel.utils.ColorMaths;
import com.github.texxel.utils.Point2D;

public abstract class AbstractHero extends AbstractChar implements Hero {

    private static final long serialVersionUID = -1893036218210928887L;

    private class Listener implements TileSetListener {

        private static final long serialVersionUID = 8094447892497757267L;

        @Override
        public boolean onTileSet( TileMap tileMap, Tile tile, int x, int y ) {
            updateFog();
            return false;
        }
    }

    private final Inventory inventory;

    public AbstractHero( Level level, Point2D spawn ) {
        super( level, spawn, 100 );
        updateFog();
        setGoal( new HeroIdleGoal( this ) );
        addSensor( new HeroDangerSensor( this ) );
        addSensor( new HeroDamageSensor( this ) );
        Listener listener = new Listener();
        level.getTileMap().getTileSetHandler().addListener( listener, EventHandler.TEXXEL_LISTEN );

        inventory = new Inventory();
        inventory.getEquippedSlots().getSlot( 0 ).setFilter( ItemUtils.weaponFilter() );
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

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public Weapon getWeapon() {
        return (Weapon) inventory.getEquippedSlots().get( 0 );
    }

    @Override
    public void setEquippedWeapon( Weapon weapon ) {
        inventory.getEquippedSlots().set( 0, weapon );
    }
}
