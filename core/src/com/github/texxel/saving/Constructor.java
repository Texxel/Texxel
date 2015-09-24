package com.github.texxel.saving;

/**
 * A Constructor is able to read a bundle and create an Object from it. Every Constructor should
 * be registered in the {@link ConstructorRegistry}.
 * @param <T> the {@link Bundlable} object that will get created
 */
public interface Constructor<T extends Bundlable> {

    /**
     * Constructs a basic instance of the correct type. <b>DO NOT READ ANY BUNDLABLES/BUNDLES</b>
     * from {@code bundle}! Doing so will result in undefined behaviour; only primitive/String values
     * should be read from the bundle. The majority of the Bundle configuration should be done in
     * the {@link Bundlable#restore(Bundle)} method.
     * @return the created object
     */
    T newInstance( Bundle bundle );

}
