package com.github.texxel.actors.ai.goals;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.actions.AttackAction;
import com.github.texxel.actors.ai.actions.ChangeGoalAction;

public class MobHuntGoal extends CharMoveGoal {

    private static final long serialVersionUID = -2044328733518735077L;

    private final Char hero;

    public MobHuntGoal( Char mob, Char hero ) {
        super( mob, hero.getLocation() );
        this.hero = hero;
    }

    @Override
    public Action nextAction() {
        Char mob = getMob();
        Char hero = this.hero;

        // change target to the hero if he can be seen. Otherwise, keep going to the last place
        // he was seen
        if ( mob.getVision().isVisible( hero.getLocation() ) )
            setTarget( hero.getLocation() );
        return super.nextAction();
    }

    @Override
    public Action onTargetReached() {
        Char mob = getMob();
        if ( mob.getVision().isVisible( hero.getLocation() ) )
            return new AttackAction( mob, hero );
        else
            return new ChangeGoalAction( mob, new MobWanderGoal( mob ) );
    }

    @Override
    public Action onCannotReachTarget() {
        Char mob = getMob();
        // maybe this should become a flee action?
        return new ChangeGoalAction( mob, new MobWanderGoal( mob ) );
    }

    /**
     * The same as {@link #getCharacter()} but better named
     * @return the mob that is hunting
     */
    public Char getMob() {
        return getCharacter();
    }

    /**
     * Gets the hero that is being hunted down
     * @return the hunted hero
     */
    public Char getHero() {
        return hero;
    }
}
