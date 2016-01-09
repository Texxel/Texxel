package com.github.texxel.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.levels.components.Theme;
import com.github.texxel.tiles.DoorClosedTile;
import com.github.texxel.tiles.DoorLockedTile;
import com.github.texxel.tiles.DoorOpenTile;
import com.github.texxel.tiles.EmbersTile;
import com.github.texxel.tiles.FloorDecorTile;
import com.github.texxel.tiles.FloorTile;
import com.github.texxel.tiles.GrassLongTile;
import com.github.texxel.tiles.GrassShortTile;
import com.github.texxel.tiles.StairsDownTile;
import com.github.texxel.tiles.StairsUpTile;
import com.github.texxel.tiles.WallDecorTile;
import com.github.texxel.tiles.WallTile;

public class TileAssets {

    public static final int SIZE = 16;

    public static final Texture SEWER_TEXTURE  = new Texture( Gdx.files.internal( "game/tiles0.png" ) );
    public static final Texture PRISON_TEXTURE = new Texture( Gdx.files.internal( "game/tiles1.png" ) );
    public static final Texture CAVES_TEXTURE  = new Texture( Gdx.files.internal( "game/tiles2.png" ) );
    public static final Texture CITY_TEXTURE   = new Texture( Gdx.files.internal( "game/tiles3.png" ) );
    public static final Texture HALLS_TEXTURE  = new Texture( Gdx.files.internal( "game/tiles4.png" ) );

    public static final TextureRegion CHASM           = defaultTexture( 0, 0 );
    public static final TextureRegion FLOOR           = defaultTexture( 1, 0 );
    public static final TextureRegion GRASS_SHORT     = defaultTexture( 2, 0 );
    public static final TextureRegion WELL_EMPTY      = defaultTexture( 3, 0 );
    public static final TextureRegion WALL            = defaultTexture( 4, 0 );
    public static final TextureRegion DOOR_CLOSED     = defaultTexture( 5, 0 );
    public static final TextureRegion DOOR_OPEN       = defaultTexture( 6, 0 );
    public static final TextureRegion STAIRS_UP       = defaultTexture( 7, 0 );
    public static final TextureRegion STAIRS_DOWN     = defaultTexture( 8, 0 );
    public static final TextureRegion EMBERS          = defaultTexture( 9, 0 );
    public static final TextureRegion DOOR_LOCKED     = defaultTexture( 10, 0 );
    public static final TextureRegion PEDESTAL        = defaultTexture( 11, 0 );
    public static final TextureRegion WALL_DECORATED  = defaultTexture( 12, 0 );
    public static final TextureRegion BARRICADE       = defaultTexture( 13, 0 );
    public static final TextureRegion FLOOR_SPECIAL   = defaultTexture( 14, 0 );
    public static final TextureRegion GRASS_LONG      = defaultTexture( 15, 0 );

    public static final TextureRegion TRAP_GREEN      = defaultTexture( 1, 1 );
    public static final TextureRegion TRAP_ORANGE     = defaultTexture( 3, 1 );
    public static final TextureRegion TRAP_YELLOW     = defaultTexture( 5, 1 );
    public static final TextureRegion TRAP_TRIGGERED  = defaultTexture( 7, 1 );
    public static final TextureRegion FLOOR_DECORATED = defaultTexture( 8, 1 );
    public static final TextureRegion DOOR_BOSS_LOCKED= defaultTexture( 9, 1 );
    public static final TextureRegion DOOR_BOSS_OPEN  = defaultTexture( 10, 1 );
    public static final TextureRegion TRAP_PURPLE     = defaultTexture( 11, 1 );
    public static final TextureRegion SIGN            = defaultTexture( 13, 1 );
    public static final TextureRegion TRAP_RED        = defaultTexture( 14, 1 );

