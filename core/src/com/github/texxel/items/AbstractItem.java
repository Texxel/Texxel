package com.github.texxel.items;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.sprites.api.Visual;
import com.github.texxel.sprites.imp.ItemVisual;

public abstract class AbstractItem implements Item {

    private static final long serialVersionUID = -393836838722396828L;

    private transient Animation animation;
    private transient Visual visual;

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Animation getLogo() {
        if ( animation == null )
            animation = new Animation( 1, getVisual().getRegion() );
        return animation;
    }

    @Override
    public Visual getVisual() {
        if ( visual == null ) {
            TextureRegion region = makeTexture();
            if ( region == null )
                throw new NullPointerException( "region cannot be null!" );
            visual = new ItemVisual( region );
        }
        return visual;
    }

    protected abstract TextureRegion makeTexture();

    @Override
    public boolean equals( Object obj ) {
        return obj.getClass().equals( this.getClass() );
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return name();
    }
}
