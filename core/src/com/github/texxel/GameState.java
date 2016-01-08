package com.github.texxel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.actors.heroes.Warrior;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.LevelDescriptor;
import com.github.texxel.utils.Assert;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The GameState is responsible for tracking the overall state of the game. The game's state is
 * composed of the games dungeon, the current level, and the active player. The GameState also
 * handles all saving/loading of the game.
 */
public final class GameState {

    /**
     * Loads a game's state from memory. Even if the given game state has already been loaded, this
     * will return a new GameState.
     * @param gameID the id of the game to load
     * @return the loaded game state or null if it does not exist
     */
    public static GameState resume( int gameID ) throws IOException {
        FileHandle file = gameFile( gameID );
        if ( !file.exists() )
            return null;

        try {
            ObjectInputStream in = new ObjectInputStream( new BufferedInputStream( file.read() ) );
            Dungeon dungeon = (Dungeon) in.readObject();
            Hero player = (Hero) in.readObject();
            return new GameState( gameID, dungeon, player );
        } catch ( Exception e ) {
            throw new IOException( "Unable to load save file", e );
        }
    }

    /**
     * Creates a brand new game
     * @param id the id of the new game to create
     * @return the new game
     */
    public static GameState newGame( int id ) {
        Dungeon dungeon = new Dungeon();
        Level level = dungeon.make( dungeon.getDescriptor( 1 ) );
        Warrior warrior = new Warrior( level, level.randomRespawnCell() );
        level.addActor( warrior );
        return new GameState( id, dungeon, warrior );
    }

    private final int id;
    private Hero player;
    private Dungeon dungeon;

    private GameState( int id, Dungeon dungeon, Hero player ) {
        this.id = id;
        this.dungeon = Assert.nonnull( dungeon, "Dungeon cannot be null" );
        this.player = Assert.nonnull( player, "Player cannot be null" );
    }

    public int id() {
        return id;
    }

    /**
     * The games dungeon.
     * @return the game's dungeon
     */
    public Dungeon getDungeon() {
        return dungeon;
    }

    public Level getLevel() {
        return player.level();
    }

    public Hero getPlayer() {
        return player;
    }

    /**
     * Sets the active player
     * @param player the active player
     */
    public void setPlayer( Hero player ) {
        this.player = player;
    }


    /**
     * Progresses the hero from one level to the next. This will remove the Hero from the current
     * level, generate/load the new level and place the hero into the new level. It will then save
     * the state of both the new level and the old level.
     * @param nextLevel the new level to progress to
     * @throws IOException if any error occurs while loading a level
     */
    public void progress( LevelDescriptor nextLevel ) throws IOException {
        Assert.nonnull( nextLevel, "Cannot go to a null level" );

        // update the previous level
        Level previous = player.level();
        previous.removeActor( player );

        // update the new level
        Level next = loadLevel( nextLevel );
        player.setLevel( next );
        player.setLocation( next.randomRespawnCell() );
        next.addActor( player );

        // save game state
        save( id, previous );
        save();
    }

    /**
     * If the level has previously been visited, then the level will be read from memory. Otherwise,
     * the level will be created anew. Note: the GameState does not remember levels in RAM, thus,
     * every call to this method will generate a new level even if the level has already been loaded
     * from memory. Generally, this should not be a problem since the there should only ever be one
     * active level at a time (except when switching levels when there is temporarily two levels).
     * @param descriptor the ID of the level to create
     * @return the created level
     * @throws NullPointerException if descriptor is null
     */
    private Level loadLevel( LevelDescriptor descriptor ) throws IOException {
        Level level = load( levelFile( id, descriptor.id() ) );
        if ( level != null )
            return level;
        return getDungeon().make( descriptor );
    }

    /**
     * Where the game will be saved to
     */
    private static FileHandle gameFile( int id ) {
        return Gdx.files.local( "d-" + id + ".dat" );
    }

    /**
     * The file that the given level will be saved to
     */
    private static FileHandle levelFile( int game, int level ) {
        return Gdx.files.local( "d-" + game + "-l-" + level + ".dat" );
    }

    /**
     * Saves the current game state
     */
    public void save() throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(
                    new BufferedOutputStream(
                            gameFile( id ).write( false )
                    ) );
            out.writeObject( dungeon );
            out.writeObject( player );
        } catch ( GdxRuntimeException | IOException e ) {
            throw new IOException( "Failed to save game state", e );
        } finally {
            if ( out != null ) {
                try {
                    out.close();
                } catch ( IOException ignored ) {
                }
            }
        }
    }

    /**
     * Saves a level overwriting whatever was previously saved.
     * @param level the level to save
     */
    private static void save( int gameID, Level level ) throws IOException {
        ObjectOutputStream oos = null;
        try {
            FileHandle output = levelFile( gameID, level.id() );
            oos = new ObjectOutputStream( new BufferedOutputStream( output.write( false ) ) );
            oos.writeObject( level );
        } catch ( GdxRuntimeException | IOException e ) {
            throw new IOException( "Failed to save level", e );
        } finally {
            if ( oos != null ) {
                try {
                    oos.close();
                } catch ( IOException ignored ) {
                }
            }
        }
    }

    /**
     * Loads the level from the save file. Returns null if the level has not been saved
     */
    private static Level load( FileHandle file ) throws IOException {
        if ( !file.exists() )
            return null;
        ObjectInputStream in = null;
        Level level;
        try {
            in = new ObjectInputStream( new BufferedInputStream( file.read() ) );
            level = (Level) in.readObject();
        } catch ( GdxRuntimeException | IOException | ClassNotFoundException e ) {
            throw new IOException( "Couldn't load level", e );
        } finally {
            if ( in != null )
                try {
                    in.close();
                } catch ( IOException ignored ) {
                }
        }
        return level;
    }


}
