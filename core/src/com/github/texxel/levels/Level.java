package com.github.texxel.levels;

import com.github.texxel.actors.Actor;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.heaps.Heap;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.events.actor.ActorDestroyEvent;
import com.github.texxel.event.events.actor.ActorSpawnEvent;
import com.github.texxel.event.events.level.LevelDestructionEvent;
import com.github.texxel.event.listeners.actor.ActorDestroyListener;
import com.github.texxel.event.listeners.actor.ActorSpawnListener;
import com.github.texxel.event.listeners.item.ItemDropListener;
import com.github.texxel.event.listeners.level.LevelDestructionListener;
import com.github.texxel.items.api.Item;
import com.github.texxel.levels.components.Theme;
import com.github.texxel.levels.components.ThemeRegistry;
import com.github.texxel.levels.components.TileFiller;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.mechanics.FogOfWar;
import com.github.texxel.mechanics.SimpleFog;
import com.github.texxel.tiles.Tile;
import com.github.texxel.tiles.WallTile;
import com.github.texxel.utils.Assert;
import com.github.texxel.utils.Point2D;
import com.github.texxel.utils.Random;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level implements Serializable {

    private static final long serialVersionUID = 5335599560586674121L;

    // level data
    private final int id;
    private final int width, height;
    private String theme;
    private final TileMap tileMap;
    private final ArrayList<Actor> actors = new ArrayList<>();

    // TODO fog should be moved to the UI
    private final FogOfWar fog;

    // event handlers
    private final EventHandler<ActorSpawnListener> actorSpawnHandler = new EventHandler<>();
    private final EventHandler<ActorDestroyListener> actorDestroyHandler = new EventHandler<>();
    private final EventHandler<LevelDestructionListener> levelDestructionHandler = new EventHandler<>();
    private final EventHandler<ItemDropListener> itemDropHandler = new EventHandler<>();

    // cached data/shared lists
    private transient ArrayList<Char> charCache = new ArrayList<>();
    private transient Map<Point2D, Heap> heapCache = new HashMap<>();
    private transient List<Actor> publicActors = Collections.unmodifiableList( actors );
    private transient List<Char> publicChars = Collections.unmodifiableList( charCache );
    private transient Map<Point2D, Heap> publicHeaps = Collections.unmodifiableMap( heapCache );

    public Level( int id, int width, int height, String theme ) {
        this.id = id;
        this.width = width;
        this.height = height;
        TileFiller.FullFiller filler = new TileFiller.FullFiller() {
            @Override
            public Tile makeTile( int x, int y ) {
                return WallTile.getInstance();
            }
        };
        this.tileMap = new TileMap( width, height, filler );
        this.fog = new SimpleFog( width, height );
        this.theme = Assert.nonnull( theme, "Theme cannot be null" );
    }

    /**
     * Gets the theme that this level is using. This is a convenience method for
     * {@code ThemeRegistry.get( level.getThemeKey() )}.
     * @return the levels theme
     */
    public Theme getTheme() {
        return ThemeRegistry.get( theme );
    }

    /**
     * Gets the key that this levels theme is using
     * @return the level themes
     */
    public String getThemeKey() {
        return theme;
    }

    /**
     * Sets the theme to use for this level. The theme should be registered into {@link ThemeRegistry}.
     * @param theme the new theme
     */
    public void setTheme( String theme ) {
        this.theme = Assert.nonnull( theme, "Theme key cannot be null" );
    }

    public int id() {
        return id;
    }

    /**
     * Gets the width of the level
     * @return the level's width
     */
    public int width() {
        return width;
    }

    /**
     * Gets the height of the level
     * @return the level's height
     */
    public int height() {
        return height;
    }

    /**
     * Adds an actor to the level. Plugins may edit/read the addition of the actor by registering
     * themselves in {@link #getActorSpawnHandler()}. There is no guarantee that the specified actor
     * actually gets added to the level.
     * @param actor tha actor to add
     * @return true if an actor was added
     */
    public boolean addActor( Actor actor ) {
        ActorSpawnEvent e = new ActorSpawnEvent( actor );
        actorSpawnHandler.dispatch(e);
        if ( e.isCancelled() )
            return false;
        actor = e.getActor();
        actors.add( actor );

        // updated cached lists
        if ( actor instanceof Char )
            charCache.add( (Char) actor );
        if ( actor instanceof Heap ) {
            Heap heap = (Heap)actor;
            heapCache.put( heap.getLocation(), heap );
        }

        return true;
    }

    /**
     * Removes an actor from the level. This event cannot be changed but can be read with
     * {@link #getActorDestroyHandler()}.
     * @param actor the actor to remove
     * @return true (always)
     */
    public boolean removeActor( Actor actor ) {
        actors.remove(actor);

        // update cached lists
        if ( actor instanceof Char )
            charCache.remove( actor );
        if ( actor instanceof Heap ) {
            Heap heap = (Heap)actor;
            heapCache.remove( heap.getLocation() );
        }
        ActorDestroyEvent e = new ActorDestroyEvent( actor );
        actorDestroyHandler.dispatch( e );
        return true;
    }

    /**
     * Gets the TileMap that backs this level. Any changes that are made to this TileMap will be
     * reflected in the Level
     * @return a TileMap
     */
    public TileMap getTileMap() {
        return tileMap;
    }

    /**
     * A cached list of all the characters in the level. This yields the same result as iterating
     * over the list returned by {@link #getActors()} and sorting out the instances that implement
     * {@link Char}; but this method is much more efficient.
     * @return a list of all the characters. The list cannot be modified
     */
    public List<Char> getCharacters() {
        return publicChars;
    }

    /**
     * Gets a list of all the actors in the level. The returned list is immutable; actors must be
     * added/removed with {@link #addActor(Actor)} and {@link #removeActor(Actor)}.
     * @return all the actors. The list cannot be modified
     */
    public List<Actor> getActors() {
        return publicActors;
    }

    /**
     * Gets a cached list of all the heaps mapped to the locations that they are added at. The
     * returned map cannot be modified. Use {@link #dropItem(Item, Point2D)} to add new heaps.
     * To remove a heap, either delete all the items in the returned heap and the heap
     * will be removed automatically or use {@link #removeActor(Actor)}
     * @return all items in the level. The map cannot be modified
     */
    public Map<Point2D, Heap> getHeaps() {
        return publicHeaps;
    }

    /**
     * Drops an item stack in the level. This event may be edited through {@link #getItemDropHandler()}.
     * @param item the item to add
     * @param location where to put the item
     * @return the heap that the item was dropped into. Never null
     */
    public Heap dropItem( final Item item, final Point2D location ) {
        if ( location == null )
            throw new NullPointerException( "'location' cannot be null" );
        if ( item == null )
            throw new NullPointerException( "'item' cannot be null" );
        Heap heap = heapCache.get( location );
        if ( heap == null ) {
            heap = new Heap( this, location );
            addActor( heap );
        }
        heap.add( item );
        return heap;
    }

    /**
     * Gets a point that is valid for a mob to spawn at
     * @return a valid respawn point
     */
    public Point2D randomRespawnCell() {
        // TODO less hacky respawn cell method
        TileMap tileMap = this.tileMap;
        ArrayList<Actor> actors = this.actors;
        locationFinder:
        while ( true ) {
            int x = Random.Int( width );
            int y = Random.Int( height );
            Tile tile = tileMap.getTile( x, y );
            if ( !tile.isPassable() )
                continue;
            for ( int i = actors.size()-1; i >= 0; i-- ) {
                Actor a = actors.get( i );
                if ( a.isOver( x, y ) )
                    continue locationFinder;
            }
            return new Point2D( x, y );
        }
    }

    /**
     * A convenience method to test if the level contains a point. This should be the exact same as
     * {@code x >= 0 && x < width && y >= 0 && y < height }
     * @return true if the point is in bounds
     */
    public boolean isInBounds( int x, int y ) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Gets the fog that covers the level. The fog gets updated every frame
     * @return the level's fog
     */
    public FogOfWar getFogOfWar() {
        return fog;
    }

    /**
     * Gets the EventHandler used to dispatch actor spawning events. Implementations of Level are
     * responsible for firing the events.
     * @return a EventHandler
     */
    public EventHandler<ActorSpawnListener> getActorSpawnHandler() {
        return actorSpawnHandler;
    }

    /**
     * Gets the EventHandler used to dispatch the actor removing events. Implementations of Level
     * are responsible for firing the events.
     * @return a EventHandler
     */
    public EventHandler<ActorDestroyListener> getActorDestroyHandler() {
        return actorDestroyHandler;
    }

    /**
     * Gets the EventHandler used to inform everyone that the level is getting destroyed. This is a
     * very important event to take note of as <b>it is integral that a level can be garbage
     * collected</b>. Any instances that hold a reference to a level must listen to this event and
     * null/switch the reference out on this event (unless the only references to that instance by
     * the level). Failure to remove references could very quickly result in a stack overflow error!
     * Pay very careful attention to this when using static references or persistent event handlers.
     * It is the responsibility of implementations of Level to fire the event.
     * @return a EventHandler
     */
    public EventHandler<LevelDestructionListener> getDestructionHandler() {
        return levelDestructionHandler;
    }

    /**
     * Gets the EventHandler used to dispatch item dropping. Implementations of Level are responsible
     * for firing the events at the correct time.
     * @return a EventHandler
     */
    public EventHandler<ItemDropListener> getItemDropHandler() {
        return itemDropHandler;
    }

    /**
     * A call to destroy the level. This should only need to be called by the core framework. Calling
     * this will fire an event in the destruction handler
     * @see #getDestructionHandler()
     */
    public void destroy() {
        levelDestructionHandler.dispatch( new LevelDestructionEvent( this ) );
    }

    /**
     * Gets the level that that this level will naturally descend to. However, there is no
     * requirement that this suggestion is used. There is also no requirement that the level this
     * returns suggests to ascend to this level.
     * @return the suggested next level
     */
    public int getLevelBelow() {
        return id() + 1;
    }

    /**
     * Gets the level that that this level will naturally ascend to. However, there is no
     * requirement that this suggestion is used. There is also no requirement that the level this
     * returns suggests to ascend to this level.
     * @return the suggested next level
     */
    public int getLevelAbove() {
        return id() - 1;
    }

    private void writeObject( ObjectOutputStream out ) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject( ObjectInputStream out ) throws IOException, ClassNotFoundException {
        out.defaultReadObject();

        charCache = new ArrayList<>();
        heapCache = new HashMap<>();
        publicActors = Collections.unmodifiableList( actors );
        publicChars = Collections.unmodifiableList( charCache );
        publicHeaps = Collections.unmodifiableMap( heapCache );

        // repopulate cached lists
        for ( Actor actor : actors ) {
            if ( actor instanceof Char )
                charCache.add( (Char)actor );
            if ( actor instanceof Heap ) {
                Heap heap = (Heap)actor;
                heapCache.put( heap.getLocation(), heap );
            }
        }
    }

}
