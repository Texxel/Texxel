package com.github.texxel.saving;

import com.github.texxel.Version;
import com.github.texxel.json.JSONArray;
import com.github.texxel.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A Bundle is a method of saving an object. At a basic level, a Bundle is just
 * a String-Object map. However, the Bundle class is intelligent enough to
 * store {@link Bundlable} objects. References to the same Bundlable instance
 * will return the same Bundlable reference after reloading. The restoring of a
 * Bundlable requires that an appropriate {@link Constructor} has been added to
 * the {@link ConstructorRegistry}.<br/>
 * <br/>
 * All Bundles have a top level Bundle called a {@link BundleGroup}. Only Bundles
 * with the same top level Bundle can be stored together.<br/>
 * <br/>
 * Bundles which have been restored cannot be edited. Attempting to edit a
 * restored Bundle will result in an {@link IllegalStateException}.
 */
public class Bundle {

    final JSONObject data;
    final BundleLookup lookup;
    final boolean editable;
    final String versionRaw;
    final Version version;

    Bundle( JSONObject data, BundleLookup lookup, boolean editable, String versionRaw ) {
        this.data = data;
        this.lookup = lookup;
        this.editable = editable;
        this.versionRaw = versionRaw;
        this.version = Version.fromString( versionRaw );
    }

    /**
     * Tests if the bundle contains a value
     * @param key the key to test for
     * @return true if the bundle contains the key
     * @throws NullPointerException if key is null
     */
    public boolean contains( String key ) {
        validateKey( key );
        return data.has( key );
    }

    /**
     * Removes a key from the mapping
     * @param key the key to remove
     * @throws NullPointerException if {@code key} is null
     */
    public void remove( String key ) {
        validateKey( key );
        data.remove( key );
    }

    /**
     * Gets the version that the Bundle was saved at
     * @return the Bundle's save version
     */
    public Version getVersion() {
        return version;
    }

    /**
     * Gets the String representation of the version that the Bundle was saved at.
     * This string will be the same as {@code getVersion().toString()} in all
     * cases except for unknown versions where is will return the string that
     * caused the unknown version.
     * @return the version string
     */
    public String getVersionRaw() {
        return versionRaw;
    }

    /**
     * Gets a clone of all the keys in the bundle
     * @return the bundle's keys
     */
    public Set<String> keys() {
        HashSet<String> keys = new HashSet<>( data.keySet() );
        // remove reserved keys from the list
        keys.remove( "__classname__" );
        keys.remove( "__version__" );
        keys.remove( "__lookuptable__" );
        return keys;
    }

    private void validateKey( String key ) {
        if ( key == null )
            throw new NullPointerException( "key cannot be null" );

        // don't let users see/edit reserved keys
        if ( key.equals( "__classname__" ) )
            throw new IllegalArgumentException( "The key '__classname__' is for internal use only" );
        if ( key.equals( "__version__" ) )
            throw new IllegalArgumentException( "The key '__version__' is for internal use only" );
        if ( key.equals( "__lookuptable__" ) )
            throw new IllegalArgumentException( "The key '__lookuptable__' is for internal use only" );
    }

    /**
     * For internal use only - sets the class that this bundle is representing
     * @param name the classes name
     */
    void putClassName( String name ) {
        data.put( "__classname__", name );
    }

    /**
     * For internal use only - gets the class that this bundle is representing
     * @return the class that this bundle represents or null if it isn't representing anything
     */
    String getClassName() {
        return data.getString( "__classname__" );
    }

    /**
     * Maps an integer to a key
     * @param key the key store the value into
     * @param val the value to store
     * @throws NullPointerException if {@code key} is null
     * @throws IllegalStateException if called on a restored Bundle
     */
    public void putInt( String key, int val ) {
        validateKey( key );
        if ( !editable )
            throw new IllegalStateException( "cannot edit a restored bundle" );
        data.put( key, val );
    }

    /**
     * Maps a double to a key.
     * @param key the key store the value into
     * @param val the value to store
     * @throws NullPointerException if {@code key} is null
     * @throws IllegalStateException if called on a restored Bundle
     */
    public void putDouble( String key, double val ) {
        validateKey( key );
        if ( !editable )
            throw new IllegalStateException( "cannot edit a restored bundle" );
        data.put( key, val );
    }

