package com.github.texxel.utils;

public class FilterUtils {

    @SuppressWarnings( "unchecked" )
    public static <T> Filter<T> anyObject() {
        return anyObject;
    }

    @SuppressWarnings( "unchecked" )
    public static <T> Filter<T> noObjects() {
        return noObjects;
    }


    private static Filter anyObject = new Filter() {
        @Override
        public boolean isAllowed( Object obj ) {
            return true;
        }
    };

    private static Filter noObjects = new Filter() {
        @Override
        public boolean isAllowed( Object obj ) {
            return false;
        }
    };

}
