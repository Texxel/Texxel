package com.github.texxel.actors.ai.senses;

import com.github.texxel.Dungeon;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Sensor;
import com.github.texxel.levels.Level;
import com.github.texxel.mechanics.FieldOfVision;
import com.github.texxel.utils.Point2D;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class AbstractEnemySensor implements Sensor {

    protected final Char character;
    private final HashMap<Char, Point2D> knownEnemies = new HashMap<>();
    private final Set<Char> publicEnemies = Collections.unmodifiableSet( knownEnemies.keySet() );

    public AbstractEnemySensor( Char character ) {
        this.character = character;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void update() {
        FieldOfVision vision = character.getVision();
        Level level = Dungeon.level();
        List<Char> chars = level.getCharacters();
        int size = chars.size();
        Char.Side side = character.getSide();
        for ( int i = 0; i < size; i++ ) {
            Char enemy = chars.get( i );
            if ( Char.Side.areEnemies( side, enemy.getSide() )
                    && vision.isVisible( enemy.getLocation() ) ) {
                onEnemySeen( enemy );
                knownEnemies.put( enemy, enemy.getLocation() );
            }
        }
        Iterator<Char> i = knownEnemies.keySet().iterator();
        while ( i.hasNext() ) {
            Char enemy = i.next();
            if ( enemy.isDead() ) {
                onEnemyDie( enemy );
                i.remove();
            }
            if ( vision.isVisible( enemy.getLocation() )) {
                onEnemyGone( enemy, knownEnemies.get( enemy ) );
                i.remove();
            }
        }
    }

    /**
     * Gets an unmodifiable list of all the enemies the character can see. The list will
     * automatically be updated as more/less enemies appear.
     * @return list of all the enemies.
     */
    public Set<Char> getKnownEnemies() {
        return publicEnemies;
    }

    /**
     * Called whenever an enemy enters the characters field of view
     * @param enemy the new enemy
     */
    protected abstract void onEnemySeen( Char enemy );

    /**
     * Called when an enemy disappears out of sight
     * @param enemy the enemy that left
     * @param lastKnown the last place the enemy was seen
     */
    protected abstract void onEnemyGone( Char enemy, Point2D lastKnown );

    /**
     * Called when an enemy is seen to die
     * @param enemy the enemy that was killed
     */
    protected void onEnemyDie( Char enemy ) {

    }

    @Override
    public void onRemove() {

    }
}
