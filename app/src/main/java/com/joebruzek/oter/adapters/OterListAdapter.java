package com.joebruzek.oter.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joebruzek.oter.R;
import com.joebruzek.oter.activities.EditOterActivity;
import com.joebruzek.oter.models.Oter;
import com.joebruzek.oter.utilities.Strings;

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
    private boolean empty = false;

    public OterListAdapter(Context c, List<Oter> list) {
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
        RecyclerView contactsList;

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
                contactsList = (RecyclerView) itemView.findViewById(R.id.oter_item_contact_list);
            }
        }

        /**
         * Handle a click event on an Oter
         * @param v
         */
        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, EditOterActivity.class);
            i.putExtra("oter", oter);
            context.startActivity(i);
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
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            holder.contactsList.setLayoutManager(layoutManager);
            holder.contactsList.setHasFixedSize(true);
            ArrayList<String> contacts = new ArrayList<String>();
            contacts.add("Joe Bruzek");
            contacts.add("Yash Pant");
            contacts.add("Mark Olsen");
            contacts.add("Alexia Lutz");
            holder.contactsList.setAdapter(new ContactListAdapter(context, contacts, false));

            if (position == 0) {
                //TODO: add 6dp to the top padding
            }
            else if (position == dataList.size() - 1) {
                //TODO: add 6dp to the bottom padding
            }
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
