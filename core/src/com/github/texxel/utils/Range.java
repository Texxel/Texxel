package com.github.texxel.utils;

/**
 * A Range is a random number generator with a specific distribution. There are lots of different
 * distributions that a Range could have; so choose the one that suits you best. Ranges should are
 * generally immutable beings.
 */
public interface Range {

    /**
     * Generates another random number in this range
     * @return the randomly generated number
     */
    float get();

}
