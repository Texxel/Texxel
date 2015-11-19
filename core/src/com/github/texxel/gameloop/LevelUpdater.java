package com.github.texxel.gameloop;

import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.Actor;
import com.github.texxel.actors.ai.Sensor;
import com.github.texxel.levels.Level;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LevelUpdater implements GameUpdater {

    private Map<Actor, Action> renderingActions = new HashMap<>();
    private boolean actionFinished;
    private Action currentAction;

    @Override
    public void update( Level level ) {
        if ( currentAction == null || currentAction.update() ) {
            actionFinished = true;
            // current action finished. Choose a new one
            Actor nextActor = getNextActor( level.getActors() );

            if ( !renderingActions.containsKey( nextActor ) ) {
                updateActor( nextActor );
            }
        }
        renderActions();
    }

    private void updateActor( Actor actor ) {
        // update the actors sensors
        List<Sensor> sensors = actor.getSensors();
        int size = sensors.size();
        for ( int i = 0; i < sensors.size(); i++ )
            sensors.get( i ).update();

        // update the actors actions
        actor.getBrain().update();
        currentAction = actor.getGoal().nextAction();
        currentAction.onStart();
        actionFinished = false;

        renderingActions.put( actor, currentAction );
    }

    /**
     * Gets the Actor with the smallest time
     * @param actors the list of actors to search through
     * @return the actor with the smallest time
     */
    private Actor getNextActor( List<Actor> actors ) {
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

    private boolean renderActions() {
        Iterator<Map.Entry<Actor, Action>> i = renderingActions.entrySet().iterator();
        boolean userControlled = false;
        while ( i.hasNext() ) {
            Map.Entry<Actor, Action> entry = i.next();
            Actor actor = entry.getKey();
            Action action = entry.getValue();
            if ( action.render() && ( action != currentAction || actionFinished ) ) {
                action.onFinish();
                i.remove();
            }
            userControlled |= actor.isUserControlled();
        }
        return userControlled;
    }

}
