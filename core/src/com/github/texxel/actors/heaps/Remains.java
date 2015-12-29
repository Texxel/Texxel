package com.github.texxel.actors.heaps;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.ItemSpriteSheet;

public class Remains implements HeapType {

    private static final long serialVersionUID = 5031486873009938076L;
    private static final Remains instance = new Remains();

    public static Remains instance() {
        return instance;
    }

    @Override
    public TextureRegion getImage( Heap heap ) {
        return ItemSpriteSheet.SKULL;
    }

    @Override
    public String name( Heap heap ) {
        return "Skeletal remains";
    }

    @Override
    public String getDescription( Heap heap ) {
        return "This is all that's left from one of your predecessors. Maybe it's worth checking for any valuables.";
    }

    private Object readResolve() {
        return instance;
    }
}
