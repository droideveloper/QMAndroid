package org.fs.qm.holders;

import android.view.View;
import android.widget.EditText;

import org.fs.qm.R;
import org.fs.qm.entities.TextCell;
import org.fs.util.ViewUtility;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.holders.TextTypeHolder
 */
public class TextTypeHolder extends BaseTypeHolder<TextCell> {

    private EditText edtText;

    public TextTypeHolder(View view) {
        super(view);
        edtText = ViewUtility.findViewById(view, R.id.edtText);
    }

    @Override protected String getClassTag() {
        return TextTypeHolder.class.getSimpleName();
    }

    @Override protected void onBindView(TextCell data) {
        super.onBindView(data);
        if(edtText != null) {
            edtText.setText(String.valueOf(data.getText()));
        }
    }
}
