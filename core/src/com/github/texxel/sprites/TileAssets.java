package com.github.texxel.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TileAssets {

    public static final int SIZE = 16;
    public static final Texture texture = new Texture( Gdx.files.internal( "tiles0.png" ), true );

    public static final TextureRegion CHASM           = makeTexture( 0,  0  );
    public static final TextureRegion FLOOR           = makeTexture( 1,  0  );
    public static final TextureRegion GRASS_SHORT     = makeTexture( 2,  0  );
    public static final TextureRegion WELL_EMPTY      = makeTexture( 3,  0  );
    public static final TextureRegion WALL            = makeTexture( 4,  0  );
    public static final TextureRegion DOOR_CLOSED     = makeTexture( 5,  0  );
    public static final TextureRegion DOOR_OPEN       = makeTexture( 6,  0  );
    public static final TextureRegion STAIRS_UP       = makeTexture( 7,  0  );
    public static final TextureRegion STAIRS_DOWN     = makeTexture( 8,  0  );
    public static final TextureRegion EMBERS          = makeTexture( 9,  0  );
    public static final TextureRegion DOOR_LOCKED     = makeTexture( 10, 0  );
    public static final TextureRegion PEDESTAL        = makeTexture( 11, 0  );
    public static final TextureRegion WALL_DECORATED  = makeTexture( 12, 0  );
    public static final TextureRegion BARRICADE       = makeTexture( 13, 0  );
    public static final TextureRegion FLOOR_SPECIAL   = makeTexture( 14, 0  );
    public static final TextureRegion GRASS_LONG      = makeTexture( 15, 0  );

    public static final TextureRegion TRAP_GREEN      = makeTexture( 1,  1  );
    public static final TextureRegion TRAP_ORANGE     = makeTexture( 3,  1  );
    public static final TextureRegion TRAP_YELLOW     = makeTexture( 5,  1  );
    public static final TextureRegion TRAP_TRIGGERED  = makeTexture( 7,  1  );
    public static final TextureRegion FLOOR_DECORATED = makeTexture( 8,  1  );
    public static final TextureRegion DOOR_BOSS_LOCKED= makeTexture( 9,  1  );
    public static final TextureRegion DOOR_BOSS_OPEN  = makeTexture( 10, 1  );
    public static final TextureRegion TRAP_PURPLE     = makeTexture( 11, 1  );
    public static final TextureRegion SIGN            = makeTexture( 13, 1  );
    public static final TextureRegion TRAP_RED        = makeTexture( 14, 1  );

    public static final TextureRegion TRAP_BLUE       = makeTexture( 0,  2  );
    public static final TextureRegion WELL_FULL       = makeTexture( 2,  2  );
    public static final TextureRegion STATUE          = makeTexture( 3,  2  );
    public static final TextureRegion STATUE_SPECIAL  = makeTexture( 4,  2  );
    public static final TextureRegion TRAP_GRAY       = makeTexture( 5,  2  );
    public static final TextureRegion TRAP_LIGHT_BLUE = makeTexture( 7,  2  );
    public static final TextureRegion BOOK_SHELF      = makeTexture( 9,  2  );
    public static final TextureRegion ALCHEMY_POT     = makeTexture( 10, 2  );
    public static final TextureRegion CHASM_FLOOR     = makeTexture( 11, 2  );
    public static final TextureRegion CHASM_FLOOR_S   = makeTexture( 12, 2  );
    public static final TextureRegion CHASM_WALL      = makeTexture( 13, 2  );
    public static final TextureRegion CHASM_WATER     = makeTexture( 14, 2  );

    public static final TextureRegion WATER           = makeTexture( 0,  3  );
    public static final TextureRegion WATER_N         = makeTexture( 1,  3  );
    public static final TextureRegion WATER_E         = makeTexture( 2,  3  );
    public static final TextureRegion WATER_NE        = makeTexture( 3,  3  );
    public static final TextureRegion WATER_S         = makeTexture( 4,  3  );
    public static final TextureRegion WATER_NS        = makeTexture( 5,  3  );
    public static final TextureRegion WATER_ES        = makeTexture( 6,  3  );
    public static final TextureRegion WATER_NES       = makeTexture( 7,  3  );
    public static final TextureRegion WATER_W         = makeTexture( 8,  3  );
    public static final TextureRegion WATER_NW        = makeTexture( 9,  3  );
    public static final TextureRegion WATER_EW        = makeTexture( 10, 3  );
    public static final TextureRegion WATER_NEW       = makeTexture( 11, 3  );
    public static final TextureRegion WATER_SW        = makeTexture( 12, 3  );
    public static final TextureRegion WATER_NSW       = makeTexture( 13, 3  );
    public static final TextureRegion WATER_ESW       = makeTexture( 14, 3  );
    public static final TextureRegion WATER_NESW      = makeTexture( 15, 3  );

    private static TextureRegion makeTexture( int x, int y ) {
        return new   TextureRegion( texture, SIZE * x, SIZE * y, SIZE, SIZE );
    }
    
}

