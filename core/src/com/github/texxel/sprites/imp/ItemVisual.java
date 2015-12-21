package com.github.texxel.sprites.imp;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemVisual extends AbstractVisual {

    private TextureRegion region;

    public ItemVisual( TextureRegion region ) {
        if ( region == null )
            throw new NullPointerException( "'region' cannot be null" );
        this.region = region;
    }

    public void setRegion( TextureRegion region ) {
        if ( region == null )
            throw new NullPointerException( "'region' cannot be null" );
        this.region = region;
    }


    @Override
    public TextureRegion getRegion() {
        return region;
    }
}
