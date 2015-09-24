package com.github.texxel.event.events.item;

import com.github.texxel.event.Cancelable;
import com.github.texxel.event.listeners.item.ItemDropListener;
import com.github.texxel.items.Item;
import com.github.texxel.utils.Point2D;

public class ItemDropEvent extends ItemEvent<ItemDropListener> implements Cancelable {

    private boolean cancelled = false;
    private Point2D location;

    public ItemDropEvent( Item item, Point2D location ) {
        super( item );
        this.location = location;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation( Point2D location ) {
        this.location = location;
    }

    public void setItem( Item item ) {
        this.item = item;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled( boolean cancelled ) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean dispatch( ItemDropListener listener ) {
        listener.onItemDropped( this );
        return cancelled;
    }
}

