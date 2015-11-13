package com.joebruzek.oter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.joebruzek.oter.models.Oter;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the Oter List recyclerView.
 *
 * Created by jbruzek on 11/13/15.
 */
public class OterListAdapter extends RecyclerView.Adapter<OterListAdapter.ViewHolder> {

    private List<Oter> dataList;
    private Context context;

    public OterListAdapter(Context c, List<Oter> list) {
        context = c;

        //deep copy
        dataList = new ArrayList<Oter>();
        dataList.addAll(list);
    }

    /**
     * ViewHolders populate the RecyclerView. Each item in the list is contained in a VewHolder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View parent;

        public ViewHolder(View itemView, Context c) {
            super(itemView);
            this.parent = itemView;


            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //TODO: handle a click event
        }
    }

    /**
     * Creation of the viewholder
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //TODO: inflate the OterListItem view
        return null;
    }

    /**
     * When the viewholder is bound to the recyclerview, initialize things here
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        //TODO: Add stuff from the oter to the view
    }

    /**
     * Get the number of items in the list
     * @return
     */
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
