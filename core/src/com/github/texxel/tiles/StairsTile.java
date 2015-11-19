package com.github.texxel.tiles;

import com.github.texxel.Dungeon;
import com.github.texxel.actors.Char;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;

public abstract class StairsTile extends AbstractTile implements Interactable {

    private Dungeon dungeon;
    private int x, y;
    private int targetLevel;

    public StairsTile( Dungeon dungeon, int targetLevel, int x, int y ) {
        this.dungeon = dungeon;
        this.targetLevel = targetLevel;
        this.x = x;
        this.y = y;
    }

    protected StairsTile( Bundle bundle ) {
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = super.bundle( topLevel );
        bundle.putInt( "x", x );
        bundle.putInt( "y", y );
        bundle.putInt( "target", targetLevel );
        bundle.putBundlable( "dungeon", dungeon );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        super.restore( bundle );
        x = bundle.getInt( "x" );
        y = bundle.getInt( "x" );
        targetLevel = bundle.getInt( "target" );
        dungeon = bundle.getBundlable( "dungeon" );
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

    @Override
    public void interact( Char ch ) {
        // dungeon.goTo( targetLevel );
    }

    @Override
    public boolean canInteract( Char ch ) {
        return ch.getLocation().equals( x, y );
    }

    /**
     * Sets the level that will be descended to
     * @param levelID the id of the level to descend to
     */
    void setTargetLevel( int levelID ) {
        this.targetLevel = levelID;
    }

    /**
     * Gets the id of the level that will be moved to
     * @return the next level's id
     */
    int getTargetLevel() {
        return targetLevel;
    }

}
