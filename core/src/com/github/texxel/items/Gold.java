package com.github.texxel.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.items.api.Sellable;
import com.github.texxel.items.helper.AbstractStackableItem;
import com.github.texxel.sprites.ItemSpriteSheet;

public class Gold extends AbstractStackableItem implements Sellable {

    private static final long serialVersionUID = 657297712584810880L;

    public Gold( int quantity ) {
        super( quantity );
    }

    public Gold() {
        super();
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
    public TextureRegion getImage() {
        return ItemSpriteSheet.GOLD;
    }

}
