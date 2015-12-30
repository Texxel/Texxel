package com.github.texxel.actors.heroes;

import com.github.texxel.actors.Char;
import com.github.texxel.items.api.Weapon;
import com.github.texxel.sprites.api.HeroVisual;

public interface Hero extends Char {

    @Override
    HeroFOV getVision();

    @Override
    HeroVisual getVisual();

    /**
     * Gets the hero's inventory
     * @return the hero's inventory. Never null
     */
    Inventory getInventory();

    /**
     * Gets the weapon that the hero has equipped. If the hero has no weapon equipped, then the hand
     * weapon is returned.
     * @return the weapon the hero will use for melee combat. Never null
     */
    Weapon getWeapon();

    /**
     * Sets the weapon to use to fight with in melee combat. This will not spend any of the hero's
     * time.
     * @param weapon the weapon to do melee combat with
     */
    void setEquippedWeapon( Weapon weapon );

}
