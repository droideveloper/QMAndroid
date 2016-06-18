package org.fs.util;

import org.fs.exception.AndroidException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.util.Collections
 */
public final class Collections {

    private Collections() {
        throw new AndroidException("no sugar for ya");
    }

    /**
     * filters collection and returns matching results as new collection
     * @param target target collection to be filtered
     * @param predicate filter logic defined
     * @param <T> Type
     * @return new Collection, filtered accordingly it might be empty
     * @throws AndroidException if target is null or empty and also if predicate is null
     */
    public static <T> Collection<T> filter(Collection<T> target, IPredicate<T> predicate) {
        if(target == null || target.isEmpty()) throw new AndroidException("target is empty or null");
        if(predicate == null) throw new AndroidException("predicate is null, can't apply filter");
        Collection<T> result = new ArrayList<>();
        for (T item : target) {
            if(predicate.apply(item)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * filter logic implementation
     * @param <T> Type
     */
    interface IPredicate<T> {
        /**
         * filtering logic you implement in here
         * @param type T type of object
         * @return true or false
         */
        boolean apply(T type);
    }
}
