package org.fs.qm.model;

import android.os.Parcel;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractEntity;
import org.fs.util.StringUtility;

import java.util.Locale;

/**
 * Created by Fatih on 20/06/16.
 * as org.fs.qm.model.Variable
 */
public final class Variable extends AbstractEntity {

    private String name;
    private double coef;
    private int    index;
    private int    bound;
    private double lhs;
    private double rhs;

    public Variable(Parcel input) {
        super(input);
    }

    private Variable(String name, double coef, int index, int bound, double lhs, double rhs) {
        this.name = name;
        this.coef = coef;
        this.index = index;
        this.bound = bound;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Builder newBuilder() {
        return new Builder()
                    .name(name)
                    .coef(coef)
                    .index(index)
                    .bound(bound)
                    .lhs(lhs)
                    .rhs(rhs);
    }

    public String getName() {
        return name;
    }

    public double getCoef() {
        return coef;
    }

    public int getIndex() {
        return index;
    }

    public int getBound() {
        return bound;
    }

    public double getLhs() {
        return lhs;
    }

    public double getRhs() {
        return rhs;
    }

    @Override public String toString() {
        return String.format(Locale.getDefault(),
                                "%.1f%s", coef, name);
    }

    @Override protected void readParcel(Parcel input) {
        boolean hasName = input.readInt() == 1;
        if (hasName) {
            name = input.readString();
        }
        coef = input.readDouble();
        index = input.readInt();
        bound = input.readInt();
        lhs = input.readDouble();
        rhs = input.readDouble();
    }

    @Override protected String getClassTag() {
        return Variable.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel out, int flags) {
        boolean hasName = !StringUtility.isNullOrEmpty(name);
        out.writeInt(hasName ? 1 : 0);
        if (hasName) {
            out.writeString(name);
        }
        out.writeDouble(coef);
        out.writeInt(index);
        out.writeInt(bound);
        out.writeDouble(lhs);
        out.writeDouble(rhs);
    }

    /**
     * public org.fs.qm.model.Variable
     */
    public static class Builder {
        private String name;
        private double coef;
        private int    index;
        private int    bound;
        private double lhs;
        private double rhs;

        public Builder() { }
        public Builder name(String name)    { this.name = name; return this; }
        public Builder coef(double coef)    { this.coef = coef; return this; }
        public Builder index(int index)     { this.index = index; return this; }
        public Builder bound(int bound)     { this.bound = bound; return this; }
        public Builder lhs(double lhs)      { this.lhs = lhs; return this; }
        public Builder rhs(double rhs)      { this.rhs = rhs; return this; }
        public Variable build() {
            return new Variable(name, coef, index, bound, lhs, rhs);
        }
    }

    public final static Creator<Variable> CREATOR = new Creator<Variable>() {

        @Override public Variable createFromParcel(Parcel input) {
            return new Variable(input);
        }

        @Override public Variable[] newArray(int size) {
            return new Variable[size];
        }
    };
}