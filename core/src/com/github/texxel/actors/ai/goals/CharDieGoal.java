package com.github.texxel.actors.ai.goals;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.actions.DieAction;

public class CharDieGoal implements Goal {

    private static final long serialVersionUID = 3518818284144639015L;

    private final Char ch;

    public CharDieGoal( Char ch ) {
        this.ch = ch;
    }

    @Override
    public void onStart() {
    }

    @Override
    public Action nextAction() {
        return new DieAction( ch );
    }

    @Override
    public void onRemove() {

    }

}
