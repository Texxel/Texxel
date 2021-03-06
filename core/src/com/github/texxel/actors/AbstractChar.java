package com.github.texxel.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.texxel.actors.ai.goals.CharDieGoal;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.events.actor.CharHealthChangedEvent;
import com.github.texxel.event.events.actor.CharMoveEvent;
import com.github.texxel.event.listeners.actor.CharAttackListener;
import com.github.texxel.event.listeners.actor.CharHealthChangedListener;
import com.github.texxel.event.listeners.actor.CharMoveListener;
import com.github.texxel.levels.Level;
import com.github.texxel.mechanics.BasicFOV;
import com.github.texxel.mechanics.FieldOfVision;
import com.github.texxel.mechanics.attacking.Attack;
import com.github.texxel.utils.Assert;
import com.github.texxel.utils.Point2D;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractChar extends AbstractActor implements Char {

    private static final long serialVersionUID = -5708961990001314051L;

    private float health, maxHealth;
    private Point2D location;
    private FieldOfVision fov;
    private Map<String, Attribute> attributes = new HashMap<>();
    private final EventHandler<CharMoveListener> moveHandler = new EventHandler<>();
    private final EventHandler<CharAttackListener> attackHandler = new EventHandler<>();
    private final EventHandler<CharHealthChangedListener> healthHandler = new EventHandler<>();

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
    public void setLevel( Level level ) {
        super.setLevel( level );
        // recreate the fov
        fov = null;
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
    public Animation getLogo() {
        return getVisual().getIdleAnimation();
    }

    @Override
    public boolean hasAttribute( String name ) {
        return attributes.containsKey( name );
    }

    @Override
    public Attribute getAttribute( String name ) {
        Attribute attribute = attributes.get( Assert.nonnull( name, "attribute key cannot be null" ) );
        if ( attribute == null ) {
            attribute = new Attribute( name );
            attributes.put( name, attribute );
        }
        return attribute;
    }

    @Override
    public void damage( Attack attack ) {
        float accuracy = attack.accuracy().get();
        float dodge = getAttribute( "dodge" ).value().get();
        if ( dodge > accuracy )
            return;

        float dmg = attack.damage().get();
        float armor = getAttribute( "armor" ).value().get();
        float netDamage = Math.max( dmg - armor, 0 );

        attack.attacker().spend( attack.delay().get() );
        setHealth( health - netDamage );
    }

    @Override
    public void setMaxHealth( float health ) {
        if ( health <= 0 )
            throw new IllegalArgumentException( "'health' cannot be smaller or equal to 0" );
        if ( this.health > health )
            this.health = health;
        this.maxHealth = health;
    }

    @Override
    public float setHealth( float health ) {
        if ( !healthHandler.isEmpty() ) {
            CharHealthChangedEvent e = new CharHealthChangedEvent( this, this.health, health );
            healthHandler.dispatch( e );
            if ( e.isCancelled() )
                return this.health;
            health = e.getNextHealth();
        }

        if ( health > maxHealth )
            health = maxHealth;
        this.health = health;
        if ( health <= 0 )
            die();
        return health;
    }

    private void die() {
        setGoal( new CharDieGoal( this ) );
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

    @Override
    public EventHandler<CharMoveListener> getMoveHandler() {
        return moveHandler;
    }

    @Override
    public EventHandler<CharAttackListener> getAttackHandler() {
        return attackHandler;
    }

    @Override
    public EventHandler<CharHealthChangedListener> getHealthHandler() {
        return healthHandler;
    }
}