package com.github.texxel.utils;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;

/**
 * This class provides a few handle methods to safely read objects in and out of streams
 */
public class SaveUtils {

    /**
     * Tries to read the object from the stream. Should the class of the object it's reading be
     * invalid or missing, then the default object will be returned. All others errors in reading
     * from the stream will be passed along from {@link ObjectInputStream#readObject()}
     * @param in the stream to read from
     * @param bup the object to return if the object in the stream be no good
     * @param <T> the type of object to read
     * @return the object in the stream
     * @throws ClassCastException the object in the stream is not of type T
     * @throws java.io.StreamCorruptedException Control information in the
     *          stream is inconsistent.
     * @throws OptionalDataException Primitive data was found in the
     *          stream instead of objects.
     * @throws IOException Any of the usual Input/Output related exceptions.
     */
    public static <T> T readObject( ObjectInputStream in, T bup ) throws IOException {
        try {
            return (T)in.readObject();
        } catch ( ClassNotFoundException e ) {
            // the mod probably got uninstalled
            return bup;
        } catch ( InvalidClassException e ) {
            // the mod programmer doesn't know about backwards compatability
            return bup;
        }
    }

}
