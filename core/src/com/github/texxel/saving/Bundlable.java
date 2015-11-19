package com.github.texxel.saving;

/**
 * A Bundlable class knows how to wrap itself up into a Bundle which can then be
 * saved. Opposite to the Bundlable interface is the {@link Constructor}
 * interface. Most Bundlable class will want to have a registered constructor
 * class to restore them.
 */
public interface Bundlable {

    /**
     * Wraps up all this classes data into a single Bundle. The Bundle can then be stored into
     * another bundle so long as both bundles were created with the same BundleGroup. This method
     * does not store itself into the passed bundle - it mealy uses the passed bundle to generate
     * another bundle (which is then returned).
     * @param topLevel the top level bundle that the returned bundle should use
     * @return the Bundle that contains this objects data.
     */
    Bundle bundle( BundleGroup topLevel  );

    /**
     * Reads out this Bundles data from the given Bundle. This method will automatically be called
     * when the a bundle is loaded. When this method is called, it is ensured that all bundlables
     * are constructed, but it is <strong>not</strong> ensured that all bundlables are initialised.
     * @param bundle the bundle to read data from
     * @throws MissingInfoException if the Bundle is missing some info
     * @throws IllegalTypeException if the Bundle has a value of the incorrect type
     */
    void restore( Bundle bundle );

}
