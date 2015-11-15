package com.github.texxel.event.events.input;

import com.github.texxel.event.Cancelable;
import com.github.texxel.event.Event;
import com.github.texxel.event.listeners.input.CellSelectedListener;
import com.github.texxel.levels.Level;

public class CellSelectedEvent implements Event<CellSelectedListener>, Cancelable {

    private final int x, y;
    private final Level level;
    private boolean cancelled = false;

    public CellSelectedEvent( Level level, int x, int y ) {
        if ( level == null )
            throw new NullPointerException( "'level' cannot be null" );
        this.level = level;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Level getLevel() {
        return level;
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
