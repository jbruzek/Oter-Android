package com.joebruzek.oter.adapters;

import android.content.Context;
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
import com.joebruzek.oter.views.ContactIcon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbruzek on 11/17/15.
 */
public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {

    //TODO: Change the adapter to work with a cursor, like this:
    //https://gist.github.com/skyfishjy/443b7448f59be978bc59

    private List<String> dataList;
    private Context context;

    /**
     * constructor
     * @param c
     * @param list
     */
    public LocationListAdapter(Context c, List<String> list) {
        context = c;

        //deep copy
        dataList = new ArrayList<String>();
        dataList.addAll(list);
    }

    /**
     * ViewHolders populate the RecyclerView. Each item in the list is contained in a VewHolder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        View parent;
        TextView name;
        RadioButton button;

        /**
         * Create the viewholder for the item
         * @param itemView the view contained in the viewHolder
         */
        public ViewHolder(View itemView) {
            super(itemView);
            this.parent = itemView;

            name = (TextView) itemView.findViewById(R.id.add_location_item_name);
            button = (RadioButton) itemView.findViewById(R.id.add_location_item_button);
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
        Log.d("Inflating", dataList.size() + "");
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_location_list_item, parent, false);
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
        holder.name.setText(dataList.get(position));
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
