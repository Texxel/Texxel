package com.github.texxel.items;

import com.github.texxel.sprites.api.WorldVisual;
import com.github.texxel.ui.Examinable;

import java.io.Serializable;

/**
 * An item is something that characters can pick up and use. It is imperative that items are made
 * immutable since many methods assume that the items can be shared freely and copy and pasted
 * between ItemStacks. Items should generally be singletons but that is not required. To define an
 * amount of single items, the ItemStack is used. To define an amount of different items, the Heap
 * is used.
 * @see ItemStack
 * @see Heap
 */
public interface Item extends WorldVisual, Examinable, Serializable {

    /**
     * Tests if this item is empty. All items should return false here. The only exception is
     * {@link EmptyItem}
     * @return true if the item is empty
     */
    boolean isEmpty();

    /**
     * The price of this item
     * @return this items price
     */
    int price();

}
