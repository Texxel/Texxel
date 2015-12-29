package com.github.texxel.items.helper;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.texxel.items.api.Item;

public abstract class AbstractItem implements Item {

    private static final long serialVersionUID = 1001589078964265160L;
    private transient Animation animation;

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int getSortPriority() {
        return 0;
    }

    @Override
    public Animation getLogo() {
        if ( animation == null )
            animation = new Animation( 1, getImage() );
        return animation;
    }

    @Override
    public String toString() {
        return name();
    }
}
