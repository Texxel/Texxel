package com.github.texxel.utils;

import java.io.Serializable;

/**
 * A class used to filter out objects
 * @param <T> the thing to filter
 */
public interface Filter<T> extends Serializable {

    /**
     * Tests if the item is allowed
     * @param obj the object to test
     * @return true if the item is allowed
     */
    boolean isAllowed( T obj );

}
