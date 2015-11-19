package com.github.texxel.event;

import com.github.texxel.saving.Bundlable;
import com.github.texxel.saving.Bundle;
import com.github.texxel.saving.BundleGroup;
import com.github.texxel.saving.Constructor;
import com.github.texxel.saving.ConstructorRegistry;

import junit.framework.TestCase;

public class EventHandlerTest extends TestCase {

    private static class TestListener implements Listener, Bundlable {

        private static final Constructor<TestListener> constructor = new Constructor<TestListener>() {
            @Override
            public TestListener newInstance( Bundle bundle ) {
                return new TestListener( bundle.getString( "id" ) );
            }
        };
        static {
            ConstructorRegistry.put( TestListener.class, constructor );
        }

        public final String id;
        public TestListener( String id ) {
            this.id = id;
        }

        public boolean onCall() {
            testString = testString + id;
            return false;
        }

        @Override
        public Bundle bundle( BundleGroup topLevel ) {
            Bundle bundle = topLevel.newBundle();
            bundle.putString( "id", id );
            return bundle;
        }

        @Override
        public void restore( Bundle bundle ) {
        }
    }

    private static class TestEvent implements Event<TestListener> {
        @Override
        public boolean dispatch( TestListener listener ) {
            return listener.onCall();
        }
    }

    private static String testString = "";

    public void testSaveRestore() throws Exception {

        EventHandler<TestListener> in = new EventHandler<>();
        in.addListener( new TestListener( "A5" ), 5 );
        in.addListener( new TestListener( "B4" ), 4 );
        in.addListener( new TestListener( "A3" ), 3 );
        in.addListener( new TestListener( "B2" ), 2 );
        in.addListener( new TestListener( "A0" ), 0 );
        in.addListener( new TestListener( "B-10" ), -10 );

        BundleGroup group = BundleGroup.newGroup();
        group.putBundlable( "handler", in );

        testString = "";
        in.dispatch( new TestEvent() );
        String inResult = testString;

        group = BundleGroup.loadGroup( group.toString() );

        EventHandler<TestListener> out = group.getBundlable( "handler" );
        testString = "";
        out.dispatch( new TestEvent() );
        String outResult = testString;

        assertEquals( inResult, outResult );
    }

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