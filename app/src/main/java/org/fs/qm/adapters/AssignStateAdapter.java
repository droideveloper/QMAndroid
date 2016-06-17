package org.fs.qm.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.fs.core.AbstractApplication;
import org.fs.core.AbstractPagerAdapter;
import org.fs.qm.R;
import org.fs.qm.views.ColumnFragmentView;
import org.fs.qm.views.RowFragmentView;

import java.util.Arrays;

/**
 * Created by Fatih on 12/06/16.
 * as org.fs.qm.adapters.AssignStateAdapter
 */
public class AssignStateAdapter extends AbstractPagerAdapter<Integer> {

    private String[] titleText;

    public AssignStateAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager, Arrays.asList(0, 1));//we only have 2 children
        //reading titles from strings.xml
        titleText = new String[dataSet.size()];
        titleText[0] = context.getResources().getString(R.string.row_fragment_title);
        titleText[1] = context.getResources().getString(R.string.column_fragment_title);
    }

    @Override protected String getClassTag() {
        return AssignStateAdapter.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }

    @Override protected Fragment onBind(int position, Integer element) {
        if(element == 0) {
            return new RowFragmentView();
        }
        return new ColumnFragmentView();
    }

    @Override public CharSequence getPageTitle(int position) {
        return titleText[position];
    }
}