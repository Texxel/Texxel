package com.github.texxel.sprites.imp.status;

import com.github.texxel.sprites.api.TemporaryVisual;
import com.github.texxel.sprites.api.Visual;
import com.github.texxel.sprites.imp.AbstractVisual;
import com.github.texxel.utils.ColorMaths;

public abstract class StatusVisual extends AbstractVisual implements TemporaryVisual {

    public enum Behaviour {
        /**
         * Float up slowly and eventually fade out
         */
        FLOAT,

        /**
         * Hover over the character and eventually fade out
         */
        HOVER
    }

    private static final float LIFE_TIME = 1;
    private static final float FLOAT_DISTANCE = 1;

    private float timeAlive = 0f;
    private final Behaviour behaviour;
    private final Visual parent;
    private boolean destroyed = false;

    public StatusVisual( Visual parent, Behaviour behaviour ) {
        this.parent = parent;
        this.behaviour = behaviour;
        setDepth( -500 );
        setLocation( parent.x(), parent.y() + parent.height() + height() );
    }

    @Override
    public void update( float dt ) {
        super.update( dt );

        timeAlive += dt;

        int color = getColor();
        int alpha = 256 - (int)( timeAlive / LIFE_TIME * 256 );
        alpha = Math.min( 255, alpha * 2 ); // so it stays fully visible for half it's life
        alpha = Math.max( 0, alpha ); // to ensure it doesn't go past 0

        color = ColorMaths.setAlpha( color, alpha );
        setColor( color );

        float x = parent.x();
        float y = parent.y() + parent.height() + height();

        if ( behaviour == Behaviour.FLOAT )
            y += FLOAT_DISTANCE * timeAlive / LIFE_TIME;

        setLocation( x, y );

        if ( timeAlive > LIFE_TIME )
            destroy();
    }

    @Override
    public void destroy() {
        destroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
}
