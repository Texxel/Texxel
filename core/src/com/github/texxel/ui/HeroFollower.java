package com.github.texxel.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.github.texxel.actors.Char;
import com.github.texxel.scenes.GameScene;
import com.github.texxel.sprites.api.Visual;

public class HeroFollower extends Actor {

    private final GameScene scene;
    private float preX, preY;

    public HeroFollower( GameScene scene ) {
        if ( scene == null )
            throw new NullPointerException( "'scene' cannot be null" );
        this.scene = scene;
    }

    @Override
    public void act( float delta ) {
        Char hero = scene.getPlayer();
        Visual visual = hero.getVisual();
        float x = visual.x();
        float y = visual.y();
        if ( x != preX || y != preY ) {
            Camera camera = scene.getGameCamera();
            camera.position.set( x, y, 0 );
            camera.update();
            preX = x;
            preY = y;
        }
    }
}
