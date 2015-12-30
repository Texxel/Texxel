package com.github.texxel.actors.ai.actions;

import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.actors.heaps.Heap;
import com.github.texxel.items.api.Item;
import com.github.texxel.sprites.api.CharVisual;
import com.github.texxel.utils.Point2D;

public class PickUpAction implements Action {

    private final Hero hero;
    private final Heap heap;
    private float timeToFinish;

    public PickUpAction( Hero hero, Heap heap ) {
        if ( hero == null )
            throw new NullPointerException( "'hero' cannot be null" );
        if ( heap == null )
            throw new NullPointerException( "'heap' cannot be null" );
        this.hero = hero;
        this.heap = heap;
    }

    @Override
    public void onStart() {
        CharVisual visual = hero.getVisual();
        Point2D loc = hero.getLocation();
        visual.setLocation( loc.x, loc.y );
        visual.play( visual.getIdleAnimation() );
    }

    @Override
    public boolean update( float dt ) {
        Item item = heap.pop();
        if ( !hero.getInventory().getBackPack().collect( item ) )
            heap.add( item );
        return true;
    }

    @Override
    public boolean render( float dt ) {
        // nothing to do
        return true;
    }

    @Override
    public void onFinish() {
    }

    @Override
    public void forceFinish() {
    }
}
