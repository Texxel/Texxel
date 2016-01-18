package com.github.texxel.utils;

/**
 * The MultiplicativeRange multiplies the ranges of other ranges together
 */
public class MultiplicativeRange implements Range {

    private static final long serialVersionUID = 4000615733133928002L;
    private final Range[] others;

    public MultiplicativeRange( Range ... others ) {
        int size = others.length;
        this.others = new Range[size];
        System.arraycopy( others, 0, this.others, 0, size );
    }

    @Override
    public float get() {
        float total = 1;
        for ( Range range : others )
            total *= range.get();
        return total;
    }
}
