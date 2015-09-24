package com.github.texxel.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.texxel.Dungeon;
import com.github.texxel.actors.ai.actions.IdleAction;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.state.CharWanderState;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.events.actor.CharMoveEvent;
import com.github.texxel.event.events.actor.CharTargetEvent;
import com.github.texxel.event.listeners.actor.CharMoveListener;
import com.github.texxel.event.listeners.actor.CharTargetListener;
import com.github.texxel.mechanics.BasicFOV;
import com.github.texxel.mechanics.FieldOfVision;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.utils.Point2D;

public abstract class AbstractChar extends AbstractActor implements Char {

    private Point2D location;
    private Point2D target;
    private FieldOfVision fov;
    private com.github.texxel.actors.ai.State currentState;
    private final EventHandler<CharTargetListener> targetHandler = new EventHandler<>();
    private final EventHandler<CharMoveListener> moveHandler = new EventHandler<>();

    /**
     * Constructs the char at the spawn point
     * @param spawn the point that the char should start at
     */
    public AbstractChar( Point2D spawn ) {
        if ( spawn == null )
            throw new IllegalArgumentException( "spawn cannot be null" );
        this.location = spawn;
        setState( new CharWanderState( this ) );
        setNextAction( new IdleAction( this ) );
    }

    /**
     * Constructs the char from a Bundle
     * @param bundle the bundle to construct from
     */
    protected AbstractChar( Bundle bundle ) {
        super( bundle );
    }

    @Override
    public Bundle bundle( BundleGroup topLevel ) {
        Bundle bundle = super.bundle( topLevel );
        bundle.put( "location", location.bundle( topLevel ) );
        bundle.put( "target", target.bundle( topLevel ) );
        return bundle;
    }

    @Override
    public void restore( Bundle bundle ) {
        super.restore( bundle );
        this.location = bundle.getBundlable( "location" );
        this.target = bundle.getBundlable( "target" );
    }

    @Override
    public Action getAction() {
        currentState.update();
        return super.getAction();
    }

    @Override
    public com.github.texxel.actors.ai.State getState() {
        return currentState;
    }

    @Override
    public void setState( com.github.texxel.actors.ai.State state ) {
        if ( state == null )
            throw new NullPointerException( "'state' cannot be null" );
        if ( currentState != null )
            currentState.onRemove();
        currentState = state;
        state.onStart();
    }

    @Override
    public void attack( Char enemy ) {
        System.out.print( name() + " attacked " + enemy.name() );
        spend( 1.0f );
    }

    @Override
    public Point2D getLocation() {
        return location;
    }

    @Override
    public Point2D getTarget() {
        return target;
    }

    @Override
    public boolean target( Point2D target ) {
        CharTargetEvent e = new CharTargetEvent( this, target );
        targetHandler.dispatch( e );
        if ( e.isCancelled() )
            return false;
        this.target = e.getTarget();
        return true;
    }

    @Override
    public Point2D setLocation( Point2D location ) {
        if ( location == null )
            throw new NullPointerException( "'location' cannot be null" );

        // inform listeners
        CharMoveEvent e = new CharMoveEvent( this, this.location, location );
        moveHandler.dispatch( e );
        location = e.getTo();
        if ( e.isCancelled() )
            return null;

        // set the location
        this.location = location;
        getVision().setLocation( location );
        return location;
    }

    @Override
    public FieldOfVision getVision() {
        FieldOfVision fov = this.fov;
        if ( fov == null ) {
            fov = this.fov = makeFOV();
            if ( fov == null )
                throw new NullPointerException( "makeFOV() cannot return null" );
        }
        return fov;
    }

    protected FieldOfVision makeFOV() {
        return new BasicFOV( Dungeon.level().getTileMap().getLosBlocking(), location );
    }

    @Override
    public EventHandler<CharTargetListener> getTargetHandler() {
        return targetHandler;
    }

    @Override
    public EventHandler<CharMoveListener> getMoveHandler() {
        return moveHandler;
    }

    @Override
    public Animation getLogo() {
        return getVisual().getIdleAnimation();
    }

    @Override
    public boolean isOver( int x, int y ) {
        return location.equals( x, y );
    }
}