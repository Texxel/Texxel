package com.github.texxel.actors.heaps;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.texxel.actors.AbstractActor;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.items.EmptyItem;
import com.github.texxel.items.api.Item;
import com.github.texxel.levels.Level;
import com.github.texxel.sprites.api.Visual;
import com.github.texxel.sprites.api.WorldVisual;
import com.github.texxel.sprites.imp.ItemVisual;
import com.github.texxel.ui.Examinable;
import com.github.texxel.utils.Filter;
import com.github.texxel.utils.Point2D;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A heap is a method to pass around a bunch of Items. The items in the heap can all have different
 * quantities and types. Heaps are mutable.
 */
public final class Heap extends AbstractActor implements WorldVisual, Examinable {

    private static final long serialVersionUID = -327026030824087259L;

    private ArrayList<Item> items = new ArrayList<>();
    private HeapType type;
    private final Point2D location;
    private transient List<Item> publicItems = Collections.unmodifiableList( items );
    private transient ItemVisual visual;
    private transient Animation animation;

    /**
     * Constructs a new heap at a specific location
     * @param location the place the heap is located
     */
    public Heap( Level level, Point2D location ) {
        super( level );
        if ( location == null )
            throw new NullPointerException( "'location' cannot be null" );
        this.location = location;
        this.type = SimpleHeap.instance();
    }

    @Override
    protected Goal defaultGoal() {
        return new HeapGoal( this );
    }

    /**
     * Gets the item on the top of this stack. If the stack is empty, then EmptyItem will be returned.
     * @return the top item. Never null
     */
    public Item topItem() {
        while( items.size() != 0 ) {
            int topIndex = items.size() - 1;
            Item item = items.get( topIndex );
            if ( item.isEmpty() ) {
                items.remove( topIndex );
            } else
                return item;
        }
        return EmptyItem.instance();
    }

    /**
     * Removes the top item from the heap and then returns it. If the heap is empty, then this
     * will return an empty item
     * @return the Item that was on top of the heap
     */
    public Item pop() {
        while( items.size() != 0 ) {
            int topIndex = items.size() - 1;
            Item item = items.get( topIndex );
            items.remove( topIndex );
            if ( !item.isEmpty() ) {
                return item;
            }
        }
        return EmptyItem.instance();
    }

    /**
     * Gets an unmodifiable list of the items in the Heap. The top item will be at the end of the
     * list. The list cannot be modified, but the item objects can be. If an item becomes empty,
     * then it will automatically be removed from the heap.
     * @return the heaps items. The list cannot be modified
     */
    public List<Item> items() {
        return publicItems;
    }

    /**
     * Returns true if there are no items in the heap
     * @return true if the heap is empty
     */
    public boolean isEmpty() {
        // call to topItem() will clean the list
        return topItem().isEmpty();
    }

    /**
     * Looks through the list looking for any item that matches the filter.
     * @param filter the thing to match against
     * @return true if a match was found
     */
    public boolean contains( Filter<Item> filter ) {
        for ( int i = items.size()-1; i >= 0; i-- ) {
            if ( filter.isAllowed( items.get( i ) ) )
                return true;
        }
        return false;
    }

    /**
     * Adds the item to the top of the heap
     * @param item the item to add
     */
    public void add( Item item ) {
        if ( item.isEmpty() )
            return;
        items.add( item );
    }

    /**
     * Removes any items that the filter matches the filter. The filter works from the top of the
     * heap to the bottom. Thus, adding items to the top of the list is supported, but removing
     * items at the bottom is not.
     * @param filter the items to remove (return true to remove, false to keep)
     * @see #keep(Filter)
     */
    public void remove( Filter<Item> filter ) {
        for ( int i = items.size()-1; i >= 0; i-- ) {
            Item item = items.get( i );
            // might as well remove empty items while we're here
            if ( item.isEmpty() ) {
                items.remove( i );
                continue;
            }
            // remove any items
            if ( filter.isAllowed( items.get( i ) ) )
                items.remove( i );
        }
    }

    /**
     * Removes the top item that is equal (using {@code .equals()}) to the passed item
     * @param item the item to remove
     * @return true if the item was found and removed
     */
    public boolean remove( Item item ) {
        if ( item == null )
            throw new NullPointerException( "item cannot be null" );
        if ( item.isEmpty() )
            return true;
        for ( int i = items.size()-1; i >= 0; i-- ) {
            Item other = items.get( i );
            // clear out empty items cause we can
            if ( other.isEmpty() )
                items.remove( i );

            if ( item.equals( other ) ) {
                items.remove( i );
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes all the items in the list except the items that the filter says to keep
     * @param filter the items to keep (return true to keep, false to remove)
     */
    public void keep( Filter<Item> filter ) {
        for ( int i = items.size()-1; i >= 0; i-- ) {
            Item item = items.get( i );
            // might as well remove empty items while we're here
            if ( item.isEmpty() ) {
                items.remove( i );
                continue;
            }
            // remove any items
            if ( !filter.isAllowed( items.get( i ) ) )
                items.remove( i );
        }
    }

    /**
     * Gets a string representation of all the items in the list. This is slow and should only be
     * used for debugging
     * @return this heap as a string
     */
    @Override
    public String toString() {
        return items.toString();
    }

    @Override
    public Visual getVisual() {
        if ( visual == null )
            visual = new ItemVisual( type.getImage( this ) );
        visual.setLocation( location.x, location.y );
        visual.setRegion( type.getImage( this ) );
        return visual;
    }

    /**
     * Gets where this heap is located
     * @return the heaps location
     */
    public Point2D getLocation() {
        return location;
    }

    /**
     * Sets what type of heap this is
     * @param type the new heap type
     * @return this
     */
    public Heap setType( HeapType type ) {
        if ( type == null )
            throw new NullPointerException( "'type' cannot be null" );
        this.type = type;
        return this;
    }

    /**
     * Gets what type of heap this is
     * @return this heap's type
     */
    public HeapType getType() {
        return type;
    }

    @Override
    public String name() {
        return type.name( this );
    }

    @Override
    public String description() {
        return type.getDescription( this );
    }

    @Override
    public Animation getLogo() {
        if ( animation == null )
            animation = new Animation( 1, type.getImage( this ) );
        return animation;
    }

    private void readObject( ObjectInputStream in ) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        publicItems = Collections.unmodifiableList( items );
        animation = null;
        visual = null;
    }

    @Override
    public boolean isOver( int x, int y ) {
        return location.equals( x, y );
    }
}
