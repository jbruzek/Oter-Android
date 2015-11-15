package com.joebruzek.oter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.joebruzek.oter.R;
import com.joebruzek.oter.models.Oter;
import com.joebruzek.oter.utilities.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the Oter List recyclerView.
 *
 * Created by jbruzek on 11/14/15.
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private List<Oter> dataList;
    private Context context;
    private boolean empty = false;

    public ContactListAdapter(Context c, List<Oter> list) {
        context = c;

        //deep copy
        dataList = new ArrayList<Oter>();
        dataList.addAll(list);

        if (dataList.size() == 0) {
            dataList.add(new Oter());
            empty = true;
        }
    }

    /**
     * ViewHolders populate the RecyclerView. Each item in the list is contained in a VewHolder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View parent;
        Oter oter;
        TextView location;
        TextView text;
        TextView time;

        /**
         * Create the viewholder for the item
         * @param itemView the view contained in the viewHolder
         */
        public ViewHolder(View itemView) {
            super(itemView);
            this.parent = itemView;

            if (!empty) {
                itemView.setClickable(true);
                itemView.setOnClickListener(this);

                //location = (TextView) itemView.findViewById(R.id.oter_item_title);
                text = (TextView) itemView.findViewById(R.id.oter_item_text);
                time = (TextView) itemView.findViewById(R.id.oter_item_time);
            }
        }

        /**
         * Handle a click event on an Oter
         * @param v
         */
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Clicked an Oter", Toast.LENGTH_SHORT).show();
            //TODO: handle a click event
            //TODO: open the EditOterActivity
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
        if (empty) {
            Log.d("Inflating", "no oters");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oter_list_item_no_oters, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oter_list_item, parent, false);
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
        if (!empty) {
            holder.oter = dataList.get(position);
            //holder.location.setText(dataList.get(position).getLocation().getName());
            holder.text.setText(dataList.get(position).getMessage());
            holder.time.setText(Strings.buildTimeString(dataList.get(position).getTime(), dataList.get(position).getLocation().getName()));
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
}
