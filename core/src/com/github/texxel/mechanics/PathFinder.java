package com.github.texxel.mechanics;

import com.github.texxel.utils.Arrays2D;
import com.github.texxel.utils.Point2D;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * The PathFinder class is able to determine the shortest path between two points. Before using any
 * methods of the PathFinder, the class must be setup with {@link #setUp(boolean[][], int, int)}.
 * Failing to call that method will result in an IllegalStateException
 */
public final class PathFinder {

    /**
     * Makes a brand new grid for path finding
     * @param width the width to create the grid
     * @param height the height to create the grid
     * @return the new grid
     */
    public static PathFinder newGrid( int width, int height ) {
        return new PathFinder( width, height );
    }

    // using a Point2D to represent a dimension. Yuck!
    private static HashMap<Point2D, SoftReference<PathFinder>> sharedGrids = new HashMap<>();

    // note: order of surrounds is important to form a straight path
    private static final int[] xSurrounds = new int[] {
            -1, 1, 0, 0, -1, -1, 1, 1
    }, ySurrounds = new int[] {
            0, 0, -1, 1, -1, 1, -1, 1
    };

    private static int MAX_USED = 0;

    private final int width, height;
    private boolean[][] passables;
    private final int[][] distance;
    private int xTarget, yTarget = -1;
    boolean dirty = true;
    // temporary storage array
    private final int[] xData, yData;

    private PathFinder( int width, int height ) {
        if ( width <= 0 || height <= 0 )
            throw new IllegalArgumentException( "width and height must be greater than 0. "
                + "Passed width=" + width + " height=" + height );
        this.width = width;
        this.height = height;
        this.distance = new int[width][height];
        this.xData = new int[width*height];
        this.yData = new int[width*height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Sets up this PathFinder with the given passable array and a starting position. Failing to
     * call this method before the others will result in a {@link IllegalStateException}
     * @param passables an array of passable cells
     * @param xTarget the x point trying to get to
     * @param yTarget the y point trying to get to
     * @throws NullPointerException if passables is null
     * @throws IllegalArgumentException if passables dimensions does not match the grids dimensions
     * @throws IndexOutOfBoundsException if (xTarget,yTarget) is out of bounds
     */
    public void setUp( boolean[][] passables, int xTarget, int yTarget ) {
        if ( passables == null )
            throw new NullPointerException( "passables cannot be null" );
        if ( !Arrays2D.isRectangular( passables ) )
            throw new IllegalArgumentException( "passables dimensions must be rectangular" );
        if ( passables.length != width || passables[0].length != height )
            throw new IllegalArgumentException( "dimensions of passables is not equal to created grid." +
                    " Passed " + width + "x" + height + ". Wanted " + passables.length + "x" + passables[0].length );
        if ( !inBounds( xTarget, yTarget ) )
            throw new IndexOutOfBoundsException( "target was not in bounds. Passed: xTarget=" + xTarget
                    + " yTarget=" + yTarget );

        // we will always need to refresh the distance map as
        // the passables array may have changed

        this.passables = passables;
        this.xTarget= xTarget;
        this.yTarget = yTarget;
        dirty = true;
    }

    /**
     * Gets the amount of steps that will be required to get from the passed start point to the
     * pre-configured target.
     * @param xStart where the x start point is
     * @param yStart where the y start point is
     * @return the distance between the points
     * @throws IllegalStateException if {@link #setUp(boolean[][], int, int)} has not been called
     * @throws IndexOutOfBoundsException if the point is outside of the bounds
     */
    public int distanceFrom( int xStart, int yStart ) {
        if ( passables == null )
            throw new IllegalStateException( "path finder has not been setup yet" );
        if ( !inBounds( xStart, yStart ) )
            throw new IndexOutOfBoundsException( "start point out of bounds. Passed: xStart="
            + xStart + " yStart=" + yStart );

        if ( dirty )
            buildDistanceMap();
        return distance[xStart][yStart];
    }

    /**
     * Tests if the target can be reached from another point. A target is defined as reachable if a
     * path can be found the the tile or, in the case the targeted tile is solid, to one of the 8
     * adjacent tiles.
     * @param xStart the x starting point
     * @param yStart the y starting point
     * @return true if the target can be reached
     * @throws IndexOutOfBoundsException if (xStart,yStart) is out of the grid
     */
    public boolean canReach( int xStart, int yStart ) {
        return distanceFrom( xStart, yStart ) != Integer.MAX_VALUE;
    }

    /**
     * Fills in {@link #distance} with the distance from the target point
     */
    private void buildDistanceMap() {
        if ( !dirty )
            return;
        Arrays2D.fill( distance, Integer.MAX_VALUE );
        int[] xData = this.xData;
        int[] yData = this.yData;
        int tail = 0;
        int head = 1;
        xData[0] = xTarget;
        yData[0] = yTarget;

        distance[xTarget][yTarget] = 0; // even if solid

        while ( tail < head ) {
            int xCheck = xData[tail];
            int yCheck = yData[tail];
            int d = distance[xCheck][yCheck];
            for ( int i = 0; i < xSurrounds.length; i++ ) {
                int xStep = xSurrounds[i];
                int yStep = ySurrounds[i];
                int xNext = xCheck + xStep;
                int yNext = yCheck + yStep;
                if ( !inBounds( xNext, yNext ) )
                    continue;
                if ( !passables[xNext][yNext] )
                    continue;
                int nextD = d + 1;
                if ( nextD < distance[xNext][yNext] ) {
                    distance[xNext][yNext] = nextD;
                    xData[head] = xNext;
                    yData[head] = yNext;
                    head++;
                }
            }
            tail++;
        }
        if ( head > MAX_USED ) {
            MAX_USED = head;
        }
        dirty = false;
    }

    private boolean inBounds( int x, int y ) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    /**
     * Finds the shortest (and straightest) path from the start point to the target. The returned
     * array will contain all the points that will need to be passed through to get to the target.
     * If it is not possible to get to the end point, then an empty array will be returned. The array
     * is ordered in same order that should be moved through. The path contains the end point but not
     * the start point, thus, the returned array will have a length equal to the amount of steps
     * that need to be taken. The next step that should be taken is at index 0. If the target is
     * set to a impassible tile, but it is possible to get to an adjacent tile, then the targeted
     * tile shall be treated as a non solid tile.
     * @param xStart the x target
     * @param yStart y target
     * @return the path to the point or null if it cannot be reached
     * @throws IndexOutOfBoundsException if (xStart,yStart) is outside of the grid
     */
    public Point2D[] getPathFrom( int xStart, int yStart ) {
        if ( !canReach( xStart, yStart ) )
            return null;
        int steps = this.distance[xStart][yStart];
        Point2D[] path = new Point2D[steps];
        int xCurrent = xStart;
        int yCurrent = yStart;

        int d = steps;
        outerLoop:
        while ( d > 0 ) {
            d--;
            for ( int i = 0; i < xSurrounds.length; i++ ) {
                int xStep = xSurrounds[i];
                int yStep = ySurrounds[i];
                int xNext = xCurrent + xStep;
                int yNext = yCurrent + yStep;
                if ( !inBounds( xNext, yNext ) )
                    continue;
                if ( distance[xNext][yNext] == d ) {
                    xCurrent = xNext;
                    yCurrent = yNext;
                    path[steps-d-1] = new Point2D( xCurrent, yCurrent );
                    continue outerLoop;
                }
            }
            throw new RuntimeException( "It should be impossible to get to here!" );
        }
        return path;
    }

    /**
     * Determines the next step that should be taken to get to the final goal. If the target tile is
     * a solid tile, then the target will be treated as a non solid tile. Thus, the returned point
     * may point to a impassible tile.
     * @param xStart the start point
     * @param yStart the start point
     * @return the next best point to step or null if it is impossible to reach
     * @throws IndexOutOfBoundsException if (xStart,yStart) is out of bounds
     */
    public Point2D getNextStep( int xStart, int yStart ) {
        if ( !canReach( xStart, yStart ) )
            return null;

        if ( xTarget == xStart && yStart == yTarget )
            return new Point2D( xStart, yStart );

        int minDistance = distance[xStart][yStart];
        int xBest = 0, yBest = 0;
        for ( int i = 0; i < xSurrounds.length; i++ ) {
            int xStep = xSurrounds[i];
            int yStep = ySurrounds[i];
            int xNext = xStart + xStep;
            int yNext = yStart + yStep;
            if ( !inBounds( xNext, yNext ) )
                continue;
            int d = distance[xNext][yNext];
            if ( d < minDistance ) {
                minDistance = d;
                xBest = xNext;
                yBest = yNext;
            }
        }
        return new Point2D( xBest, yBest );
    }

    /**
     * Gets the distance (or the amount of steps) between two points as the crow flies.
     * @return the distance between the two points (always >=  0)
     */
    public static int gridDistance( int x1, int y1, int x2, int y2 ) {
        int xDist = Math.abs( x1 - x2 );
        int yDist = Math.abs( y1 - y2 );
        return Math.max( xDist, yDist );
    }

    /**
     * The same as {@link #gridDistance(int, int, int, int)} but simplified for using Point2Ds
     * @param p1 the first point
     * @param p2 the second point
     * @return the distance between the points
     */
    public static int gridDistance( Point2D p1, Point2D p2 ) {
        return gridDistance( p1.x, p1.y, p2.x, p2.y );
    }

    /**
     * Tests if two points are directly next to each other or on top each other. Diagonals
     * are counted as being next to each other.
     * @return true if the two points are next to each other
     */
    public static boolean isNextTo( int x1, int y1, int x2, int y2 ) {
        int xDist = Math.abs( x1 - x2 );
        int yDist = Math.abs( y1 - y2 );
        return xDist <= 1 && yDist <= 1;
    }

    /**
     * The same as {@link #isNextTo(int, int, int, int)} but simplified for using Point2Ds
     * @param p1 one point
     * @param p2 another point
     * @return true if the points are directly next to each other
     */
    public static boolean isNextTo( Point2D p1, Point2D p2 ) {
        return isNextTo( p1.x, p1.y, p2.x, p2.y );
    }

}