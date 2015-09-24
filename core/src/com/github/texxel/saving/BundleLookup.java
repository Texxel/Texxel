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
     * Loads all the Bundlables from a "lookup bundle".
     * @throws IllegalTypeException if the bundle does not have only integers as keys
     * @throws IllegalTypeException if the bundle contains a value that is not a Bundlable
     * @throws RuntimeException if there was an issue loading a class
     * @throws IllegalStateException if no constructor was registered for class
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
            String className = bundle.getString( "classname" );
            Class<Bundlable> clazz = null;
            try {
                Class<?> classRaw = Class.forName( className );
                clazz = (Class<Bundlable>) classRaw;
            } catch ( ClassNotFoundException e ) {
                System.err.println( "Couldn't find class: " + className + " in bundle lookup. Ignoring that bundlable" );
            } catch ( ClassCastException e ) {
                throw new IllegalTypeException( "Classes in a bundle lookup must extend from Bundlable. Found: " + className );
            } catch ( Exception e ) {
                throw new RuntimeException( "Couldn't load class: " + className, e );
            }
            Constructor<Bundlable> constructor = ConstructorRegistry.get( clazz );
            if ( constructor == null )
                throw new IllegalStateException( "No constructor registered for class: " + className );
            Bundlable object = constructor.newInstance(bundle);
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
            bundle.put( "classname", object.getClass().getName() );
            save.put( Integer.toString( id ), bundle );
        }
    }

}
