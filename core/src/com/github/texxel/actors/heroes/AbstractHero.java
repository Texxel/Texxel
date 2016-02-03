package com.github.texxel.actors.heroes;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.actors.AbstractChar;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.goals.HeroIdleGoal;
import com.github.texxel.actors.ai.sensors.HeroDamageSensor;
import com.github.texxel.actors.ai.sensors.HeroDangerSensor;
import com.github.texxel.items.ItemUtils;
import com.github.texxel.items.api.Item;
import com.github.texxel.items.api.Weapon;
import com.github.texxel.items.helper.AbstractWeapon;
import com.github.texxel.levels.Level;
import com.github.texxel.mechanics.*;
import com.github.texxel.sprites.api.EmptyTexture;
import com.github.texxel.utils.Point2D;

public abstract class AbstractHero extends AbstractChar implements Hero {

    private static final long serialVersionUID = -1893036218210928887L;

    private final Inventory inventory;

    public AbstractHero( Level level, Point2D spawn ) {
        super( level, spawn, 100 );
        addSensor( new HeroDangerSensor( this ) );
        addSensor( new HeroDamageSensor( this ) );

        inventory = new Inventory();
        inventory.getEquippedSlots().getSlot( 0 ).setFilter( ItemUtils.weaponFilter() );
    }

    @Override
    protected Goal defaultGoal() {
        return new HeroIdleGoal( this );
    }

    @Override
    protected FieldOfVision makeFOV() {
        return new ComplexFOV( level().getTileMap().getLosBlocking(), getLocation() );
    }

    @Override
    public Side getSide() {
        return Side.GOOD;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public Weapon weapon() {
        Item item = inventory.getEquippedSlots().get( 0 );
        if ( item instanceof Weapon )
            return (Weapon)item;
        else
            return handWeapon();
    }

    public Weapon handWeapon() {
        return new AbstractWeapon( 1, 1, 1 ) {
            private static final long serialVersionUID = -2856833038145940547L;

            @Override
            public TextureRegion getImage() {
                return EmptyTexture.instance();
            }

            @Override
            public String name() {
                return "Hand";
            }

            @Override
            public String description() {
                return "Your hand";
            }
        };
    }

    @Override
    public void setEquippedWeapon( Weapon weapon ) {
        inventory.getEquippedSlots().set( 0, weapon );
    }
}
