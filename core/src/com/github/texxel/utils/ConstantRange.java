package com.github.texxel.utils;

/**
 * The ConstantRange class always returns the same number
 */
public class ConstantRange implements Range {

    private final float number;

    public ConstantRange( float number ) {
        this.number = number;
    }

    @Override
    public float get() {
        return number;
    }
}