    public static final TextureRegion TRAP_BLUE       = defaultTexture( 0, 2 );
    public static final TextureRegion WELL_FULL       = defaultTexture( 2, 2 );
    public static final TextureRegion STATUE          = defaultTexture( 3, 2 );
    public static final TextureRegion STATUE_SPECIAL  = defaultTexture( 4, 2 );
    public static final TextureRegion TRAP_GRAY       = defaultTexture( 5, 2 );
    public static final TextureRegion TRAP_LIGHT_BLUE = defaultTexture( 7, 2 );
    public static final TextureRegion BOOK_SHELF      = defaultTexture( 9, 2 );
    public static final TextureRegion ALCHEMY_POT     = defaultTexture( 10, 2 );
    public static final TextureRegion CHASM_FLOOR     = defaultTexture( 11, 2 );
    public static final TextureRegion CHASM_FLOOR_S   = defaultTexture( 12, 2 );
    public static final TextureRegion CHASM_WALL      = defaultTexture( 13, 2 );
    public static final TextureRegion CHASM_WATER     = defaultTexture( 14, 2 );

    public static final TextureRegion WATER           = defaultTexture( 0, 3 );
    public static final TextureRegion WATER_N         = defaultTexture( 1, 3 );
    public static final TextureRegion WATER_E         = defaultTexture( 2, 3 );
    public static final TextureRegion WATER_NE        = defaultTexture( 3, 3 );
    public static final TextureRegion WATER_S         = defaultTexture( 4, 3 );
    public static final TextureRegion WATER_NS        = defaultTexture( 5, 3 );
    public static final TextureRegion WATER_ES        = defaultTexture( 6, 3 );
    public static final TextureRegion WATER_NES       = defaultTexture( 7, 3 );
    public static final TextureRegion WATER_W         = defaultTexture( 8, 3 );
    public static final TextureRegion WATER_NW        = defaultTexture( 9, 3 );
    public static final TextureRegion WATER_EW        = defaultTexture( 10, 3 );
    public static final TextureRegion WATER_NEW       = defaultTexture( 11, 3 );
    public static final TextureRegion WATER_SW        = defaultTexture( 12, 3 );
    public static final TextureRegion WATER_NSW       = defaultTexture( 13, 3 );
    public static final TextureRegion WATER_ESW       = defaultTexture( 14, 3 );
    public static final TextureRegion WATER_NESW      = defaultTexture( 15, 3 );

    private static TextureRegion defaultTexture( int x, int y ) {
        return makeTexture( SEWER_TEXTURE, x, y );
    }

    /**
     * Makes the texture for an tile from the position in an atlas
     */
    private static TextureRegion makeTexture( Texture texture, int x, int y ) {
        return new TextureRegion( texture, SIZE * x, SIZE * y, SIZE, SIZE );
    }

    /**
     * Generates a completly new theme from the given texture. The theme will only include the
     * standard Texxel tiles
     * @param texture the texture to generate from
     * @return the generated theme
     */
    public static Theme generate( Texture texture ) {
        return fill( new Theme(), texture );
    }

