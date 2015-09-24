package com.github.texxel.items;

import com.github.texxel.sprites.api.WorldVisual;

import java.util.List;

public interface Heap extends WorldVisual {

    /**
     * Gets the item on the top of this stack
     * @return the top item. May be null only if the heap is empty
     */
    Item topItem();

    /**
     * Gets the items in the Heap. The top item will be at index 0.
     * @return the heaps items. THe list cannot be modified
     */
    List<Item> items();

    /**
     * Returns true if there are no items in the heap
     * @return true if the heap is empty
     */
    boolean isEmpty();

    /**
     * Looks through the list looking for any items with the same class. If {@code matchSubclasses}
     * is true, then items who subclass {@code item} will also match.
     * @param item the class of the item to match
     * @param matchSubclasses if finding a subclass of {@code item} should return true
     * @return true if a match was found
     */
    boolean contains( Class<? extends Item> item, boolean matchSubclasses );

    /**
     * Determines the quantity of items with the class {@code item}. If {@code matchSubclasses} is
     * true, then subclasses of {@code item} are included in the count.
     * @param item the item's class to count
     * @param matchSubclasses weather to match subclasses or not
     * @return the quantity of items found matching the item class. 0 if no items were found
     */
    int amount( Class<? extends Item> item, boolean matchSubclasses );

    /**
     * Adds an amount of items to the heap. If the heap already contains an item that is equal, then
     * the added items will be stacked with them
     * @param item the item to add
     * @return the Item that the given item was stored in. May be the same item as given
     */
    Item add( Item item );

    /**
     * Removes an amount of items from the list.
     * @param type the class of the item
     * @param amount the amount of items to remove
     * @param matchSubclasses remove subclasses of {@code type} also
     */
    void remove( Class<? extends Item> type, int amount, boolean matchSubclasses );

    /**
     * Removes an item that is equal to this item
     * @param item the item to remove
     */
    void remove( Item item );

    int getX();

    int getY();

}
