package com.github.texxel.items.api;

import com.github.texxel.mechanics.attacking.Effect;
import com.github.texxel.utils.Range;

import java.util.List;

public interface Weapon extends Item {

    Range delay();

    Range accuracy();

    /**
     * Gets the damage that this weapon does
     * @return the range of the damage
     */
    Range damage();

    /**
     * Gets a list of the effects for this weapon
     * @return the effects that this weapon has
     */
    List<Effect> effects();

}
