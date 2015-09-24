package com.github.texxel.levels.components;

import com.github.texxel.tiles.Tile;
import com.github.texxel.utils.Rectangle;

/**
 * Tile Fillers create an easy way to fill in a section of a TileMap in some basic shapes.
 */
public interface TileFiller {

    /**
     * Paints the tiles onto the map.
     * @param tileMap the map to perform the operation on
     */
    void paint( TileMap tileMap );

    /**
     * The FullFiller covers the entire tile map in a single tile
     */
    abstract class FullFiller implements TileFiller {
        @Override
        public void paint( TileMap tileMap ) {
            final int width = tileMap.width();
            final int height = tileMap.height();
            for ( int i = 0; i < width; i++ ) {
                for ( int j = 0; j < height; j++ ) {
                    tileMap.setTile( i, j, makeTile( i, j ) );
                }
            }
        }

        public abstract Tile makeTile( int x, int y );
    }

    /**
     * The SolidSquare paainter is used to fill in a rectangle of a map with a solid tile
     */
    abstract class SolidSquare implements TileFiller {

        private final int left, right, top, bottom;

        /**
         * Makes a solid square painter that will fill in all tiles inside the square
         * @param rectangle the rectangle to fill in
         */
        public SolidSquare( Rectangle rectangle ) {
            this.left = rectangle.x;
            this.right = rectangle.x2;
            this.top = rectangle.y2;
            this.bottom = rectangle.y;
        }

        /**
         * Makes a solid square painter from the specified bounds. All bounds are included in the
         * tile filling.
         */
        public SolidSquare( int left, int top, int right, int bottom ) {
            if ( left > right )
                throw new IllegalArgumentException( "left cannot be greater than right. Passed: left="
                        + left + ", right=" + right );
            if ( bottom > top )
                throw new IllegalArgumentException( "bottom cannot be greater than top. Passed: top="
                        + top + ", bottom=" + bottom );
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }

        @Override
        public void paint( TileMap tileMap ) {
            int right = this.right + 1;
            int top = this.top + 1;
            for ( int i = left; i < right; i++ ) {
                for ( int j = bottom; j < top; j++ ) {
                    tileMap.setTile( i, j, makeTile( i, j ) );
                }
            }
        }

        public abstract Tile makeTile( int x, int y );

    }

    /**
     * The Border filler paints the border in one type of tile and the inside in another type
     */
    abstract class Border implements TileFiller {

        private final int left, right, top, bottom;

        /**
         * Makes a border filler that will fill in all tiles inside the square
         * @param rectangle the rectangle to fill in
         */
        public Border( Rectangle rectangle ) {
            this.left = rectangle.x;
            this.top = rectangle.y2;
            this.bottom = rectangle.y;
            this.right = rectangle.x2;
        }

        /**
         * Makes a border filler from the specified bounds. All bounds are included in the
         * tile filling.
         */
        public Border( int left, int top, int right, int bottom ) {
            if ( left > right )
                throw new IllegalArgumentException( "left cannot be greater than right. Passed: left="
                        + left + ", right=" + right );
            if ( bottom > top )
                throw new IllegalArgumentException( "bottom cannot be greater than top. Passed: top="
                        + top + ", bottom=" + bottom );
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }


        @Override
        public void paint( TileMap tileMap ) {
            int left = this.left;
            int right = this.right;
            int top = this.top;
            int bottom = this.bottom;
            for ( int i = left; i <= right; i++ ) {
                for ( int j = bottom; j <= top; j++ ) {
                    if ( i == left || i == right || j == top || j == bottom )
                        tileMap.setTile( i, j, makeOuterTile( i, j ) );
                    else
                        tileMap.setTile( i, j, makeInnerTile( i, j ) );
                }
            }
        }

        public abstract Tile makeInnerTile ( int x, int y );

        public abstract Tile makeOuterTile ( int x, int y );
    }

}
