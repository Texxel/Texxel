package com.github.texxel.actors.ai.actions;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.sprites.api.CharVisual;
import com.github.texxel.sprites.imp.status.DamageStatus;
import com.github.texxel.utils.Point2D;

public class AttackAction implements Action {

    private final Char attacker, defender;
    private float timeToFinish;

    public AttackAction( Char attacker, Char defender ) {
        if ( attacker == null )
            throw new NullPointerException( "'attacker' cannot be null" );
        if ( defender == null )
            throw new NullPointerException( "'defender' cannot be null" );
        this.attacker = attacker;
        this.defender = defender;
    }

    @Override
    public void onStart() {
        CharVisual visual = attacker.getVisual();
        Point2D loc = attacker.getLocation();
        visual.setLocation( loc.x, loc.y );
        Animation animation = visual.getAttackAnimation();
        timeToFinish = animation.getAnimationDuration();
        Point2D otherLoc = defender.getLocation();
        visual.setDirection( loc.directionTo( otherLoc ) );
        visual.play( animation );
    }

    @Override
    public boolean update( float dt ) {
        timeToFinish -= dt;
        if ( timeToFinish <= 0 ) {
            float damage = attacker.attack( defender );
            defender.getVisual().attach( new DamageStatus( defender.getVisual(), damage ) );
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean render( float dt ) {
        // nothing to do: graphics done in onStart/onFinish
        return true;
    }

    @Override
    public void onFinish() {
        CharVisual visual = attacker.getVisual();
        visual.play( visual.getIdleAnimation() );
    }

    @Override
    public void forceFinish() {
        attacker.attack( defender );
    }
}
