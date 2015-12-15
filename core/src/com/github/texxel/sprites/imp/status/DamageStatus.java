package com.github.texxel.sprites.imp.status;

import com.github.texxel.sprites.api.Visual;

public class DamageStatus extends TextStatus {

    public DamageStatus( Visual parent, float damage ) {
        super( parent, Integer.toString( Math.round( damage ) ) );
    }

}
