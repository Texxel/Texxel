package com.github.texxel.levels;

import com.github.texxel.Dungeon;
import com.github.texxel.actors.Actor;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.heaps.Heap;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.actors.heroes.Warrior;
import com.github.texxel.actors.mobs.Rat;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.events.actor.ActorDestroyEvent;
import com.github.texxel.event.events.actor.ActorSpawnEvent;
import com.github.texxel.event.events.level.LevelDestructionEvent;
import com.github.texxel.event.listeners.actor.ActorDestroyListener;
import com.github.texxel.event.listeners.actor.ActorSpawnListener;
import com.github.texxel.event.listeners.item.ItemDropListener;
import com.github.texxel.event.listeners.level.LevelDestructionListener;
import com.github.texxel.items.Gold;
import com.github.texxel.items.api.Item;
import com.github.texxel.items.weapons.Sword;
import com.github.texxel.levels.components.TileFiller;
import com.github.texxel.levels.components.TileMap;
import com.github.texxel.mechanics.FogOfWar;
import com.github.texxel.mechanics.SimpleFog;
import com.github.texxel.tiles.Tile;
import com.github.texxel.tiles.WallTile;
import com.github.texxel.utils.Point2D;
import com.github.texxel.utils.Random;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractLevel implements Level {

    private static final long serialVersionUID = 5335599560586674121L;

    private final Dungeon dungeon;
    private final int id;
    private final int width, height;

    // TODO fog should be moved to the UI
    private final FogOfWar fog;

    private final TileMap tileMap;
    private final ArrayList<Actor> actors = new ArrayList<>();

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

    public AbstractLevel( Dungeon dungeon, int id, int width, int height ) {
        this.dungeon = dungeon;
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
    }

    @Override
    public Dungeon dungeon() {
        return dungeon;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public void populate() {
        Hero hero = new Warrior( this, randomRespawnCell() );
        addActor( hero );

        for ( int i = 0; i < 3; i++ ) {
            addActor( new Rat( this, randomRespawnCell() ) );
        }

        for ( int i = 0; i < 10; i++ ) {
            dropItem( new Gold( i ), randomRespawnCell() );
            dropItem( new Sword(), randomRespawnCell() );
        }
        dropItem( new Gold( 6 ), hero.getLocation().plus( 1, 0 ) );

    }

    @Override
    public boolean addActor( Actor actor ) {
        ActorSpawnEvent e = new ActorSpawnEvent( actor );
        actorSpawnHandler.dispatch( e );
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

    @Override
    public boolean removeActor( Actor actor ) {
        actors.remove( actor );

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

    @Override
    public TileMap getTileMap() {
        return tileMap;
    }

    @Override
    public List<Char> getCharacters() {
        return publicChars;
    }

    @Override
    public List<Actor> getActors() {
        return publicActors;
    }

    @Override
    public Map<Point2D, Heap> getHeaps() {
        return publicHeaps;
    }

    @Override
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

    @Override
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

    @Override
    public boolean isInBounds( int x, int y ) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    @Override
    public FogOfWar getFogOfWar() {
        return fog;
    }

    @Override
    public EventHandler<ActorSpawnListener> getActorSpawnHandler() {
        return actorSpawnHandler;
    }

    @Override
    public EventHandler<ActorDestroyListener> getActorDestroyHandler() {
        return actorDestroyHandler;
    }

    @Override
    public EventHandler<LevelDestructionListener> getDestructionHandler() {
        return levelDestructionHandler;
    }

    @Override
    public EventHandler<ItemDropListener> getItemDropHandler() {
        return itemDropHandler;
    }

    @Override
    public void destroy() {
        levelDestructionHandler.dispatch( new LevelDestructionEvent( this ) );
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
