package com.github.texxel.tiles;

import com.github.texxel.Texxel;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.levels.Level;
import com.github.texxel.levels.components.LevelDescriptor;
import com.github.texxel.scenes.IntervalLevelScreen;

public abstract class StairsTile extends AbstractTile implements Interactable {

    private static final long serialVersionUID = -335016907648851199L;

    private final Level level;
    private final int x, y;
    private LevelDescriptor targetLevel;

    public StairsTile( Level level, LevelDescriptor nextLevel, int x, int y ) {
        if ( level == null )
            throw new NullPointerException( "'level' cannot be null" );
        if ( nextLevel == null )
            throw new NullPointerException( "'nextLevel' cannot be null" );
        this.level = level;
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
        if ( !(ch instanceof Hero) )
            return;
        Texxel.getInstance().setScreen(
                new IntervalLevelScreen( level.dungeon(), level, targetLevel, (Hero)ch ) );
    }

    @Override
    public boolean canInteract( Char ch ) {
        // only hero's can move though levels
        return ch instanceof Hero && ch.getLocation().equals( x, y );
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
