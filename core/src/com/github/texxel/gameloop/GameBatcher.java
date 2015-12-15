package com.github.texxel.gameloop;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.NumberUtils;
import com.github.texxel.sprites.api.CustomRenderer;
import com.github.texxel.sprites.api.GroupVisual;
import com.github.texxel.sprites.api.Visual;
import com.github.texxel.utils.GameTimer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * The GameBatcher is much like a SpriteBatcher but it will not flush when the texture is switched.
 * The GameBatcher also orders the game's sprites in the correct order. All this means that a ton
 * of GameSprites can be thrown at the GameBatcher in any order and the sprites will still be batched
 * together and drawn in ascending depth.
 */
public class GameBatcher {

    private TreeMap<Integer, HashMap<Texture, ArrayList<Visual>>> collections = new TreeMap<>();
    public final SpriteBatch batch;
    private Camera camera;

    public GameBatcher( Camera camera ) {
        this.camera = camera;
        batch = new SpriteBatch();
    }

    /**
     * Adds a Visual to the batch.
     * @param visual the sprite to add
     * @throws NullPointerException if the sprite is null
     */
    public void draw( Visual visual ) {
        int depth = visual.depth();
        HashMap<Texture, ArrayList<Visual>> set = collections.get( depth );
        if ( set == null )
            collections.put( depth, set = new HashMap<>() );
        Texture texture = visual.getRegion().getTexture();
        ArrayList<Visual> sprites = set.get( texture );
        if ( sprites == null )
            set.put( texture, sprites = new ArrayList<>() );
        sprites.add( visual );

        if ( visual instanceof GroupVisual ) {
            for ( Visual child : ( (GroupVisual) visual ).attachedVisuals() ) {
                draw( child );
            }
        }
    }

    /**
     * Flushes all sprites in the batch. Upon calling this method, all the actual drawing is done.
     * Failing to call this method will result in the sprites just building up and up and eventually
     * causing a stack overflow error. This method should not be called until all sprites to be
     * drawn for the frame have been added.
     */
    public void flush() {
        Batch batch = this.batch;
        float dt = GameTimer.tickTime();

        batch.setProjectionMatrix( camera.combined );

        batch.begin();
        for ( HashMap<Texture, ArrayList<Visual>> set : collections.descendingMap().values() ) {
            if ( set == null )
                continue;
            for ( ArrayList<Visual> sprites : set.values() ) {
                for ( Visual sprite : sprites ) {
                    actuallyDraw( sprite, batch, dt );
                }
                sprites.clear();
            }
        }
        batch.end();
    }

    /**
     * Actually draws the sprite to the batch
     * @param visual the visual to draw
     */
    private void actuallyDraw( Visual visual, Batch batch, float dt ) {
        visual.update( dt );

        if ( visual instanceof CustomRenderer ) {
            // visual can do it's own rendering
            if ( ( (CustomRenderer) visual ).render( batch ) )
                doDefaultDraw( visual, batch );
        } else {
            // do a basic drawing for the visual
            doDefaultDraw( visual, batch );
        }
    }

    private void doDefaultDraw( Visual visual, Batch batch ) {
        batch.setColor( NumberUtils.intToFloatColor( visual.getColor() ) );
        batch.draw( visual.getRegion(),
                visual.x()+visual.xOffset(), visual.y()+visual.yOffset(),
                visual.width()/2, visual.height()/2,
                visual.width(), visual.height(),
                1, 1,
                visual.getRotation() );
    }

}
