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
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;

import java.util.HashMap;

public class Dungeon {

    private static final EventHandler<LevelConstructionListener> constructionHandler = new EventHandler<>();
    private static final EventHandler<LevelSaveListener> saveHandler = new EventHandler<>();
    private static final HashMap<Integer, LevelDescriptor> levelRegistry = new HashMap<>();

    private static Level currentLevel;

    static {
        // FIXME RegularLevel class loading
        try {
            Class.forName( RegularLevel.class.getName() );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        }
    }

    private Dungeon() {
        // static methods only
    }

    public static void register( int id, LevelDescriptor descriptor ) {
        levelRegistry.put( id, descriptor );
    }

    public static LevelDescriptor getDescriptor( int id ) {
        return levelRegistry.get( id );
    }

    public static Level goTo( int levelID ) {
        if ( currentLevel != null )
            currentLevel.destroy();
        currentLevel = null;
        if ( !load( levelID ) )
            make( levelID );
        return currentLevel;
    }

    private static void make( int id ) {
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

    private static boolean load( int id ) {
        FileHandle file = fileHandle( id );
        if ( !file.exists() )
            return false;
        String data = file.readString();
        Bundle levelBundle = BundleGroup.loadGroup( data );
        currentLevel = levelBundle.getBundlable( "level" );
        return true;
    }

    public static void gameEnd() {
        saveLevel( currentLevel );
        currentLevel.destroy();
        currentLevel = null;
    }

    private static FileHandle saveLevel( Level level ) {
        FileHandle levelFile = fileHandle( level.id() );
        BundleGroup bundleGroup = BundleGroup.newGroup();
        bundleGroup.put( "level", level );
        levelFile.writeString( bundleGroup.toString(), false );
        return levelFile;
    }

    private static FileHandle fileHandle( int id ) {
        return Gdx.files.local( "level" + id + ".json" );
    }

    /**
     * Gets the current level. Note: using this method when switching between
     * @return the current level
     */
    public static Level level() {
        return currentLevel;
    }

    public static EventHandler<LevelConstructionListener> levelConstructionHandler() {
        return constructionHandler;
    }

    public static EventHandler<LevelSaveListener> getLevelSaveHandler() {
        return saveHandler;
    }

}
