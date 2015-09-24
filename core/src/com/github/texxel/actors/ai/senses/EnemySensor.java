package com.github.texxel.actors.ai.senses;

import com.github.texxel.Dungeon;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Sensor;
import com.github.texxel.levels.Level;
import com.github.texxel.mechanics.FieldOfVision;

import java.util.List;

public abstract class EnemySensor implements Sensor {

    private final Char character;

    public EnemySensor( Char character ) {
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
                alert( enemy );
            }
        }
    }

    private void alert( Char enemy ) {

    }

    @Override
    public void onRemove() {

    }
}
