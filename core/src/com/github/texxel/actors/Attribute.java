package com.github.texxel.actors;

import com.github.texxel.utils.ConstantRange;
import com.github.texxel.utils.Range;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Attribute implements Serializable {

    private static final long serialVersionUID = 5929859995859958592L;
    public final String name;
    public Range baseValue;
    private ArrayList<Bonus> bonuses = new ArrayList<>();
    @SuppressWarnings( "NonSerializableFieldInSerializableClass" )
    private List<Bonus> publicBonuses = Collections.unmodifiableList( bonuses );

    Attribute( String name ) {
        this.name = name;
        baseValue = ConstantRange.ZERO;
    }

    public Range value() {
        Range amount = baseValue;
        for ( Bonus bonus : bonuses )
            amount = bonus.alter( amount );
        return amount;
    }

    public List<Bonus> getBonuses() {
        return publicBonuses;
    }

    public Attribute addBonus( Bonus bonus ) {
        bonuses.add( bonus );
        bonuses.sort( new Comparator<Bonus>() {
            @Override
            public int compare( Bonus a, Bonus b ) {
                return Integer.compare( a.priority(), b.priority() );
            }
        } );
        return this;
    }

    public Attribute removeBonus( Bonus bonus ) {
        bonuses.remove( bonus );
        return this;
    }

}
