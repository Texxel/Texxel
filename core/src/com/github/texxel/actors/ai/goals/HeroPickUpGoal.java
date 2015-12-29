package com.github.texxel.actors.ai.goals;

import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.actions.ChangeGoalAction;
import com.github.texxel.actors.ai.actions.PickUpAction;
import com.github.texxel.actors.heaps.Heap;
import com.github.texxel.actors.heroes.Hero;

public class HeroPickUpGoal extends CharMoveGoal {

    private static final long serialVersionUID = -2785185440664350354L;
    private final Hero hero;
    private final Heap heap;

    public HeroPickUpGoal( Hero hero, Heap heap ) {
        super( hero, heap.getLocation() );
        this.heap = heap;
        this.hero = hero;
    }

    @Override
    public Action onTargetReached() {
        // wait after this next action
        hero.setGoal( new HeroIdleGoal( hero ) );

        return new PickUpAction( hero, heap );
    }

    @Override
    public Action onCannotReachTarget() {
        return new ChangeGoalAction( hero, new HeroIdleGoal( hero ) );
    }

    @Override
    public Action nextAction() {
        // stop if the heap ever gets removed
        if ( heap.isEmpty() || !hero.getVision().isVisible( heap.getLocation() ) )
            return new ChangeGoalAction( hero, new HeroIdleGoal( hero ) );

        setTarget( heap.getLocation() );
        return super.nextAction();
    }
}
