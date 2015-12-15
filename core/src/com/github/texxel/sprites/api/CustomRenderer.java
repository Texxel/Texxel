package com.github.texxel.sprites.api;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * A custom renderer is for visuals that want better control over what gets drawn. By implementing
 * this interface, the rendering system will let the visual define it's own method for drawing.
 * Some custom renderers do not have a texture to return for {@link Visual#getRegion()}. Those renderers
 * should <strong>not return null</strong> but return the empty texture provided by
 * {@link EmptyTexture#instance()}
 */
public interface CustomRenderer {

    /**
     * Called when it is time for this object to be drawn. When this method is called, this object
     * will not have done the default render yet. It can be signalled that the default render
     * should happen by returning true. If false is returned, then no rendering will be done (except
     * whatever is done in this method obviously).
     * @param batch the batch to draw the object into
     * @return true to do the default render also
     */
    boolean render( Batch batch );

}
