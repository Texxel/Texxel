package com.github.texxel.actors.mobs;

import com.github.texxel.actors.AbstractChar;
import com.github.texxel.actors.ai.state.CharWanderState;
import com.github.texxel.saving.Bundle;
import com.github.texxel.utils.Point2D;

public abstract class AbstractMob extends AbstractChar implements Mob {

    protected AbstractMob( Bundle bundle ) {
        super( bundle );
    }

    public AbstractMob( Point2D spawn ) {
        super( spawn );
        setState( new CharWanderState( this ) );
    }


}
