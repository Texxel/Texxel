package com.github.texxel.ui;

import com.github.texxel.actors.Actor;
import com.github.texxel.event.events.input.CellSelectedEvent;
import com.github.texxel.event.listeners.input.CellSelectedListener;
import com.github.texxel.levels.Level;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.tiles.Tile;

public class ExamineMaker implements CellSelectedListener {

    static {
        ConstructorRegistry.put( ExamineMaker.class, new Constructor<ExamineMaker>() {
            @Override
            public ExamineMaker newInstance( Bundle bundle ) {
                return new ExamineMaker();
            }
        } );
    }

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
            if ( actor instanceof Examinable ) {
                Examinable examinable = (Examinable)actor;
                if ( examinable.isOver( x, y ) ) {
                    examine( (Examinable) actor );
                    e.setCancelled( true );
                }
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

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        return topLevel.newBundle();
    }

    @Override
    public void restore( Bundle bundle ) {

    }
}
