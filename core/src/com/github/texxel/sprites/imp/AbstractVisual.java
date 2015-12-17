package com.github.texxel.sprites.imp;

import com.badlogic.gdx.graphics.Color;
import com.github.texxel.sprites.api.Visual;
import com.github.texxel.utils.Point2D;

public abstract class AbstractVisual implements Visual {

    private float x, y;
    private int depth;
    private float width = 1, height = 1;
    private float xOffset, yOffset;
    private float xScale = 1, yScale = 1;
    private Point2D direction = Point2D.RIGHT;
    private float rotation;
    private int color = Color.WHITE.toIntBits();

    @Override
    public float x() {
        return x;
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public int depth() {
        return depth;
    }

    @Override
    public Visual setDepth( int depth ) {
        this.depth = depth;
        return this;
    }

    @Override
    public Visual setLocation( float x, float y ) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public float width() {
        return width;
    }

    @Override
    public float height() {
        return height;
    }

    @Override
    public Visual setSize( float width, float height ) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public Visual setScale( float x, float y ) {
        xScale = x;
        yScale = y;
        return this;
    }

    @Override
    public float yScale() {
        return yScale;
    }

    @Override
    public float xScale() {
        return xScale;
    }

    @Override
    public Visual setOffset( float x, float y ) {
        xOffset = x;
        yOffset = y;
        return this;
    }

    @Override
    public float yOffset() {
        return yOffset;
    }

    @Override
    public float xOffset() {
        return xOffset;
    }

    @Override
    public Visual setDirection( Point2D dir ) {
        if ( dir == null )
            throw new NullPointerException( "'dir' cannot be null" );
        this.direction = dir;
        return this;
    }

    @Override
    public Point2D getDirection() {
        return direction;
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public Visual setRotation( float rotation ) {
        this.rotation = rotation;
        return this;
    }

    @Override
    public Visual setColor( int color ) {
        this.color = color;
        return this;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void update( float delta ) {

    }
}
