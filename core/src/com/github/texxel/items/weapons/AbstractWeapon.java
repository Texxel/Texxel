package com.github.texxel.items.weapons;

import com.github.texxel.actors.Char;
import com.github.texxel.items.AbstractItem;

public abstract class AbstractWeapon extends AbstractItem implements Weapon {
    private static final long serialVersionUID = -5741285143699499672L;

    private final int tier;

    public AbstractWeapon( int tier ) {
        this.tier = tier;
    }

    @Override
    public int price() {
        return 1 << tier;
    }

    @Override
    public float accuracy() {
        return 1;
    }

    @Override
    public float speed() {
        return 1;
    }

    @Override
    public int proc( Char attacker, Char defender, int damageRolled ) {
        return damageRolled;
    }

    @Override
    public float damageRoll() {
        return tier;
    }
}
