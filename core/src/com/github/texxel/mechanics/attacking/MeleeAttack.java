package com.github.texxel.mechanics.attacking;

import com.github.texxel.actors.Char;
import com.github.texxel.items.api.Weapon;
import com.github.texxel.utils.MultiplicativeRange;
import com.github.texxel.utils.Range;

import java.util.ArrayList;
import java.util.List;

public class MeleeAttack implements Attack {

    private Char ch;
    private Weapon weapon;
    private Range accuracy;
    private Range delay;
    private Range damage;

    private List<Effect> effects = new ArrayList<>();

    public MeleeAttack( Char ch, Weapon weapon ) {
        this.ch = ch;
        this.weapon = weapon;
        this.damage = weapon.damage();
        this.accuracy = new MultiplicativeRange( weapon.accuracy(), ch.getAttribute( "accuracy" ).value() );
        this.delay = weapon.delay();
        effects.addAll( weapon.effects() );
    }

    @Override
    public Char attacker() {
        return ch;
    }

    @Override
    public Range accuracy() {
        return accuracy;
    }

    @Override
    public Range delay() {
        return delay;
    }

    @Override
    public List<Effect> effects() {
        return effects;
    }

    @Override
    public Range damage() {
        return damage;
    }

}