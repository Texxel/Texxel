package com.github.texxel.actors.mobs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.actors.AbstractChar;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.goals.MobWanderGoal;
import com.github.texxel.actors.ai.sensors.MobEnemySensor;
import com.github.texxel.items.api.Weapon;
import com.github.texxel.levels.Level;
import com.github.texxel.mechanics.attacking.Effect;
import com.github.texxel.sprites.api.EmptyTexture;
import com.github.texxel.utils.ConstantRange;
import com.github.texxel.utils.Point2D;
import com.github.texxel.utils.Range;

import java.util.Collections;
import java.util.List;

public abstract class AbstractMob extends AbstractChar implements Mob {

    private static final long serialVersionUID = 2042000201307302808L;

    public AbstractMob( Level level, Point2D spawn, float health ) {
        super( level, spawn, health );
        addSensor( new MobEnemySensor( this ) );
    }

    @Override
    public Weapon weapon() {
        return new Weapon() {
            @Override
            public Range delay() {
                return ConstantRange.ONE;
            }

            @Override
            public Range accuracy() {
                return getAttribute( "accuracy" ).value();
            }

            @Override
            public Range damage() {
                return getAttribute( "damage" ).value();
            }

            @Override
            public List<Effect> effects() {
                return Collections.emptyList();
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public TextureRegion getImage() {
                return EmptyTexture.instance();
            }

            @Override
            public int getSortPriority() {
                return 500;
            }

            @Override
            public String name() {
                return "Hand";
            }

            @Override
            public String description() {
                return "The hand";
            }

            @Override
            public Animation getLogo() {
                return new Animation( 1, getImage() );
            }
        };
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
