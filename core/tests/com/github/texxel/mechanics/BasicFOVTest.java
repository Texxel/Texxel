package com.github.texxel.mechanics;

import com.github.texxel.utils.Arrays2D;
import com.github.texxel.utils.Point2D;

import junit.framework.TestCase;

public class BasicFOVTest extends TestCase {

    @SuppressWarnings( "SpellCheckingInspection" )
    private String[] map = new String[] {
            "XXXXXXX",
            "X.....X",
            "X.XXX.X",
            "X...X.X",
            "X.....X",
            "XXXXXXX",
    };

    static boolean[][] makeMap( String[] design ) {
        int width = design[0].length();
        int height = design.length;
        boolean[][] map = new boolean[width][height];
        for ( int j = 0; j < height; j++ ) {
            String row = design[j];
            for ( int i = 0; i < width; i++ )
                map[i][j] = row.charAt( i ) == 'X';
        }
        return map;
    }

    // test for this classes makeMap() method
    public void testMakeMap() {
        boolean[][] map = makeMap( this.map );
        assertTrue ( map[0][3] );
        assertFalse( map[1][3] );
        assertFalse( map[2][3] );
        assertFalse( map[3][3] );
        assertTrue ( map[4][3] );
        assertFalse( map[5][3] );
        assertTrue( map[6][3] );
    }

    public void testCreation() {
        BasicFOV fov = new BasicFOV( makeMap( map ), new Point2D( 4, 5 ) );
        // test correct location
        assertTrue( fov.getLocation().equals( 4, 5 ) );
        // test correct filling of map
        assertTrue ( fov.isSolid( 0, 3 ) );
        assertFalse( fov.isSolid( 1, 3 ) );
        assertFalse( fov.isSolid( 2, 3 ) );
        assertFalse( fov.isSolid( 3, 3 ) );
        assertTrue ( fov.isSolid( 4, 3 ) );
        assertFalse( fov.isSolid( 5, 3 ) );
        assertTrue( fov.isSolid( 6, 3 ) );
    }

    public void testViewDistance() {
        BasicFOV fov = new BasicFOV( makeMap( map ), new Point2D( 3, 2 ) );
        fov.setViewDistance( 4 );
        assertEquals( 4, fov.getViewDistance() );
    }

    public void testDirty() {
        BasicFOV fov = new BasicFOV( makeMap( map ), new Point2D( 3, 2 ) );
        // test initial dirty
        assertTrue( fov.dirty );

        // test cleaned when asked
        fov.isVisible( 1, 1 );
        assertFalse( fov.dirty );

        // test dirty on viewDistance change
        fov.setViewDistance( 3 );
        assertTrue( fov.dirty );
        fov.isVisible( 1, 1 );
        fov.setViewDistance( 3 );
        assertFalse( fov.dirty );

        // test dirty on solid change
        fov.setSolid( 4, 4, true ); // make sure 4, 4 is solid first
        fov.isVisible( 1, 1 );
        fov.setSolid( 4, 4, false );
        assertTrue( fov.dirty );
        fov.isVisible( 1, 1 );
        // fov hasn't changed so not dirty
        fov.setSolid( 4, 4, false );
        assertFalse( fov.dirty );
    }

    public void testIsVisible() {
        BasicFOV fov = new BasicFOV( new boolean[20][20], new Point2D( 8, 6 ), 4 );
        Arrays2D.fill( fov.visible, false );
        fov.visible[2][3] = true;
        fov.dirty = false;
        assertTrue( fov.isVisible( 8 + ( 2 - 4 ), 6 + ( 3 - 4 ) ) );
    }

    public void testOpenToOutside() {
        BasicFOV fov = new BasicFOV( makeMap( new String[] {
                "........",
                "........",
                "........",
                "........",
                "........",
                "........"
        }), new Point2D( 3, 2 ) );
        fov.isVisible( 1, 1 );
        // just making sure it doesn't throw an indexOutOfBounds exception in the algorithm
        // thus, no assertions are needed
    }

