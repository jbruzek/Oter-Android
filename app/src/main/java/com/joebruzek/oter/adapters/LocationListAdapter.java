package com.joebruzek.oter.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.joebruzek.oter.R;
import com.joebruzek.oter.models.Location;
import com.joebruzek.oter.views.ContactIcon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbruzek on 11/17/15.
 */
public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {

    private static final int LOADING_ITEM = 0;
    private static final int LOCATION_ITEM = 1;
    private List<Location> dataList;
    private Context context;
    private boolean isLoading = false;

    /**
     * constructor
     * @param c
     * @param list
     */
    public LocationListAdapter(Context c, List<Location> list) {
        context = c;

        //deep copy
        dataList = new ArrayList<Location>();
        dataList.addAll(list);
    }

    /**
     * ViewHolders populate the RecyclerView. Each item in the list is contained in a VewHolder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements DialogInterface.OnClickListener {
        View parent;
        TextView name;
        TextView address;

        /**
         * Create the viewholder for the item
         * @param itemView the view contained in the viewHolder
         */
        public ViewHolder(View itemView) {
            super(itemView);
            this.parent = itemView;

            name = (TextView) itemView.findViewById(R.id.add_location_item_name);
            address = (TextView) itemView.findViewById(R.id.add_location_item_subtitle);
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            ArrayList<Location> temp = new ArrayList<Location>();
            temp.add(dataList.get(i));
            setDataSet(temp);
        }
    }

    /**
     * Creation of the viewholder
     * @param parent
     * @param i
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view;
        if (getItemViewType(i) == LOADING_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_list_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_location_list_item, parent, false);
        }
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    /**
     * When the viewholder is bound to the recyclerview, initialize things here
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == LOCATION_ITEM) {
            holder.name.setText(dataList.get(position).getName());
            holder.address.setText(dataList.get(position).getAddress());
        }
    }

    /**
     * Get what type of view this is
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (isLoading && position == 0) {
            return LOADING_ITEM;
        } else {
            return LOCATION_ITEM;
        }
    }

    /**
     * Get the number of items in the list
     * @return
     */
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * Change the data list
     * @param list
     */
    public void setDataSet(List<Location> list) {
        isLoading = false;
        this.dataList = list;
        notifyDataSetChanged();
    }

    /**
     * Set the adapter to a "loading" state
     */
    public void setLoading() {
        isLoading = true;
        ArrayList<Location> temp = new ArrayList<Location>();
        temp.add(new Location());
        this.dataList = temp;
        notifyDataSetChanged();
    }
}
