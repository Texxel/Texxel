package com.github.texxel.actors.heaps;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.ItemSpriteSheet;

public class Tomb implements HeapType {

    private static final long serialVersionUID = -276451642096028656L;
    private static final Tomb instance = new Tomb();

    public static Tomb instance() {
        return instance;
    }

    @Override
    public TextureRegion getImage( Heap heap ) {
        return ItemSpriteSheet.TOMB;
    }

    @Override
    public String name( Heap heap ) {
        return "Tomb";
    }

    @Override
    public String getDescription( Heap heap ) {
        return "This ancient tomb may contain something useful, but its owner will most certainly object to checking.";
    }

    private Object readResolve() {
        return instance;
    }
}
