package com.github.texxel.actors;

import com.github.texxel.utils.Range;

public interface Bonus {

    int INITIAL = 0, MULTIPLY = 10, ADD = 20, FINAL = 30;

    int priority();

    Range alter( Range value );

}
