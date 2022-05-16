package net.lastcraft.base.util;

import java.util.Collection;
import java.util.Iterator;

@Deprecated
public class DIterator<T> {

    private Collection<T> collection;
    private Iterator<T> iterator;

    public DIterator(Collection<T> collection) {
        this.collection = collection;
        this.iterator = collection.iterator();
    }

    public T getNext() {
        if (!this.iterator.hasNext()) 
            this.iterator = this.collection.iterator();
        
        return this.iterator.next();
    }

    @Override
    public String toString() {
        return "DIterator{Collection=" + this.collection.getClass().getSimpleName()
                + ",size=" + collection.size() + '}';
    }
}
