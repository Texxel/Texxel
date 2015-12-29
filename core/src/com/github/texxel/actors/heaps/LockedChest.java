package com.github.texxel.actors.heaps;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.ItemSpriteSheet;

public class LockedChest implements HeapType {

    private static final long serialVersionUID = 5737737000605541302L;
    private static final LockedChest instance = new LockedChest();

    public static LockedChest instance() {
        return instance;
    }

    @Override
    public TextureRegion getImage( Heap heap ) {
        return ItemSpriteSheet.LOCKED_CHEST;
    }

    @Override
    public String name( Heap heap ) {
        return "Locked chest";
    }

    @Override
    public String getDescription( Heap heap ) {
        return "You won't know what's inside until you open it! But to open it you need a golden key.";
    }

    private Object readResolve() {
        return instance;
    }
}
