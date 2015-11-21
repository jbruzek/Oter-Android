package com.joebruzek.oter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.joebruzek.oter.R;
import com.joebruzek.oter.models.Contact;
import com.joebruzek.oter.utilities.Contacts;
import com.joebruzek.oter.views.ContactIcon;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for a Contact List recyclerView.
 *
 * Created by jbruzek on 11/14/15.
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private static final int ROLLOVER_ITEM = 0;
    private static final int CONTACT_ITEM = 1;

    private List<String> dataList;
    private Context context;
    private boolean editable = false;
    private int limit;
    private int rollover = 0;
    private boolean hitLimit = false;

    /**
     * constructor that takes an editable flag
     * @param c
     * @param list
     * @param editable if true, the elements have an x that can be used to delete elements from the list
     */
    public ContactListAdapter(Context c, List<String> list, boolean editable) {
        context = c;
        this.editable = editable;

        //deep copy
        dataList = new ArrayList<String>();
        dataList.addAll(list);

        //default limit. This might not be needed, since we have the hitLimit flag.
        limit = 4;
    }

    /**
     * constructor that takes a limit. By default if the list has a limit it is not editable
     * @param c
     * @param list
     * @param limit how many contacts to display before appending a "and x other people" item
     */
    public ContactListAdapter(Context c, List<String> list, int limit) {
        this(c, list, false);

        this.limit = limit;
        formatDataSetToLimit();
    }

    /**
     * ViewHolders populate the RecyclerView. Each item in the list is contained in a VewHolder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        View parent;
        ContactIcon icon;
        TextView name;
        ImageButton delete;
        Contact contact;

        /**
         * Create the viewholder for the item
         * @param itemView the view contained in the viewHolder
         */
        public ViewHolder(View itemView) {
            super(itemView);
            this.parent = itemView;

            name = (TextView) itemView.findViewById(R.id.contact_list_item_name);
            icon = (ContactIcon) itemView.findViewById(R.id.contact_list_item_icon);
            delete = (ImageButton) itemView.findViewById(R.id.contact_list_item_button);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataList.remove(contact.getNumber());
                    notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * Creation of the viewholder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
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
        switch(getItemViewType(position)) {
            case ROLLOVER_ITEM:
                holder.name.setText("and " + rollover + " other people");
                holder.icon.setContactName("+ " + rollover);
                break;
            case CONTACT_ITEM:
                holder.contact = Contacts.getContact(context, dataList.get(position));
                holder.name.setText(holder.contact.getName());
                holder.icon.setContact(holder.contact);
        }

        //only show the x button if this list is editable
        if (!editable) {
            ((ViewGroup)holder.parent).removeView(holder.delete);
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
     * Format the data list to fit the limit size
     */
    private void formatDataSetToLimit() {
        if (dataList.size() <= limit) {
            return;
        }
        hitLimit = true;
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < limit + 1; i++) {
            temp.add(dataList.get(i));
        }
        rollover = dataList.size() - limit;
        dataList = temp;
    }

    /**
     * Get the item view type at a position. Indicated whether or not this is a limit item
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == dataList.size() - 1 && hitLimit) {
            return ROLLOVER_ITEM;
        }
        return CONTACT_ITEM;
    }

    /**
     * Get the datalist.
     * @return
     */
    public List<String> getDataList() {
        return dataList;
    }
}
