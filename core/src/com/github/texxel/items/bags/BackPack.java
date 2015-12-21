package com.github.texxel.items.bags;

import com.github.texxel.items.EmptyItem;
import com.github.texxel.items.Item;
import com.github.texxel.items.ItemStack;
import com.github.texxel.utils.Filter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class BackPack implements Serializable {

    private static final long serialVersionUID = -4804185696695483477L;

    private final List<Slot> slots = new ArrayList<>();
    private transient List<Bag> bags = new ArrayList<>();
    private transient List<Slot> publicSlots = Collections.unmodifiableList( slots );
    private transient List<Bag> publicBags  = Collections.unmodifiableList( bags );

    public BackPack( int size ) {
        for ( int i = 0; i < size; i++ )
            slots.add( new Slot() );
    }

    /**
     * Gets an unmodifiable list of the contents in the backpack. This does not include the slots in
     * all the sub bags. While the list is unmodifiable, the slots inside the list are mutable. You
     * can add items directly to the slots here but is generally recommended that you use
     * {@link #collect(ItemStack)} so it gets filed properly
     * @return all the items in this backpack
     */
    public List<Slot> getContents() {
        return publicSlots;
    }

    /**
     * Gets the bags that are contained inside this backpack. The list can not modified. To add or
     * remove a bag, add the backpack as an item
     * @return the sub bags
     */
    public List<Bag> getBags() {
        bags.clear();
        for ( Slot slot : slots ) {
            if ( slot.getItemStack().item() instanceof Bag )
                bags.add( (Bag)slot.getItemStack().item() );
        }
        return publicBags;
    }

    /**
     * Tests if the item can fit into the backpack
     * @param item the item to try and store
     * @return true if it can fit
     */
    public boolean canCollect( Item item ) {
        for ( Bag bag : getBags() )
            if ( bag.canCollect( item ) )
                return true;
        for ( Slot slot : slots )
            if ( slot.getFilter().isAllowed( item )
                    && slot.getItemStack().canStackWith( item ) )
                return true;
        return false;
    }

    /**
     * Attempts to collect the item. If the backpack cannot store the item, then false will be
     * returned and nothing further will happen
     * @param stack the item to collect
     * @return true if the item was collected
     */
    public boolean collect( ItemStack stack ) {
        if ( stack == null )
            throw new NullPointerException( "cannot add a null item" );

        if ( stack.isEmpty() )
            return true;

        for ( Bag bag : getBags() ) {
            if ( bag.collect( stack ) )
                return true;
        }
        for ( Slot slot : slots ) {
            if ( slot.getFilter().isAllowed( stack.item() ) ) {
                ItemStack slotItem = slot.getItemStack();
                if ( slotItem.canStackWith( stack ) ) {
                    slot.setItemStack( slotItem.stackedWith( stack ) );
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Removes an amount of items from the backpack. This will try to remove the item from this
     * backpack before the sub bags
     * @param filter anything that the filter matches will be removed (unless the quantity is exceeded)
     * @param quantity the amount of items to remove or -1 for as many as possible
     * @return all the items that were removed. An empty array if none were removed
     * @throws IllegalArgumentException if quantity is < -1
     */
    public List<ItemStack> remove( Filter<Item> filter, int quantity ) {
        if ( quantity < -1 )
            throw new IllegalArgumentException( "Cannot remove " + quantity + " items" );

        ArrayList<ItemStack> removed = new ArrayList<>();
        int itemsLeftToRemove = quantity == -1 ? Integer.MAX_VALUE : quantity;
        for ( int i = slots.size() - 1; i >= 0; i-- ) {
            Slot slot = slots.get( i );
            ItemStack stack = slot.getItemStack();
            Item item = stack.item();
            if ( filter.isAllowed( item ) ) {
                if ( itemsLeftToRemove >= stack.quantity() ) {
                    removed.add( stack );
                    itemsLeftToRemove -= stack.quantity();
                    slot.setItemStack( EmptyItem.stackInstance() );
                } else {
                    removed.add( new ItemStack( item, itemsLeftToRemove ) );
                    slot.setItemStack( new ItemStack( item, stack.quantity() - itemsLeftToRemove ) );
                }
                if ( itemsLeftToRemove <= 0 )
                    break;
            }
        }
        return removed;
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

    private void writeObject( ObjectOutputStream out ) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject( ObjectInputStream in ) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        // these things will automagically get updated
        bags = new ArrayList<>();
        publicSlots = Collections.unmodifiableList( slots );
        publicBags = Collections.unmodifiableList( bags );
    }
}