    public void testThickWalls() {
        @SuppressWarnings( "SpellCheckingInspection" )
        BasicFOV fov = new BasicFOV( makeMap( new String[] {
                "XXXXXXXXX",
                "XXXXXXXXX",
                "XXXXXXXXX",
                "XXXX.XXXX",
                "XXXXXXXXX",
                "XXXXXXXXX",
                "XXXXXXXXX",
        } ), new Point2D( 4, 3 ) );

        //printFov( fov );

        assertTrue( fov.isVisible( 3, 2 ) );
        assertTrue( fov.isVisible( 3, 3 ) );
        assertTrue( fov.isVisible( 3, 4 ) );
        assertTrue( fov.isVisible( 4, 2 ) );
        assertTrue( fov.isVisible( 4, 3 ) );
        assertTrue( fov.isVisible( 4, 4 ) );
        assertTrue( fov.isVisible( 5, 2 ) );
        assertTrue( fov.isVisible( 5, 3 ) );
        assertTrue( fov.isVisible( 5, 4 ) );

        assertFalse( fov.isVisible( 2, 2 ) );
        assertFalse( fov.isVisible( 6, 2 ) );
        // i can't be bothered testing more...
    }

    public void testScanSector() {
        @SuppressWarnings( "SpellCheckingInspection" )
        BasicFOV fov = new BasicFOV( makeMap( new String[] {
                "XXXXXXX",
                "X+....X",
                "X..XX.X",
                "X.....X",
                "X.....X",
                "XXXXXXX"
        }), new Point2D( 1, 1 ) );

        //printFov( fov );

        assertTrue( fov.isVisible( 2, 1 ) );
        assertTrue( fov.isVisible( 3, 1 ) );
        assertTrue( fov.isVisible( 5, 1 ) );
        assertTrue( fov.isVisible( 6, 1 ) );
        assertTrue( fov.isVisible( 2, 2 ) );
        assertTrue( fov.isVisible( 3, 3 ) );
        assertTrue( fov.isVisible( 4, 2 ) );
        assertTrue( fov.isVisible( 5, 2 ) );
        assertFalse( fov.isVisible( 5, 3 ) );
    }


    private void printFov( BasicFOV fov ) {
        for ( int j = 0; j < fov.height; j++ ) {
            for ( int i = 0; i < fov.width; i++ ) {
                if ( i == fov.getLocation().x && j == fov.getLocation().y ) {
                    System.out.print( 'x' );
                    continue;
                }
                if ( fov.isSolid( i, j ) ) {
                    if ( fov.isVisible( i, j ) )
                        System.out.print( '%' );
                    else
                        System.out.print( '#' );
                } else {
                    if ( fov.isVisible( i, j ) )
                        System.out.print( '.' );
                    else
                        System.out.print( '-' );
                }
            }
            System.out.println();
        }
    }

    public void DISABLEDtestNormalRoom() {
        // TODO make FOV pass this test
        @SuppressWarnings( "SpellCheckingInspection" )
        BasicFOV fov = new BasicFOV( makeMap( new String[] {
                ".........",
                ".XXXXXXX.",
                ".XXXXXXX.",
                ".XX....X.",
                ".XX....X.",
                ".XX..@.X.",
                ".XX....X.",
                ".XXXXXXX.",
                ".XXXXXXX.",
                ".........",
        } ), new Point2D( 5, 5 ));

        // errors can be seen in this print
        printFov( fov );

        assertTrue( fov.isVisible( 4, 4 ) );
        assertTrue( fov.isVisible( 3, 3 ) );
        assertTrue( fov.isVisible( 2, 2 ) );
        assertTrue( fov.isVisible( 2, 3 ) );
        assertTrue( fov.isVisible( 2, 4 ) );
        assertTrue( fov.isVisible( 2, 7 ) );

        assertFalse( fov.isVisible( 1, 1 ) );
        assertFalse( fov.isVisible( 1, 3 ) );

    }

    public void testGeneralEyeSight() {
        BasicFOV fov = new BasicFOV( makeMap( map ), new Point2D( 1, 1 ) );

        //printFov( fov );

        // where character is standing
        assertTrue( fov.isVisible( 1, 1 ) );

        // simple corridor sight
        assertTrue( fov.isVisible( 5, 1 ) );
        assertTrue( fov.isVisible( 1, 4 ) );

        // can see onto walls
        assertTrue( fov.isVisible( 0, 1 ) );
        assertTrue( fov.isVisible( 1, 0 ) );
        assertTrue( fov.isVisible( 6, 1 ) );
        assertTrue( fov.isVisible( 1, 5 ) );

        // can see corner walls
        assertTrue( fov.isVisible( 0, 0 ) );

        // blocked by walls
        assertFalse( fov.isVisible( 5, 3 ) );
    }

}