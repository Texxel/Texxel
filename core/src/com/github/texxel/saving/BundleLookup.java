package com.github.texxel.saving;

import java.util.HashMap;
import java.util.Map;

/**
 * The BundleLookup class is the magic behind storing Bundlable references.
 */
final class BundleLookup {

    private final HashMap<Integer, Bundlable> bundlables = new HashMap<>();
    private final HashMap<Integer, Bundlable> bundlablesBuffer = new HashMap<>();

    /**
     * Loads all the Bundlables from a "lookup bundle". If a bundlable is found that has a non existent
     * class, then that bundlable will get ignored.
     * @throws IllegalTypeException if the bundle does not have only integers as keys
     * @throws IllegalTypeException if the bundle contains a value that is not a Bundlable
     * @throws UnregisteredConstructorException if the class has not registered a constructor
     * @throws NullPointerException if the constructor returned a null instance
     */
    @SuppressWarnings( "unchecked" )
    public void restore( Bundle data ) {
        for ( String idString : data.keys() ) {
            int id;
            try {
                id = Integer.parseInt( idString, 10 );
            } catch ( NumberFormatException e ) {
                throw new IllegalTypeException( "Bundle lookups must only have integers as keys" );
            }
            Bundle bundle = data.getBundle( idString );
            String className = bundle.getClassName();
            Class<Bundlable> clazz;
            try {
                Class<?> classRaw = Class.forName( className );
                clazz = (Class<Bundlable>) classRaw;
            } catch ( ClassNotFoundException e ) {
                System.err.println( "Couldn't find class: " + className + " in bundle lookup. Ignoring that bundlable" );
                continue;
            } catch ( ClassCastException e ) {
                throw new IllegalTypeException( "Classes in a bundle lookup must extend from Bundlable. Found: " + className );
            }
            Constructor<Bundlable> constructor = ConstructorRegistry.get( clazz );
            if ( constructor == null )
                throw new UnregisteredConstructorException( "No constructor registered for class: " + className );
            Bundlable object = constructor.newInstance( bundle );
            if ( object == null )
                throw new NullPointerException( "Constructor returned a null instance. Constructor of class: " + className );
            bundlables.put( id, object );
        }
        for ( Map.Entry<Integer, Bundlable> entry : bundlables.entrySet() ) {
            int id = entry.getKey();
            Bundlable b = entry.getValue();
            Bundle bundle = data.getBundle( Integer.toString( id ) );
            b.restore( bundle );
        }
    }

    public int add( Bundlable object ) {
        int id = System.identityHashCode( object );
        if ( bundlables.containsKey( id ) )
            return id;
        if ( object != null )
            if ( ConstructorRegistry.get( object.getClass() ) == null )
                throw new UnregisteredConstructorException( "Bundlable " + object.getClass()
                        + " is missing a registered constructor" );
        bundlablesBuffer.put( id, object );
        return id;
    }

    public Bundlable get( int id ) {
        Bundlable bundlable = bundlables.get( id );
        if ( bundlable != null )
            return bundlable;
        // try again in case it's just in the buffer
        return bundlablesBuffer.get( id );
    }

    public Bundle bundle( BundleGroup bundleGroup ) {
        Bundle data = bundleGroup.newBundle();
        add( bundlables, data, bundleGroup );
        while( !bundlablesBuffer.isEmpty() ) {
            HashMap<Integer, Bundlable> buffer2 = new HashMap<>( bundlablesBuffer );
            bundlables.putAll( bundlablesBuffer );
            bundlablesBuffer.clear();
            add( buffer2, data, bundleGroup );
        }
        return data;
    }

    private void add( HashMap<Integer, Bundlable> bundlables, Bundle save, BundleGroup bundleGroup ) {
        for ( Map.Entry<Integer, Bundlable> entry : bundlables.entrySet() ) {
            int id = entry.getKey();
            Bundlable object = entry.getValue();
            if ( object == null )
                continue;
            Bundle bundle = object.bundle( bundleGroup );
            if ( bundle == null )
                throw new NullPointerException( "Bundlables must always return a bundle when saving. Class: '" + object.getClass() + "'. Object: " + object.toString()  );
            bundle.putClassName( object.getClass().getName() );
            save.putBundle( Integer.toString( id ), bundle );
        }
    }

}
