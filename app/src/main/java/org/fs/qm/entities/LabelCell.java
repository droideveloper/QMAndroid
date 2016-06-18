package org.fs.qm.entities;

import android.os.Parcel;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractEntity;
import org.fs.util.StringUtility;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.entities.TextCell
 */
public final class LabelCell extends AbstractEntity implements ICellEntity {

    private String textStr;

    public static LabelCell newLabelCellEntity(String textStr) {
        return new LabelCell(textStr);
    }

    private LabelCell(String textStr) {
        this.textStr = textStr;
    }

    public LabelCell(Parcel input) {
        super(input);
    }

    @Override protected void readParcel(Parcel input) {
        boolean hasTextStr = input.readInt() == 1;
        if(hasTextStr) {
            textStr = input.readString();
        }
    }

    public String getTextStr() {
        return textStr;
    }

    @Override protected String getClassTag() {
        return LabelCell.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel output, int flags) {
        boolean hasTextStr = !StringUtility.isNullOrEmpty(textStr);
        output.writeInt(hasTextStr ? 1 : 0);
        if(hasTextStr) {
            output.writeString(textStr);
        }
    }

    public final static Creator<LabelCell> CREATOR = new Creator<LabelCell>() {

        @Override public LabelCell createFromParcel(Parcel input) {
            return new LabelCell(input);
        }

        @Override public LabelCell[] newArray(int size) {
            return new LabelCell[size];
        }
    };
}