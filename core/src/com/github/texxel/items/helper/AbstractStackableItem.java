package com.github.texxel.items.helper;

import com.github.texxel.items.api.Stackable;

public abstract class AbstractStackableItem extends AbstractItem implements Stackable {

    private static final long serialVersionUID = 7187372991825816044L;
    private int quantity;

    public AbstractStackableItem( int quantity ) {
        this.quantity = quantity;
    }

    public AbstractStackableItem() {
        this( 0 );
    }

    @Override
    public boolean isEmpty() {
        return quantity == 0;
    }

    @Override
    public int quantity() {
        return quantity;
    }

    @Override
    public Stackable setQuantity( int amount ) {
        if ( quantity < 0 )
            throw new IllegalArgumentException( "Cannot have the quantity to less than 0" );
        this.quantity = amount;
        return this;
    }

    @Override
    public boolean canStackWith( Stackable item ) {
        return item.getClass() == getClass();
    }

    @Override
    public boolean add( Stackable item ) {
        if ( canStackWith( item ) ) {
            quantity += item.quantity();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return name() + "x" + quantity;
    }
}
