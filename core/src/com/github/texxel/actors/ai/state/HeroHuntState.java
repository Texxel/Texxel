package com.github.texxel.actors.ai.state;

import com.github.texxel.actors.Char;
import com.github.texxel.mechanics.PathFinder;
import com.github.texxel.utils.Point2D;

public class HeroHuntState extends CharMoveState {

    private final Char hero, enemy;

    public HeroHuntState( Char hero, Char enemy ) {
        super( hero );
        this.hero = hero;
        this.enemy = enemy;
    }

    @Override
    public void onTargetReached() {
        Point2D heroLoc = hero.getLocation();
        Point2D enemyLoc = enemy.getLocation();
        if ( !PathFinder.isNextTo( heroLoc.x, heroLoc.y, enemyLoc.x, enemyLoc.y ) )
            throw new RuntimeException( "Target was reached but was not next to target?!?" );
        hero.attack( enemy );
        hero.setState( new HeroIdleState( hero ) );
    }

    @Override
    public void onCannotReachTarget() {
        hero.setState( new HeroIdleState( hero ) );
    }

    @Override
    public void onNoTarget() {
        Point2D location = enemy.getLocation();
        if ( hero.getVision().isVisible( location.x, location.y ) ) {
            hero.setState( new HeroIdleState( hero ) );
        }
    }
}