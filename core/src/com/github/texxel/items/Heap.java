package com.github.texxel.items;

import com.github.texxel.sprites.ItemSpriteSheet;
import com.github.texxel.sprites.api.Visual;
import com.github.texxel.sprites.api.WorldVisual;
import com.github.texxel.sprites.imp.ItemVisual;
import com.github.texxel.utils.Filter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A heap is a method to pass around a bunch of Items. The items in the heap can all have different
 * quantities and types. Heaps are mutable.
 */
public final class Heap implements WorldVisual, Serializable {

    private static final long serialVersionUID = -327026030824087259L;

    private ArrayList<ItemStack> stacks = new ArrayList<>();
    private transient List<ItemStack> publicStacks = Collections.unmodifiableList( stacks );
    private transient ItemVisual visual = new ItemVisual( ItemSpriteSheet.CHEST );

    /**
     * Gets the item on the top of this stack. If the stack is empty, then EmptyItem will be returned.
     * @return the top item. Never null
     */
    public ItemStack topItem() {
        while( stacks.size() != 0 ) {
            int topIndex = stacks.size() - 1;
            ItemStack item = stacks.get( topIndex );
            if ( item.isEmpty() ) {
                stacks.remove( topIndex );
            } else
                return item;
        }
        return EmptyItem.stackInstance();
    }

    /**
     * Gets an unmodifiable list of the items in the Heap. The top item will be at the end of the list
     * @return the heaps items. The list cannot be modified
     */
    public List<ItemStack> items() {
        return publicStacks;
    }

    /**
     * Returns true if there are no items in the heap
     * @return true if the heap is empty
     */
    public boolean isEmpty() {
        // call to topItem will clean the list
        return topItem().isEmpty();
    }

    /**
     * Looks through the list looking for any item that matches the filter.
     * @param filter the thing to match against
     * @return true if a match was found
     */
    public boolean contains( Filter<Item> filter ) {
        for ( int i = stacks.size()-1; i >= 0; i-- ) {
            if ( filter.isAllowed( stacks.get( i ).item() ) )
                return true;
        }
        return false;
    }

    /**
     * Adds an amount of items to the heap. If the heap already contains an item that is equal, then
     * the added items will be stacked with them
     * @param item the item to add
     * @return the ItemStack that the given item was stored in. May be the same item as given
     */
    public ItemStack add( ItemStack item ) {
        for ( int i = stacks.size()-1; i >= 0; i-- ) {
            ItemStack other = stacks.get( i );
            if ( other.canStackWith( item ) ) {
                ItemStack combined = other.stackedWith( item );
                stacks.set( i, combined );
                return combined;
            }
        }
        stacks.add( item );
        return item;
    }

    /**
     * Removes the top item in the stack that matches the given item
     * @param item the item to remove
     * @return true if the item was found and removed
     */
    public boolean remove( ItemStack item ) {
        for ( int i = stacks.size()-1; i >= 0; i-- ) {
            ItemStack other = stacks.get( i );
            if ( other.equals( item ) ) {
                stacks.remove( i );
                return true;
            }
        }
        return false;
    }

    /**
     * Removes any items that the filter matches
     * @param filter the items to remove
     */
    public void remove( Filter<Item> filter ) {
        for ( int i = stacks.size()-1; i >= 0; i-- ) {
            if ( filter.isAllowed( stacks.get( i ).item() ) )
                stacks.remove( i );
        }
    }

    /**
     * Removes an item that is equal (using {@link Item#equals(Object)} to this item
     * @param item the item to remove
     * @return true if the item was removed
     */
    public boolean remove( Item item ) {
        boolean found = false;
        for ( int i = stacks.size()-1; i >= 0; i-- ) {
            if ( item.equals( stacks.get( i ).item() ) ) {
                stacks.remove( i );
                found = true;
            }
        }
        return found;
    }

    /**
     * Finds all items of type search and removes them. In their place, it leaves the other item
     * @param search the other item
     * @param replacement the item to replace
     * @param onTop if true, the replaced item will be put on top of the stack. Otherwise, it will
     *              be left in the same place as the removed item
     * @return true if the replacement was made (i.e. the search item was found)
     */
    public boolean replace( Item search, Item replacement, boolean onTop ) {
        boolean found = false;
        for ( int i = stacks.size()-1; i >= 0; i-- ) {
            ItemStack item = stacks.get( i );
            if ( search.equals( item.item() ) ) {
                if ( onTop ) {
                    stacks.remove( i );
                    add( new ItemStack( replacement, item.quantity() ) );
                } else {
                    stacks.set( i, new ItemStack( replacement, item.quantity() ) );
                }
                found = true;
            }
        }
        return found;
    }

    /**
     * Gets a string representation of all the item stacks in the list. This is slow and should
     * only be used for debugging
     * @return this heap as a string
     */
    @Override
    public String toString() {
        return stacks.toString();
    }

    /**
     * Gets the visual of this heap. Note, heaps don't know where they are, so you must manually
     * set the location of the visual
     * @return the heaps visual
     */
    @Override
    public Visual getVisual() {
        visual.setRegion( topItem().item().getVisual().getRegion() );
        return visual;
    }

    private void readObject( ObjectInputStream in ) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        publicStacks = Collections.unmodifiableList( stacks );
        visual = new ItemVisual( ItemSpriteSheet.CHEST );
    }
}
