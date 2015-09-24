package com.github.texxel.actors.ai.state;

import com.github.texxel.Dungeon;
import com.github.texxel.actors.heroes.Hero;
import com.github.texxel.actors.heroes.HeroFOV;

public class HeroMoveState extends CharMoveState {

    private final Hero hero;

    public HeroMoveState( Hero hero ) {
        super( hero );
        this.hero = hero;
    }

    @Override
    public void onTargetReached() {
        setTarget( null );
        System.out.println( "target reached" );
    }

    @Override
    public void onCannotReachTarget() {
        setTarget( null );
        System.out.println( "target unreachable" );
    }

    @Override
    public void onNoTarget() {
    }

    @Override
    public void setPassables( boolean[][] passables ) {
        super.setPassables( passables );
        int width = Dungeon.level().width();
        int height= Dungeon.level().height();
        HeroFOV fov = hero.getVision();
        for ( int i = 0; i < width; i++ ) {
            for ( int j = 0; j < height; j++ ) {
                if ( !fov.isDiscovered( i, j ) )
                    passables[i][j] = false;
            }
        }
    }
}
