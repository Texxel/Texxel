package com.github.texxel.sprites.imp.mobvisuals;

import com.github.texxel.sprites.api.MobVisual;
import com.github.texxel.sprites.imp.AbstractCharVisual;

public abstract class AbstractMobVisual extends AbstractCharVisual implements MobVisual {

    public AbstractMobVisual() {
        super();
    }

    public AbstractMobVisual( int pixelWidth, int pixelHeight ) {
        super( pixelWidth, pixelHeight );
    }
}
