package com.github.texxel.event;

import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EventHandlerTest {

    interface TestListener extends Listener {
        boolean onEvent();
    }

    class TestEvent implements Event<TestListener> {
        @Override
        public boolean dispatch( TestListener listener ) {
            return listener.onEvent();
        }
    }

    @Test
    public void listenersExecutedInOrder() throws Exception {
        EventHandler<TestListener> handler = new EventHandler<>();
        final TestListener l1 = mock( TestListener.class );
        TestListener l2 = mock( TestListener.class );
        TestListener l3 = mock( TestListener.class );
        TestListener l4 = mock( TestListener.class );
        TestListener l5 = mock( TestListener.class );
        handler.addListener( l1, EventHandler.VERY_EARLY );
        handler.addListener( l2, EventHandler.EARLY );
        handler.addListener( l3, EventHandler.NORMAL );
        handler.addListener( l4, EventHandler.LATE );
        handler.addListener( l5, EventHandler.VERY_LATE );

        handler.dispatch( new TestEvent() );

        InOrder inOrder = inOrder( l1, l2, l3, l4, l5 );
        inOrder.verify( l1 ).onEvent();
        inOrder.verify( l2 ).onEvent();
        inOrder.verify( l3 ).onEvent();
        inOrder.verify( l4 ).onEvent();
        inOrder.verify( l5 ).onEvent();
    }

    @Test
    public void eventIsCanceled() {
        EventHandler<TestListener> handler = new EventHandler<>();

        TestListener l1 = mock( TestListener.class );
        TestListener l2 = mock( TestListener.class );
        TestListener l3 = mock( TestListener.class );
        when( l2.onEvent() ).thenReturn( true );

        handler.addListener( l1, EventHandler.EARLY );
        handler.addListener( l2, EventHandler.NORMAL );
        handler.addListener( l3, EventHandler.LATE );

        handler.dispatch( new TestEvent() );

        verify( l1 ).onEvent();
        verify( l2 ).onEvent();
        verify( l3, never() ).onEvent();
    }

    @Test
    public void listenersAreRemoved() {
        EventHandler<TestListener> handler = new EventHandler<>();
        final TestListener l = mock( TestListener.class );
        handler.addListener( l, EventHandler.NORMAL );
        handler.removeListener( l, EventHandler.NORMAL );

        handler.dispatch( new TestEvent() );

        verify( l, never() ).onEvent();
    }

    @Test
    public void listenersCanBeAddedAtMultipleLevels() {
        EventHandler<TestListener> handler = new EventHandler<>();
        final TestListener l = mock( TestListener.class );
        handler.addListener( l, EventHandler.NORMAL );
        handler.addListener( l, EventHandler.EARLY );

        handler.dispatch( new TestEvent() );

        verify( l, times( 2 ) ).onEvent();
    }

    @Test
    public void handlerCanRemoveAllListeners() {
        EventHandler<TestListener> handler = new EventHandler<>();
        final TestListener l = mock( TestListener.class );
        handler.addListener( l, EventHandler.NORMAL );
        handler.addListener( l, EventHandler.EARLY );
        handler.removeAll( l );

        handler.dispatch( new TestEvent() );

        verify( l, never() ).onEvent();
    }

    @Test
    public void listenersCanOnlyBeRegisteredOnceAtEachLevel() {
        EventHandler<TestListener> handler = new EventHandler<>();
        final TestListener l = mock( TestListener.class );
        handler.addListener( l, EventHandler.NORMAL );
        handler.addListener( l, EventHandler.NORMAL );

        handler.dispatch( new TestEvent() );

        verify( l ).onEvent();
    }

}