package com.github.texxel.items.bags;

import com.github.texxel.items.*;
import com.github.texxel.items.EmptyItem;
import com.github.texxel.utils.Filter;
import com.github.texxel.utils.FilterUtils;

import java.io.Serializable;

public final class Slot implements Serializable {

    private static final long serialVersionUID = -625567128821904276L;

    private ItemStack item = EmptyItem.stackInstance();
    private Filter<Item> filter = FilterUtils.anyObject();

    public ItemStack getItemStack() {
        return item;
    }

    public Slot setItemStack( ItemStack item ) {
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

    public Filter<Item> getFilter() {
        return filter;
    }

    public boolean isEmpty() {
        return item.isEmpty();
    }
}
