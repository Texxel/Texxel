package com.github.texxel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.events.level.LevelCreatedEvent;
import com.github.texxel.event.events.level.LevelPlanEvent;
import com.github.texxel.event.events.level.LevelPopulateEvent;
import com.github.texxel.event.events.level.LevelTilePlacedEvent;
import com.github.texxel.event.listeners.level.LevelConstructionListener;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.RegularLevel;
import com.github.texxel.levels.components.LevelDescriptor;
import com.github.texxel.saving.Bundlable;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.BundleWriter;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;

import java.util.HashMap;

public class Dungeon implements Bundlable {

    static {
        ConstructorRegistry.put( Dungeon.class, new Constructor<Dungeon>() {
            @Override
            public Dungeon newInstance( Bundle bundle ) {
                int id = bundle.getInt( "id" );
                if ( dungeonRegistry.containsKey( id ) ) {
                    return dungeonRegistry.get( id );
                } else {
                    Dungeon dungeon = new Dungeon( id );
                    String location = bundle.getString( "location" );
                    BundleGroup group = BundleWriter.load( location );
                    dungeon.actuallyRestore( group.getBundle( "dungeon" ) );
                    return dungeon;
                }
            }
        } );
    }

    private static final HashMap<Integer, Dungeon> dungeonRegistry = new HashMap<>();
    private final EventHandler<LevelConstructionListener> constructionHandler = new EventHandler<>();
    private final HashMap<Integer, LevelDescriptor> levelRegistry = new HashMap<>();
    private final int id;
    private boolean constructed;

    {
        for ( int i = 1; i <= 10; i++ ) {
            register( i, new RegularLevel.RegularDescriptor( this, i ) );
        }
    }

    public Dungeon( int id ) {
        this.id = id;
        dungeonRegistry.put( id, this );
    }

    /**
     * Sets the level that will generated when the specified id is asked for
     * @param id the level's id
     * @param descriptor the constructor for the level
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
     * @return the constructor for the id. Never null
     */
    public LevelDescriptor getDescriptor( int id ) {
        LevelDescriptor descriptor = levelRegistry.get( id );
        if ( descriptor == null )
            return new RegularLevel.RegularDescriptor( this, id );
        else
            return descriptor;
    }

    /**
     * Tests if a Level has been registered with the given id
     * @param id the id to test for
     * @return true if the level has been registered
     */
    public boolean isRegistered( int id ) {
        return levelRegistry.containsKey( id );
    }

    /**
     * If the level has previously been visited, then the level will be read from memory. Otherwise,
     * the level will be created anew.
     * @param descriptor the ID of the level to create
     * @return the created level
     * @throws NullPointerException if descriptor is null
     */
    public Level loadLevel( LevelDescriptor descriptor ) {
        Level level = load( descriptor.id() );
        if (level != null)
            return level;
        return make( descriptor );
    }

    private static Level load( int id ) {
        FileHandle file = levelFile( id );
        if ( !file.exists() )
            return null;
        String data = file.readString();
        Bundle levelBundle = BundleGroup.loadGroup( data );
        return levelBundle.getBundlable( "level" );
    }

    private static FileHandle levelFile( int id ) {
        return Gdx.files.local( "level-" + id + ".json" );
    }

    private Level make( LevelDescriptor id ) {
        LevelPlanEvent planEvent = new LevelPlanEvent( id, "hello" );
        constructionHandler.dispatch( planEvent );
        LevelDescriptor descriptor = planEvent.getLevel();
        Level level = descriptor.construct();

        LevelTilePlacedEvent tilePlacedEvent = new LevelTilePlacedEvent( level );
        constructionHandler.dispatch( tilePlacedEvent );

        level.populate();
        LevelPopulateEvent levelPopulateEvent = new LevelPopulateEvent( level );
        constructionHandler.dispatch( levelPopulateEvent );

        LevelCreatedEvent madeEvent = new LevelCreatedEvent( level );
        constructionHandler.dispatch( madeEvent );

        return level;
    }

    /**
     * Gets the construction handler for levels. Any time a level is created in this dungeon, the
     * given handler will be notified. Note: The construction handler is not saved!
     * @return the construction handler
     */
    public EventHandler<LevelConstructionListener> levelConstructionHandler() {
        return constructionHandler;
    }

    private String saveLocation() {
        return "dungeon-" + id + ".json";
    }

    /**
     * Saves the dungeon to a file. Note: this does <b>not</b> save any levels - they must manually
     * be saved separately
     */
    public void save() {
        BundleGroup topLevel = BundleGroup.newGroup();
        Bundle bundle = actuallyBundle( topLevel );
        topLevel.putBundle( "dungeon", bundle );
        BundleWriter.write( saveLocation(), topLevel );
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = topLevel.newBundle();
        bundle.putInt( "id", id );
        bundle.putString( "location", saveLocation() );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
    }

    private Bundle actuallyBundle( BundleGroup topLevel ) {
        Bundle bundle = topLevel.newBundle();
        return bundle;
    }

    private void actuallyRestore( Bundle bundle ) {

    }
}
