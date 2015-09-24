package com.github.texxel.saving;

/**
 * An exception to throw when a field is requested from a Bundle but the bundle is not
 */
public class IllegalTypeException extends RuntimeException {

    public IllegalTypeException() {
    }

    public IllegalTypeException( String message ) {
        super( message );
    }

    public IllegalTypeException( String message, Throwable cause ) {
        super( message, cause );
    }

    public IllegalTypeException( Throwable cause ) {
        super( cause );
    }

    public IllegalTypeException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
