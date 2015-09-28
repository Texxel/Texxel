package com.github.texxel.actors.ai.brains;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Brain;
import com.github.texxel.actors.ai.goals.CharDieGoal;

public class CharDieAI implements Brain {

    public CharDieAI( Char ch ) {
        ch.setGoal( new CharDieGoal( ch ) );
    }

    @Override
    public void update() {

    }
}
