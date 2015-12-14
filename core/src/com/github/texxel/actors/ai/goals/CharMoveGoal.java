package com.github.texxel.actors.ai.goals;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.actions.StepAction;
import com.github.texxel.levels.Level;
import com.github.texxel.mechanics.PathFinder;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.utils.Arrays2D;
import com.github.texxel.utils.Point2D;

import java.util.List;

/**
 * The char mover will
 */
public abstract class CharMoveGoal implements Goal {

    private Char character;
    private Point2D target;
    private boolean[][] passable;
    private Level level;

    public CharMoveGoal( Char character, Point2D target ) {
        if ( character == null )
            throw new NullPointerException( "'character' cannot be null" );
        if ( target == null )
            throw new NullPointerException( "'target' cannot be null" );
        level = character.level();
        this.character = character;
        this.target = target;
        passable = new boolean[level.width()][level.height()];
    }

    public CharMoveGoal( Bundle bundle ) {

    }

    @Override
    public void onStart() {
    }

    @Override
    public Action nextAction() {
        if ( character.getLocation().equals( target ) ) {
            return onTargetReached();
        }
        Point2D step = nextPoint();
        if ( step == null ) {
            return onCannotReachTarget();
        }
        if ( passable[step.x][step.y] ) {
            return new StepAction( character, step );
        } else {
            if ( PathFinder.isNextTo( step.x, step.y, target.x, target.y ) ) {
                return onTargetReached();
            } else {
                throw new RuntimeException( "Can reach target but must go through solid wall to do so?!?" );
            }
        }
    }

    @Override
    public void onRemove() {

    }

    public void setTarget( Point2D target ) {
        if ( target == null )
            throw new NullPointerException( "'target' cannot be null" );
        this.target = target;
    }

    public Point2D getTarget() {
        return target;
    }

    public Char getCharacter() {
        return character;
    }

    public Point2D nextPoint() {
        Level level = this.level;
        boolean[][] passable = this.passable;
        Char character = this.character;
        setPassables( passable );
        PathFinder pathFinder = PathFinder.sharedGrid( level.width(), level.height() );
        pathFinder.setUp( passable, target.x, target.y );
        Point2D loc = character.getLocation();
        return pathFinder.getNextStep( loc.x, loc.y );
    }

    public void setPassables( boolean[][] passables ) {
        Level level = this.level;
        Arrays2D.copy( level.getTileMap().getPassables(), passables );
        List<Char> characters = level.getCharacters();
        int size = characters.size();
        for ( int i = 0; i < size; i++ ) {
            Point2D loc = characters.get( i ).getLocation();
            if ( character.getVision().isVisible( loc.x, loc.y ) )
                passables[loc.x][loc.y] = false;
        }
        Point2D loc = getCharacter().getLocation();
        passables[loc.x][loc.y] = true;
    }

    /**
     * Called when the character reaches the target
     */
    public abstract Action onTargetReached();

    /**
     * Called when there is no what to reach the target
     */
    public abstract Action onCannotReachTarget();

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = topLevel.newBundle();
        bundle.putNNBundlable( "character", character );
        bundle.putNNBundlable( "target", target );
        bundle.put( "passable", passable );
        bundle.putNNBundlable( "level", level );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        character = bundle.getNNBundlable( "character" );
        target = bundle.getNNBundlable( "target" );
        passable = bundle.getBooleanArray( "passable" );
        level = bundle.getNNBundlable( "level" );
    }
}
