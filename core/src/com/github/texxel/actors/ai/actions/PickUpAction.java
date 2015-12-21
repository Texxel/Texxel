package com.github.texxel.actors.ai.actions;

import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.brains.HeroIdleAI;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.items.Heap;
import com.github.texxel.items.ItemStack;
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
    public boolean update() {
        ItemStack stack = heap.topItem();
        if ( hero.getInventory().collect( stack ) ) {
            heap.remove( stack );
        }
        hero.setBrain( new HeroIdleAI( hero ) );
        return true;
    }

    @Override
    public boolean render() {
        // nothing to do
        return true;
    }

    @Override
    public void onFinish() {
    }

    @Override
    public void forceFinish() {
        hero.setBrain( new HeroIdleAI( hero ) );
    }
}
