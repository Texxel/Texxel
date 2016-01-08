package com.github.texxel.actors.mobs;

import com.github.texxel.actors.AbstractChar;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.goals.MobWanderGoal;
import com.github.texxel.actors.ai.sensors.MobEnemySensor;
import com.github.texxel.levels.Level;
import com.github.texxel.utils.Point2D;

public abstract class AbstractMob extends AbstractChar implements Mob {

    private static final long serialVersionUID = 2042000201307302808L;

    public AbstractMob( Level level, Point2D spawn, float health ) {
        super( level, spawn, health );
        addSensor( new MobEnemySensor( this ) );
    }

    @Override
    protected Goal defaultGoal() {
        return new MobWanderGoal( this );
    }

    @Override
    public Side getSide() {
        return Side.EVIL;
    }
}
