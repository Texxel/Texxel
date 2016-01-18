package com.github.texxel.actors.heroes;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.actors.AbstractChar;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.goals.HeroIdleGoal;
import com.github.texxel.actors.ai.sensors.HeroDamageSensor;
import com.github.texxel.actors.ai.sensors.HeroDangerSensor;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.listeners.level.TileSetListener;
import com.github.texxel.items.ItemUtils;
import com.github.texxel.items.api.Item;
import com.github.texxel.items.api.Weapon;
import com.github.texxel.items.helper.AbstractWeapon;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.mechanics.FieldOfVision;
import com.github.texxel.mechanics.FogOfWar;
import com.github.texxel.sprites.api.EmptyTexture;
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
        addSensor( new HeroDangerSensor( this ) );
        addSensor( new HeroDamageSensor( this ) );
        Listener listener = new Listener();
        level.getTileMap().getTileSetHandler().addListener( listener, EventHandler.TEXXEL_LISTEN );

        inventory = new Inventory();
        inventory.getEquippedSlots().getSlot( 0 ).setFilter( ItemUtils.weaponFilter() );
    }

    @Override
    protected Goal defaultGoal() {
        return new HeroIdleGoal( this );
    }

    @Override
    public HeroFOV getVision() {
        FieldOfVision fov = super.getVision();
        if ( fov instanceof HeroFOV )
            return (HeroFOV)fov;
        else
            // this should never be able to happen since makeFOV() returns a HeroFOV
            throw new ClassCastException( "Hero's FOV must be instance of HeroFOV" );
    }

    @Override
    protected HeroFOV makeFOV() {
        return new BasicHeroFOV( level().getTileMap().getLosBlocking(), getLocation() );
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
    public Side getSide() {
        return Side.GOOD;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public Weapon weapon() {
        Item item = inventory.getEquippedSlots().get( 0 );
        if ( item instanceof Weapon )
            return (Weapon)item;
        else
            return handWeapon();
    }

    public Weapon handWeapon() {
        return new AbstractWeapon( 1, 1, 1 ) {
            private static final long serialVersionUID = -2856833038145940547L;

            @Override
            public TextureRegion getImage() {
                return EmptyTexture.instance();
            }

            @Override
            public String name() {
                return "Hand";
            }

            @Override
            public String description() {
                return "Your hand";
            }
        };
    }

    @Override
    public void setEquippedWeapon( Weapon weapon ) {
        inventory.getEquippedSlots().set( 0, weapon );
    }
}
