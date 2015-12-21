package com.github.texxel.ui;

import com.github.texxel.actors.Actor;
import com.github.texxel.event.events.input.CellSelectedEvent;
import com.github.texxel.event.listeners.input.CellSelectedListener;
import com.github.texxel.levels.Level;
import com.github.texxel.tiles.Tile;

public class ExamineMaker implements CellSelectedListener {

    private static final long serialVersionUID = 5631469402460467111L;

    @Override
    public void onCellSelected( CellSelectedEvent e ) {
        if ( e.isCancelled() )
            return;
        Level level = e.getLevel();
        int x = e.getX();
        int y = e.getY();
        if ( !level.isInBounds( x, y ) )
            return;
        for ( Actor actor : level.getActors() ) {
            if ( actor instanceof Examinable && actor.isOver( x, y )) {
                examine( (Examinable) actor );
                e.setCancelled( true );
                return;
            }
        }

        Tile tile = level.getTileMap().getTile( e.getX(), e.getY() );
        examine( tile );
        e.setCancelled( true );

    }

    private void examine( Examinable examinable ) {
        System.out.print( examinable.name() );
    }

}
