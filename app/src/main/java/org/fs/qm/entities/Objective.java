package org.fs.qm.entities;

import org.fs.exception.AndroidException;
import org.fs.qm.R;

import java.util.Locale;

/**
 * Created by Fatih on 13/06/16.
 * as org.fs.qm.entities.Objective
 */
public enum Objective {
    MAXIMIZE(R.id.radioMax),
    MINIMIZE(R.id.radioMin);

    private final  int          intValue;
    private static Objective[]  valuesCache;

    Objective(int intValue) {
        this.intValue = intValue;
    }

    public static Objective from(int intValue) {
        //lazy initialization
        if(valuesCache == null) {
            valuesCache = values();
        }
        //loop over values
        for (Objective func : valuesCache) {
            if(func.intValue == intValue) {
                return func;
            }
        }
        //docs says send exception if no such enum exists
        throw new AndroidException(String.format(Locale.US,
                                                 "Objective is not one of the enum values in the value list as %d",
                                                 intValue));
    }
}
