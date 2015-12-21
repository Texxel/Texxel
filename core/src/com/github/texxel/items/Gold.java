package com.github.texxel.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.ItemSpriteSheet;

public class Gold extends AbstractItem {

    private static final long serialVersionUID = 657297712584810880L;
    private static Gold instance = new Gold();

    public static Gold instance() {
        return instance;
    }

    @Override
    public int price() {
        return 1;
    }

    @Override
    public String name() {
        return "Gold";
    }

    @Override
    public String description() {
        return "So shiny";
    }

    @Override
    protected TextureRegion makeTexture() {
        return ItemSpriteSheet.GOLD;
    }

    private Object readResolve() {
        return instance;
    }

}
