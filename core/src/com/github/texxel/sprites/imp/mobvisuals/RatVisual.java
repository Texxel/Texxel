package com.github.texxel.sprites.imp.mobvisuals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RatVisual extends AbstractMobVisual {

    private static TextureRegion[][] frames = TextureRegion.split( new Texture( "game/rat.png" ), 16, 15 );

    public RatVisual() {
        super( 16, 15 );
    }

    @Override
    protected TextureRegion[][] frames() {
        return frames;
    }

    @Override
    protected Animation makeAttackAnimation() {
        return makeAnimation( 15, 0, Animation.PlayMode.NORMAL, 2, 3, 4, 5, 0 );
    }

    @Override
    protected Animation makeDieAnimation() {
        return makeAnimation( 10, 0, Animation.PlayMode.NORMAL, 11, 12, 13, 14 );
    }

    @Override
    protected Animation makeIdleAnimation() {
        return makeAnimation( 2, 0, Animation.PlayMode.LOOP, 0, 0, 0, 1 );
    }

    @Override
    protected Animation makeOperateAnimation() {
        return makeAnimation( 15, 0, Animation.PlayMode.NORMAL, 2, 3, 4, 5, 0 );
    }

    @Override
    protected Animation makeWalkAnimation() {
        return makeAnimation( 10, 0, Animation.PlayMode.LOOP, 6, 7, 8, 9, 10 );
    }
}
