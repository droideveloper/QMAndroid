package org.fs.qm.model;

import android.os.Parcel;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractEntity;
import org.fs.util.Collections;
import org.fs.util.StringUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fatih on 20/06/16.
 * as org.fs.qm.model.Constraint
 */
public final class Constraint extends AbstractEntity {

    private String         name;
    private int            index;
    private int            bound;
    private double         lhs;
    private double         rhs;
    private List<Variable> vars;

    private Constraint(String name, int index, int bound, double lhs, double rhs, List<Variable> vars) {
        this.name = name;
        this.index = index;
        this.bound = bound;
        this.lhs = lhs;
        this.rhs = rhs;
        this.vars = vars;
    }

    public Constraint(Parcel input) {
        super(input);
    }

    public String getName() {
        return name;
    }

    public Constraint setName(String name) {
        this.name = name;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public Constraint setIndex(int index) {
        this.index = index;
        return this;
    }

    public int getBound() {
        return bound;
    }

    public Constraint setBound(int bound) {
        this.bound = bound;
        return this;
    }

    public double getLhs() {
        return lhs;
    }

    public Constraint setLhs(double lhs) {
        this.lhs = lhs;
        return this;
    }

    public double getRhs() {
        return rhs;
    }

    public Constraint setRhs(double rhs) {
        this.rhs = rhs;
        return this;
    }

    public boolean hasVariables() {
        return !Collections.isNullOrEmpty(vars);
    }

    public int variablesSize() {
        return vars != null ? vars.size() : 0;
    }

    public double[] variablesCoef() {
        double[] coefs = new double[variablesSize()];
        for (int i = 0; i < variablesSize(); i++) {
            Variable var = variableAt(i);
            if(var != null) {
                coefs[i] = var.getCoef();
            } else {
                coefs[i] = 0.0d;
            }
        }
        return coefs;
    }

    public Variable variableAt(int index) {
        if(index < variablesSize() && index >= 0) {
            return vars.get(index);
        }
        return null;
    }

    public List<Variable> getVars() {
        return vars;
    }

    public Constraint setVars(List<Variable> vars) {
        this.vars = vars;
        return this;
    }

    @Override public String toString() {
        StringBuilder str = new StringBuilder(128);
        str.append(name);
        str.append("\t=\t");
        for (int i = 0; i < variablesSize(); i++) {
            Variable var = variableAt(i);
            if(var != null) {
                str.append(var.toString());
                if (i != variablesSize() -1) {
                    str.append("\t");
                    str.append(var.getCoef() >= 0 ? '+' : '+');
                    str.append("\t");
                }
            }
        }
        return str.toString();
    }

    @Override protected void readParcel(Parcel input) {
        boolean hasName = input.readInt() == 1;
        if (hasName) {
            name = input.readString();
        }
        index = input.readInt();
        bound = input.readInt();
        lhs   = input.readDouble();
        rhs   = input.readDouble();
        boolean hasVars = input.readInt() == 1;
        if (hasVars) {
            vars = new ArrayList<>();
            input.readList(vars, Variable.class.getClassLoader());
        }
    }

    @Override protected String getClassTag() {
        return Constraint.class.getSimpleName();
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
        out.writeInt(index);
        out.writeInt(bound);
        out.writeDouble(lhs);
        out.writeDouble(rhs);
        boolean hasVars = !Collections.isNullOrEmpty(vars);
        out.writeInt(hasVars ? 1 : 0);
        if (hasVars) {
            out.writeList(vars);
        }
    }

    public static class Builder {
        private String         name;
        private int            index;
        private int            bound;
        private double         lhs;
        private double         rhs;
        private List<Variable> vars;

        public Builder() { }
        public Builder name(String name) { this.name = name; return this;   }
        public Builder index(int index)  { this.index = index; return this; }
        public Builder bound(int bound)  { this.bound = bound; return this; }
        public Builder lhs(double lhs) { this.lhs = lhs; return this; }
        public Builder rhs(double rhs) { this.rhs = rhs; return this; }
        public Builder vars(List<Variable> vars) { this.vars = vars; return this; }
        public Constraint build() {
            return new Constraint(name, index, bound, lhs, rhs, vars);
        }
    }

    public final static Creator<Constraint> CREATOR = new Creator<Constraint>() {

        @Override
        public Constraint createFromParcel(Parcel input) {
            return new Constraint(input);
        }

        @Override
        public Constraint[] newArray(int size) {
            return new Constraint[size];
        }
    };
}