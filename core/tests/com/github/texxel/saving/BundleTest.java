package com.github.texxel.saving;

import com.github.texxel.Version;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BundleTest extends TestCase {

    private static class TestBundlable implements Bundlable {

        private static final Constructor<TestBundlable> constructor = new Constructor<TestBundlable>() {
            @Override
            public TestBundlable newInstance( Bundle bundle ) {
                return new TestBundlable();
            }
        };
        static {
            ConstructorRegistry.put( TestBundlable.class, constructor );
        }

        public TestBundlable reference = null;
        public String value = "hello";

        @Override
        public Bundle bundle( BundleGroup topLevel ) {
            Bundle bundle = topLevel.newBundle();
            bundle.putString( "value", value );
            bundle.putBundlable( "reference", reference );
            return bundle;
        }

        @Override
        public void restore( Bundle bundle ) {
            value = bundle.getString( "value" );
            reference = bundle.getBundlable( "reference" );
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public boolean equals( Object obj ) {
            if ( !( obj instanceof TestBundlable ) )
                return false;
            TestBundlable other = (TestBundlable) obj;
            if ( !other.value.equals( this.value ) )
                return false;
            if ( this.reference == null )
                return other.reference == null;
            return other.reference != null && other.reference.value.equals( this.reference.value );
        }

        @Override
        public String toString() {
            return "me: " + value + " other: " + reference.value;
        }
    }

    private static class Unregistered implements Bundlable {

        @Override
        public Bundle bundle( BundleGroup topLevel ) {
            return topLevel.newBundle();
        }

        @Override
        public void restore( Bundle bundle ) {
        }
    }

    private static class NullBundle implements Bundlable {

        private static final Constructor<NullBundle> constructor = new Constructor<NullBundle>() {
            @Override
            public NullBundle newInstance( Bundle bundle ) {
                return new NullBundle();
            }
        };
        static {
            ConstructorRegistry.put( NullBundle.class, constructor );
        }

        @Override
        public Bundle bundle( BundleGroup topLevel ) {
            return null;
        }

        @Override
        public void restore( Bundle bundle ) {
        }
    }


    private static class NullConstruction implements Bundlable {

        private static final Constructor<NullConstruction> constructor = new Constructor<NullConstruction>() {
            @Override
            public NullConstruction newInstance( Bundle bundle ) {
                return null;
            }
        };
        static {
            ConstructorRegistry.put( NullConstruction.class, constructor );
        }

        @Override
        public Bundle bundle( BundleGroup topLevel ) {
            return topLevel.newBundle();
        }

        @Override
        public void restore( Bundle bundle ) {
        }
    }

    public void testCreateBundle() throws Exception {
        assertNotNull( BundleGroup.newGroup() );
    }

    public void testCreateSubBundle() throws Exception {
        BundleGroup group = BundleGroup.newGroup();
        Bundle bundle = group.newBundle();
        assertNotNull( bundle );
    }

    public void testCannotReassignBundleGroup() throws Exception {
        BundleGroup groupA = BundleGroup.newGroup();
        Bundle bundle = groupA.newBundle();
        BundleGroup groupB = BundleGroup.newGroup();
        try {
            groupB.putBundle( "test", bundle );
        } catch ( IllegalArgumentException e ) {
            return;
        }
        fail( "Shouldn't be able to add the bundle" );
    }

    public void testCannotEditRestoredBundle() throws Exception {
        BundleGroup group = BundleGroup.newGroup();

        group = BundleGroup.loadGroup( group.toString() );

        try {
            group.putString( "hello", "bye" );
        } catch ( IllegalStateException e ) {
            return;
        }
        fail( "Should have failed to edit bundle" );
    }

    public void testPutString() throws Exception {
        BundleGroup group = BundleGroup.newGroup();
        group.putString( "hello", "a" );

        group = BundleGroup.loadGroup( group.toString() );

        String retrieve = group.getString( "hello" );
        assertEquals( "a", retrieve );
    }

    public void testPutPrimitives() throws Exception {
        BundleGroup group = BundleGroup.newGroup();
        List<Object> objects = new ArrayList<>();
        objects.add( 1 );
        objects.add( 1.6 );
        objects.add( "Hello" );
        objects.add( true );
        group.putPrimitives( "primitives", objects );

        group = BundleGroup.loadGroup( group.toString() );

        List<Object> restored = group.getPrimitives( "primitives" );
        assertEquals( 1,        restored.get( 0 ) );
        assertEquals( 1.6, restored.get( 1 ) );
        assertEquals( "Hello",  restored.get( 2 ) );
        assertEquals( true,     restored.get( 3 ) );
    }

    public void testPutInt() throws Exception {
        BundleGroup group = BundleGroup.newGroup();
        group.putInt( "One", 1 );
        group.putInt( "-4", -4 );

        group = BundleGroup.loadGroup( group.toString() );

        assertEquals( 1, group.getInt( "One" ) );
        assertEquals( -4, group.getInt( "-4" ) );
        assertEquals( 5, group.getInt( "none", 5 ) );
    }

    public void testGetDouble() throws Exception {
        BundleGroup group = BundleGroup.newGroup();
        group.putDouble( "1.5", 1.5 );
        group.putDouble( "-4.3", -4.3 );

        group = BundleGroup.loadGroup( group.toString() );

        assertEquals( 1.5, group.getDouble( "1.5" ) );
        assertEquals( -4.3, group.getDouble( "-4.3" ) );
        assertEquals( 5.5, group.getDouble( "none", 5.5 ) );
    }

    public void testGetBoolean() throws Exception {
        BundleGroup group = BundleGroup.newGroup();
        group.putBoolean( "true", true );
        group.putBoolean( "false", false );

        group = BundleGroup.loadGroup( group.toString() );

        assertEquals( true, group.getBoolean( "true" ) );
        assertEquals( false, group.getBoolean( "false" ) );
        assertEquals( true,  group.getBoolean( "none", true ) );
    }

    public void testPutBundlable() throws Exception {
        BundleGroup group = BundleGroup.newGroup();
        TestBundlable in = new TestBundlable();
        in.value = "BOO!";
        in.reference = null;
        group.putBundlable( "b", in );

        group = BundleGroup.loadGroup( group.toString() );

        TestBundlable out = group.getBundlable( "b" );
        assertEquals( in, out );
    }

    public void testCircularReference() throws Exception {
        BundleGroup group = BundleGroup.newGroup();
        TestBundlable inA = new TestBundlable();
        inA.value = "A";

        TestBundlable inB = new TestBundlable();
        inB.value = "B";

        inA.reference = inB;
        inB.reference = inA;

        group.putBundlable( "A", inA );
        group.putBundlable( "B", inB );

        group = BundleGroup.loadGroup( group.toString() );

        TestBundlable outA = group.getBundlable( "A" );
        TestBundlable outB = group.getBundlable( "B" );

        assertTrue( outA.equals( inA ) );
        assertTrue( outB.equals( inB ) );

        assertTrue( outA == outB.reference );
        assertTrue( outB == outA.reference );
    }

    public void testBundleCollection() {
        BundleGroup group = BundleGroup.newGroup();

        TestBundlable inA = new TestBundlable();
        inA.value = "A";
        TestBundlable inB = new TestBundlable();
        inB.value = "B";
        TestBundlable inC = new TestBundlable();
        inC.value = "C";

        List<Bundlable> inList = new ArrayList<>();
        inList.add( inA );
        inList.add( inB );
        inList.add( null );
        inList.add( inC );
        inList.add( null );

        group.putBundlables( "tests", inList );

        group = BundleGroup.loadGroup( group.toString() );

        List<Bundlable> outList = group.getBundlables( "tests" );
        assertEquals( outList, inList );
    }

    public void testUnregisteredClassOnSave() {
        BundleGroup group = BundleGroup.newGroup();
        try {
            group.putBundlable( "test", new Unregistered() );
        } catch (  UnregisteredConstructorException e ) {
            return;
        }
        fail( "test should have thrown a unregistered constructor error" );
    }

    public void testBundlableReturnsNullBundle() {
        BundleGroup group = BundleGroup.newGroup();
        group.putBundlable( "test", new NullBundle() );
        try {
            group.toString();
        } catch ( NullPointerException e ) {
            return;
        }
        fail( "Bundle shouldn't have been able to restore object" );
    }

    public void testNullConstruction() {
        BundleGroup group = BundleGroup.newGroup();
        group.putBundlable( "test", new NullConstruction() );

        String save = group.toString();
        try {
            BundleGroup.loadGroup( save );
        } catch ( NullPointerException e ) {
            return;
        }
        fail( "Bundle load should have failed due to null construction" );
    }

    public void testNullBundlable() throws Exception {
        BundleGroup group = BundleGroup.newGroup();
        group.putBundlable( "null", null );
        group = BundleGroup.loadGroup( group.toString() );
        assertNull( group.getBundlable( "null" ) );
    }

    public void testGetKeys() throws Exception {
        BundleGroup group = BundleGroup.newGroup();
        group.putString( "A", "1" );
        group.putString( "B", "2" );
        group.putString( "C", "3" );

        List<String> expected = new ArrayList<>();
        expected.add( "A" );
        expected.add( "B" );
        expected.add( "C" );

        List<String> actual = new ArrayList<>();
        actual.addAll( group.keys() );
        assertEquals( expected, actual );

        // test again with a loaded bundle
        group = BundleGroup.loadGroup( group.toString() );
        actual = new ArrayList<>();
        actual.addAll( group.keys() );
        assertEquals( expected, actual );
    }


    public void testVersion() {
        BundleGroup group = BundleGroup.newGroup();
        Version expected = Version.currentVersion();
        // check the top level has the correct version
        assertEquals( expected, group.getVersion() );
        // check sub bundles have correct version
        assertEquals( expected, group.newBundle().getVersion() );

        group = BundleGroup.loadGroup( group.toString() );
        assertEquals( expected, group.getVersion() );
    }

    public void testDoubleArray() {
        BundleGroup group = BundleGroup.newGroup();
        double[][] array = {{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        group.put( "array", array );

        group = BundleGroup.loadGroup( group.toString() );

        double[][] result = group.getDoubleArray( "array" );

        assertTrue( Arrays.deepEquals( array, result ) );
    }
}