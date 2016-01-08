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
        private static final long serialVersionUID = -6046834780522884918L;

        @Override
        public boolean isAllowed( Object obj ) {
            return true;
        }

        private Object readResolve() {
            return anyObject;
        }
    };

    private static Filter noObjects = new Filter() {
        private static final long serialVersionUID = -4995083487578520212L;

        @Override
        public boolean isAllowed( Object obj ) {
            return false;
        }

        private Object readResolve() {
            return noObjects;
        }
    };

}
