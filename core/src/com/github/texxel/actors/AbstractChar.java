package com.github.texxel.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.texxel.actors.ai.brains.CharDieAI;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.events.actor.CharMoveEvent;
import com.github.texxel.event.listeners.actor.CharMoveListener;
import com.github.texxel.levels.Level;
import com.github.texxel.mechanics.BasicFOV;
import com.github.texxel.mechanics.FieldOfVision;
import com.github.texxel.utils.Point2D;

public abstract class AbstractChar extends AbstractActor implements Char {

    private static final long serialVersionUID = -5708961990001314051L;

    private float health, maxHealth;
    private Point2D location;
    private FieldOfVision fov;
    private final EventHandler<CharMoveListener> moveHandler = new EventHandler<>();

    /**
     * Constructs the char at the spawn point
     * @param spawn the point that the char should start at
     * @param health the maximum health of the char
     * @throws NullPointerException is spawn is null
     * @throws IllegalArgumentException if health is <= 0
     */
    public AbstractChar( Level level, Point2D spawn, float health ) {
        super( level );
        if ( spawn == null )
            throw new NullPointerException( "spawn cannot be null" );
        if ( health <= 0 )
            throw new IllegalArgumentException( "health cannot be <= 0" );
        this.location = spawn;
        maxHealth = this.health = health;
    }

    @Override
    public float attack( Char enemy ) {
        spend( 1.0f );
        return enemy.damage( 3, this );
    }

    @Override
    public Point2D getLocation() {
        return location;
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
            return this.location;

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
        return new BasicFOV( level().getTileMap().getLosBlocking(), location );
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
    public float damage( float damage, Object source ) {
        setHealth( getHealth() - damage );
        return damage;
    }

    @Override
    public void setMaxHealth( float health ) {
        if ( health <= 0 )
            throw new IllegalArgumentException( "'health' cannot be smaller than 0" );
        if ( this.health > health )
            this.health = health;
        this.maxHealth = health;
    }

    @Override
    public void setHealth( float health ) {
        if ( health > maxHealth )
            throw new IllegalArgumentException( "'health' cannot be greater than max health" );
        this.health = health;
        if ( health <= 0 )
            die();
    }

    private void die() {
        setBrain( new CharDieAI( this ) );
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public float getMaxHealth() {
        return maxHealth;
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    @Override
    public boolean isOver( int x, int y ) {
        return location.equals( x, y );
    }
}