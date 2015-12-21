package com.github.texxel.actors.ai.goals;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.actions.IdleAction;
import com.github.texxel.utils.Point2D;

public class MobWanderGoal extends CharMoveGoal {

    private static final long serialVersionUID = 6452631135492255587L;

    /**
     * Wander around aimlessly
     * @param mob the mob to wander
     * @param target the first place to wander to
     */
    public MobWanderGoal( Char mob, Point2D target ) {
        super( mob, target );
    }

    /**
     * Same as {@link #MobWanderGoal(Char, Point2D)} but with a random starting point
     * @param mob the mob to wander
     */
    public MobWanderGoal( Char mob ) {
        // then the next update will get a random location
        this( mob, mob.getLocation() );
    }

    @Override
    public Action onTargetReached() {
        Char mob = getCharacter();
        setTarget( mob.level().randomRespawnCell() );
        return new IdleAction( mob, 1 );
    }

    @Override
    public Action onCannotReachTarget() {
        Char mob = getCharacter();
        setTarget( mob.level().randomRespawnCell() );
        return new IdleAction( mob, 1 );
    }
}
