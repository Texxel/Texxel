package com.github.texxel.gameloop;

import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.Actor;
import com.github.texxel.levels.Level;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LevelUpdater {

    private Map<Actor, Action> delayedActions = new HashMap<>();
    private Action currentAction;
    private Actor currentActor;

    public void update( Level level ) {
        while ( true ) {
            if ( updateDelayedActions() ) {
                //System.out.println( "waiting for delayed actions to finish" );
                // need to return to renderer
                return;
            }

            // make sure we have a current action
            if ( currentAction == null ) {
                currentActor = getNextActor( level.getActors() );
                if ( delayedActions.containsKey( currentActor ))
                    if ( currentActor.isUserControlled() )
                        return;
                    else
                        continue;
                currentAction = currentActor.getAction();
                if ( currentAction == null ) {
                    if ( currentActor.isUserControlled() ) {
                        //System.out.println( currentActor );
                        // go to the renderer
                        return;
                    } else {
                        System.err.println( "Non user controlled actor had a null action. Game may freeze now..." );
                        continue;
                    }
                } else {
                    currentAction.onStart();
                }
            }

            // update the current action
            if ( currentAction.finished() ) {
                delayedActions.put( currentActor, currentAction );
                currentAction = null;
                boolean userControlled = currentActor.isUserControlled();
                currentActor = null;
                if ( userControlled )
                    return;
            } else {
                currentAction.update();
                currentAction.render();
                // action may take some time. return to renderer
                if ( !currentAction.finished() ) {
                    //System.out.println( "action " + currentAction + " not finished" );
                    return;
                }
            }
        }
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

    private boolean updateDelayedActions() {
        Iterator<Map.Entry<Actor, Action>> i = delayedActions.entrySet().iterator();
        boolean userControlled = false;
        //System.out.println( delayedActions );
        while ( i.hasNext() ) {
            Map.Entry<Actor, Action> entry = i.next();
            Actor actor = entry.getKey();
            Action action = entry.getValue();
            if ( action.finishedGraphics() )
                i.remove();
            else
                action.render();
            userControlled |= actor.isUserControlled();
        }
        return userControlled;
    }

}
