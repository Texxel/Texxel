package com.github.texxel.actors.ai.sensors;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.goals.MobHuntGoal;
import com.github.texxel.mechanics.PathFinder;
import com.github.texxel.utils.Point2D;

import java.util.Set;

public class MobEnemySensor extends AbstractEnemySensor {

    private static final long serialVersionUID = -8424558011723944818L;

    public MobEnemySensor( Char mob ) {
        super( mob );
    }

    private void updateHuntedEnemy() {
        Set<Char> enemies = getKnownEnemies();
        if (enemies.size() > 0 ) {
            // find the closest enemy
            int minDistance = Integer.MAX_VALUE;
            // enemy is the enemy to the enemy: i.e. the hero
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
            character.setGoal( new MobHuntGoal( character, enemy ) );
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
