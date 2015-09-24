package com.github.texxel.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.saving.Bundlable;
import com.github.texxel.sprites.api.WorldVisual;
import com.github.texxel.ui.Examinable;

/**
 * A Tile is what composes the majority of a Level. Because of the large amount of tiles that may
 * be present all at once, Tiles should have very fast implementations. All the vanilla Tiles in
 * Texxel are immutable and singletons, however, it is not required that either of these be true.
 */
public interface Tile extends Examinable, Bundlable {

    /**
     * Tests if the tile if the tile is solid, ie will it stop something from moving pass it. The
     * result of this method effects the result of ray casts, thus, effecting things like where
     * wands beam end or where projectiles fall. It does <b>not</b> effect characters' path finding
     * algorithms.
     */
    boolean isSolid();

    /**
     * Tests if the tile cannot be seen through. If a tile is opaque, character's fields of visions
     * will be obstructed by the tile.
     * @return true if the tile cannot be seen through
     */
    boolean isOpaque();

    /**
     * Returns true if a character should path around the object.
     * @return true if a character can walk through the tile.
     */
    boolean isPassable();

    /**
     * Gets the texture that is used to draw this tile. Unlike other world objects which are drawn
     * with through the {@link WorldVisual} interface, the large amount of
     * tiles meant that Tiles had to be optimised to be drawn as simple texture regions. All tiles
     * will be drawn at depth 0.
     * @return the region of this tile
     */
    TextureRegion getImage();

}