    /**
     * Maps a boolean to a key.
     * @param key the key store the value into
     * @param val the value to store
     * @throws NullPointerException if {@code key} is null
     * @throws IllegalStateException if called on a restored Bundle
     */
    public void putBoolean( String key, boolean val ) {
        validateKey( key );
        if ( !editable )
            throw new IllegalStateException( "cannot edit a restored bundle" );
        data.put( key, val );
    }

    /**
     * Maps a String to a key. If the value is null, then the key will be removed
     * from the Bundle
     * @param key the key store the value into
     * @param value the value to store
     * @throws NullPointerException if {@code key} is null
     * @throws IllegalStateException if called on a restored Bundle
     */
    public void putString( String key, String value ) {
        validateKey( key );
        if ( !editable )
            throw new IllegalStateException( "cannot edit a restored bundle" );
        data.put( key, value );
    }

    /**
     * Maps a Bundlable to a key. When the Bundle is saved, the passed Bundlable
     * will be asked to bundle itself and the returned bundle will be stored.
     * If {@code object} is null, then the key will be removed
     * @param key the key store the object into
     * @param object the Bundlable to store
     * @throws NullPointerException if {@code key} is null
     * @throws IllegalStateException if called on a restored Bundle
     */
    public void putBundlable( String key, Bundlable object ) {
        validateKey( key );
        if ( !editable )
            throw new IllegalStateException( "cannot edit a restored bundle" );
        if ( object == null )
            data.remove( key );
        data.put( key, lookup.add( object ) );
    }

    public void putNNBundlable( String key, Bundlable object ) {
        if ( object == null )
            throw new NullPointerException( "Attempted to add a null object as not null" );
        putBundlable( key, object );
    }

    /**
     * Puts a Bundle into the list. Only Bundles with the same parent bundle can be stored
     * @param key the key to store with
     * @param bundle the bundle to store
     * @throws IllegalArgumentException if the bundle has a different top level bundle
     * @throws NullPointerException if the key is null
     * @throws IllegalStateException if the bundle is a restored bundle
     * @throws NullPointerException id key or bundle are null
     */
    public void putBundle( String key, Bundle bundle ) {
        if ( bundle.lookup != this.lookup )
            throw new IllegalArgumentException( "cannot store a bundle with a different parent bundle" );
        validateKey( key );
        if ( !editable )
            throw new IllegalStateException( "cannot edit a restored bundle" );
        data.put( key, bundle.data );
    }

    /**
     * Convenience method for {@code getInt( key, 0 )}
     * @see #getInt(String, int)
     */
    public int getInt( String key ) {
        validateKey( key );
        return data.optInt( key, 0 );
    }

    /**
     * Gets an integer mapped to the key. If the mapped value was not an integer
     * or the value did not exist, then {@code defaultValue} is returned.
     * @param key the key to look up
     * @param defaultValue the value to return if the mapped value didn't exist
     * @return the mapped int
     * @throws NullPointerException if {@code key} is null
     */
    public int getInt( String key, int defaultValue ) {
        validateKey( key );
        return data.optInt( key, defaultValue );
    }

    /**
     * Convenience method for {@code getDouble(key, 0.0)}
     * @see #getDouble(String, double)
     */
    public double getDouble( String key ) {
        validateKey( key );
        return data.optDouble( key, 0.0 );
    }

    /**
     * Gets a double mapped to the key. If the mapped value was not an double
     * or the value did not exist, then {@code defaultValue} is returned.
     * @param key the key to look up
     * @param defaultValue the value to return if the mapped value didn't exist
     * @return the mapped double
     * @throws NullPointerException if {@code key} is null
     */
    public double getDouble( String key, double defaultValue ) {
        validateKey( key );
        return data.optDouble( key, defaultValue );
    }

    /**
     * Convenience method for {@code getBoolean(key, false)}
     * @see #getBoolean(String, boolean)
     */
    public boolean getBoolean( String key ) {
        validateKey( key );
        return data.optBoolean( key, false );
    }

