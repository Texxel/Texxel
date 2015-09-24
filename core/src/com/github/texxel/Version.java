package com.github.texxel;

import java.util.HashMap;

public enum Version {

    V0_0_0( "0.0.0" ),
    /**
     * Represents an unknown version. This might be due to a corrupt save file or an attempt to
     * load a save made by a future version.
     */
    UNKNOWN( "unknown" );

    static class StaticAccess {
        public static final HashMap<String, Version> versions = new HashMap<>();
    }

    private final String value;

    Version( String value ) {
        this.value = value;
        StaticAccess.versions.put( value, this );
    }

    /**
     * Gets the version number of this version (e.g. "1.8.2"). The returned string is human readable.
     * @return this version's code
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Gets a version from it's version number (e.g. "1.7.0"). The version number must be exactly
     * the same as what is returned from the {@link #toString()} method
     * @param name the versions number
     * @return the Version that has that number (or null if non existent)
     * @see #valueOf(String)
     */
    public static Version fromString( String name ) {
        Version value = StaticAccess.versions.get( name );
        if ( value == null )
            return UNKNOWN;
        else
            return value;
    }

    public static Version currentVersion() {
        return V0_0_0;
    }
}
