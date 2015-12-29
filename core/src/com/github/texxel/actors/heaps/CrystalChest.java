package com.github.texxel.actors.heaps;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.ItemSpriteSheet;

public class CrystalChest implements HeapType {

    private static final long serialVersionUID = 9061383250255530509L;
    private static final CrystalChest instance = new CrystalChest();

    public static CrystalChest instance() {
        return instance;
    }

    @Override
    public TextureRegion getImage( Heap heap ) {
        return ItemSpriteSheet.CRYSTAL_CHEST;
    }

    @Override
    public String name( Heap heap ) {
        return "Crystal chest";
    }

    @Override
    public String getDescription( Heap heap ) {
        return "You can see <item name> inside, but to open the chest you need a golden key.";
    }

    private Object readResolve() {
        return instance;
    }
}
