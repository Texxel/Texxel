package com.github.texxel.items;

import com.github.texxel.utils.SaveUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * An ItemStack is a set amount of the same type of item. ItemStacks are immutable so long as the
 * Items passed to it is also immutable.
 */
public final class ItemStack implements Serializable {
    private static final long serialVersionUID = 7041244592531456029L;

    private transient Item item;
    private final int quantity;

    /**
     * Constructs an ItemStack of a type of item and a quantity
     * @param item the type of item
     * @param qty the quantity of that item
     */
    public ItemStack( Item item, int qty ) {
        if ( item == null )
            throw new NullPointerException( "'item' cannot be null" );
        if ( qty < 0 )
            throw new IllegalArgumentException( "Cannot have a qty less than 0" );
        this.item = item;
        this.quantity = qty;
    }

    /**
     * The amount of items in this stack
     * @return the items quantity
     */
    public int quantity() {
        return quantity;
    }

    /**
     * Gets the type of item this stack represents
     * @return the items type
     */
    public Item item() {
        return item;
    }

    /**
     * The price of this entire stack: i.e. {@code item.price() * qty}
     * @return this stacks price
     */
    public int price() {
        return quantity * item.price();
    }

    /**
     * Tests if this stack is an empty stack
     * @return true if the stack is empty
     */
    public boolean isEmpty() {
        return quantity == 0 || item.isEmpty();
    }

    /**
     * Tests if the other item can be stacked on top of this item. This will always return true
     * if the other item is empty or this stack is empty.
     * @param item the other item to test with
     * @return true if the other item can get stacked on top
     */
    public boolean canStackWith( Item item ) {
        return this.item.equals( item ) || item.isEmpty() || isEmpty();
    }

    /**
     * Tests if this stack can be combined with the other stack. This method is commutative.
     * @param stack the other stack to combine with
     * @return true if they can combine
     */
    public boolean canStackWith( ItemStack stack ) {
        return canStackWith( stack.item() ) || stack.isEmpty();
    }

    /**
     * Stacks the other stack onto this stack. This should only be called if it was first checked
     * that the stacks could be combined together with {@link #canStackWith(ItemStack)}. This
     * method is commutative: a.stackedWith(b) = b.stackedWith(a).
     * @param other the other stack to stack with
     * @return this item and the other item stacked together
     * @throws IllegalArgumentException if the other item cannot be stacked with this item
     * @see #canStackWith(Item)
     */
    public ItemStack stackedWith( ItemStack other ) {
        if ( !this.canStackWith( other ) )
            throw new IllegalArgumentException( "Stacks could not stack" );

        if ( this.isEmpty() ) {
            // simply return the other stack
            return other;
        } else {
            // return the stacks combined together
            return new ItemStack( item, quantity + other.quantity );
        }
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof ItemStack ) {
            ItemStack other = (ItemStack)obj;
            return other.quantity == this.quantity
                    && other.item.equals( this.item() );
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + quantity;
        hash = 31 * hash + item.hashCode();
        return hash;
    }

    private void writeObject( ObjectOutputStream out ) throws IOException {
        out.defaultWriteObject();

        out.writeObject( item );
    }

    private void readObject( ObjectInputStream in ) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        item = SaveUtils.readObject( in, EmptyItem.instance() );
    }

    /**
     * Gets the string version of the item and the quantity in the stack. This is unoptimised and
     * should only be used for debugging.
     * @return the string version of this stack
     */
    @Override
    public String toString() {
        return item.toString() + "x" + quantity;
    }
}
