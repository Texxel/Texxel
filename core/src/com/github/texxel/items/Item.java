package com.github.texxel.items;

import com.github.texxel.sprites.api.WorldVisual;
import com.github.texxel.ui.Examinable;

public interface Item extends WorldVisual, Examinable {

    boolean isEmpty();

    int getQuantity();

    void setQuantity( int amount );

    boolean isUnique();

    boolean canStackWith( Item item );

    boolean perform( Action action );

    // TODO burning and freezing (and any custom event I want?) of items

}
