package com.github.texxel.actors;

import com.github.texxel.utils.AdditiveRange;
import com.github.texxel.utils.Range;

public class AdditiveBonus implements Bonus {

    private static final long serialVersionUID = 7161221215837542599L;
    private final Range amount;

    public AdditiveBonus( Range amount ) {
        this.amount = amount;
    }

    @Override
    public int priority() {
        return Bonus.ADD;
    }

    @Override
    public Range alter( Range value ) {
        return new AdditiveRange( value, amount );
    }
}
