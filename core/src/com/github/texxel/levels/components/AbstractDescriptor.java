package com.github.texxel.levels.components;

import com.github.texxel.Dungeon;
import com.github.texxel.levels.Level;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;

import java.util.Collection;
import java.util.Collections;

public abstract class AbstractDescriptor implements LevelDescriptor {

    private final Dungeon dungeon;
    private final int id;
    private int width = 32, height = 32;
    private LevelBuilder builder = new BasicBuilder();
    private LevelDecorator decorator = new BasicDecorator();


    public AbstractDescriptor( Dungeon dungeon, int id ) {
        this.dungeon = dungeon;
        this.id = id;
    }

    @Override
    public Level construct() {
        Level level = constructLevel( dungeon, id, width, height );
        Collection<Room> rooms = null;
        while ( rooms == null )
            rooms = builder.planRooms( width, height );
        decorator.decorate( level, Collections.unmodifiableCollection( rooms ) );
        return level;
    }

    protected abstract Level constructLevel( Dungeon dungeon, int id, int width, int height );

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public void setSize( int width, int height ) {
        this.width = width;
        this.height = height;
    }

    @Override
    public LevelBuilder getBuilder() {
        return builder;
    }

    @Override
    public void setBuilder( LevelBuilder builder ) {
        if ( builder == null )
            throw new NullPointerException( "'builder' cannot be null" );
        this.builder = builder;
    }

    @Override
    public LevelDecorator getDecorator() {
        return decorator;
    }

    @Override
    public void setDecorator( LevelDecorator decorator ) {
        if ( decorator == null )
            throw new NullPointerException( "'decorator' cannot be null" );
        this.decorator = decorator;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        // TODO bundle abstract descriptor
        return null;
    }

    @Override
    public void restore( Bundle bundle ) {

    }
}
