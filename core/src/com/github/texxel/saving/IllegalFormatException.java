package com.github.texxel.saving;

/**
 * A constructor that is used when a Bundle cannot be loaded because it has an invalid format
 */
public class IllegalFormatException extends RuntimeException {

    public IllegalFormatException() {
    }

    public IllegalFormatException( String message ) {
        super( message );
    }

    public IllegalFormatException( String message, Throwable cause ) {
        super( message, cause );
    }

    public IllegalFormatException( Throwable cause ) {
        super( cause );
    }

    public IllegalFormatException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
