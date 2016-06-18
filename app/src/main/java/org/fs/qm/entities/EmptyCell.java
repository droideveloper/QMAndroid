package org.fs.qm.entities;

import android.os.Parcel;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractEntity;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.entities.EmptyCell
 */
public final class EmptyCell extends AbstractEntity implements ICellEntity {


    public static EmptyCell newEmptyCellEntity() {
        return new EmptyCell();
    }

    public EmptyCell(Parcel input) {
        super(input);
    }

    private EmptyCell() { }

    @Override protected void readParcel(Parcel input) {
    }

    @Override protected String getClassTag() {
        return EmptyCell.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel out, int flags) {
    }

    public final static Creator<EmptyCell> CREATOR = new Creator<EmptyCell>() {

        @Override public EmptyCell createFromParcel(Parcel input) {
            return new EmptyCell(input);
        }

        @Override public EmptyCell[] newArray(int size) {
            return new EmptyCell[size];
        }
    };
}