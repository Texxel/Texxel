package com.github.texxel.event.events.item;

import com.github.texxel.event.Event;
import com.github.texxel.event.Listener;
import com.github.texxel.items.api.Item;

public abstract class ItemEvent<T extends Listener> implements Event<T> {

    protected Item item;

    public ItemEvent( Item item ) {
        if ( item == null )
            throw new NullPointerException( "item cannot be null" );
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

}
