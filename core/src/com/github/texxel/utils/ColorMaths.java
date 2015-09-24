package com.github.texxel.utils;

import com.badlogic.gdx.graphics.Color;

/**
 * The ColorMaths class provides some helpful utilities for performing operations on colors.
 */
public final class ColorMaths {

    private ColorMaths() {
        // only static methods
    }

    private static final int COLOR_MASK = 0xFF;

    public static int toRGBA( float r, float g, float b, float a ) {
        return ((int)(255 * r) << 24) | ((int)(255 * g) << 16) | ((int)(255 * b) << 8) | ((int)(255 * a));
    }

    public static int toRGBA( int r, int g, int b, int a ) {
        return (r << 24) | (g << 16) | (b << 8) | a;
    }

    public static int toARGB( float a, float r, float g, float b ) {
        return ((int)(255 * a) << 24) | ((int)(255 * b) << 16) | ((int)(255 * g) << 8) | ((int)(255 * r));
    }

    public static int toARGB( int a, int r, int g, int b ) {
        return (a << 24) | (b << 16) | (g << 8) | r;
    }

    public static int toRGBA( Color color ) {
        return toRGBA( color.r, color.g, color.b, color.a );
    }

    public static int toARGB( Color color ) {
        return color.toIntBits();
    }

    public static int ARGBtoRGBA( int argb ) {
        int a = (argb >> 24) & COLOR_MASK;
        int r = (argb >> 16) & COLOR_MASK;
        int g = (argb >> 8) & COLOR_MASK;
        int b = argb & COLOR_MASK;
        return toARGB( a, r, g, b );
    }

    public static int RGBAtoARGB( int rgba ) {
        int r = (rgba >> 24) & COLOR_MASK;
        int g = (rgba >> 16) & COLOR_MASK;
        int b = (rgba >> 8) & COLOR_MASK;
        int a = rgba & COLOR_MASK;
        return toRGBA( r, g, b, a );
    }

}
