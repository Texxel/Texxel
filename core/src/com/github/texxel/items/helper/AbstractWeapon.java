package com.github.texxel.items.helper;

import com.github.texxel.items.api.Sellable;
import com.github.texxel.items.api.Weapon;
import com.github.texxel.mechanics.attacking.Effect;
import com.github.texxel.utils.Range;
import com.github.texxel.utils.UniformRange;

import java.util.Collections;
import java.util.List;

public abstract class AbstractWeapon extends AbstractItem implements Weapon, Sellable {
    private static final long serialVersionUID = -5741285143699499672L;

    private final int tier;
    private final float accuracy, delay;
    private final Range accRange, delayRange;

    public AbstractWeapon( int tier, float accuracy, float delay ) {
        this.tier = tier;
        this.accuracy = accuracy;
        this.delay = delay;
        accRange = new UniformRange( 0, accuracy );
        delayRange = new UniformRange( 0, delay );
    }

    @Override
    public int price() {
        return 1 << tier;
    }

    @Override
    public Range accuracy() {
        return accRange;
    }

    @Override
    public Range damage() {
        return new UniformRange( tier, (tier * tier - tier + 10) / accuracy * delay );
    }

    @Override
    public Range delay() {
        return delayRange;
    }

    @Override
    public List<Effect> effects() {
        // return enchaantment.effects();
        return Collections.emptyList();
    }

}
