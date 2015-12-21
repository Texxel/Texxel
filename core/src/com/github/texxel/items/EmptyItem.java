package com.github.texxel.items;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.api.EmptyTexture;
import com.github.texxel.sprites.api.Visual;

public final class EmptyItem implements Item {

    private static final long serialVersionUID = -5505657725204831439L;
    private static EmptyItem instance = new EmptyItem();
    private static ItemStack stackInstance = new ItemStack( instance, 0 );

    private transient Animation animation;
    private transient Visual visual;

    public static EmptyItem instance() {
        return instance;
    }

    public static ItemStack stackInstance() {
        return stackInstance;
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
            animation = new Animation( 1, new TextureRegion( EmptyTexture.instance() ) );
        return animation;
    }

    @Override
    public Visual getVisual() {
        return null;
    }

    @Override
    public boolean equals( Object obj ) {
        return obj == this;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public int price() {
        return 0;
    }

    private Object readResolve() {
        return instance;
    }
}
