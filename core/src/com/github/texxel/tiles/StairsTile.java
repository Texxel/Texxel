package com.github.texxel.tiles;

/**
 * A basic implementation of the PortalTile
 */
public abstract class StairsTile extends AbstractTile implements PortalTile {

    private static final long serialVersionUID = -335016907648851199L;

    private int targetLevel;

    public StairsTile( int nextLevel ) {
        this.targetLevel = nextLevel;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isPassable() {
        return true;
    }

    /**
     * Sets the level that will be descended to
     * @param target where the stairs will go to
     */
    public void setTargetLevel( int target ) {
        this.targetLevel = target;
    }

    @Override
    public int getTargetLevel() {
        return targetLevel;
    }

}
