package com.github.texxel.sprites.imp.herovisuals;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.texxel.sprites.api.HeroVisual;
import com.github.texxel.sprites.imp.AbstractCharVisual;

public abstract class AbstractHeroVisual extends AbstractCharVisual implements HeroVisual {

    @Override
    protected Animation makeIdleAnimation() {
        return makeAnimation( 1, 0, Animation.PlayMode.LOOP, 0, 0, 0, 1, 0, 0, 1, 1 );
    }

    @Override
    public Animation makeAttackAnimation() {
        return makeAnimation( 15, 0, Animation.PlayMode.NORMAL, 13, 14, 15, 0 );
    }

    @Override
    public Animation makeDieAnimation() {
        return makeAnimation( 20, 0, Animation.PlayMode.NORMAL, 8, 9, 10, 11, 12, 11 );
    }

    @Override
    public Animation makeOperateAnimation() {
        return makeAnimation( 8, 0, Animation.PlayMode.NORMAL, 16, 17, 16, 17 );
    }

    @Override
    public Animation makeWalkAnimation() {
        return makeAnimation( 20, 0, Animation.PlayMode.LOOP, 2, 3, 4, 5, 6, 7 );
    }

}