    /**
     * Gets a boolean mapped to the key. If the mapped value was not a boolean
     * or the value did not exist, then {@code defaultValue} is returned. "true" and "false" will
     * aslo get converted to a boolean.
     * @param key the key to look up
     * @param defaultValue the value to return if the mapped value didn't exist
     * @return the mapped boolean
     * @throws NullPointerException if {@code key} is null
     */
    public boolean getBoolean( String key, boolean defaultValue ) {
        validateKey( key );
        return data.optBoolean( key, defaultValue );
    }

    /**
     * Convenience method for {@code getString(key, null)}
     * @see #getString(String, String)
     */
    public String getString( String key ) {
        validateKey( key );
        return data.optString( key, null );
    }

    /**
     * Gets a String that is mapped to a key. If a mapping existed but was not
     * a string, then mapped value will be converted to a string. If no mapping
     * existed, then {@code defaultValue} will be returned.
     * @param key the key to look up
     * @param defaultValue the value to return if the mapped value didn't exist
     * @return the String mapped to key
     * @throws NullPointerException if {@code key} is null
     */
    public String getString( String key, String defaultValue ) {
        validateKey( key );
        return data.optString( key, defaultValue );
    }

    /**
     * Reads out a Bundlable. If the value associated with this key is not a
     * Bundlable, then null is returned.
     * @param key the key to look up
     * @param <T> the type of Bundlable that is expected
     * @return the read bundlable
     * @throws ClassCastException if the bundlable is not of type T
     */
    public <T extends Bundlable> T getBundlable( String key ) {
        validateKey( key );
        int id = data.optInt( key, -1 );
        if ( id == -1 )
            return null;
        return (T)lookup.get( id );
    }

    public <T extends Bundlable> T getNNBundlable( String key ) {
        T b = getBundlable( key );
        if ( b == null )
            throw new NullPointerException( "Bundlable was null" );
        return b;
    }

    /**
     * Gets the Bundle that is mapped to the key. If there is nothing mapped to
     * the key or the mapping is not a Bundle, then null is returned.
     * @param key the key to look up
     * @return the bundle stored into {@code key}
     */
    public Bundle getBundle( String key ) {
        validateKey( key );
        JSONObject data = this.data.optJSONObject( key );
        if ( data == null )
            return null;
        return new Bundle( data, lookup, editable, versionRaw );
    }

    /**
     * Stores a collection of Bundlables into the Bundle. If the Collection was a list,
     * then the order will be maintained.
     * @param key the key to store the collection with
     * @param collection the Collection to store
     */
    public void putBundlables( String key, Collection<? extends Bundlable> collection ) {
        if ( !editable )
            throw new IllegalStateException( "cannot edit a restored bundle" );
        validateKey( key );
        if ( collection == null ) {
            data.remove( key );
            return;
        }
        List<Object> list = new ArrayList<>( collection.size() );
        for ( Bundlable bundlable : collection ) {
            int id = lookup.add( bundlable );
            list.add( id );
        }
        data.put( key, list );
    }

    /**
     * Gets a List of values from a key. If there is not a list of values in the key, then an empty
     * list is returned
     * @param key the key to look up
     * @return the List mapped to the key
     * @throws NullPointerException if key is null
     * @throws ClassCastException if all the bundlables are not of type E
     */
    public <E extends Bundlable> List<E> getBundlables( String key ) {
        validateKey( key );
        JSONArray array = data.optJSONArray( key );
        if ( array == null )
            return new ArrayList<>();
        int size = array.length();
        List<E> bundlables = new ArrayList<>( size );
        for ( int i = 0; i < size; i++ ) {
            bundlables.add( (E)lookup.get( (int)array.get( i ) ) );
        }
        return bundlables;
    }

    /**
     * Adds a collection of boxed primitives (or Strings) to the bundle. The added collection does
     * not need to contain objects of the same type. The objects must be a Integer, Double, Boolean
     * or a String.
     * @param key the key to add the list to
     * @param objects the primitives to add
     */
    public void putPrimitives( String key, Collection<Object> objects ) {
        if ( !editable )
            throw new IllegalStateException( "cannot edit a restored bundle" );
        validateKey( key );
        if ( objects == null ) {
            data.remove( key );
            return;
        }
        data.put( key, objects );
    }

