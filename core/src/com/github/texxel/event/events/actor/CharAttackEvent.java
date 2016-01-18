package com.github.texxel.event.events.actor;

import com.github.texxel.event.Event;
import com.github.texxel.event.listeners.actor.CharAttackListener;
import com.github.texxel.mechanics.attacking.Attack;
import com.github.texxel.utils.Assert;

public class CharAttackEvent implements Event<CharAttackListener> {

    private final Attack attack;

    public CharAttackEvent( Attack attack ) {
        this.attack = Assert.nonnull( attack, "Cannot fire a null attack event" );
    }

    @Override
    public boolean dispatch( CharAttackListener listener ) {
        listener.onAttack( attack );
        return false;
    }
}
