package com.github.texxel.event.listeners.item;

import com.github.texxel.event.Listener;
import com.github.texxel.event.events.item.ItemDropEvent;

public interface ItemDropListener extends Listener {
    void onItemDropped( ItemDropEvent e );
}
