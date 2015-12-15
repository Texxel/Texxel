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

    /**
     * Produces 0xRRGGBBAA
     *
     */
    public static int toRGBA( float r, float g, float b, float a ) {
        return ((int)(255 * r) << 24) | ((int)(255 * g) << 16) | ((int)(255 * b) << 8) | ((int)(255 * a));
    }

    /**
     * Produces 0xRRGGBBAA
     */
    public static int toRGBA( int r, int g, int b, int a ) {
        return (r << 24) | (g << 16) | (b << 8) | a;
    }

    /**
     * Produces 0xAARRGGBB
     */
    public static int toARGB( float a, float r, float g, float b ) {
        return ((int)(255 * a) << 24) | ((int)(255 * r) << 16) | ((int)(255 * g) << 8) | ((int)(255 * b));
    }

    /**
     * Produces 0xAARRGGBB
     */
    public static int toARGB( int a, int r, int g, int b ) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /**
     * Produces 0xRRGGBBAA
     */
    public static int toRGBA( Color color ) {
        return toRGBA( color.r, color.g, color.b, color.a );
    }

    /**
     * Produces 0xAARRGGBB
     */
    public static int toARGB( Color color ) {
        return toARGB( color.a, color.r, color.g, color.b );
    }

    /**
     * Takes 0xAARRGGBB and returns 0xRRGGBBAA
     */
    public static int ARGBtoRGBA( int argb ) {
        return ( ( argb & 0x00FFFFFF ) << 8 ) | ( ( argb & 0xFF000000 ) >>> 24 );
    }

    /**
     * Takes 0xRRGGBBAA and returns 0xAARRGGBB
     */
    public static int RGBAtoARGB( int rgba ) {
        return ( ( rgba & 0xFFFFFF00 ) >>> 8 ) | ( ( rgba & 0x000000FF ) << 24 );
    }

    /**
     * Sets the alpha bits of a color encoded in 0xAARRGGBB
     */
    public static int setAlpha( int argb, int alpha ) {
        return ( argb & 0x00FFFFFF ) | ( alpha << 24 );
    }

    /**
     * Sets the red bits of a color encoded in 0xAARRGGBB
     */
    public static int setRed( int argb, int red ) {
        return ( argb & 0xFF00FFFF ) | ( red << 16 );
    }

    /**
     * Sets the green bits of a color encoded in 0xAARRGGBB
     */
    public static int setGreen( int argb, int green ) {
        return ( argb & 0xFFFF00FF ) | ( green << 8 );
    }

    /**
     * Sets the blue bits of a color encoded in 0xAARRGGBB
     */
    public static int setBlue( int argb, int blue ) {
        return ( argb & 0xFFFFFF00 ) | ( blue );
    }

    /**
     * Gest the alpha bits of a color encoded in 0xAARRGGBB
     */
    public static int getAlpha( int argb ) {
        return ( argb & 0xFF000000 ) >>> 24;
    }

    /**
     * Gets the red bits of a color encoded in 0xAARRGGBB
     */
    public static int getRed( int argb ) {
        return ( argb & 0x00FF0000 ) >>> 16;
    }

    /**
     * Gets the green bits of a color encoded in 0xAARRGGBB
     */
    public static int getGreen( int argb ) {
        return ( argb & 0x0000FF00 ) >>> 8;
    }

    /**
     * Gets the blue bits of a color encoded in 0xAARRGGBB
     */
    public static int getBlue( int argb ) {
        return ( argb & 0x000000FF );
    }

}
