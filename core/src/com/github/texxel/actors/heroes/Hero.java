package com.github.texxel.actors.heroes;

import com.github.texxel.actors.Char;
import com.github.texxel.items.api.Weapon;
import com.github.texxel.sprites.api.HeroVisual;

public interface Hero extends Char {

    @Override
    HeroVisual getVisual();

    /**
     * Gets the hero's inventory
     * @return the hero's inventory. Never null
     */
    Inventory getInventory();

    /**
     * Sets the weapon to use to fight with in melee combat. This will not spend any of the hero's
     * time.
     * @param weapon the weapon to do melee combat with
     */
    void setEquippedWeapon( Weapon weapon );

}
