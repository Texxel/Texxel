package com.github.texxel.utils;

public interface Category<T> {

    /**
     * Gets the next instance to select from the category
     * @return the next item in the category
     */
    T next();

}
