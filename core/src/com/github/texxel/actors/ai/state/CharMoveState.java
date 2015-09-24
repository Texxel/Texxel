package com.github.texxel.actors.ai.state;

import com.github.texxel.Dungeon;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.State;
import com.github.texxel.actors.ai.actions.IdleAction;
import com.github.texxel.actors.ai.actions.WalkAction;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.events.actor.CharTargetEvent;
import com.github.texxel.event.listeners.actor.CharTargetListener;
import com.github.texxel.levels.Level;
import com.github.texxel.mechanics.PathFinder;
import com.github.texxel.utils.Arrays2D;
import com.github.texxel.utils.Point2D;

import java.util.List;

public abstract class CharMoveState implements State, CharTargetListener {

    private final Char character;
    private Point2D target;
    private final boolean[][] passable;

    public CharMoveState( Char character ) {
        this.character = character;
        passable = new boolean[Dungeon.level().width()][Dungeon.level().height()];
        character.getTargetHandler().addListener( this, EventHandler.VERY_LATE );
    }

    @Override
    public void onStart() {
    }

    @Override
    public void update() {
        if ( target == null ) {
            onNoTarget();
            return;
        }
        if ( character.getLocation().equals( target ) ) {
            onTargetReached();
            return;
        }
        Point2D step = nextPoint();
        if ( step == null ) {
            onCannotReachTarget();
            return;
        }
        if ( passable[step.x][step.y] ) {
            character.setNextAction( new WalkAction( character, step ) );
        } else {
            if ( PathFinder.isNextTo( step.x, step.y, target.x, target.y ) )
                onTargetReached();
        }
    }

    @Override
    public void onRemove() {
        character.getTargetHandler().removeListener( this, EventHandler.VERY_LATE );
    }

    @Override
    public void onCharTarget( CharTargetEvent e ) {
        this.target = e.getTarget();
    }

    public void setTarget( Point2D target ) {
        character.target( target );
        if ( target == null )
            character.setNextAction( new IdleAction( character ) );
    }

    public Point2D getTarget() {
        return target;
    }

    public Char getCharacter() {
        return character;
    }

    public Point2D nextPoint() {
        Level level = Dungeon.level();
        boolean[][] passable = this.passable;
        Char character = this.character;
        setPassables( passable );
        PathFinder pathFinder = PathFinder.sharedGrid( level.width(), level.height() );
        pathFinder.setUp( passable, target.x, target.y );
        Point2D loc = character.getLocation();
        return pathFinder.getNextStep( loc.x, loc.y );
    }

    public void setPassables( boolean[][] passables ) {
        Level level = Dungeon.level();
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

    public abstract void onTargetReached();

    public abstract void onCannotReachTarget();

    public abstract void onNoTarget();
}
