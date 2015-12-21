package com.github.texxel.items.weapons;

import com.github.texxel.actors.Char;
import com.github.texxel.items.Item;

public interface Weapon extends Item {

    float damageRoll();

    int proc( Char attacker, Char defender, int damageRolled );

    float speed();

    float accuracy();

}
