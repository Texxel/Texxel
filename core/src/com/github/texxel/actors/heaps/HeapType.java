package com.github.texxel.actors.heaps;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.Serializable;

public interface HeapType extends Serializable {

    /**
     * Gets what a heap should look like
     * @param heap the heap that is being displayed
     * @return the image
     */
    TextureRegion getImage( Heap heap );

    /**
     * Gets what a heap should be called
     * @param heap the heap
     * @return the heaps name
     */
    String name( Heap heap );

    /**
     * Gets the description of a heap
     * @param heap the heap
     * @return the heaps description
     */
    String getDescription( Heap heap );

}
