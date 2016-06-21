package org.fs.qm.model;

import android.os.Parcel;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractEntity;

/**
 * Created by Fatih on 21/06/16.
 * as org.fs.qm.model.Position
 */
public final class Position extends AbstractEntity {

    private double x;
    private double y;

    public static Position create(double x, double y) {
        return new Position(x, y);
    }

    public Position(Parcel input) {
        super(input);
    }

    private Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override protected void readParcel(Parcel input) {
        x = input.readDouble();
        y = input.readDouble();
    }

    @Override protected String getClassTag() {
        return Position.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel out, int flags) {
        out.writeDouble(x);
        out.writeDouble(y);
    }

    public final static Creator<Position> CREATOR = new Creator<Position>() {

        @Override public Position createFromParcel(Parcel input) {
            return new Position(input);
        }

        @Override public Position[] newArray(int size) {
            return new Position[size];
        }
    };
}