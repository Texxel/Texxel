package com.github.texxel.tiles;

public final class TileList {

    public static final WallTile WALL               = new WallTile();
    public static final WallDecorTile WALL_DECOR    = new WallDecorTile();

    public static final FloorTile FLOOR             = new FloorTile();
    public static final FloorDecorTile FLOOR_DECOR  = new FloorDecorTile();

    public static final DoorClosedTile DOOR_CLOSED  = new DoorClosedTile();
    public static final DoorOpenTile DOOR_OPEN      = new DoorOpenTile();
    public static final DoorLockedTile DOOR_LOCKED  = new DoorLockedTile();

    public static final GrassShortTile GRASS_SHORT  = new GrassShortTile();
    public static final GrassLongTile GRASS_LONG    = new GrassLongTile();
    public static final EmbersTile EMBERS           = new EmbersTile();

    public static final StairsUpTile STAIRS_UP      = new StairsUpTile();
    public static final StairsDownTile STAIRS_DOWN  = new StairsDownTile();

    private TileList() {
        // only static fields
    }
}
