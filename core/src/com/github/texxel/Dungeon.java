package com.github.texxel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.texxel.event.Event;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.listeners.level.LevelConstructionListener;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.LevelDecorator;
import com.github.texxel.levels.components.LevelDescriptor;
import com.github.texxel.levels.components.Room;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Dungeon implements Serializable {

    private static final long serialVersionUID = -2168020760891847785L;
    private final HashMap<Integer, LevelDescriptor> levelRegistry = new HashMap<>();
    private final int id;
    private final EventHandler<LevelConstructionListener> constructionHandler = new EventHandler<>();

    {
        for ( int i = 1; i <= 10; i++ ) {
            register( i, new LevelDescriptor( this, i ) );
        }
    }

    public Dungeon( int id ) {
        this.id = id;
    }

    /**
     * Sets the level that will generated when the specified id is asked for
     * @param id the level's id
     * @param descriptor the descriptor for the level
     */
    public void register( int id, LevelDescriptor descriptor ) {
        if ( descriptor == null )
            throw new NullPointerException( "Cannot register a null level" );
        levelRegistry.put( id, descriptor );
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
     * If the level has previously been visited, then the level will be read from memory. Otherwise,
     * the level will be created anew. Note: the Dungeon does not remember levels, thus, every call
     * to this method will generate a new level even if the level has already been created.
     * @param descriptor the ID of the level to create
     * @return the created level
     * @throws NullPointerException if descriptor is null
     */
    public Level loadLevel( LevelDescriptor descriptor ) {
        Level level = load( levelFile( descriptor.id() ) );
        if ( level != null )
            return level;
        return make(descriptor);
    }

    /**
     * Loads the level from the save file. Returns null if the level has not been saved
     */
    private static Level load( FileHandle file ) {
        if ( !file.exists() )
            return null;
        ObjectInputStream ois = null;
        Level level;
        try {
            ois = new ObjectInputStream( new BufferedInputStream( file.read() ) );
            level = (Level) ois.readObject();
        } catch ( IOException | ClassNotFoundException e ) {
            e.printStackTrace();
            // TODO better level loading exception handling
            throw new RuntimeException( "Couldn't load level" );
        } finally {
            if ( ois != null )
                try {
                    ois.close();
                } catch ( IOException ignored ) {
                }
        }
        return level;
    }

    /**
     * Creates a brand new level
     */
    private Level make( LevelDescriptor descriptor ) {
        ConstructionEvent event = new ConstructionEvent();
        event.state = State.STARTING;
        event.descriptor = descriptor;
        constructionHandler.dispatch(event);

        Level level = new Level( this, id, descriptor.width(), descriptor.height() );

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
     * The file that the given level will be saved to
     */
    private FileHandle levelFile( int id ) {
        return Gdx.files.local( "d-" + this.id + "-l-" + id + ".dat" );
    }

    /**
     * The file that this dungeon will be saved to
     */
    private FileHandle dungeonFile() {
        return Gdx.files.local( "d-" + id + ".dat" );
    }

    /**
     * Saves the dungeon to a file. Note: this does <b>not</b> save any levels - they must manually
     * be saved separately using {@link #save(Level)}
     */
    public void save() throws IOException {
        ObjectOutputStream oos = null;
        try {
            FileHandle output = dungeonFile();
            oos = new ObjectOutputStream( new BufferedOutputStream( output.write( false ) ) );
            oos.writeObject( this );
        } finally {
            if ( oos != null ) {
                try {
                    oos.close();
                } catch ( IOException ignored ) {
                    // just let the memory leak...
                }
            }
        }
    }

    /**
     * Saves a level overwriting whatever was previously saved.
     * @param level the level to save
     */
    public void save( Level level ) throws IOException {
        ObjectOutputStream oos = null;
        try {
            FileHandle output = levelFile( level.id() );
            oos = new ObjectOutputStream( new BufferedOutputStream( output.write( false ) ) );
            oos.writeObject( level );
        } finally {
            if ( oos != null ) {
                try {
                    oos.close();
                } catch ( IOException ignored ) {
                }
            }
        }
    }

    // Level construction state
    private enum State {
        STARTING,
        INITIALISED,
        PLANNED,
        DECORATED
    }

    // Level construction event (never seen in listeners so only a single instance is needed per creation)
    private static class ConstructionEvent implements Event<LevelConstructionListener> {

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
                default:
                    throw new RuntimeException( "This should never happen" );
            }
            return false;
        }
    }


}