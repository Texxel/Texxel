package com.github.texxel.gameloop;

import com.github.texxel.actors.Actor;
import com.github.texxel.actors.ai.Action;
import com.github.texxel.actors.ai.Goal;
import com.github.texxel.levels.Level;

import org.junit.Test;
import org.mockito.InOrder;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings( "ArraysAsListWithZeroOrOneArgument" )
public class LevelUpdaterTest {

    private Actor mockActor( float energy, Action action, Action ... actions  ) {
        Actor actor = mock( Actor.class );
        when( actor.getEnergy() ).thenReturn( energy );
        Goal goal = mock( Goal.class );
        when( actor.getGoal() ).thenReturn( goal );
        when( goal.nextAction() ).thenReturn( action, actions );
        return actor;
    }

    private Level mockLevel( Actor ... actors ) {
        Level level = mock( Level.class );
        when( level.getActors() ).thenReturn( Arrays.asList( actors ) );
        return level;
    }

    @Test
    public void actorsActionIsCalledToUpdate() {
        LevelUpdater updater = new LevelUpdater();
        Action action = mock( Action.class );
        Actor actor = mockActor( 10, action );
        Level level = mockLevel( actor );

        updater.update( level, 10 );

        verify( action, times( 1 ) ).update( 10 );
        verify( action, times( 1 ) ).render( 10 );
    }

    @Test
    public void actorsAreChargedAndThenToldToAct() {
        LevelUpdater updater = new LevelUpdater();

        Actor actor = mock( Actor.class );
        Goal goal = mock( Goal.class );
        when( goal.nextAction() ).thenReturn( mock( Action.class ) );
        when( actor.getGoal() ).thenReturn( goal );
        when( actor.getEnergy() ).thenReturn( -10f, -5f, 0f, 10f, 15f, 20f ); // simulate charging
        Level level = mock( Level.class );
        when( level.getActors() ).thenReturn( Arrays.asList( actor ) );

        updater.update( level, 10 );

        verify( actor, times( 3 ) ).charge();
        assertEquals( 15, actor.getEnergy(), 0.1f );
    }

    @Test
    public void actionsAreUpdatedUntilTheyReturnTrue() {
        LevelUpdater updater = new LevelUpdater();

        Action action = mock( Action.class );
        when( action.update( anyFloat() ) ).thenReturn( false, false, false, true ); // 4 calls before finishing
        when( action.render( anyFloat() ) ).thenReturn( true ); // to ensure the action can be passed on
        Actor actor = mockActor( 10, action, mock( Action.class ) );

        Level level = mockLevel( actor );

        // updated the level a bunch
        for ( int i = 0; i < 10; i++ )
            updater.update( level, 5 );

        verify( action, times( 4 ) ).update( 5 );
    }

    @Test
    public void actionsAreRenderedEachUpdate() {
        LevelUpdater updater = new LevelUpdater();

        // action.update() will always return false, thus, the action will never finish
        Action action = mock( Action.class );
        Actor actor = mockActor( 10, action, mock( Action.class ) );

        Level level = mockLevel( actor );

        // updated the action a bunch
        for ( int i = 0; i < 10; i++ )
            updater.update( level, 5 );

        // Every call to update must have been followed by a call to render
        InOrder inOrder = inOrder( action );
        for ( int i = 0; i < 10; i++ ) {
            inOrder.verify( action ).update( 5 );
            inOrder.verify( action ).render( 5 );
        }
        // but no more than 10
        inOrder.verify( action, never() ).update( 5 );
        inOrder.verify( action, never() ).render( 5 );
    }

    @Test
    public void actorsAreCalledInOrder() {
        LevelUpdater updater = new LevelUpdater();
        Action action1 = mock( Action.class );
        Action action2 = mock( Action.class );
        Actor actor1 = mockActor( 10, action1 );
        Actor actor2 = mockActor( 20, action2 );
        Level level = mockLevel( actor1, actor2 );

        updater.update( level, 3 );

        // only the actor with the higher energy should be updated
        verify( action1, never() ).update( anyFloat() );
        verify( action1, never() ).render( anyFloat() );
        verify( action2, times( 1 ) ) .update( 3 );
        verify( action2, times( 1 ) ).render( 3 );
    }

    @Test
    public void actionsAreRenderedUtilTheyReturnTrue() {
        LevelUpdater updater = new LevelUpdater();
        Action action1 = mock( Action.class );
        Action action2 = mock( Action.class );

        when( action1.update( anyFloat() ) ).thenReturn( false, true );
        when( action1.render( anyFloat() ) ).thenReturn( true, false, false, false, true );
        Actor actor = mockActor( 10, action1, action2 );
        Level level = mockLevel( actor );

        // update a bunch
        for ( int i = 0; i < 20; i++ )
            updater.update( level, 5 );

        InOrder inOrder = inOrder( action1, action2 );
        inOrder.verify( action1 ).update( 5 ); // -> false (keep updating)
        inOrder.verify( action1 ).render( 5 ); // -> true (ignored)
        inOrder.verify( action1 ).update( 5 ); // -> true  (stop updating)
        inOrder.verify( action1, times( 4 ) ).render( 5 ); // -> false x 3 (keep rendering), true (next action)
        inOrder.verify( action2 ).update( 5 ); // -> false
        inOrder.verify( action2 ).render( 5 );
    }

    @Test
    public void twoActionsUpdatedInTheSameFrame() {
        LevelUpdater updater = new LevelUpdater();

        Action actionA = mock( Action.class );
        Actor actorA = mock( Actor.class );
        when( actorA.getEnergy() ).thenReturn( 10f, 5f );
        Goal goalA = mock( Goal.class );
        when( goalA.nextAction() ).thenReturn( actionA );
        when( actorA.getGoal() ).thenReturn( goalA );
        when( actionA.update( anyFloat() ) ).thenReturn( true );

        Action actionB = mock( Action.class );
        Actor actorB = mockActor( 8, actionB );
        when( actionB.update( anyFloat() ) ).thenReturn( true );

        Level level = mockLevel( actorA, actorB );

        updater.update( level, 3 );

        InOrder inOrder = inOrder( actionA, actionB );
        inOrder.verify( actionA ).update( 3 );
        inOrder.verify( actionB ).update( 3 );
        verify( actionA ).render( 3 );
        verify( actionB ).render( 3 );
    }

    @Test
    public void actionsReceiveStartAndStopEvents() {
        LevelUpdater updater = new LevelUpdater();
        Action action = mock( Action.class );
        Actor actor = mockActor( 6, action );
        when( action.update( anyFloat() ) ).thenReturn( false, true );
        when( action.render( anyFloat() ) ).thenReturn( true );
        Level level = mockLevel( actor );

        updater.update( level, 3 );
        updater.update( level, 4 );

        InOrder inOrder = inOrder( action );
        inOrder.verify( action ).onStart();
        inOrder.verify( action ).update( 3 );
        inOrder.verify( action ).render( 3 );
        inOrder.verify( action ).update( 4 );
        inOrder.verify( action ).render( 4 );
        inOrder.verify( action ).onFinish();
    }

    @Test
    public void actionIsToldToReset() {
        Action action = mock( Action.class );
        Actor actor = mockActor( 10, action );
        Level level = mockLevel( actor );

        LevelUpdater updater = new LevelUpdater();
        updater.update( level, 3 );
        updater.quit();

        verify( action ).forceFinish();
    }

}