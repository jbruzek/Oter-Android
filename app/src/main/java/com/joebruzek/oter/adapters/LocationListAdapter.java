package com.joebruzek.oter.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.joebruzek.oter.R;
import com.joebruzek.oter.database.DatabaseContract;
import com.joebruzek.oter.models.Location;
import com.joebruzek.oter.views.ContactIcon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbruzek on 11/17/15.
 */
public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {

    private static final int LOADING_ITEM = 0;
    private static final int DETAILS_ITEM = 1;
    private static final int LOCATION_ITEM = 2;
    private List<Location> dataList;
    private Location selectedLocation;
    private Context context;
    private boolean isLoading = false;
    private boolean isDetails = false;
    private ViewHolder detailsHolder;

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
     * Constructor that takes only one location. This initializes the adapter in a "detail" format
     * @param c
     * @param l
     */
    public LocationListAdapter(Context c, Location l) {
        context = c;

        //go to details for this location
        setDetailsLocation(l);
    }

    /**
     * ViewHolders populate the RecyclerView. Each item in the list is contained in a VewHolder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        View parent;
        TextView name;
        TextView address;
        EditText nickname;
        int position;

        /**
         * Create the viewholder for the item
         * @param itemView the view contained in the viewHolder
         */
        public ViewHolder(View itemView) {
            super(itemView);
            this.parent = itemView;

            name = (TextView) itemView.findViewById(R.id.add_location_item_name);
            address = (TextView) itemView.findViewById(R.id.add_location_item_subtitle);
            nickname = (EditText) itemView.findViewById(R.id.add_location_detail_nickname);
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
        } else if (getItemViewType(i) == DETAILS_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_location_details_item, parent, false);
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (getItemViewType(position) == LOCATION_ITEM) {
            holder.name.setText(dataList.get(position).getName());
            holder.address.setText(dataList.get(position).getAddress());
            holder.position = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setDetailsLocation(dataList.get(position));
                }
            });
        } else if (getItemViewType(position) == DETAILS_ITEM) {
            detailsHolder = holder;
            if (selectedLocation != null) {
                holder.nickname.setText(selectedLocation.getNickname());
            }
            holder.nickname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        handled = true;
                    }
                    return handled;
                }
            });
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
        }

        if (isDetails && position == getItemCount() - 1) {
            return DETAILS_ITEM;
        }

        return LOCATION_ITEM;
    }

    /**
     * Get the number of items in the list
     * @return
     */
    @Override
    public int getItemCount() {
        if (isDetails) {
            return dataList.size() + 1;
        }
        return dataList.size();
    }

    /**
     * Change the data list
     * @param list
     */
    public void setDataSet(List<Location> list) {
        isLoading = false;
        isDetails = false;
        this.dataList = list;
        notifyDataSetChanged();
    }

    /**
     * Set the adapter to a "loading" state
     */
    public void setLoading() {
        isLoading = true;
        isDetails = false;
        ArrayList<Location> temp = new ArrayList<Location>();
        temp.add(new Location());
        this.dataList = temp;
        notifyDataSetChanged();
    }

    /**
     * Set the adapter in a "details" state
     * @param l
     */
    private void setDetailsLocation(Location l) {
        isLoading = false;
        isDetails = true;

        selectedLocation = l;
        ArrayList<Location> temp = new ArrayList<Location>();
        temp.add(l);
        this.dataList = temp;
        notifyDataSetChanged();
    }

    /**
     * get the nickname for this adapter
     * @return could be null if we're not in details mode
     */
    private String getNickName() {
        if (detailsHolder != null) {
            return detailsHolder.nickname.getText().toString();
        }
        return "";
    }

    /**
     * Return the selected location. If the location does not exist, return null
     * @return
     */
    public Location getSelectedLocation() {
        if (isDetails) {
            selectedLocation.setNickname(getNickName());
            return selectedLocation;
        }
        return null;
    }
}
