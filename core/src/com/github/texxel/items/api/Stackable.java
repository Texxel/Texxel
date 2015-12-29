package com.github.texxel.items.api;

/**
 * An item that can be stacked with items of the same type.
 */
public interface Stackable extends Item {

    /**
     * Gets the quantity of this item
     * @return the amount of items in this stack
     */
    int quantity();

    /**
     * Sets the quantity of the other item
     * @param amount the new amount in this item
     * @return this
     */
    Stackable setQuantity( int amount );

    /**
     * Tests if this item can stack with an item of the other type
     * @param item the item to test if it can be stacked with
     * @return true if the other item can be stacked with this item
     */
    boolean canStackWith( Stackable item );

    /**
     * Adds the other item to this item. If the item can be stacked with this item, then this items
     * quantity will be set to the sum of this item and the other item and true will be returned. If
     * the items cannot be stacked, then false will be returned and no changes will happen
     * @param item the item to add. It will not get altered.
     * @return true if the item was added. False otherwise
     */
    boolean add( Stackable item );

}
