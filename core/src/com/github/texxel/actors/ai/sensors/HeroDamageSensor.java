package com.github.texxel.actors.ai.sensors;

import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Sensor;
import com.github.texxel.actors.ai.goals.HeroIdleGoal;
import com.github.texxel.event.EventHandler;
import com.github.texxel.event.events.actor.CharHealthChangedEvent;
import com.github.texxel.event.listeners.actor.CharHealthChangedListener;

/**
 * The hero damage sensor will stop the hero whenever he is damaged
 */
public class HeroDamageSensor implements Sensor {

    private static final long serialVersionUID = 363750174096639841L;
    final Char hero;
    final DamageListener listener;

    public HeroDamageSensor( Char hero ) {
        if ( hero == null )
            throw new NullPointerException( "'hero' cannot be null" );
        this.hero = hero;
        listener = new DamageListener();
    }

    @Override
    public void onStart() {
        hero.getHealthHandler().addListener( listener, EventHandler.TEXXEL_LISTEN );
    }

    @Override
    public void update() {

    }

    @Override
    public void onRemove() {
        hero.getHealthHandler().removeListener( listener, EventHandler.TEXXEL_LISTEN );
    }

    private class DamageListener implements CharHealthChangedListener {
        private static final long serialVersionUID = -2973991290170852849L;

        @Override
        public void onHealthChanged( CharHealthChangedEvent e ) {
            Char c = e.getCharacter();
            if ( c != hero )
                return;
            float previous = e.getPreviousHealth();
            float next = e.getNextHealth();
            if ( next < previous )
                c.setGoal( new HeroIdleGoal( c ) );
        }

    }
}
