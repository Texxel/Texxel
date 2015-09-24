package com.github.texxel.event.listeners.input;

import com.github.texxel.event.Listener;
import com.github.texxel.event.events.input.CellSelectedEvent;

public interface CellSelectedListener extends Listener {
    void onCellSelected( CellSelectedEvent e );
}
