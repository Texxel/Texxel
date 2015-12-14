package com.github.texxel.actors.ai.actions;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.sprites.api.CharVisual;
import com.github.texxel.tiles.Interactable;
import com.github.texxel.utils.GameTimer;

public class HeroInteractAction implements Action {

    private final Interactable tile;
    private final Char hero;
    private float timeToFinish;

    public HeroInteractAction( Char hero, Interactable tile ) {
        if ( hero == null )
            throw new NullPointerException( "'hero' cannot be null" );
        if ( tile == null )
            throw new NullPointerException( "'tile' cannot be null" );
        this.hero = hero;
        this.tile = tile;
    }

    @Override
    public void onStart() {
        CharVisual visual = hero.getVisual();
        Animation animation = visual.getOperateAnimation();
        visual.play( animation );
        timeToFinish = animation.getAnimationDuration();
    }

    @Override
    public boolean update() {
        timeToFinish -= GameTimer.tickTime();
        if ( timeToFinish < 0 ) {
            tile.interact( hero );
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean render() {
        return true;
    }

    @Override
    public void onFinish() {
        CharVisual visual = hero.getVisual();
        visual.play( visual.getIdleAnimation() );
    }

    @Override
    public void forceFinish() {
        tile.interact( hero );
    }

}
