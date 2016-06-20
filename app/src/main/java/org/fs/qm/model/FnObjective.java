package org.fs.qm.model;

import android.os.Parcel;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractEntity;
import org.fs.qm.entities.Objective;
import org.fs.util.Collections;
import org.fs.util.StringUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fatih on 20/06/16.
 * as org.fs.qm.model.FnObjective
 */
public final class FnObjective extends AbstractEntity {

    private String         name;
    private Objective      type;
    private List<Variable> vars;

    private FnObjective(String name, Objective type, List<Variable> vars) {
        this.name = name;
        this.type = type;
        this.vars = vars;
    }

    public FnObjective(Parcel input) {
        super(input);
    }

    public List<Variable> getVars() {
        return vars;
    }

    public Objective getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setType(Objective type) {
        this.type = type;
    }

    public void setVars(List<Variable> vars) {
        this.vars = vars;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasVariables() {
        return !Collections.isNullOrEmpty(vars);
    }

    public int variablesSize() {
        return vars != null ? vars.size() : 0;
    }

    public Variable variableAt(int index) {
        if(index < variablesSize() && index >= 0) {
            return vars.get(index);
        }
        return null;
    }

    public Builder newBuilder() {
        return new Builder()
                    .name(name)
                    .type(type)
                    .vars(vars);
    }

    @Override protected void readParcel(Parcel input) {
        boolean hasName = input.readInt() == 1;
        if (hasName) {
            this.name = input.readString();
        }
        String type = input.readString();
        this.type = Objective.valueOf(type);
        boolean hasVars = input.readInt() == 1;
        if (hasVars) {
            vars = new ArrayList<>();
            input.readList(vars, Variable.class.getClassLoader());
        }
    }

    @Override protected String getClassTag() {
        return FnObjective.class.getSimpleName();
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
        out.writeString(type.name());
        boolean hasVars = !Collections.isNullOrEmpty(vars);
        out.writeInt(hasVars ? 1 : 0);
        if (hasVars) {
            out.writeList(vars);
        }
    }

    public static class Builder {
        private String         name;
        private Objective      type;
        private List<Variable> vars;

        public Builder() { }
        public Builder name(String name)            { this.name = name; return this; }
        public Builder type(Objective type)         { this.type = type; return this; }
        public Builder vars(List<Variable> vars)    { this.vars = vars; return this; }
        public FnObjective build() {
            return new FnObjective(name, type, vars);
        }
    }

    public final static Creator<FnObjective> CREATOR = new Creator<FnObjective>() {

        @Override public FnObjective createFromParcel(Parcel input) {
            return new FnObjective(input);
        }

        @Override public FnObjective[] newArray(int size) {
            return new FnObjective[size];
        }
    };
}