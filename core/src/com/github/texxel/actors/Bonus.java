package com.github.texxel.actors;

import com.github.texxel.utils.Range;

import java.io.Serializable;

public interface Bonus extends Serializable {

    int INITIAL = 0, MULTIPLY = 10, ADD = 20, FINAL = 30;

    int priority();

    Range alter( Range value );

}
