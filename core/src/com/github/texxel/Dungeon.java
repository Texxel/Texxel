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
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;

import java.util.HashMap;

public class Dungeon implements Bundlable {

    static {
        ConstructorRegistry.put( Dungeon.class, new Constructor<Dungeon>() {
            @Override
            public Dungeon newInstance( Bundle bundle ) {
                return new Dungeon();
            }
        } );
    }

    private EventHandler<LevelConstructionListener> constructionHandler = new EventHandler<>();
    private final HashMap<Integer, LevelDescriptor> levelRegistry = new HashMap<>();

    {
        for ( int i = 1; i <= 10; i++ ) {
            register( i, new RegularLevel.RegularDescriptor( this, i ) );
        }
    }

    /**
     * Sets the level that will generated when the specified id is asked for
     * @param id the level's id
     * @param descriptor the constructor for the level
     */
    public void register( int id, LevelDescriptor descriptor ) {
        levelRegistry.put( id, descriptor );
    }

    /**
     * Gets the level that will be constructed when the given id is requested
     * @param id the level's id
     * @return the constructor for the id
     */
    public LevelDescriptor getDescriptor( int id ) {
        return levelRegistry.get( id );
    }

    /**
     * If the level has previously been visited, then the level will be read from memory. Otherwise,
     * the level will be created anew.
     * @param id the ID of the level to create
     * @return the created level
     */
    public Level loadLevel( int id ) {
        Level level = load( id );
        if (level != null)
            return level;
        return make( id );
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
        return Gdx.files.local( "level" + id + ".json" );
    }

    private Level make( int id ) {
        LevelPlanEvent planEvent = new LevelPlanEvent( levelRegistry.get( id ), "hello" );
        constructionHandler.dispatch( planEvent );
        LevelDescriptor descriptor = planEvent.getLevel();
        Level level = descriptor.constructLevel();

        LevelTilePlacedEvent tilePlacedEvent = new LevelTilePlacedEvent( level );
        constructionHandler.dispatch( tilePlacedEvent );

        level.populate();
        LevelPopulateEvent levelPopulateEvent = new LevelPopulateEvent( level );
        constructionHandler.dispatch( levelPopulateEvent );

        LevelCreatedEvent madeEvent = new LevelCreatedEvent( level );
        constructionHandler.dispatch( madeEvent );

        return level;
    }

    public EventHandler<LevelConstructionListener> levelConstructionHandler() {
        return constructionHandler;
    }

    public void save() {
        BundleGroup bundle = BundleGroup.newGroup();
        bundle( bundle );
        FileHandle file = dungeonFile();
        file.writeString( bundle.toString(), false );
    }

    private static FileHandle dungeonFile() {
        return Gdx.files.local( "dungeon.json" );
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = topLevel.newBundle();
        bundle.putBundlable( "constructionHandler", constructionHandler );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        constructionHandler = bundle.getBundlable( "constructionHandler" );
    }
}
