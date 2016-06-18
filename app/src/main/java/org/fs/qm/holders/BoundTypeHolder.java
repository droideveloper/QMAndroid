package org.fs.qm.holders;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import org.fs.qm.R;
import org.fs.qm.adapters.BoundAdapter;
import org.fs.qm.entities.BoundCell;
import org.fs.util.ViewUtility;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.holders.BoundTypeHolder
 */
public class BoundTypeHolder extends BaseTypeHolder<BoundCell> implements Spinner.OnItemSelectedListener {

    private  Spinner      spinner;

    public BoundTypeHolder(View view) {
        super(view);
        spinner = ViewUtility.findViewById(view, R.id.dropDownSigns);
    }

    @Override protected String getClassTag() {
        return BoundTypeHolder.class.getSimpleName();
    }

    @Override protected void onBindView(BoundCell data) {
        super.onBindView(data);
        BoundAdapter adapter = new BoundAdapter(itemView.getContext(), data.getBounds());
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        data.setSelectedIndex(position);
    }

    @Override public void onNothingSelected(AdapterView<?> parent) {
        //ignored
    }
}
