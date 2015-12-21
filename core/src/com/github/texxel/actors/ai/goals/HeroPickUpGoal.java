package com.github.texxel.actors.ai.goals;

import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.actions.ChangeGoalAction;
import com.github.texxel.actors.ai.actions.IdleAction;
import com.github.texxel.actors.ai.actions.PickUpAction;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.items.Heap;
import com.github.texxel.levels.Level;
import com.github.texxel.utils.Point2D;

public class HeroPickUpGoal extends CharMoveGoal {

    private static final long serialVersionUID = -2785185440664350354L;
    private final Hero hero;

    public HeroPickUpGoal( Hero hero, Point2D item ) {
        super( hero, item );
        this.hero = hero;
    }

    @Override
    public Action onTargetReached() {
        // wait after this next action
        hero.setGoal( new HeroIdleGoal( hero ) );

        Level level = hero.level();
        Heap heap = level.getHeaps().get( getTarget() );
        if ( heap != null )
            return new PickUpAction( hero, heap );
        return new IdleAction( hero, 0 );
    }

    @Override
    public Action onCannotReachTarget() {
        return new ChangeGoalAction( hero, new HeroIdleGoal( hero ) );
    }
}
