package org.fs.qm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fs.common.BusManager;
import org.fs.core.AbstractApplication;
import org.fs.core.AbstractRecyclerAdapter;
import org.fs.exception.AndroidException;
import org.fs.qm.R;
import org.fs.qm.entities.ICellEntity;
import org.fs.qm.holders.RowTypeHolder;

import java.util.List;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.adapters.GridRecyclerAdapter
 */
public class GridRecyclerAdapter extends AbstractRecyclerAdapter<List<ICellEntity>, RowTypeHolder> {

    private BusManager busManager;

    public GridRecyclerAdapter(List<List<ICellEntity>> dataSet, Context context) {
        super(dataSet, context);
        this.busManager = new BusManager();//we create bus manager only for those adapter rest is ignored
    }

    @Override public RowTypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       final LayoutInflater factory = inflaterFactory();
       if(factory != null) {
           View view = factory.inflate(R.layout.layout_row_recycler, parent, false);
           return new RowTypeHolder(view, busManager);
       }
       throw new AndroidException("you can not grab inflater since context is death.");
    }

    @Override public void onBindViewHolder(RowTypeHolder viewHolder, int position) {
//        position = position % dataSet.size();
        final List<ICellEntity> columnCells = getItemAt(position);
        if(columnCells != null) {
            viewHolder.notifyDataSet(columnCells);
        }
    }

//    @Override public int getItemCount() {
//        return Integer.MAX_VALUE;
//    }

    @Override public int getItemViewType(int position) {
        return 0;
    }

    @Override protected String getClassTag() {
        return GridRecyclerAdapter.class.getSimpleName();
    }

    @Override protected boolean isLogEnabled() {
        return AbstractApplication.isDebug();
    }
}