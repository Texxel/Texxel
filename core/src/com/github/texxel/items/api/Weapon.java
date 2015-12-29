package com.github.texxel.items.api;

import com.github.texxel.actors.Char;

public interface Weapon extends Item {

    float damageRoll( Char attacker, Char defender );

    int proc( Char attacker, Char defender, int damageRolled );

    float speed( Char attacker );

    float accuracy( Char attacker );

}
