package com.github.texxel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.events.level.LevelCreatedEvent;
import com.github.texxel.event.events.level.LevelPlanEvent;
import com.github.texxel.event.events.level.LevelPopulateEvent;
import com.github.texxel.event.events.level.LevelTilePlacedEvent;
import com.github.texxel.event.listeners.level.LevelConstructionListener;
import com.github.texxel.event.listeners.level.LevelSaveListener;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.RegularLevel;
import com.github.texxel.levels.components.LevelDescriptor;
import com.github.texxel.saving.Bundlable;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;

import java.util.HashMap;

public class Dungeon implements Bundlable {

    private final EventHandler<LevelConstructionListener> constructionHandler = new EventHandler<>();
    private final EventHandler<LevelSaveListener> saveHandler = new EventHandler<>();
    private final HashMap<Integer, LevelDescriptor> levelRegistry = new HashMap<>();

    private Level currentLevel;

    {

        register( 1, new RegularLevel.RegularDescriptor( this, 1 ) );
        register( 2, new RegularLevel.RegularDescriptor( this, 2 ) );
        register( 3, new RegularLevel.RegularDescriptor( this, 3 ) );
        register( 4, new RegularLevel.RegularDescriptor( this, 4 ) );
        register( 5, new RegularLevel.RegularDescriptor( this, 5 ) );
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

    public Level goTo( int levelID ) {
        if ( currentLevel != null )
            currentLevel.destroy();
        currentLevel = null;
        if ( !load( levelID ) )
            make( levelID );
        return currentLevel;
    }

    private void make( int id ) {
        LevelPlanEvent planEvent = new LevelPlanEvent( levelRegistry.get( id ), "hello" );
        constructionHandler.dispatch( planEvent );
        LevelDescriptor descriptor = planEvent.getLevel();
        Level level = currentLevel = descriptor.constructLevel();

        LevelTilePlacedEvent tilePlacedEvent = new LevelTilePlacedEvent( level );
        constructionHandler.dispatch( tilePlacedEvent );

        level.populate();
        LevelPopulateEvent levelPopulateEvent = new LevelPopulateEvent( level );
        constructionHandler.dispatch( levelPopulateEvent );

        LevelCreatedEvent madeEvent = new LevelCreatedEvent( level );
        constructionHandler.dispatch( madeEvent );
    }

    private boolean load( int id ) {
        FileHandle file = fileHandle( id );
        if ( !file.exists() )
            return false;
        String data = file.readString();
        Bundle levelBundle = BundleGroup.loadGroup( data );
        currentLevel = levelBundle.getBundlable( "level" );
        return true;
    }

    public void gameEnd() {
        saveLevel( currentLevel );
        currentLevel.destroy();
        currentLevel = null;
    }

    private FileHandle saveLevel( Level level ) {
        FileHandle levelFile = fileHandle( level.id() );
        BundleGroup bundleGroup = BundleGroup.newGroup();
        bundleGroup.put( "level", level );
        levelFile.writeString( bundleGroup.toString(), false );
        return levelFile;
    }

    private FileHandle fileHandle( int id ) {
        return Gdx.files.local( "level" + id + ".json" );
    }

    /**
     * Gets the current level. Note: using this method when switching between
     * @return the current level
     */
    public Level level() {
        return currentLevel;
    }

    public EventHandler<LevelConstructionListener> levelConstructionHandler() {
        return constructionHandler;
    }

    public EventHandler<LevelSaveListener> getLevelSaveHandler() {
        return saveHandler;
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        return topLevel.newBundle();
    }

    @Override
    public void restore( Bundle bundle ) {

    }
}
