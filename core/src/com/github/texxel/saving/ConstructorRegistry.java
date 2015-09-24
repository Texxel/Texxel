package com.github.texxel.saving;

import java.util.HashMap;

/**
 * The ConstructorRegistry is so all Constructors can register themselves with
 * the class they know how to restore. If a Constructor is not registered here,
 * then it will not be possible for the Bundle class to restores the class
 * automatically.
 */
public final class ConstructorRegistry {

    private static HashMap<Class<? extends Bundlable>, Constructor<? extends Bundlable>> matches = new HashMap<>();

    /**
     * Gets the constructor to restore the requested class
     * @param clazz the class to restore
     * @param <T> the Bundlable that will be restored.
     * @return the Constructor for the class
     */
    @SuppressWarnings( "unchecked" )
    public static <T extends Bundlable> Constructor<T> get( Class<T> clazz ) {
        return (Constructor<T>)matches.get( clazz );
    }

    /**
     * Sets the Constructor that will restore the Bundlable class
     * @param clazz the class to restore
     * @param constructor the Constructor that can restore the class
     * @param <T> the Bundlable class that will be restored
     */
    public static <T extends Bundlable> void put( Class<T> clazz, Constructor<T> constructor ) {
        matches.put( clazz, constructor );
    }

    private ConstructorRegistry() {
        // only static methods
    }

}
