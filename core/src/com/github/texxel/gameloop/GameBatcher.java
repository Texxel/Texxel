package com.github.texxel.gameloop;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.texxel.sprites.GameSprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The GameBatcher is much like a SpriteBatcher but it will not flush when the texture is switched.
 * The GameBatcher also orders the game's sprites in the correct order. All this means that a ton
 * of GameSprites can be thrown at the GameBatcher in any order and the sprites will still be batched
 * together and drawn in ascending depth.
 */
public class GameBatcher {

    public interface OptimisedDrawer {
        void onDraw( SpriteBatch batch );
    }

    // TODO determine if a GameBatcher is actually faster than a SpriteBatcher
    // game batcher is nicer to the OpenGl but makes more objects (in iteration) and stops texture
    // garbage collection by holding them in lists.

    private HashMap<Integer, HashMap<Texture, ArrayList<GameSprite>>> collections = new HashMap<>();
    private HashMap<Integer, OptimisedDrawer> optimisedDrawers = new HashMap<>();
    private final SpriteBatch batch;
    private Camera camera;

    public GameBatcher( Camera camera ) {
        this.camera = camera;
        batch = new SpriteBatch();
    }

    public void addOptimisedDrawer( int depth, OptimisedDrawer drawer ) {
        this.optimisedDrawers.put( depth, drawer );
        // make sure there is an entry at the same position
        // needed so the draw method can find the optimised drawer
        if ( !this.collections.containsKey( depth ) )
            collections.put( depth, null );
    }

    /**
     * Adds a Visual to the batch.
     * @param sprite the sprite to add
     * @throws NullPointerException if the sprite is null
     */
    public void draw( GameSprite sprite ) {
        int depth = sprite.getDepth();
        HashMap<Texture, ArrayList<GameSprite>> set = collections.get( depth );
        if ( set == null )
            collections.put( depth, set = new HashMap<>() );
        Texture texture = sprite.getTexture();
        ArrayList<GameSprite> sprites = set.get( texture );
        if ( sprites == null )
            set.put( texture, sprites = new ArrayList<>() );
        sprites.add( sprite );
    }

    /**
     * Flushes all sprites in the batch. Upon calling this method, all the actual drawing is done.
     * Failing to call this method will result in the sprites just building up and up and eventually
     * causing a stack overflow error. This method should not be called until all sprites to be
     * drawn for the frame have been added.
     */
    public void flush() {
        batch.setProjectionMatrix( camera.combined );

        batch.begin();
        for ( Map.Entry<Integer, HashMap<Texture, ArrayList<GameSprite>>> entry : collections.entrySet() ) {
            int depth = entry.getKey();
            OptimisedDrawer drawer = optimisedDrawers.get( depth );
            if ( drawer != null )
                drawer.onDraw( batch );

            HashMap<Texture, ArrayList<GameSprite>> set = entry.getValue();
            if ( set == null )
                continue;
            for ( ArrayList<GameSprite> sprites : set.values() ) {
                for ( GameSprite sprite : sprites ) {
                    sprite.draw( batch );
                }
                sprites.clear();
            }
        }
        batch.end();
    }

}
