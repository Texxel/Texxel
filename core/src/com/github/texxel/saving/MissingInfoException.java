package com.github.texxel.saving;

/**
 * An exception to use when a bundle is missing a required field.
 */
public class MissingInfoException extends RuntimeException {

    public MissingInfoException() {
    }

    public MissingInfoException( String message ) {
        super( message );
    }

    public MissingInfoException( String message, Throwable cause ) {
        super( message, cause );
    }

    public MissingInfoException( Throwable cause ) {
        super( cause );
    }

    public MissingInfoException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
