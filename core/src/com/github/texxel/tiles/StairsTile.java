package com.github.texxel.tiles;

import com.github.texxel.Dungeon;
import com.github.texxel.Texxel;
import com.github.texxel.actors.Char;
import com.github.texxel.levels.components.LevelDescriptor;
import com.github.texxel.scenes.IntervalLevelScreen;

public abstract class StairsTile extends AbstractTile implements Interactable {

    private static final long serialVersionUID = -335016907648851199L;

    private final Dungeon dungeon;
    private final int x, y;
    private LevelDescriptor targetLevel;

    public StairsTile( Dungeon dungeon, LevelDescriptor nextLevel, int x, int y ) {
        if ( dungeon == null )
            throw new NullPointerException( "'dungeon' cannot be null" );
        if ( nextLevel == null )
            throw new NullPointerException( "'nextLevel' cannot be null" );
        this.dungeon = dungeon;
        this.targetLevel = nextLevel;
        this.x = x;
        this.y = y;
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
        Texxel.getInstance().setScreen(
                new IntervalLevelScreen( dungeon, targetLevel, transitionReason() ) );
    }

    protected abstract IntervalLevelScreen.TransitionReason transitionReason();

    @Override
    public boolean canInteract( Char ch ) {
        return ch.getLocation().equals( x, y );
    }

    /**
     * Sets the level that will be descended to
     * @param target where the stairs will go to
     */
    public void setTargetLevel( LevelDescriptor target ) {
        if ( target == null )
            throw new NullPointerException( "'target' cannot be null" );
        this.targetLevel = target;
    }

    /**
     * Gets the id of the level that will be moved to
     * @return the next level's descriptor
     */
    public LevelDescriptor getTargetLevel() {
        return targetLevel;
    }

}
