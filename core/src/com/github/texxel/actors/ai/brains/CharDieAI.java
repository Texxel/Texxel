package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.goals.CharDieGoal;

public class CharDieAI implements Brain {

    private static final long serialVersionUID = 2148873903130694539L;

    public CharDieAI( Char ch ) {
        ch.setGoal( new CharDieGoal( ch ) );
    }

    @Override
    public void update() {

    }

}
