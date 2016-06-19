package org.fs.qm.holders;

import android.view.View;
import android.widget.TextView;

import org.fs.qm.R;
import org.fs.qm.entities.LabelCell;
import org.fs.util.ViewUtility;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.holders.LabelTypeHolder
 */
public class LabelTypeHolder extends BaseTypeHolder<LabelCell> {

    private TextView txtLabel;

    public LabelTypeHolder(View view) {
        super(view);
        txtLabel = ViewUtility.findViewById(view, R.id.txtLabel);
    }

    @Override protected String getClassTag() {
        return LabelTypeHolder.class.getSimpleName();
    }

    @Override protected void onBindView(LabelCell data) {
        super.onBindView(data);
        if(txtLabel != null) {
            txtLabel.setText(data.getTextStr());
        }
    }
}
