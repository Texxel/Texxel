package com.github.texxel.actors.mobs;

import com.github.texxel.Dungeon;
import com.github.texxel.actors.AbstractChar;
import com.github.texxel.actors.ai.brains.MobWanderAI;
import com.github.texxel.actors.ai.senses.MobEnemySensor;
import com.github.texxel.saving.Bundle;
import com.github.texxel.utils.Point2D;

public abstract class AbstractMob extends AbstractChar implements Mob {

    protected AbstractMob( Bundle bundle ) {
        super( bundle );
    }

    public AbstractMob( Point2D spawn, float health ) {
        super( spawn, health );
        setBrain( new MobWanderAI( this, Dungeon.level().randomRespawnCell() ) );
        addSensor( new MobEnemySensor( this ) );
    }

    @Override
    public Side getSide() {
        return Side.EVIL;
    }
}
