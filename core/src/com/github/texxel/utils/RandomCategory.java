package com.github.texxel.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class RandomCategory<T> implements Category<T>, Serializable {

    private static final long serialVersionUID = 4884408571353445951L;

    transient int size = 0;
    transient Object[] objs = new Object[4];
    transient float[] probs = new float[4];
    transient float totalProb = 0;

    /**
     * Adds an object to the category with a probability. The probability is a relative probability
     * to the other objects in the category.
     * @param obj the object to add
     * @param probability the probability to add the object with
     * @return this
     */
    public RandomCategory<T> add( T obj, float probability  ) {
        Assert.nonnull( obj, "Cannot add a null object" );
        Assert.over( probability, 0, "Probability must be over 0" );

        assert objs.length == probs.length;
        if ( size >= objs.length )
            increaseCapacity( objs.length * 2 );
        assert size < objs.length;

        objs[size] = obj;
        probs[size] = probability;
        size++;

        totalProb += probability;

        return this;
    }

    /**
     * Adds all the elements of the other category
     * @param other the other category to add
     */
    public void addAll( RandomCategory<? extends T> other ) {
        for ( int i = 0; i < other.size; i++ ) {
            add( (T)other.objs[i], other.probs[i] );
        }
    }

    /**
     * Removes anything that is equal to obj. More explicitly, if {@code obj.equals( other )} returns
     * true, then the object will be removed.
     * @param obj the object to remove
     * @return this
     */
    public RandomCategory<T> remove( T obj ) {
        Assert.nonnull( obj, "Cannot remove a null object" );
        for ( int i = 0; i < size; i++ ) {
            Object other = objs[i];
            assert other != null;
            if ( obj.equals( other ) ) {
                remove( i );
                i--; // recheck the element that was placed in the new index
            }
        }
        return this;
    }

    @Override
    public T next() {
        float total = Random.Float( totalProb );
        float[] probs = this.probs;
        for ( int i = size-1; i >= 0; i-- ) {
            total -= probs[i];
            if ( total <= 0 )
                return (T) objs[i];
        }
        throw new IllegalStateException( "No items have been added to the category" );
    }

    /**
     * Removes the element at the given position. This simply shifts the element at the end of the
     * array into the given index and then nulls out the end element.
     */
    private void remove( int index ) {
        int lastIndex = size-1;

        totalProb -= probs[index];

        objs[index] = objs[lastIndex];
        probs[index] = probs[lastIndex];

        objs[lastIndex] = null;
        probs[lastIndex] = 0;

        size--;
    }

    /**
     * Sets the the size to be the new size. The newSize should be lager than the current size
     * or else an IndexOutOfBounds exception will be thrown
     */
    private void increaseCapacity( int newSize ) {
        Object[] newObjs = new Object[newSize];
        float[] newProbs = new float[newSize];
        System.arraycopy( objs, 0, newObjs, 0, size );
        System.arraycopy( probs, 0, newProbs, 0, size );
        objs = newObjs;
        probs = newProbs;
    }

    private void readObject( ObjectInputStream in ) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        totalProb = 0;
        size = in.readInt();
        int capacity = Math.max( size, 4 );
        objs = new Object[capacity];
        probs = new float[capacity];

        for (int i = 0; i < size; i++ ) {
            Object obj = SaveUtils.readObject( in, null );
            if ( obj == null ) {
                // skip that item
                size--;
                i--;
                continue;
            }
            objs[i] = obj;
            probs[i] = in.readFloat();
        }

    }

    private void writeObject( ObjectOutputStream out ) throws IOException {
        out.defaultWriteObject();

        out.writeInt( size );

        // write the objects in obj-prob order
        for ( int i = 0; i < size; i++ ) {
            //noinspection NonSerializableObjectPassedToObjectStream
            out.writeObject( objs[i] );
            out.writeFloat( probs[i] );
        }
    }

}
