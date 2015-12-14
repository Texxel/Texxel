package com.github.texxel.actors.ai.sensors;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.brains.HeroIdleAI;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.utils.Point2D;

/**
 * The HeroDangerSensor will stop the hero whenever an enemy approaches by setting the hero's brain
 * to HeroIdleAI. This sensor will work for any character but it doesn't make sense to use it for
 * anything other than the hero.
 */
public class HeroDangerSensor extends AbstractEnemySensor {

    static {
        ConstructorRegistry.put( HeroDangerSensor.class, new Constructor<HeroDangerSensor>() {
            @Override
            public HeroDangerSensor newInstance( Bundle bundle ) {
                return new HeroDangerSensor( bundle );
            }
        } );
    }

    public HeroDangerSensor( Char character ) {
        super( character );
    }

    protected HeroDangerSensor( Bundle bundle ) {
        super( bundle );
    }

    @Override
    protected void onEnemySeen( Char enemy ) {
        character.setBrain( new HeroIdleAI( character ) );
    }

    @Override
    protected void onEnemyGone( Char enemy, Point2D lastKnown ) {
    }
}
