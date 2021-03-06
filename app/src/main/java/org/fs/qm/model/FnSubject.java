package org.fs.qm.model;

import android.os.Parcel;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractEntity;
import org.fs.util.Collections;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fatih on 20/06/16.
 * as org.fs.qm.model.FnSubject
 */
public final class FnSubject extends AbstractEntity {

    private List<Constraint> cons;

    private FnSubject(List<Constraint> cons) {
        this.cons = cons;
    }

    public FnSubject(Parcel input) {
        super(input);
    }

    public List<Constraint> getCons() {
        return cons;
    }

    public void setCons(List<Constraint> cons) {
        this.cons = cons;
    }

    public boolean hasConstraints() {
        return !Collections.isNullOrEmpty(cons);
    }

    public int constraintsSize() {
        return cons != null ? cons.size() : 0;
    }

    public Constraint constraintAt(int index) {
        if(hasConstraints() && index >= 0 && index < constraintsSize()) {
            return cons.get(index);
        }
        return null;
    }

    public Builder newBuilder() {
        return new Builder()
                        .cons(cons);
    }

    @Override public String toString() {
        StringBuilder str = new StringBuilder(128);
        str.append("Subject to:\n");
        for (int i = 0; i < constraintsSize(); i++) {
            Constraint c = constraintAt(i);
            if(c != null) {
                str.append("\t\t");
                str.append(c.toString());
                str.append("\n");
            }
        }
        return str.toString();
    }

    @Override protected void readParcel(Parcel input) {
        boolean hasCons = input.readInt() == 1;
        if (hasCons) {
            cons = new ArrayList<>();
            input.readList(cons, Constraint.class.getClassLoader());
        }
    }

    @Override protected String getClassTag() {
        return FnSubject.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel out, int flags) {
        boolean hasCons = !Collections.isNullOrEmpty(cons);
        out.writeInt(hasCons ? 1 : 0);
        if (hasCons) {
            out.writeList(cons);
        }
    }

    public static class Builder {
        private List<Constraint> cons;

        public Builder() { }
        public Builder cons(List<Constraint> cons) { this.cons = cons; return this; }
        public FnSubject build() {
            return new FnSubject(cons);
        }
    }

    public final static Creator<FnSubject> CREATOR = new Creator<FnSubject>() {

        @Override public FnSubject createFromParcel(Parcel input) {
            return new FnSubject(input);
        }

        @Override public FnSubject[] newArray(int size) {
            return new FnSubject[size];
        }
    };
}