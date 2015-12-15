package com.github.texxel.event;

import junit.framework.TestCase;

public class EventHandlerTest extends TestCase {

    private static class TestListener implements Listener {
        private static final long serialVersionUID = -6383987784609461608L;

        public final String id;
        public TestListener( String id ) {
            this.id = id;
        }

        public boolean onCall() {
            testString = testString + id;
            return false;
        }
    }

    private static class TestEvent implements Event<TestListener> {
        @Override
        public boolean dispatch( TestListener listener ) {
            return listener.onCall();
        }
    }

    private static String testString = "";

    public void testAddListener() throws Exception {
        EventHandler<TestListener> handler = new EventHandler<>();
        handler.addListener( new TestListener( "A" ), EventHandler.LATE );
        assertEquals( 1, handler.listeners.size() );
    }

    public void testExecutionOrder() throws Exception {
        EventHandler<TestListener> handler = new EventHandler<>();
        handler.addListener( new TestListener( "A" ), EventHandler.VERY_EARLY );
        handler.addListener( new TestListener( "B" ), EventHandler.EARLY );
        handler.addListener( new TestListener( "C" ), EventHandler.NORMAL );
        handler.addListener( new TestListener( "D" ), EventHandler.LATE );
        handler.addListener( new TestListener( "E" ), EventHandler.VERY_LATE );
        testString = "";
        handler.dispatch( new TestEvent() );
        assertEquals( testString, "ABCDE" );
    }

}