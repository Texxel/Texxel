package com.github.texxel.tiles;

/**
 * A PortalTile lets the current player move through levels. Portal tiles work by having some special
 * logic in the {@link com.github.texxel.ui.HeroControl} class.
 */
public interface PortalTile {

    /**
     * Gets the level that the hero should move to
     * @return the next level
     */
    int getTargetLevel();

}
