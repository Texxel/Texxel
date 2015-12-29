package com.github.texxel.actors.heaps;

import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.actors.ai.actions.DestroyAction;
import com.github.texxel.actors.ai.actions.NoBrainAction;

/**
 * The heap goal makes an actor do nothing. Every update, it will check if the heap is empty; if it
 * is empty, then the heap will get removed from the level.
 */
public class HeapGoal implements Goal {

    private static final long serialVersionUID = 2992795704153100790L;
    private final Heap heap;

    private transient NoBrainAction idleAction;

    public HeapGoal( Heap heap ) {
        this.heap = heap;
    }

    @Override
    public void onStart() {

    }

    @Override
    public Action nextAction() {
        // if the item is empty, delete it
        if ( heap.isEmpty() )
            return new DestroyAction( heap );

        // idle our time away
        if ( idleAction == null )
            idleAction = new NoBrainAction( heap );
        return idleAction;
    }

    @Override
    public void onRemove() {

    }
}
