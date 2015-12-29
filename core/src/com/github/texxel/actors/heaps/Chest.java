package com.github.texxel.actors.heaps;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.ItemSpriteSheet;

public class Chest implements HeapType {

    private static final long serialVersionUID = 7446090083513197868L;
    private static final Chest instance = new Chest();

    public static Chest instance() {
        return instance;
    }

    @Override
    public TextureRegion getImage( Heap heap ) {
        return ItemSpriteSheet.CHEST;
    }

    @Override
    public String name( Heap heap ) {
        return "Chest";
    }

    @Override
    public String getDescription( Heap heap ) {
        return "You won't know what's inside until you open it!";
    }

    private Object readResolve() {
        return instance;
    }

}
