package com.joebruzek.oter.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joebruzek.oter.R;
import com.joebruzek.oter.activities.EditOterActivity;
import com.joebruzek.oter.database.DatabaseContract;
import com.joebruzek.oter.database.DatabaseListener;
import com.joebruzek.oter.database.DatabaseListenerComposite;
import com.joebruzek.oter.database.OterDataLayer;
import com.joebruzek.oter.models.Oter;
import com.joebruzek.oter.utilities.Measurements;
import com.joebruzek.oter.utilities.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the Oter List recyclerView.
 *
 * This adapter uses a cursor that belongs to the Oter table in the database
 *
 * Created by jbruzek on 11/13/15.
 */
public class OterListAdapter extends RecyclerView.Adapter<OterListAdapter.ViewHolder> implements DatabaseListener {

    private static final int EMPTY_VIEW_ITEM = 0;
    private static final int OTER_VIEW_ITEM = 1;

    private Cursor cursor;
    private Context context;
    private boolean empty = false;
    private OterDataLayer dataLayer;

    /**
     * Constructor
     * @param c Context
     * @param curs The cursor over the data (Must be a cursor over the Oters table)
     */
    public OterListAdapter(Context c, Cursor curs) {
        context = c;
        dataLayer = new OterDataLayer(c);
        dataLayer.openDB();

        if (curs != null) {
            switchCursor(curs);
        }

        empty = isEmpty(cursor);

        //listen for data changes
        DatabaseListenerComposite.getInstance().registerListener(this);
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
            }

            location = (TextView) itemView.findViewById(R.id.oter_item_title);
            text = (TextView) itemView.findViewById(R.id.oter_item_text);
            time = (TextView) itemView.findViewById(R.id.oter_item_time);
            contactsList = (RecyclerView) itemView.findViewById(R.id.oter_item_contact_list);
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
     * @param viewType EMPTY_VIEW_ITEM or OTER_VIEW_ITEM
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case EMPTY_VIEW_ITEM:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.oter_list_item_no_oters, parent, false));
            case OTER_VIEW_ITEM:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.oter_list_item, parent, false));
        }
        //something went wrong
        return null;
    }

    /**
     * When the viewholder is bound to the recyclerview, initialize things here
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == OTER_VIEW_ITEM) {
            if (!cursor.moveToPosition(position)) {
                throw new IllegalStateException("couldn't move cursor to position " + position);
            }

            holder.oter = dataLayer.buildOter(cursor);
            holder.location.setText(holder.oter.getLocation().getName());
            holder.text.setText(holder.oter.getMessage());
            holder.time.setText(Strings.buildTimeString(holder.oter.getTime()));

            int limit = 2;

            //set the height of the contact list
            //TODO: implement comments
            int height = 2;
            //int height = Math.min(holder.oter.getContacts().size(), limit + 1);

            holder.contactsList.getLayoutParams().height = Measurements.dpToPixel(context, (56 * height));
            holder.contactsList.requestLayout();

            //TODO: Remove touch events for the contacts list.

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            holder.contactsList.setLayoutManager(layoutManager);
            holder.contactsList.setHasFixedSize(true);

            //TODO: implement contacts
            holder.contactsList.setAdapter(new ContactListAdapter(context, new ArrayList<String>(), limit));
            //holder.contactsList.setAdapter(new ContactListAdapter(context, holder.oter.getContacts(), limit));

            if (position == 0) {
                //TODO: add 6dp to the top padding
            }
            else if (position == getItemCount() - 1) {
                //TODO: add 6dp to the bottom padding
            }
        }
    }

    /**
     * Get the number of items in the dataset
     *
     * If the dataset is empty, return 1 so we can imflate the "empty list" viewHolder
     * @return
     */
    @Override
    public int getItemCount() {
        if (empty) {
            return 1;
        }
        return cursor.getCount();
    }

    /**
     * Get the view type for the position. Used to decide if we're inflating a placeholder view or an oter view
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if(empty) {
            return EMPTY_VIEW_ITEM;
        }

        return OTER_VIEW_ITEM;
    }

    /**
     * switch the existing cursor with a new one
     * @param c
     */
    public void switchCursor(Cursor c) {
        if (OterDataLayer.cursorsEqual(cursor, c)) {
            c.close();
            return;
        }

        if (cursor != null) {
            cursor.close();
        }
        cursor = c;
        cursor.moveToFirst();
        empty = isEmpty(cursor);
        notifyDataSetChanged();
    }

    /**
     * Check to see if the cursor is empty
     *
     * @param c the cursor to check
     * @return
     */
    private boolean isEmpty(Cursor c) {
        return (c == null || c.getCount() == 0);
    }

    /**
     * Update the list with a new cursor if the oter table has changed
     * @param tableName
     */
    private void updateIfChanged(String tableName) {
        if (tableName.equals(DatabaseContract.OtersContract.TABLE_NAME)) {
            Cursor newCurs = dataLayer.getAllOtersCursor();
            switchCursor(newCurs);
        }
    }

    /**
     * An item in the database has been updated
     * @param tableName
     * @param id
     */
    @Override
    public void onItemUpdated(String tableName, long id) {
        updateIfChanged(tableName);
    }

    /**
     * An item has been inserted into the database
     * @param tableName
     * @param id
     */
    @Override
    public void onItemInserted(String tableName, long id) {
        updateIfChanged(tableName);
    }

    /**
     * An item has been deleted from the database
     * @param tableName
     * @param id
     */
    @Override
    public void onItemDeleted(String tableName, long id) {
        updateIfChanged(tableName);
    }
}
