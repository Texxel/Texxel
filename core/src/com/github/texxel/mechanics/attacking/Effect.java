package com.github.texxel.mechanics.attacking;

import com.github.texxel.actors.Char;

public interface Effect {

    /**
     * Does something based on how much an enemy will be damaged. This can modify how much damage was
     * dealt or it can do some other thing such as applying a burning buff. Note that this method is
     * called after damage reductions such as armor are applied to the damage
     * @param damage the damage that will be dealt
     * @param attacker the Char attacking
     * @param defender the Char defending
     * @return the damage to actually deal
     */
    float proc( float damage, Char attacker, Char defender );

}
