package com.github.texxel.items.bags;

import com.github.texxel.items.EmptyItem;
import com.github.texxel.items.api.Item;
import com.github.texxel.utils.Filter;
import com.github.texxel.utils.FilterUtils;

import java.io.Serializable;

public final class Slot implements Serializable {

    private static final long serialVersionUID = -625567128821904276L;

    private Item item = EmptyItem.instance();
    private Filter<Item> filter = FilterUtils.anyObject();

    public Item getItem() {
        return item;
    }

    public Slot setItem( Item item ) {
        if ( item == null )
            throw new NullPointerException( "item cannot be null");
        this.item = item;
        return this;
    }

    public Slot setFilter( Filter<Item> filter ) {
        if ( filter == null )
            throw new NullPointerException( "'filter' cannot be null" );
        this.filter = filter;
        return this;
    }

    @Override
    public String toString() {
        return item.toString();
    }

    public Filter<Item> getFilter() {
        return filter;
    }

    public boolean isEmpty() {
        return item.isEmpty();
    }
}
