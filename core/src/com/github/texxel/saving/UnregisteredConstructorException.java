package com.github.texxel.saving;

public class UnregisteredConstructorException extends RuntimeException {

    public UnregisteredConstructorException() {
        super();
    }

    public UnregisteredConstructorException( String message ) {
        super( message );
    }

    public UnregisteredConstructorException( String message, Throwable cause ) {
        super( message, cause );
    }

    public UnregisteredConstructorException( Throwable cause ) {
        super( cause );
    }

    protected UnregisteredConstructorException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
