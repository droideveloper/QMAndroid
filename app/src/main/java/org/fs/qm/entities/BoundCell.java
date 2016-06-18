package org.fs.qm.entities;

import android.os.Parcel;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractEntity;

import java.util.List;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.entities.BoundCell
 */
public final class BoundCell extends AbstractEntity implements ICellEntity {

    private List<String> bounds;

    public static BoundCell newBoundCellEntity(List<String> bounds) {
        return new BoundCell(bounds);
    }

    private BoundCell(List<String> bounds) {
        this.bounds = bounds;
    }

    public BoundCell(Parcel input) {
        super(input);
    }

    @Override protected void readParcel(Parcel input) {
        boolean hasBounds = input.readInt() == 1;
        if(hasBounds) {
            input.readStringList(bounds);
        }
    }

    public List<String> getBounds() {
        return bounds;
    }

    @Override protected String getClassTag() {
        return BoundCell.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel output, int flags) {
        boolean hasBounds = bounds != null && !bounds.isEmpty();
        output.writeInt(hasBounds ? 1 : 0);
        if(hasBounds) {
            output.writeStringList(bounds);
        }
    }

    public final static Creator<BoundCell> CREATOR = new Creator<BoundCell>() {

        @Override public BoundCell createFromParcel(Parcel input) {
            return new BoundCell(input);
        }

        @Override public BoundCell[] newArray(int size) {
            return new BoundCell[size];
        }
    };
}