package com.github.texxel.items.api;

/**
 * An item that can be upgraded.
 */
public interface Upgradable {

    /**
     * Upgrades this item by one more level
     */
    void upgrade();

    /**
     * Degrades the item by one level
     */
    void degrade();

    /**
     * Sets the level of this item
     * @param level the new items level
     */
    void setLevel( int level );

    /**
     * Gets the level that this item is at
     * @return this items level
     */
    int getLevel();

}
