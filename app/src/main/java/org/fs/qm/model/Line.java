package org.fs.qm.model;

import android.os.Parcel;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractEntity;
import org.fs.util.StringUtility;

/**
 * Created by Fatih on 21/06/16.
 * as org.fs.qm.model.Line
 */
public final class Line extends AbstractEntity {

    private Position first;  //(x, 0) with values
    private Position second; //(0, y) with values
    private int      direction;//gt or lt (greater than or lower than) -1 for lt and 1 for gt TODO

    public static Line create(Position first, Position second, int direction) {
        return new Line(first, second, direction);
    }

    public Line(Parcel input) {
        super(input);
    }

    private Line(Position first, Position second, int direction) {
        this.first = first;
        this.second = second;
        this.direction = direction;
    }

    @Override protected void readParcel(Parcel input) {
        boolean hasFirst = input.readInt() == 1;
        if (hasFirst) {
            first = input.readParcelable(Position.class.getClassLoader());
        }
        boolean hasSecond = input.readInt() == 1;
        if (hasSecond) {
            second = input.readParcelable(Position.class.getClassLoader());
        }
        direction = input.readInt();
    }

    public Position getFirst() {
        return first;
    }

    public Position getSecond() {
        return second;
    }

    public int getDirection() {
        return direction;
    }

    @Override protected String getClassTag() {
        return Line.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel out, int flags) {
        boolean hasFirst = !StringUtility.isNullOrEmpty(first);
        out.writeInt(hasFirst ? 1 : 0);
        if (hasFirst) {
            out.writeParcelable(first, flags);
        }
        boolean hasSecond = !StringUtility.isNullOrEmpty(second);
        out.writeInt(hasSecond ? 1 : 0);
        if (hasSecond) {
            out.writeParcelable(second, flags);
        }
        out.writeInt(direction);
    }

    public final static Creator<Line> CREATOR = new Creator<Line>() {

        @Override public Line createFromParcel(Parcel input) {
            return new Line(input);
        }

        @Override public Line[] newArray(int size) {
            return new Line[size];
        }
    };
}