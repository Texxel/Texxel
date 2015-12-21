package com.github.texxel.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.github.texxel.actors.Char;
import com.github.texxel.scenes.GameScene;
import com.github.texxel.sprites.api.Visual;

/**
 * The HeroFollower positions the camera over the hero whenever his sprite moves. If the sprite
 * never moves, then this class will do nothing letting the camera be panned around.
 */
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
