package com.github.texxel.utils;

import junit.framework.TestCase;

public class RandomCategoryTest extends TestCase {

    private class Element {
        public String id;

        public Element set( String id ) {
            this.id = id;
            return this;
        }

        @Override
        public String toString() {
            return id;
        }
    }

    public void testAdd() throws Exception {
        RandomCategory<Element> category = new RandomCategory<>();

        category.add( new Element(), 5 );
        assertEquals( 1, category.size );
        assertEquals( Element.class, category.objs[0].getClass() );
        assertEquals( 5, category.probs[0], 0.01f );
        assertEquals( 5, category.totalProb, 0.01f );


        category.add( new Element(), 4 );
        assertEquals( 2, category.size );
        assertEquals( Element.class, category.objs[1].getClass() );
        assertEquals( 4, category.probs[1], 0.01f );
        assertEquals( 9, category.totalProb, 0.01f );
    }

    public void testResize() {
        RandomCategory<Element> category = new RandomCategory<>();
        for ( int i = 0; i < 100; i++ ) {
            category.add( new Element(), i );
        }
    }

    public void testRemove() throws Exception {
        RandomCategory<Element> category = new RandomCategory<>();

        Element a = new Element().set( "a" );
        Element b = new Element().set( "b" );
        Element c = new Element().set( "c" );

        category.add( a, 1 );
        category.add( b, 2 );
        category.add( c, 3 );
        category.add( a, 4 );
        category.add( b, 5 );
        category.add( c, 6 );
        category.remove( b );

        assertEquals( 4, category.size );
        assertEquals( 1 + 3 + 4 + 6, category.totalProb, 0.01f );
    }

    public void testNext() throws Exception {

        RandomCategory<Element> category = new RandomCategory<>();

        Element a = new Element().set( "a" );
        Element b = new Element().set( "b" );
        Element c = new Element().set( "c" );

        category.add( a, 1 ).add( b, 2 ).add( c, 3 );

        // Testing by lots of trails. Bad coder monkey!
        // Anyhow, elements should come out with the approximate relative probabilities
        int aHits = 0; int bHits = 0; int cHits = 0;

        for ( int i = 0; i < 10000; i++ ) {
            Element e = category.next();
            if ( e == a )
                aHits++;
            else if ( e == b )
                bHits++;
            else if ( e == c )
                cHits++;
            else
                throw new AssertionError( "Invalid output: " + e );
        }

        int margin = 1000;
        int expectedB = aHits * 2;
        int expectedC = aHits * 3;
        assertEquals( expectedB, bHits, margin );
        assertEquals( expectedC, cHits, margin );

    }
}