package com.github.texxel.items.bags;

import com.github.texxel.items.Item;
import com.github.texxel.items.ItemStack;
import com.github.texxel.utils.Filter;
import com.github.texxel.utils.FilterUtils;
import com.github.texxel.utils.SaveUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

public class Bag extends BackPack {

    private static final long serialVersionUID = 2385627011069300852L;
    private transient Filter<Item> filter = FilterUtils.anyObject();

    public Filter<Item> getFilter() {
        return filter;
    }

    public Bag( int size ) {
        super( size );
    }

    public Bag setFilter( Filter<Item> filter ) {
        if ( filter == null )
            throw new NullPointerException( "'filter' cannot be null" );
        this.filter = filter;
        return this;
    }

    @Override
    public boolean canCollect( Item item ) {
        return filter.isAllowed( item ) && super.canCollect( item );
    }

    @Override
    public boolean collect( ItemStack item ) {
        return !filter.isAllowed( item.item() ) && super.collect( item );
    }

    @Override
    public List<Bag> getBags() {
        return Collections.emptyList();
    }

    private void writeObject( ObjectOutputStream out ) throws IOException {
        out.defaultWriteObject();

        out.writeObject( filter );
    }

    @SuppressWarnings( "unchecked" )
    private void readObject( ObjectInputStream in ) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        filter = SaveUtils.readObject( in, FilterUtils.<Item>anyObject() );
    }

}
