package com.github.texxel.gameloop;

import com.github.texxel.actors.Actor;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.Sensor;
import com.github.texxel.levels.Level;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LevelUpdater implements GameUpdater {

    private Map<Actor, Action> renderingActions = new HashMap<>();
    private Action currentAction;

    @Override
    public void update( Level level, float delta ) {

        do {
            // the old action has finished: start a new action
            if ( currentAction == null ) {
                Actor actor = getNextActor( level.getActors() );

                // if the actor is already doing something, just wait for it to finish
                if ( renderingActions.containsKey( actor ) ) {
                    break;
                }

                // start the new action
                currentAction = getAction( actor );
                currentAction.onStart();
                renderingActions.put( actor, currentAction );
            }

            // update a little bit more
            if ( currentAction.update( delta ) ) {
                // the action finished, move onto another action on next iteration
                currentAction = null;
            }

        } while ( currentAction == null );

        // draw all the actions
        renderActions( delta );
    }

    /**
     * Gets the next action to use from the actor. Before the action is taken from the actor, the
     * sensors will be updated.
     * @return true if the current action was set. False if no action should be updated (just wait
     *          for rendering to complete)
     */
    private static Action getAction( Actor actor ) {

        // update the sensors
        List<Sensor> sensors = actor.getSensors();
        int size = sensors.size();
        for ( int i = 0; i < size; i++ ) {
            Sensor sensor = sensors.get( i );
            assert sensor != null;
            sensor.update();
        }

        // get the actor's action
        Goal goal = actor.getGoal();
        if ( goal == null )
            throw new IllegalStateException( actor + " had no goal" );
        Action action = goal.nextAction();
        if ( action == null )
            throw new IllegalStateException( goal + " returned null action. From goal" + goal );
        return action;
    }

    /**
     * Gets the Actor with the most energy which is still positive. If no actors have any energy,
     * then all the actors will be told to charge up.
     * @param actors the list of actors to search through
     * @return the actor with the smallest time
     */
    private static Actor getNextActor( List<Actor> actors ) {
        Actor nextActor = null;
        while ( nextActor == null ) {
            // try to find an actor with positive energy
            float maxEnergy = 0;
            int size = actors.size();
            for ( int i = 0; i < size; i++ ) {
                Actor a = actors.get( i );
                float energy = a.getEnergy();
                if ( energy > maxEnergy ) {
                    nextActor = a;
                    maxEnergy = energy;
                }
            }

            // no actor with positive energy. Charge everyone up
            if ( nextActor == null ) {
                for ( int i = 0; i < size; i++ )
                    actors.get( i ).charge();
            }
        }
        return nextActor;
    }

    /**
     * Tells all the actions to render a little bit more. When an action says it's finished
     * rendering, it will be removed from renderingActions
     */
    private void renderActions( float delta ) {
        Iterator<Map.Entry<Actor, Action>> i = renderingActions.entrySet().iterator();
        while ( i.hasNext() ) {
            Map.Entry<Actor, Action> entry = i.next();
            Action action = entry.getValue();
            if ( action.render( delta ) && ( action != currentAction ) ) {
                action.onFinish();
                i.remove();
            }
        }
    }

    @Override
    public void quit() {
        currentAction.forceFinish();
    }

}
