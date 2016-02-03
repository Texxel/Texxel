package com.github.texxel.mechanics;

import com.github.texxel.utils.Arrays2D;
import com.github.texxel.utils.Point2D;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * The BasicFOV class determines a FieldOfVision through a INSERT FORMULA USED HERE. The BasicFOV
 * can only operate on an rectangular grid.
 */
public class BasicFOV implements FieldOfVision {
    private static final long serialVersionUID = -8194245029904709243L;

    // TODO BasicFOV doesn't see correctly
    // see BasicFOVTest.testNormalRoom() for errors

    protected final int width, height;
    Point2D location;
    boolean[][] solid;
    int radius;

    transient boolean[][] visible;
    transient boolean dirty;

    /**
     * Exact same as {@code BasicFOV( solids, location, 8 )}.
     * @see #BasicFOV(boolean[][], Point2D, int)
     */
    public BasicFOV( boolean[][] solids, Point2D location ) {
        this( solids, location, 8 );
    }

    /**
     * Constructs a FieldOfVision with the view positioned at {@code location} with line of sight
     * blocking objects defined by {@code solids}. The solids array is copied at runtime, thus,
     * changes to the array will not effect this class.
     * @param solids the line of sight blocking cells in the world
     * @param location the viewer's location
     * @param viewDistance the radius of the fov
     */
    public BasicFOV( boolean[][] solids, Point2D location, int viewDistance ) {
        if ( !Arrays2D.isRectangular( solids ) )
            throw new IllegalArgumentException( "passables must be rectangular" );
        if ( solids.length == 0 || solids[0].length == 0 )
            throw new IllegalArgumentException( "passables must be larger than a 1x1 array" );
        if ( location == null )
            throw new NullPointerException( "location cannot be null" );
        if ( !Arrays2D.contains( solids, location.x, location.y ) )
            throw new IndexOutOfBoundsException( "location is not inside the grid. Passed " + location );
        if ( viewDistance < 0 )
            throw new IndexOutOfBoundsException( "view distance must be >= 0. Passed " + viewDistance );

        this.width = solids.length;
        this.height = solids[0].length;
        this.radius = viewDistance;
        this.solid = Arrays2D.copyOf( solids );
        // *2 'cause we need diameter. +1 'cause add origin
        visible = new boolean[viewDistance*2+1][viewDistance*2+1];
        this.location = location;
        dirty = true;
    }

    @Override
    public void setLocation( Point2D location ) {
        if ( location == null )
            throw new NullPointerException( "location cannot be null" );
        if ( !Arrays2D.contains( solid, location.x, location.y ) )
            throw new IllegalArgumentException( "location cannot be outside grid. Passed " + location );
        if ( this.location.equals( location ) )
            return;
        this.location = location;
        dirty = true;
    }

    @Override
    public Point2D getLocation() {
        return location;
    }

    @Override
    public void setViewDistance( int viewDistance ) {
        if ( viewDistance < 0 )
            throw new IllegalArgumentException( "view distance must be >= 0. Passed " + viewDistance );
        if ( this.radius == viewDistance )
            return;
        this.radius = viewDistance;
        // *2 'cause we need diameter. +1 'cause add origin
        this.visible = new boolean[viewDistance*2+1][viewDistance*2+1];
        dirty = true;
    }

    @Override
    public boolean isKnown( int x, int y ) {
        return true;
    }

    @Override
    public void setKnown( int x, int y, boolean discovered ) {
        // ignored
    }

    @Override
    public int getViewDistance() {
        return radius;
    }

    @Override
    public boolean isVisible( int x, int y ) {
        if ( dirty )
            update();
        if ( !Arrays2D.contains( solid, x, y ) )
            throw new IndexOutOfBoundsException( "location is outside of the world. Passed " + x + " " + y );
        // translate world units to vision units
        x -= location.x - radius;
        y -= location.y - radius;
        return Arrays2D.contains( visible, x, y ) && visible[x][y];
    }

    @Override
    public boolean isVisible( Point2D point ) {
        return isVisible( point.x, point.y );
    }

    @Override
    public boolean isSolid( int x, int y ) {
        return solid[x][y];
    }

    @Override
    public void setSolid( int x, int y, boolean solid ) {
        if ( this.solid[x][y] == solid )
            return;
        this.solid[x][y] = solid;
        dirty = true;
    }

    /**
     * Performs a full update of the FOV
     */
    protected void update() {
        dirty = false;
        Arrays2D.fill( visible, false );

        //System.err.println( "New scan started. Radius=" + radius );

        scanSector( 1, 0, 0, 1 );
        scanSector( 0, 1, 1, 0 );
        scanSector( 0, 1,-1, 0 );
        scanSector( 1, 0, 0,-1 );
        scanSector(-1, 0, 0,-1 );
        scanSector( 0,-1,-1, 0 );
        scanSector( 0,-1, 1, 0 );
        scanSector(-1, 0, 0, 1 );

        // the all scans miss the origin. Add it manually
        visible[radius][radius] = true;
    }

