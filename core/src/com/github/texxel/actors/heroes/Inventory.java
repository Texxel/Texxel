package com.github.texxel.actors.heroes;

import com.github.texxel.items.bags.SomeSlots;

import java.io.Serializable;

public class Inventory implements Serializable {

    private static final long serialVersionUID = 7755164159646722527L;

    private SomeSlots equipped = new SomeSlots( 4 );
    private SomeSlots backPack = new SomeSlots( 20 );

    public SomeSlots getEquippedSlots() {
        return equipped;
    }

    public SomeSlots getBackPack() {
        return backPack;
    }

}
