package com.github.texxel.actors.ai.goals;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.actions.AttackAction;
import com.github.texxel.actors.ai.actions.ChangeGoalAction;
import com.github.texxel.actors.ai.actions.IdleAction;

import java.io.IOException;
import java.io.ObjectInputStream;

public class HeroHuntGoal extends CharMoveGoal {

    private static final long serialVersionUID = -857244165152873635L;
    private final Char enemy;
    private transient Char hero;

    public HeroHuntGoal( Char hero, Char enemy ) {
        super( hero, enemy.getLocation() );
        this.hero = hero;
        this.enemy = enemy;
    }

    @Override
    public Action nextAction() {
        // keep tracking the enemy whenever he can be seen
        if ( hero.getVision().isVisible( enemy.getLocation() ) )
            setTarget( enemy.getLocation() );
        return super.nextAction();
    }

    @Override
    public Action onTargetReached() {
        Char hero = this.hero;
        // drop everything after this turn
        hero.setGoal( new HeroIdleGoal( hero ) );

        // attack the enemy if we can see them
        if ( hero.getVision().isVisible( enemy.getLocation() ) )
            return new AttackAction( hero, enemy );
        else
            return new IdleAction( hero, 0 );
    }

    @Override
    public Action onCannotReachTarget() {
        return new ChangeGoalAction( hero, new HeroIdleGoal( hero ) );
    }

    public Char getEnemy() {
        return enemy;
    }

    private void readObject( ObjectInputStream in ) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        hero = getCharacter();
    }
}
