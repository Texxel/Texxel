package com.github.texxel.utils;

import com.badlogic.gdx.graphics.Color;

import junit.framework.TestCase;

public class ColorMathsTest extends TestCase {

    public void testToRGBAInts() throws Exception {
        assertEquals( 0xFF000000, ColorMaths.toRGBA( 255, 0, 0, 0 ) );
        assertEquals( 0x00FF0000, ColorMaths.toRGBA( 0, 255, 0, 0 ) );
        assertEquals( 0x0000FF00, ColorMaths.toRGBA( 0, 0, 255, 0 ) );
        assertEquals( 0x000000FF, ColorMaths.toRGBA( 0, 0, 0, 255 ) );
    }

    public void testToRGBAFloats() throws Exception {
        assertEquals( 0xFF000000, ColorMaths.toRGBA( 1f, 0, 0, 0 ) );
        assertEquals( 0x00FF0000, ColorMaths.toRGBA( 0, 1f, 0, 0 ) );
        assertEquals( 0x0000FF00, ColorMaths.toRGBA( 0, 0, 1f, 0 ) );
        assertEquals( 0x000000FF, ColorMaths.toRGBA( 0, 0, 0, 1f ) );
    }

    public void testToARGBInts() throws Exception {
        assertEquals( 0xFF000000, ColorMaths.toARGB( 1f, 0, 0, 0 ) );
        assertEquals( 0x00FF0000, ColorMaths.toARGB( 0, 1f, 0, 0 ) );
        assertEquals( 0x0000FF00, ColorMaths.toARGB( 0, 0, 1f, 0 ) );
        assertEquals( 0x000000FF, ColorMaths.toARGB( 0, 0, 0, 1f ) );
    }

    public void testToARGBFloats() throws Exception {
        assertEquals( 0xFF000000, ColorMaths.toARGB( 1f, 0, 0, 0 ) );
        assertEquals( 0x00FF0000, ColorMaths.toARGB( 0, 1f, 0, 0 ) );
        assertEquals( 0x0000FF00, ColorMaths.toARGB( 0, 0, 1f, 0 ) );
        assertEquals( 0x000000FF, ColorMaths.toARGB( 0, 0, 0, 1f ) );
    }

    public void testToRGBAColor() throws Exception {
        assertEquals( 0xFF000000, ColorMaths.toRGBA( new Color( 1f, 0, 0, 0 ) ) );
        assertEquals( 0x00FF0000, ColorMaths.toRGBA( new Color( 0, 1f, 0, 0 ) ) );
        assertEquals( 0x0000FF00, ColorMaths.toRGBA( new Color( 0, 0, 1f, 0 ) ) );
        assertEquals( 0x000000FF, ColorMaths.toRGBA( new Color( 0, 0, 0, 1f ) ) );
    }

    public void testToARGB2() throws Exception {
        assertEquals( 0x00FF0000, ColorMaths.toARGB( new Color( 1f, 0, 0, 0 ) ) );
        assertEquals( 0x0000FF00, ColorMaths.toARGB( new Color( 0, 1f, 0, 0 ) ) );
        assertEquals( 0x000000FF, ColorMaths.toARGB( new Color( 0, 0, 1f, 0 ) ) );
        assertEquals( 0xFF000000, ColorMaths.toARGB( new Color( 0, 0, 0, 1f ) ) );
    }

    public void testARGBtoRGBA() throws Exception {
        assertEquals( 0x000000FF, ColorMaths.ARGBtoRGBA( 0xFF000000 ) );
        assertEquals( 0xFF000000, ColorMaths.ARGBtoRGBA( 0x00FF0000 ) );
        assertEquals( 0x00FF0000, ColorMaths.ARGBtoRGBA( 0x0000FF00 ) );
        assertEquals( 0x0000FF00, ColorMaths.ARGBtoRGBA( 0x000000FF ) );
    }

    public void testRGBAtoARGB() throws Exception {
        assertEquals( 0x00FF0000, ColorMaths.RGBAtoARGB( 0xFF000000 ) );
        assertEquals( 0x0000FF00, ColorMaths.RGBAtoARGB( 0x00FF0000 ) );
        assertEquals( 0x000000FF, ColorMaths.RGBAtoARGB( 0x0000FF00 ) );
        assertEquals( 0xFF000000, ColorMaths.RGBAtoARGB( 0x000000FF ) );
    }

    public void testSetAlpha() throws Exception {
        assertEquals( 0xAB654321, ColorMaths.setAlpha( 0x87654321, 0xAB ) );
    }

    public void testSetRed() throws Exception {
        assertEquals( 0x87AB4321, ColorMaths.setRed( 0x87654321, 0xAB ) );
    }

    public void testSetGreen() throws Exception {
        assertEquals( 0x8765AB21, ColorMaths.setGreen( 0x87654321, 0xAB ) );
    }

    public void testSetBlue() throws Exception {
        assertEquals( 0x876543AB, ColorMaths.setBlue( 0x87654321, 0xAB ) );
    }

    public void testGetAlpha() throws Exception {
        assertEquals( 0x87, ColorMaths.getAlpha( 0x87654321 ) );
    }

    public void testGetRed() throws Exception {
        assertEquals( 0x65, ColorMaths.getRed( 0x87654321 ) );
    }

    public void testGetGreen() throws Exception {
        assertEquals( 0x43, ColorMaths.getGreen( 0x87654321 ) );
    }

    public void testGetBlue() throws Exception {
        assertEquals( 0x21, ColorMaths.getBlue( 0x87654321 ) );
    }
}