package com.github.texxel;

import com.github.texxel.event.Event;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.listeners.level.LevelConstructionListener;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.LevelDecorator;
import com.github.texxel.levels.components.LevelDescriptor;
import com.github.texxel.levels.components.Room;
import com.github.texxel.levels.components.ThemeRegistry;
import com.github.texxel.utils.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public final class Dungeon implements Serializable {

    private static final long serialVersionUID = -2168020760891847785L;
    private final HashMap<Integer, LevelDescriptor> levelRegistry = new HashMap<>();
    private final EventHandler<LevelConstructionListener> constructionHandler = new EventHandler<>();

    {
        // register the default levels
        for ( int i = 1; i <= 5; i++ )
            register( i, new LevelDescriptor( this, i ).setTheme( ThemeRegistry.SEWERS ) );
        for ( int i = 6; i <= 10; i++ )
            register( i, new LevelDescriptor( this, i ).setTheme( ThemeRegistry.PRISON ) );
        for ( int i = 11; i <= 15; i++ )
            register( i, new LevelDescriptor( this, i ).setTheme( ThemeRegistry.CAVES ) );
        for ( int i = 16; i <= 20; i++ )
            register( i, new LevelDescriptor( this, i ).setTheme( ThemeRegistry.CITY ) );
        for ( int i = 21; i <= 25; i++ )
            register( i, new LevelDescriptor( this, i ).setTheme( ThemeRegistry.HALLS ) );

    }

    /**
     * Sets the level that will generated when the specified id is asked for
     * @param id the level's id
     * @param descriptor the descriptor for the level
     */
    public void register( int id, LevelDescriptor descriptor ) {
        levelRegistry.put( id, Assert.nonnull( descriptor, "cannot register a null descriptor" ) );
    }

    /**
     * Gets the level that will be constructed when the given id is requested. If the id has not
     * been registered, then a missing room descriptor will be given.
     * @param id the level's id
     * @return the descriptor for the id. Never null
     * @see #isRegistered(int)
     */
    public LevelDescriptor getDescriptor( int id ) {
        LevelDescriptor descriptor = levelRegistry.get( id );
        if ( descriptor == null )
            return new LevelDescriptor( this, id );
        else
            return descriptor;
    }

    /**
     * Tests if a LevelDescriptor has been registered with the given id
     * @param id the id to test for
     * @return true if the level has been registered
     */
    public boolean isRegistered( int id ) {
        return levelRegistry.containsKey(id);
    }

    /**
     * Creates a brand new level
     */
    Level make( LevelDescriptor descriptor ) {
        Assert.nonnull( descriptor, "cannot make a null level");

        ConstructionEvent event = new ConstructionEvent();
        event.state = State.STARTING;
        event.descriptor = descriptor;
        constructionHandler.dispatch(event);

        Level level = new Level( descriptor.id(), descriptor.width(), descriptor.height(), descriptor.getTheme() );

        event.level = level;
        event.state = State.INITIALISED;
        constructionHandler.dispatch(event);

        Collection<Room> rooms = null;
        while ( rooms == null )
            rooms = descriptor.getPlanner().planRooms(descriptor.width(), descriptor.height());

        event.rooms = rooms;
        event.state = State.PLANNED;
        constructionHandler.dispatch( event );

        List<LevelDecorator> decorators = descriptor.getDecorators();
        Collection<Room> unmodifiableRooms = Collections.unmodifiableCollection( rooms );
        for ( int i = 0; i < decorators.size(); i++ ) {
            LevelDecorator decorator = decorators.get( i );
            decorator.decorate( level, unmodifiableRooms );
        }

        event.state = State.DECORATED;
        constructionHandler.dispatch( event );

        return level;
    }

    /**
     * Gets the construction handler. The construction handler has events fired every time a new
     * level is generated.
     * @return this dungeon's construction handler
     */
    public EventHandler<LevelConstructionListener> getConstructionHandler() {
        return constructionHandler;
    }

    // Level construction state
    enum State {
        STARTING,
        INITIALISED,
        PLANNED,
        DECORATED
    }

    // Level construction event (never seen in listeners so only a single instance is needed per creation)
    static class ConstructionEvent implements Event<LevelConstructionListener> {

        State state;
        LevelDescriptor descriptor;
        Level level;
        Collection<Room> rooms;

        @Override
        public boolean dispatch(LevelConstructionListener listener) {
            switch (state) {
                case STARTING:
                    listener.onConstructionStarted(descriptor);
                    break;
                case INITIALISED:
                    listener.onLevelInitialised(descriptor, level);
                    break;
                case PLANNED:
                    listener.onLevelPlanned( descriptor, level, rooms );
                    break;
                case DECORATED:
                    listener.onLevelCreated( descriptor, level, rooms );
                    break;
            }
            return false;
        }
    }
}