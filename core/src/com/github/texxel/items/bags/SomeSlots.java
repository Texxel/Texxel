package com.github.texxel.items.bags;

import com.github.texxel.items.ItemUtils;
import com.github.texxel.items.api.Item;
import com.github.texxel.items.api.Stackable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SomeSlots implements Serializable, Iterable<Slot> {

    private static final long serialVersionUID = -4804185696695483477L;

    private final List<Slot> slots = new ArrayList<>();

    public SomeSlots( int size ) {
        for ( int i = 0; i < size; i++ )
            slots.add( new Slot() );
    }

    /**
     * Reorders all the slots in the correct order
     */
    private void reorder() {
        Collections.sort( slots, ItemUtils.getSlotComparator() );
    }

    /**
     * Gets the slot at a specific position.
     * @return the slot at the given position
     * @throws IndexOutOfBoundsException if {@code slot < 0  || slot >= size}
     */
    public Slot getSlot( int slot ) {
        return slots.get( slot );
    }

    /**
     * Convenience method for {@code getSlot( i ).getItem()}
     * @param slot the index of the slot to get the item from
     * @return the item in the slot
     */
    public Item get( int slot ) {
        return slots.get( slot ).getItem();
    }

    /**
     * Convenience method for {@code getSlot( i ).setItem( item )}
     * @param slot the index of the slot to set
     * @param item the item to set in the slot
     */
    public void set( int slot, Item item ) {
        slots.get( slot ).setItem( item );
    }

    /**
     * Tests if the item can fit into the backpack
     * @param item the item to try and store
     * @return true if it can fit
     */
    public boolean canCollect( Item item ) {
        for ( Slot slot : slots ) {
            if ( slot.getFilter().isAllowed( item ) ) {
                // can take it if the slot is empty
                if ( slot.isEmpty() ) {
                    return true;
                }
                // can take it if the items can stack
                Item inPack = slot.getItem();
                if ( inPack instanceof Stackable ) {
                    if (( (Stackable) inPack ).canStackWith( (Stackable)item ))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * Attempts to collect the item. If the backpack cannot store the item, then false will be
     * returned and nothing further will happen.
     * @param item the item to collect
     * @return true if the item was collected
     */
    public boolean collect( Item item ) {
        if ( item == null )
            throw new NullPointerException( "cannot add a null item" );

        if ( item.isEmpty() )
            return true;

        for ( Slot slot : slots ) {
            if ( slot.getFilter().isAllowed( item ) ) {
                Item slotItem = slot.getItem();
                if ( slotItem.isEmpty() ) {
                    slot.setItem( item );
                    return true;
                }
                if ( slotItem instanceof Stackable
                        && item instanceof Stackable
                        && ( (Stackable) slotItem ).add( (Stackable) item ) ) {
                    reorder();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the amount of slots in the backpack
     * @return the backpacks size
     */
    public int getSize() {
        return slots.size();
    }

    /**
     * Sets the amount of slots in the backpack. If the size is greater than the current size, then
     * the backpack is padded out with empty slots. If the size is smaller, then the empty slots will
     * be discarded. If there are not enough empty slots to match the set size, then the slots at
     * the end of the list will be removed and returned.
     * @param newSize the new size of the backpack
     * @return the non empty slots that were removed
     */
    public Slot[] setSize( int newSize ) {
        if ( newSize < 0 )
            throw new IllegalArgumentException( "cannot set backpack size to less than 0" );
        int oldSize = slots.size();
        int slotsToRemove = newSize - oldSize;
        if ( slotsToRemove <= 0 ) {
            for ( int i = 0; i < slotsToRemove; i++ ) {
                slots.add( new Slot() );
            }
            return new Slot[0];
        } else {
            // attempt to remove empty slots first
            Iterator<Slot> iterator = slots.iterator();
            while ( iterator.hasNext() ) {
                Slot next = iterator.next();
                if ( next.isEmpty() ) {
                    iterator.remove();
                    slotsToRemove--;
                    if ( slotsToRemove <= 0 )
                        return new Slot[0];
                }
            }
            // remove the slots at the end
            Slot[] removed = new Slot[slotsToRemove];
            assert slotsToRemove <= slots.size() ;
            for ( int i = 0; i < slotsToRemove; i++ ) {
                removed[i] = slots.remove( slots.size()-1 );
                assert removed[i] != null;
            }
            return removed;
        }
    }

    @Override
    public String toString() {
        return slots.toString();
    }

    /**
     * An iterator across all the slots. The iterator does not support removing.
     * @return a slot iterator
     */
    @Override
    public Iterator<Slot> iterator() {
        return new Iterator<Slot>() {

            int index = 0;

            @Override
            public boolean hasNext() {
                return index < slots.size()-1;
            }

            @Override
            public Slot next() {
                index++;
                return slots.get( index );
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException( "SomeSlot iterator does not support removal" );
            }
        };
    }

    private void writeObject( ObjectOutputStream out ) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject( ObjectInputStream in ) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
}
