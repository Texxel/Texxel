package com.github.texxel.items.api;

/**
 * An item that can ge sold at a shop
 */
public interface Sellable extends Item {

    /**
     * Gets the price of this item. If this item is a {@link Stackable} item, then the price will
     * already be multiplied by the quantity
     * @return the price of this item multiplied by the quantity
     */
    int price();

}
