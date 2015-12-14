package com.github.texxel.actors.ai.sensors;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.brains.MobHuntAI;
import com.github.texxel.mechanics.PathFinder;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;
import com.github.texxel.utils.Point2D;

import java.util.Set;

public class MobEnemySensor extends AbstractEnemySensor {

    static {
        ConstructorRegistry.put( MobEnemySensor.class, new Constructor<MobEnemySensor>() {
            @Override
            public MobEnemySensor newInstance( Bundle bundle ) {
                return new MobEnemySensor( bundle );
            }
        } );
    }

    public MobEnemySensor( Char mob ) {
        super( mob );
    }

    protected MobEnemySensor( Bundle bundle ) {
        super( bundle );
    }

    private void updateHuntedEnemy() {
        Set<Char> enemies = getKnownEnemies();
        if (enemies.size() > 0 ) {
            // find the closest enemy
            int minDistance = Integer.MAX_VALUE;
            Char enemy = null;
            for ( Char ch : enemies ) {
                int distance = PathFinder.gridDistance( ch.getLocation(), character.getLocation() );
                if ( distance < minDistance ) {
                    minDistance = distance;
                    enemy = ch;
                }
            }
            if ( enemy == null )
                throw new RuntimeException( "enemy should never be null" );

            // hunt the selected enemy
            character.setBrain( new MobHuntAI( character, enemy ) );
        }
        // else
        // do nothing
        // hunt ai will cancel itself
    }

    @Override
    protected void onEnemySeen( Char enemy ) {
        updateHuntedEnemy();
    }

    @Override
    protected void onEnemyGone( Char enemy, Point2D lastKnown ) {
        updateHuntedEnemy();
    }

    @Override
    protected void onEnemyDie( Char enemy ) {
        updateHuntedEnemy();
    }



}