    /**
     * Registers every Vanila Texxel tile to the given theme. The texture must be laid out in the same
     * pattern that the default texxel tile sprite sheets are. This method should be used sparingly 
     * since every call will generate many new TextureRegions.
     * @param theme the theme to register the regions to
     * @param texture the texture to generate the theme from
     * @return theme (the same instane)
     */
    public static Theme fill( Theme theme, Texture texture ) {
        
//        theme.put( ChasmTile.class,             makeTexture( texture,  0, 0  ) );
        theme.put( FloorTile.class,             makeTexture( texture,  1, 0  ) );
        theme.put( GrassShortTile.class,        makeTexture( texture,  2, 0  ) );
//        theme.put( EmptWellTile.class,          makeTexture( texture,  3, 0  ) );
        theme.put( WallTile.class,              makeTexture( texture,  4, 0  ) );
        theme.put( DoorClosedTile.class,        makeTexture( texture,  5, 0  ) );
        theme.put( DoorOpenTile.class,          makeTexture( texture,  6, 0  ) );
        theme.put( StairsUpTile.class,          makeTexture( texture,  7, 0  ) );
        theme.put( StairsDownTile.class,        makeTexture( texture,  8, 0  ) );
        theme.put( EmbersTile.class,            makeTexture( texture,  9, 0  ) );
        theme.put( DoorLockedTile.class,        makeTexture( texture,  10, 0  ) );
//        theme.put( PedestalTile.class,          makeTexture( texture,  11, 0  ) );
        theme.put( WallDecorTile.class,         makeTexture( texture,  12, 0  ) );
//        theme.put( BarricadeTile.class,         makeTexture( texture,  13, 0  ) );
        theme.put( FloorDecorTile.class,        makeTexture( texture,  14, 0  ) );
        theme.put( GrassLongTile.class,         makeTexture( texture,  15, 0  ) );

//        theme.put( TRAP_GREEN.class,       makeTexture( texture,  1, 1  ) );
//        theme.put( TRAP_ORANGE.class,      makeTexture( texture,  3, 1  ) );
//        theme.put( TRAP_YELLOW.class,      makeTexture( texture,  5, 1  ) );
//        theme.put( TRAP_TRIGGERED.class,   makeTexture( texture,  7, 1  ) );
//        theme.put( FLOOR_DECORATED.class,  makeTexture( texture,  8, 1  ) );
//        theme.put( DOOR_BOSS_LOCKED.class, makeTexture( texture,  9, 1  ) );
//        theme.put( DOOR_BOSS_OPEN.class,   makeTexture( texture,  10, 1  ) );
//        theme.put( TRAP_PURPLE.class,      makeTexture( texture,  11, 1  ) );
//        theme.put( SIGN.class,             makeTexture( texture,  13, 1  ) );
//        theme.put( TRAP_RED.class,         makeTexture( texture,  14, 1  ) );

//        theme.put( TRAP_BLUE.class,        makeTexture( texture,  0, 2  ) );
//        theme.put( WELL_FULL.class,        makeTexture( texture,  2, 2  ) );
//        theme.put( STATUE.class,           makeTexture( texture,  3, 2  ) );
//        theme.put( STATUE_SPECIAL.class,   makeTexture( texture,  4, 2  ) );
//        theme.put( TRAP_GRAY.class,        makeTexture( texture,  5, 2  ) );
//        theme.put( TRAP_LIGHT_BLUE.class,  makeTexture( texture,  7, 2  ) );
//        theme.put( BOOK_SHELF.class,       makeTexture( texture,  9, 2  ) );
//        theme.put( ALCHEMY_POT.class,      makeTexture( texture,  10, 2  ) );
//        theme.put( CHASM_FLOOR.class,      makeTexture( texture,  11, 2  ) );
//        theme.put( CHASM_FLOOR_S.class,    makeTexture( texture,  12, 2  ) );
//        theme.put( CHASM_WALL.class,       makeTexture( texture,  13, 2  ) );
//        theme.put( CHASM_WATER.class,      makeTexture( texture,  14, 2  ) );

//        theme.put( WATER.class,            makeTexture( texture,  0, 3  ) );
//        theme.put( WATER_N.class,          makeTexture( texture,  1, 3  ) );
//        theme.put( WATER_E.class,          makeTexture( texture,  2, 3  ) );
//        theme.put( WATER_NE.class,         makeTexture( texture,  3, 3  ) );
//        theme.put( WATER_S.class,          makeTexture( texture,  4, 3  ) );
//        theme.put( WATER_NS.class,         makeTexture( texture,  5, 3  ) );
//        theme.put( WATER_ES.class,         makeTexture( texture,  6, 3  ) );
//        theme.put( WATER_NES.class,        makeTexture( texture,  7, 3  ) );
//        theme.put( WATER_W.class,          makeTexture( texture,  8, 3  ) );
//        theme.put( WATER_NW.class,         makeTexture( texture,  9, 3  ) );
//        theme.put( WATER_EW.class,         makeTexture( texture,  10, 3  ) );
//        theme.put( WATER_NEW.class,        makeTexture( texture,  11, 3  ) );
//        theme.put( WATER_SW.class,         makeTexture( texture,  12, 3  ) );
//        theme.put( WATER_NSW.class,        makeTexture( texture,  13, 3  ) );
//        theme.put( WATER_ESW.class,        makeTexture( texture,  14, 3  ) );
//        theme.put( WATER_NESW.class,       makeTexture( texture,  15, 3  ) );

        return theme;
    }
    
}

