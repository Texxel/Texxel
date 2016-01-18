package com.github.texxel.event.listeners.actor;

import com.github.texxel.event.Listener;
import com.github.texxel.mechanics.attacking.Attack;

public interface CharAttackListener extends Listener {

    void onAttack( Attack attack );

}
