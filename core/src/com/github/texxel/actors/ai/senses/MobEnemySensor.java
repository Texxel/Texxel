package com.github.texxel.actors.ai.senses;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.brains.MobHuntAI;
import com.github.texxel.mechanics.PathFinder;
import com.github.texxel.utils.Point2D;

import java.util.Set;

public class MobEnemySensor extends AbstractEnemySensor {

    public MobEnemySensor( Char mob ) {
        super( mob );
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
