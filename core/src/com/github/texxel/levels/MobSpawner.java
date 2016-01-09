package com.github.texxel.levels;

import com.github.texxel.actors.AbstractActor;
import com.github.texxel.actors.Char;
import com.github.texxel.actors.MobConstructor;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.utils.Point2D;
import com.github.texxel.utils.RandomCategory;

import java.util.List;

/**
 * The mob spawner makes sure that there are always some baddies in the current level
 */
class MobSpawner extends AbstractActor {

    private static final long serialVersionUID = -4232985687122527717L;
    private static final int MIN_BADDIES = 5;

    public MobSpawner( Level level ) {
        super( level );
    }

    void fillLevel() {
        System.out.println( "Filling the level" );

        Level level = level();
        List<Char> chars = level.getCharacters();

        // determine how many baddies there currently are in the level
        int mobs = 0;
        for ( int i = 0; i < chars.size(); i++ ) {
            Char c = chars.get( i );
            if ( c.getSide() == Char.Side.EVIL )
                mobs++;
        }
        // bring the level population back up
        for ( int i = mobs; i < MIN_BADDIES; i++ ) {
            System.out.println( "Adding a mob" );
            RandomCategory<MobConstructor> bestiary = level.getBestiary();
            MobConstructor constructor = bestiary.next();
            Point2D loc = level.randomRespawnCell();
            Char mob = constructor.newInstance( level, loc );
            level.addActor( mob );
        }
    }

    @Override
    protected Goal defaultGoal() {
        return new Goal() {
            private static final long serialVersionUID = 492815697628011086L;

            @Override
            public void onStart() {
                fillLevel();
            }

            @Override
            public Action nextAction() {
                return new Action() {
                    @Override
                    public void onStart() {
                        fillLevel();
                        spend( 1 );
                    }

                    @Override
                    public boolean update( float delta ) {
                        return true;
                    }

                    @Override
                    public boolean render( float delta ) {
                        return true;
                    }

                    @Override
                    public void forceFinish() {

                    }

                    @Override
                    public void onFinish() {

                    }
                };
            }

            @Override
            public void onRemove() {
            }
        };
    }

    @Override
    public boolean isOver( int x, int y ) {
        return false;
    }
}
