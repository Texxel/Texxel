package com.github.texxel.actors.heaps;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The SimpleHeap just shows whatever is on top of the heap
 */
public class SimpleHeap implements HeapType {

    private static final long serialVersionUID = 1495410721405353117L;
    private static final SimpleHeap instance = new SimpleHeap();

    public static SimpleHeap instance() {
        return instance;
    }

    @Override
    public TextureRegion getImage( Heap heap ) {
        return heap.topItem().getImage();
    }

    @Override
    public String name( Heap heap ) {
        return heap.topItem().name();
    }

    @Override
    public String getDescription( Heap heap ) {
        return heap.topItem().description();
    }

    private Object readResolve() {
        return instance;
    }
}
