package com.github.texxel.levels.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.tiles.Tile;
import com.github.texxel.utils.Assert;

import java.util.HashMap;

/**
 * A Theme defines how the tiles in a level look. To use a theme, it should be registered into
 * {@link ThemeRegistry}. See that classes description for more info.
 */
public class Theme {

    /**
     * The special lookup is a class that can do complex actions to decide what a tile looks like.
     */
    public interface SpecialTexture {

        /**
         * Gets what the given tile should be displayed as. If this SpecialTexture does not know
         * what to draw the passed tile as, then null will be returned.
         * @param tile the tile to get the texture for
         * @param map the TileMap the tile is in
         * @param x the x position of the tile
         * @param y the y position of the tile
         * @return the tile's image (or null to let someone else to figure it out)
         */
        TextureRegion image( Tile tile, TileMap map, int x, int y );
    }

    private final HashMap<Class<? extends Tile>, SpecialTexture> specials;
    private final HashMap<Class<? extends Tile>, TextureRegion> basics;

    /**
     * Constructs an empty theme
     */
    public Theme() {
        specials = new HashMap<>( 1 );
        basics = new HashMap<>( 20 );
    }

    /**
     * Creates a copy of another theme. Individual Textures and SpecialTextures are not deep cloned.
     * @param theme the theme to copy
     */
    public Theme( Theme theme ) {
        specials = new HashMap<>( theme.specials );
        basics = new HashMap<>( theme.basics );
    }

    /**
     * Gets the texture to use to draw a tile. First, this method will ask if any of SpecialTextures
     * know what the texture should look like. If none of them know what to draw, then the basic
     * textures will be asked. If none of them know, then the tile's default texture will be used.
     * @param tile the tile to get the image for
     * @param map the TileMap the tile is in
     * @param x the x position of the tile
     * @param y the y position of the tile
     * @return the tile's image. Never null
     */
    public TextureRegion image( Tile tile, TileMap map, int x, int y ) {

        // try asking the specials for the texture
        Class clazz = tile.getClass();
        while ( Tile.class.isAssignableFrom( clazz ) ) {
            SpecialTexture special = specials.get( clazz );
            if ( special != null ) {
                TextureRegion region = special.image( tile, map, x, y );
                if ( region != null )
                    return region;
            }
            clazz = clazz.getSuperclass();
        }

        // try asking the texture map for the tile
        clazz = tile.getClass();
        while ( Tile.class.isAssignableFrom( clazz ) ) {
            TextureRegion region = basics.get( tile.getClass() );
            if ( region != null )
                return region;
            clazz = clazz.getSuperclass();
        }

        // return the default texture
        return tile.getDefaultImage();
    }

    /**
     * Adds a SpecialTexture to the theme. The registered class will effect that class and all it's
     * subclasses. However, any subclasses will have priority over superclasses. This does nothing
     * for interfaces.
     * @param tileClass the class to effect
     * @param special the SpecialTexture to add. Null to delete the existing one
     */
    public void put( Class<? extends Tile> tileClass, SpecialTexture special ) {
        if ( special == null ) {
            specials.remove( tileClass );
        } else {
            specials.put( tileClass, special );
        }
    }

    /**
     * Sets the SpecialTexture to use with a class. The registered class will effect that class and
     * all it's subclasses. However, any subclasses will have priority over superclasses. This does
     * nothing for interfaces.
     * @param tile the class to effect
     * @param region the region to use for the class or null to delete the previous value
     */
    public void put( Class<? extends Tile> tile, TextureRegion region ) {
        if ( region == null )
            basics.remove( tile );
        else
            basics.put( Assert.nonnull( tile, "tile class cannot be null" ), region );
    }

}
