package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.actions.PickUpAction;
import com.github.texxel.actors.ai.actions.SetBrainAction;
import com.github.texxel.actors.ai.goals.CharMoveGoal;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.items.Heap;
import com.github.texxel.utils.Point2D;

public class HeroPickUpAI implements Brain {

    private static final long serialVersionUID = 5123151792941768153L;

    private class Mover extends CharMoveGoal {

        private static final long serialVersionUID = -3060928595245595803L;

        public Mover( Char hero, Point2D heapLoc ) {
            super( hero, heapLoc );
        }

        @Override
        public Action onTargetReached() {
            return new PickUpAction( hero, heap );
        }

        @Override
        public Action onCannotReachTarget() {
            return new SetBrainAction( hero, new HeroIdleAI( hero ) );
        }

    }

    final Hero hero;
    final Heap heap;
    final Mover mover;

    public HeroPickUpAI( Hero hero, Heap heap, Point2D heapLocation ) {
        if ( hero == null )
            throw new NullPointerException( "'hero' cannot be null" );
        if ( heap == null )
            throw new NullPointerException( "'heap' cannot be null" );
        if ( heapLocation == null )
            throw new NullPointerException( "heapLocation cannot be null" );
        this.hero = hero;
        this.heap = heap;
        mover = new Mover( hero, heapLocation );
        hero.setGoal( mover );
    }

    @Override
    public void update() {
        // don't ever stop running
    }

    public Char hero() {
        return hero;
    }

    public Heap heap() {
        return heap;
    }

}