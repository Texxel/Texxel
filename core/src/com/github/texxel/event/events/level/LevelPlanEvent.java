package com.github.texxel.event.events.level;

import com.github.texxel.event.Event;
import com.github.texxel.event.listeners.level.LevelConstructionListener;
import com.github.texxel.levels.components.LevelDescriptor;

public class LevelPlanEvent implements Event<LevelConstructionListener> {

    private LevelDescriptor descriptor;
    private String reason;

    public LevelPlanEvent( LevelDescriptor descriptor, String reason ) {
        if ( descriptor == null )
            throw new NullPointerException( "'level' cannot be null" );
        if ( reason == null )
            throw new NullPointerException( "'reason' cannot be null" );
        this.descriptor = descriptor;
        this.reason = reason;
    }

    /**
     * Gets the LevelDescriptor that will generate the foundation of the planned level
     * @return the planned Level's level descriptor
     */
    public LevelDescriptor getLevel() {
        return descriptor;
    }

    /**
     * Sets the level descriptor that will be used to build the level. Changing the descriptor will
     * not update fields such as the level's width and height.
     * @param descriptor the planned level's descriptor
     */
    public void setLevel( LevelDescriptor descriptor ) {
        if ( descriptor == null )
            throw new NullPointerException( "'descriptor' cannot be null" );
        this.descriptor = descriptor;
    }

    public String getReason() {
        return reason;
    }

    public void setReason( String reason ) {
        if ( reason == null )
            throw new NullPointerException( "'reason' cannot be null" );
        this.reason = reason;
    }

    @Override
    public boolean dispatch( LevelConstructionListener listener ) {
        listener.onLevelPlan( this );
        return false;
    }
}
