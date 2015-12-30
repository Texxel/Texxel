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
    public void update( Level level ) {

        // update the action a little bit more
        do {
            if ( currentAction == null ) {
                Actor actor = getNextActor( level.getActors() );
                //
                if ( renderingActions.containsKey( actor ) )
                    break;
                currentAction = getAction( actor );
                currentAction.onStart();
                renderingActions.put( actor, currentAction );
            }

            // if the action finishes, move onto another action
            if ( currentAction.update() ) {
                currentAction = null;
            }
        } while ( currentAction == null );

        // draw all the actions
        renderActions();
    }

    /**
     * Gets the next action to use from the actor. Before the action is taken from the actor, the
     * sensors will be updated.
     * @return true if the current action was set. False if no action should be updated (just wait
     *          for rendering to complete)
     */
    private static Action getAction( Actor actor ) {

        List<Sensor> sensors = actor.getSensors();
        int size = sensors.size();
        for ( int i = 0; i < size; i++ ) {
            Sensor sensor = sensors.get( i );
            assert sensor != null;
            sensor.update();
        }

        Goal goal = actor.getGoal();
        if ( goal == null )
            throw new IllegalStateException( actor + " had no goal" );
        Action action = goal.nextAction();
        if ( action == null )
            throw new IllegalStateException( goal + " returned null action. From goal" + goal );

        return action;
    }

    /**
     * Gets the Actor with the smallest time
     * @param actors the list of actors to search through
     * @return the actor with the smallest time
     */
    private static Actor getNextActor( List<Actor> actors ) {
        float minTime = Float.MAX_VALUE;
        Actor nextActor = null;
        int size = actors.size();
        for ( int i = 0; i < size; i++ ) {
            Actor a = actors.get( i );
            float time = a.getTime();
            if ( time < minTime ) {
                nextActor = a;
                minTime = time;
            }
        }
        if ( nextActor == null )
            throw new IllegalStateException( "The level had no actors!!!" );
        return nextActor;
    }

    private void renderActions() {
        Iterator<Map.Entry<Actor, Action>> i = renderingActions.entrySet().iterator();
        while ( i.hasNext() ) {
            Map.Entry<Actor, Action> entry = i.next();
            Action action = entry.getValue();
            if ( action.render() && ( action != currentAction ) ) {
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
