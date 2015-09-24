package com.github.texxel.mechanics;

import com.github.texxel.utils.Point2D;

import junit.framework.TestCase;

public class PathFinderTest extends TestCase {
    private static final boolean[][] passables = {
            { false, false, false, false, false, false, false },
            { false, true,  true,  true,  false, true,  false },
            { false, true,  false, true,  false, true,  false },
            { false, true,  false, true,  false, true,  false },
            { false, true,  true,  true,  true,  true,  false },
            { false, false, false, false, false, false, false }
    };

    private static final boolean[][] openField = {
            { false, false, false, false, false, false, false },
            { false, true,  true,  true,  true,  true,  false },
            { false, true,  true,  true,  true,  true,  false },
            { false, true,  true,  true,  true,  true,  false },
            { false, true,  true,  true,  true,  true,  false },
            { false, false, false, false, false, false, false }
    };

    /**
     * Quick and dirty way of making a pathfinder from a visual string array. 'X' goes to wall.
     * @return a constructed path finder.
     */
    PathFinder makeFinder( String[] walls, int x, int y ) {
        boolean[][] grid = BasicFOVTest.makeMap( walls );
        PathFinder finder = PathFinder.newGrid( grid.length, grid[0].length );
        finder.setUp( grid, x, y );
        return finder;
    }

    // tests that a correct path can be found
    public void testPathMaker() throws Exception {
        PathFinder pathFinder = PathFinder.newGrid( 6, 7 );
        pathFinder.setUp( passables, 3, 1 );
        Point2D[] path = pathFinder.getPathFrom( 1, 5 );
        assertEquals( 6, path.length );
        assertEquals( new Point2D( 2, 5 ), path[0] ); // first step
        assertEquals( new Point2D( 3, 5 ), path[1] );
        assertEquals( new Point2D( 4, 4 ), path[2] );
        // assertEquals( new Point2D( 4, 3 ), path[3] ); // may be 3,3 if not straight
        assertEquals( new Point2D( 4, 2 ), path[4] );
        assertEquals( new Point2D( 3, 1 ), path[5] ); // end square
    }

    // tests the the created path attempts to go straight instead of crooked
    public void testPathStraightness() throws Exception {
        PathFinder pathFinder = PathFinder.newGrid( 6, 7 );
        pathFinder.setUp( openField, 4, 3 );
        Point2D[] path = pathFinder.getPathFrom( 1, 3 );
        assertEquals( 3, path.length );
        assertEquals( new Point2D( 2, 3 ), path[0] );
        assertEquals( new Point2D( 3, 3 ), path[1] );
        assertEquals( new Point2D( 4, 3 ), path[2] );

        pathFinder.setUp( openField, 2, 5 );
        path = pathFinder.getPathFrom( 2, 1 );
        assertEquals( 4, path.length );
        assertEquals( new Point2D( 2, 2 ), path[0] );
        assertEquals( new Point2D( 2, 3 ), path[1] );
        assertEquals( new Point2D( 2, 4 ), path[2] );
        assertEquals( new Point2D( 2, 5 ), path[3] );
    }

    public void testGeneration() throws Exception {
        PathFinder pathFinder = PathFinder.newGrid( 6, 7 );
        assertEquals( 6, pathFinder.getWidth() );
        assertEquals( 7, pathFinder.getHeight() );
    }

    public void testIllegalArgSetup() {
        try {
            PathFinder.newGrid( 3, 4 ).setUp( passables, 2, 2 );
            fail();
        } catch ( IllegalArgumentException ignored ) {
        }

        try {
            PathFinder.newGrid( 6, 7 ).setUp( passables, 6, 7 );
            fail();
        } catch ( IndexOutOfBoundsException ignored ) {
        }
    }

    public void testDistanceMap() throws Exception {
        PathFinder pathFinder = PathFinder.newGrid( 6, 7 );
        pathFinder.setUp( passables, 1, 3 );
        assertEquals( 0, pathFinder.distanceFrom( 1, 3 ) );
        assertEquals( 1, pathFinder.distanceFrom( 2, 3 ) );
        assertEquals( 3, pathFinder.distanceFrom( 4, 3 ) );
        assertEquals( 4, pathFinder.distanceFrom( 4, 1 ) );
        assertEquals( 3, pathFinder.distanceFrom( 3, 1 ) );
        assertEquals( 6, pathFinder.distanceFrom( 1, 5 ) );
        assertEquals( Integer.MAX_VALUE, pathFinder.distanceFrom( 0, 0 ) );
    }

    public void testNextStep() throws Exception {
        PathFinder pathFinder = PathFinder.newGrid( 6, 7 );
        pathFinder.setUp( passables, 1, 1 );
        Point2D nextStep = pathFinder.getNextStep( 1, 5 );
        assertEquals( new Point2D( 2, 5 ), nextStep );
    }

    public void testGoToSelf() {
        @SuppressWarnings( "SpellCheckingInspection" )
        PathFinder pathFinder = makeFinder( new String[] {
                "XXXXXXX",
                "X.....X",
                "X.....X",
                "X.....X",
                "XXXXXXX"
        }, 2, 2 );
        Point2D next = pathFinder.getNextStep( 2, 2 );
        assertEquals( new Point2D( 2, 2 ), next );

        Point2D[] steps = pathFinder.getPathFrom( 2, 2 );
        assertEquals( 0, steps.length );
    }

    public void testGoToWall() throws Exception{
        boolean[][] grid = new boolean[][] {
                { true,  true,  true,  true,  true },
                { true, false, false, false,  true },
                { true, false, false, false,  true },
                { true, false, false, false,  true },
                { true,  true,  true,  true,  true },
        };
        PathFinder pathFinder = PathFinder.newGrid( 5, 5 );
        pathFinder.setUp( grid, 2, 3 );

        Point2D next = pathFinder.getNextStep( 0, 0 );
        assertEquals( new Point2D( 0, 1 ), next );

        Point2D[] path = pathFinder.getPathFrom( 0, 0 );
        assertEquals( path.length, 5 );
        assertEquals( new Point2D( 2, 3 ), path[4] );
    }

    public void testGoToImpossible() {
        boolean[][] grid = new boolean[][] {
                { true,  true,  true,  true,  true },
                { true, false, false, false,  true },
                { true, false, false, false,  true },
                { true, false, false, false,  true },
                { true,  true,  true,  true,  true },
        };
        PathFinder pathFinder = PathFinder.newGrid( 5, 5 );
        pathFinder.setUp( grid, 2, 2 );

        Point2D next = pathFinder.getNextStep( 0, 0 );
        assertNull( next );

        Point2D[] path = pathFinder.getPathFrom( 0, 0 );
        assertNull( path );
    }
}