    /**
     * Gets a list of boxed primitives (or Strings) from the bundle. If the key does not exist, an
     * empty list is returned
     * @param key the key to add the list to
     * @return the stored objects
     */
    public List<Object> getPrimitives( String key ) {
        validateKey( key );
        JSONArray array = data.optJSONArray( key );
        if ( array == null )
            return new ArrayList<>();
        int size = array.length();
        List<Object> objects = new ArrayList<>( size );
        for ( int i = 0; i < size; i++ ) {
            objects.add( array.get( i ) );
        }
        return objects;
    }

    public void put( String key, boolean[][] array ) {
        validateKey( key );
        if ( !editable )
            throw new IllegalStateException( "cannot edit a restored bundle" );
        JSONArray jsonArray = new JSONArray();
        for ( boolean[] a : array ) {
            jsonArray.put( new JSONArray( a ) );
        }
        data.put( key, jsonArray );
    }

    public void put( String key, int[][] array ) {
        validateKey( key );
        if ( !editable )
            throw new IllegalStateException( "cannot edit a restored bundle" );
        JSONArray jsonArray = new JSONArray();
        for ( int[] a : array ) {
            jsonArray.put( new JSONArray( a ) );
        }
        data.put( key, jsonArray );
    }

    public void put( String key, float[][] array ) {
        validateKey( key );
        if ( !editable )
            throw new IllegalStateException( "cannot edit a restored bundle" );
        JSONArray jsonArray = new JSONArray();
        for ( float[] a : array ) {
            jsonArray.put( new JSONArray( a ) );
        }
        data.put( key, jsonArray );
    }

    public void put( String key, double[][] array ) {
        validateKey( key );
        if ( !editable )
            throw new IllegalStateException( "cannot edit a restored bundle" );
        JSONArray jsonArray = new JSONArray();
        for ( double[] a : array ) {
            jsonArray.put( new JSONArray( a ) );
        }
        data.put( key, jsonArray );
    }

    public boolean[][] getBooleanArray( String key ) {
        validateKey( key );
        if ( !contains( key ) )
            return null;
        JSONArray array = (JSONArray)data.get( key );
        if ( array == null )
            return null;
        int size = array.length();
        boolean[][] result = new boolean[size][];
        for ( int i = 0; i < size; i++ ) {
            JSONArray array2 = array.getJSONArray( i );
            int size2 = array2.length();
            boolean[] result2 = new boolean[size2];
            for ( int j = 0; j < size2; j++ ) {
                result2[j] = array2.getBoolean( j );
            }
            result[i] = result2;
        }
        return result;
    }

    public int[][] getIntArray( String key ) {
        validateKey( key );
        if ( !contains( key ) )
            return null;
        JSONArray array = (JSONArray)data.get( key );
        if ( array == null )
            return null;
        int size = array.length();
        int[][] result = new int[size][];
        for ( int i = 0; i < size; i++ ) {
            JSONArray array2 = array.getJSONArray( i );
            int size2 = array2.length();
            int[] result2 = new int[size2];
            for ( int j = 0; j < size2; j++ ) {
                result2[j] = array2.getInt( j );
            }
            result[i] = result2;
        }
        return result;
    }

    public float[][] getFloatArray( String key ) {
        validateKey( key );
        if ( !contains( key ) )
            return null;
        JSONArray array = (JSONArray)data.get( key );
        if ( array == null )
            return null;
        int size = array.length();
        float[][] result = new float[size][];
        for ( int i = 0; i < size; i++ ) {
            JSONArray array2 = array.getJSONArray( i );
            int size2 = array2.length();
            float[] result2 = new float[size2];
            for ( int j = 0; j < size2; j++ ) {
                result2[j] = (float)array2.getDouble( j );
            }
            result[i] = result2;
        }
        return result;
    }

    public double[][] getDoubleArray( String key ) {
        validateKey( key );
        if ( !contains( key ) )
            return null;
        JSONArray array = (JSONArray)data.get( key );
        if ( array == null )
            return null;
        int size = array.length();
        double[][] result = new double[size][];
        for ( int i = 0; i < size; i++ ) {
            JSONArray array2 = array.getJSONArray( i );
            int size2 = array2.length();
            double[] result2 = new double[size2];
            for ( int j = 0; j < size2; j++ ) {
                result2[j] = array2.getDouble( j );
            }
            result[i] = result2;
        }
        return result;
    }

    public void put( String key, Object[][] bundlables ) {

    }

}