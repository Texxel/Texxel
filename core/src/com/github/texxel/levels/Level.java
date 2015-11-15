package com.github.texxel.levels;

import com.github.texxel.Dungeon;
import com.github.texxel.actors.Actor;
import com.github.texxel.actors.Char;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.listeners.actor.ActorDestroyListener;
import com.github.texxel.event.listeners.actor.ActorSpawnListener;
import com.github.texxel.event.listeners.input.CellSelectedListener;
import com.github.texxel.event.listeners.item.ItemDropListener;
import com.github.texxel.event.listeners.level.LevelDestructionListener;
import com.github.texxel.items.Heap;
import com.github.texxel.items.Item;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.mechanics.FogOfWar;
import com.github.texxel.saving.Bundlable;
import com.github.texxel.utils.Point2D;

import java.util.List;

public interface Level extends Bundlable {

    /**
     * Gets the TileMap that backs this level. Any changes that are made to this TileMap will be
     * reflected in the Level
     * @return a TileMap
     */
    TileMap getTileMap();

    /**
     * Gets a list of all the actors in the level. The returned list is immutable; actors must be
     * added/removed with {@link #addActor(Actor)} and {@link #removeActor(Actor)}.
     * @return all the actors. The list cannot be modified
     */
    List<Actor> getActors();

    /**
     * A cached list of all the characters in the level. This yields the same result as iterating
     * over the list returned by {@link #getActors()} and sorting out the instances that implement
     * {@link Char}; but this method is much more efficient.
     * @return a list of all the characters. The list cannot be modified
     */
    List<Char> getCharacters();

    /**
     * Gets a list of all the heaps in the level. Use {@link #dropItem(Item, int, int)} to add an
     * item to the list.
     * @return all items in the level. The list cannot be modified
     */
    List<Heap> getHeaps();

    /**
     * Gets the fog that covers the level. The fog gets updated every frame
     * @return the level's fog
     */
    FogOfWar getFogOfWar();

    /**
     * Gets the width of the level
     * @return the level's width
     */
    int width();

    /**
     * Gets the height of the level
     * @return the level's height
     */
    int height();

    /**
     * Gets the dungeon this level is in.
     * @return the game's dungeon
     */
    Dungeon dungeon();

    /**
     * Adds an actor to the level. Plugins may edit/read the addition of the actor by registering
     * themselves in {@link #getActorSpawnHandler()}. There is no guarantee that the specified actor
     * actually gets added to the level.
     * @param actor tha actor to add
     * @return true if an actor was added
     */
    boolean addActor( Actor actor );

    /**
     * Removes an actor from the level. This event cannot be changed but can be read with
     * {@link #getActorDestroyHandler()}.
     * @param actor the actor to remove
     * @return true (always)
     */
    boolean removeActor( Actor actor );

    /**
     * Drops an item in the level. This event may be edited through {@link #getItemDropHandler()}.
     * @param item the item to add
     * @param x the x location to add the item at
     * @param y the y location to add the item at
     * @return true if the item was added
     */
    boolean dropItem( Item item, int x, int y );

    /**
     * Gets a point that is valid for a mob to spawn at
     * @return a valid respawn point
     */
    Point2D randomRespawnCell();

    /**
     * Gets the EventHandler used to dispatch actor spawning events. Implementations of Level are
     * responsible for firing the events.
     * @return a EventHandler
     */
    EventHandler<ActorSpawnListener> getActorSpawnHandler();

    /**
     * Gets the EventHandler used to dispatch the actor removing events. Implementations of Level
     * are responsible for firing the events.
     * @return a EventHandler
     */
    EventHandler<ActorDestroyListener> getActorDestroyHandler();

    /**
     * Gets the EventHandler used to dispatch item dropping. Implementations of Level are responsible
     * for firing the events at the correct time.
     * @return a EventHandler
     */
    EventHandler<ItemDropListener> getItemDropHandler();

    /**
     * Gets the EventHandler used to inform everyone that the level is getting destroyed. This is a
     * very important event to take note of as <b>it is integral that a level can be garbage
     * collected</b>. Any instances that hold a reference to a level must listen to this event and
     * null/switch the reference out on this event (unless the only references to that instance by
     * the level). Failure to remove references could very quickly result in a stack overflow error!
     * Pay very careful attention to this when using static references or persistent event handlers
     * (e.g. the {@link com.github.texxel.ui.InputHandler}).It is the responsibility of
     * implementations of Level to fire the event.
     * @return a EventHandler
     */
    // TODO make it harder to keep a level alive
    EventHandler<LevelDestructionListener> getDestructionHandler();

    /**
     * Gets the EventHandler that will fire a CellSelectedEvent whenever the user touches a cell.
     * It is <b>not</b> the responsibility for implementations of Level to handle firing of selection
     * events (that's done by the rendering/user input classes)
     * @return a EventHandler
     */
    EventHandler<CellSelectedListener> getCellSelectHandler();

    /**
     * A call to destroy the level. This should only need to be called by the core framework.
     * Implementations should also fire the destruction event in this method.
     * @see #getDestructionHandler()
     */
    void destroy();

    void populate();

    int id();

    /**
     * A convenience method to test if the level contains a point. This should be the exact same as
     * {@code x >= 0 && x < width && y >= 0 && y < height }
     * @return true if the point is in bounds
     */
    boolean isInBounds( int x, int y );

}
