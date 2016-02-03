package com.github.texxel.actors;

import com.github.texxel.utils.MultiplicativeRange;
import com.github.texxel.utils.Range;

public class MultiplicativeBonus implements Bonus {

    private static final long serialVersionUID = 6402238445370922129L;
    private final Range scale;

    public MultiplicativeBonus( Range scale ) {
        this.scale = scale;
    }

    @Override
    public int priority() {
        return Bonus.MULTIPLY;
    }

    @Override
    public Range alter( Range value ) {
        return new MultiplicativeRange( value, scale );
    }
}
