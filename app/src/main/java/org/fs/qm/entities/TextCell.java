package org.fs.qm.entities;

import android.os.Parcel;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractEntity;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.entities.TextCell
 */
public final class TextCell extends AbstractEntity implements ICellEntity {

    private double text;

    public static TextCell newTextCellEntity(double text) {
        return new TextCell(text);
    }

    private TextCell(double text) {
        this.text = text;
    }

    public TextCell(Parcel input) {
        super(input);
    }

    @Override protected void readParcel(Parcel input) {
       boolean hasText = input.readInt() == 1;
       if(hasText) {
           text = input.readDouble();
       }
    }

    public double getText() {
        return text;
    }

    public void setText(double text) {
        this.text = text;
    }

    @Override protected String getClassTag() {
        return TextCell.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel out, int flags) {
        boolean hasText = text >= 0.0d;
        out.writeInt(hasText ? 1 : 0);
        if(hasText) {
            out.writeDouble(text);
        }
    }

    public final static Creator<TextCell> CREATOR = new Creator<TextCell>() {

        @Override public TextCell createFromParcel(Parcel input) {
            return new TextCell(input);
        }

        @Override public TextCell[] newArray(int size) {
            return new TextCell[size];
        }
    };
}