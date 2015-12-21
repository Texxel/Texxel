package com.github.texxel.items.weapons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.ItemSpriteSheet;

public class Sword extends AbstractWeapon {

    private static final long serialVersionUID = 7190115410565231271L;
    private static final Sword instance = new Sword();
    public static Sword instance() {
        return instance;
    }

    public Sword() {
        super( 2 );
    }

    @Override
    protected TextureRegion makeTexture() {
        return ItemSpriteSheet.SHORT_SWORD;
    }

    @Override
    public String name() {
        return "Short sword";
    }

    @Override
    public String description() {
        return "A tiny little blade";
    }

    private Object readResolve() {
        return instance;
    }
}