    void scanSector( int xx, int xy, int yx, int yy ) {
        //System.out.println( "Scan sector started: " + xx + " " + xy + " " + yx + " " + yy );
        scanSector( 1, 0, 0.0f, 1.0f, xx, xy, yx, yy, 0 );
        //System.out.println( "Scan sector finished" );
    }


    /**
     * Scans a sector of the world to calculate what is visible and what is not visible. The
     * algorithm uses a recursive shadow casting technique, very similar to the one found
     * <a href="http://www.roguebasin.com/index.php?title=FOV_using_recursive_shadowcasting">here</a>.
     * This method's algorithm runs it calculation in the standard right hand cartesian co-ordinate
     * system (y-up, x-right) and operates in the first 45deg (anticlockwise from x-axis) and
     * converts co-ordinates in all other sectors to co-ordinates in that sector.
     * @param xStart the x start cell relative to the view (should pass 1 for starting scan)
     * @param yStart the y start cell relative to the view (should pass 0 for starting scan)
     * @param startSlope the bottom slope to start scanning from (should pass 0 for starting scan)
     * @param endSlope the top slope to stop scanning at (should pass 1 for starting scan)
     * @param xx, xy, yx, yy: the 2x2 magic transformation matrix
     */
    void scanSector( int xStart, int yStart,
                     float startSlope, float endSlope,
                     int xx, int xy, int yx, int yy,
                     int depth ) {
        int width = this.width;
        int height = this.height;
        int radius = this.radius;
        int xLoc = location.x;
        int yLoc = location.y;

        for ( int i = xStart; i < radius; i++ ) {
            boolean lastWasSolid = false;
            for ( int j = yStart; j < radius; j++ ) {
                //println( "i=" + i + " j=" + j, depth );
                if ( i*i + j*j > radius*radius )
                    break;
                // slopes on left corners of the cell
                float topSlope = (j+0.5f)/(i-0.5f);
                float botSlope = (j-0.5f)/(i-0.5f);
                //println( "i=" + i + " j=" + j + " t=" + topSlope + " b=" + botSlope, depth );
                if ( botSlope > endSlope )
                    break; // goto next row
                if ( topSlope < startSlope ) {
                    // we can start a little higher next time
                    yStart++;
                    continue; // try again one cell higher
                }
                //print( "*", 0 );
                //  (i,j) : coords in transformed space
                // (vx,vy): coords in vision space
                // (wx,wy): coords in world space
                int vx = i * xx + j * xy;
                int vy = i * yx + j * yy;
                int wx = vx + xLoc;
                int wy = vy + yLoc;
                vx += radius;
                vy += radius;
                // check vision hasn't exceeded world bounds
                if ( wx < 0 || wx >= width )
                    continue;
                if ( wy < 0 || wy >= height )
                    continue;

                visible[vx][vy] = true;
                if ( solid[wx][wy] ) {
                    if ( lastWasSolid )
                        break;
                    // end slope = slope on bottom right corner
                    float nextEndSlope = (j-0.5f)/(i+0.5f);
                    if ( nextEndSlope > startSlope ) {
                        // optimisation. Nothing will get found when this is false
                        //println( "start wall slope bounds: s=" + startSlope + " e=" + nextEndSlope, depth );
                        scanSector( i + 1, yStart, startSlope, nextEndSlope, xx, xy, yx, yy, depth + 1 );
                    //} else {
                        //println( "skipped scan: s=" + startSlope + " e=" + nextEndSlope, depth );
                    }
                    lastWasSolid = true;
                } else {
                    if ( lastWasSolid ) {
                        // we're at the end of a wall
                        // let another scanner with restricted slopes take over
                        //println( "end wall slope bounds: s=" + botSlope + " e=" + endSlope, depth );
                        scanSector( i, j, botSlope, endSlope, xx, xy, yx, yy, depth+1 );
                        return;
                    }
                }
            }
            // there's a wall blocking right to the top
            if ( lastWasSolid )
                return;
        }
    }

    private void readObject( ObjectInputStream inputStream ) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        dirty = true;
        this.visible = new boolean[radius*2+1][radius*2+1];
    }

    //private void print( String message, int depth ) {
        //for ( int k = 0; k < depth; k++ )
            //System.out.print( "  " );
        //System.out.print( message );
    //}

    //private void println( String message, int depth ) {
    //    print( message + '\n', depth );
    //}

}
