package com.github.texxel.actors.ai.actions;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.items.api.Weapon;
import com.github.texxel.mechanics.attacking.Attack;
import com.github.texxel.mechanics.attacking.MeleeAttack;
import com.github.texxel.sprites.api.CharVisual;
import com.github.texxel.utils.Assert;
import com.github.texxel.utils.Point2D;

public class AttackAction implements Action {

    private final Char attacker, defender;
    private final Weapon weapon;
    private float timeToFinish;

    public AttackAction( Char attacker, Weapon weapon, Char defender ) {
        this.attacker = Assert.nonnull( attacker, "attacker cannot be null" );
        this.defender = Assert.nonnull( defender, "defender cannot be null" );
        this.weapon = Assert.nonnull( weapon, "weapon cannot be null" );
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
            Attack attack = new MeleeAttack( attacker, weapon );
            defender.damage( attack );
            // displaying the damage done is done somewhere else...
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean render( float dt ) {
        // nothing to do: graphics handled in onStart/onFinish
        return true;
    }

    @Override
    public void onFinish() {
        CharVisual visual = attacker.getVisual();
        visual.play( visual.getIdleAnimation() );
    }

    @Override
    public void forceFinish() {
    }
}
