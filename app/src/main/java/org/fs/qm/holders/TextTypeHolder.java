package org.fs.qm.holders;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;

import org.fs.qm.R;
import org.fs.qm.entities.TextCell;
import org.fs.qm.views.TextDialogFragmentView;
import org.fs.util.ViewUtility;

import java.lang.ref.WeakReference;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.holders.TextTypeHolder
 */
public class TextTypeHolder extends BaseTypeHolder<TextCell> implements View.OnClickListener, TextDialogFragmentView.OnTextViewChangedListener {

    private EditText edtText;
    private WeakReference<FragmentManager> fragmentManagerRef;

    public TextTypeHolder(View view, FragmentManager fragmentManager) {
        super(view);
        edtText = ViewUtility.findViewById(view, R.id.edtText);
        edtText.setOnClickListener(this);
        fragmentManagerRef = fragmentManager != null ? new WeakReference<>(fragmentManager) : null;
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

    @Override public void onClick(View v) {
        TextDialogFragmentView cellView = new TextDialogFragmentView();
        cellView.setTextChangedListener(this);
        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager != null) {
            cellView.show(fragmentManager, "inputDialog");
        }
    }

    @Override public void onTextChanged(String nextStr) {
        edtText.setText(nextStr);
        //changed value of data too that will be used in solving problem
        double text = Double.parseDouble(nextStr);
        data.setText(text);
    }

    FragmentManager getFragmentManager() {
        return fragmentManagerRef != null ? fragmentManagerRef.get() : null;
    }
}
