package com.github.texxel.actors.ai.goals;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.actions.StepAction;
import com.github.texxel.levels.Level;
import com.github.texxel.mechanics.PathFinder;
import com.github.texxel.utils.Arrays2D;
import com.github.texxel.utils.Point2D;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * The char mover will
 */
public abstract class CharMoveGoal implements Goal {

    private static final long serialVersionUID = -434380692957738700L;

    private final Char character;
    private Point2D target;
    private transient boolean[][] passable;
    private final Level level;
    private transient PathFinder finder;

    public CharMoveGoal( Char character, Point2D target ) {
        if ( character == null )
            throw new NullPointerException( "'character' cannot be null" );
        if ( target == null )
            throw new NullPointerException( "'target' cannot be null" );
        level = character.level();
        this.character = character;
        this.target = target;
        passable = new boolean[level.width()][level.height()];
        finder = PathFinder.newGrid( level.width(), level.height() );
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
        PathFinder pathFinder = PathFinder.newGrid( level.width(), level.height() );
        pathFinder.setUp( passable, target.x, target.y );
        Point2D loc = character.getLocation();
        return pathFinder.getNextStep( loc.x, loc.y );
    }

    private void setPassables( boolean[][] passables ) {
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

    private void readObject( ObjectInputStream inputStream )
            throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        passable = new boolean[level.width()][level.height()];
        finder = PathFinder.newGrid( level.width(), level.height() );
    }

}
