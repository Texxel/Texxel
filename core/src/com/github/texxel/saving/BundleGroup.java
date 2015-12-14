package com.github.texxel.saving;

import com.github.texxel.Version;
import com.github.texxel.json.JSONException;
import com.github.texxel.json.JSONObject;

/**
 * The BundleGroup class is designed to be a top level bundle. As the BundleGroup
 * is a top level Bundle, it cannot be stored into bundles. The BundleGroup
 * provides the mechanism of creating new bundles: if a new top level bundle
 * is wanted, then use {@link #newGroup()}. If a sub Bundle is wanted, then use
 * the {@link #newBundle()} method on an already existing BundleGroup. The
 * BundleGroup class is also responsible for loading and saving data: data can
 * be saved by converting the BundleGroup into a String with {@link #toString()}.
 * The BundleGroup can then be loaded with {@link #loadGroup(String)}.
 */
public class BundleGroup extends Bundle {

    /**
     * Gets a new top level Bundle. This Bundle cannot be stored in other
     * bundles (use {@link #getBundle(String)} for that).
     * @return a new top level Bundle
     */
    public static BundleGroup newGroup() {
        return new BundleGroup(
                new JSONObject(),
                new BundleLookup(),
                true,
                Version.currentVersion().toString() );
    }

    /**
     * Loads a top level bundle from a string. The passed string should be the
     * exact same as what was passed out from the {@link #toString()} method.
     * The loaded Bundle cannot be edited. Attempting to edit the Bundle (or
     * any sub bundles) will result in an {@link IllegalStateException}.
     * @param data the String to read data from
     * @return a BundleGroup which has loaded the passed data.
     * @throws IllegalFormatException if passed data is invalid for any reason
     */
    public static BundleGroup loadGroup( String data ) {
        JSONObject json;
        try {
            json = new JSONObject( data );
        } catch ( JSONException e ) {
            throw new IllegalFormatException( "data was not in a valid json format", e );
        }
        BundleLookup lookup = new BundleLookup();
        String version;
        try {
            version = json.getString( "__version__" );
        } catch ( JSONException e ) {
            throw new IllegalFormatException( "All loaded bundles must have a version field" );
        }
        Bundle bundleLookup = new Bundle( json.getJSONObject( "__lookuptable__" ), lookup, false, version );
        lookup.restore( bundleLookup );
        return new BundleGroup( json, lookup, false, version );
    }

    private BundleGroup( JSONObject data, BundleLookup lookup, boolean editable, String rawVersion ) {
        super( data, lookup, editable, rawVersion );
        data.put( "__version__", rawVersion );
    }

    /**
     * Gets a new bundle that shares this parent bundle. The returned bundle can then be stored into
     * other bundles that are created with this same parent bundle.
     * @return the new bundle
     */
    public Bundle newBundle() {
        if ( !editable )
            throw new IllegalStateException( "Cannot make a new Bundle from a restored bundle" );
        return new Bundle( new JSONObject(), lookup, true, versionRaw );
    }

    /**
     * Converts this BundleGroup into a String. The returned String can then be written to a file
     * and then restored later
     * @return a savable String
     */
    @Override
    public String toString() {
        data.put( "__lookuptable__", lookup.bundle( this ).data );
        return data.toString();
    }
}
