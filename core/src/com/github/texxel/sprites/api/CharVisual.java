package com.github.texxel.sprites.api;

import com.badlogic.gdx.graphics.g2d.Animation;

public interface CharVisual extends Visual {

    Animation getAttackAnimation();

    Animation getDieAnimation();

    Animation getIdleAnimation();

    Animation getOperateAnimation();

    Animation getWalkAnimation();

}