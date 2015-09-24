package com.github.texxel.event.events.input;

import com.github.texxel.event.Cancelable;
import com.github.texxel.event.Event;
import com.github.texxel.event.listeners.input.CellSelectedListener;

public class CellSelectedEvent implements Event<CellSelectedListener>, Cancelable {

    private final int x, y;
    private boolean cancelled = false;

    public CellSelectedEvent( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled( boolean consumed ) {
        this.cancelled = consumed;
    }

    @Override
    public boolean dispatch( CellSelectedListener listener ) {
        listener.onCellSelected( this );
        return cancelled;
    }
}
