package com.github.texxel.items;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.items.api.Item;
import com.github.texxel.sprites.api.EmptyTexture;

public final class EmptyItem implements Item {

    private static final long serialVersionUID = -5505657725204831439L;
    private static EmptyItem instance = new EmptyItem();

    private transient Animation animation;

    public static EmptyItem instance() {
        return instance;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }


    @Override
    public String name() {
        return "missingno";
    }

    @Override
    public String description() {
        return "I don't exist. Why are you looking at me!";
    }

    @Override
    public Animation getLogo() {
        if ( animation == null )
            animation = new Animation( 1, getImage() );
        return animation;
    }

    @Override
    public TextureRegion getImage() {
        return EmptyTexture.instance();
    }

    @Override
    public int getSortPriority() {
        return -100;
    }

    @Override
    public boolean equals( Object obj ) {
        return obj == this;
    }

    @Override
    public String toString() {
        return "Empty";
    }

    @Override
    public int hashCode() {
        return 0;
    }

    private Object readResolve() {
        return instance;
    }
}
