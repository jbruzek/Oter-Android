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
import com.joebruzek.oter.views.ContactIcon;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for a Contact List recyclerView.
 *
 * Created by jbruzek on 11/14/15.
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private List<String> dataList;
    private Context context;
    private boolean editable = false;

    public ContactListAdapter(Context c, List<String> list, boolean editable) {
        context = c;
        this.editable = editable;

        //deep copy
        dataList = new ArrayList<String>();
        dataList.addAll(list);
    }

    /**
     * ViewHolders populate the RecyclerView. Each item in the list is contained in a VewHolder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        View parent;
        ContactIcon icon;
        TextView name;
        ImageButton delete;

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
                    Toast.makeText(context, "Delete " + name.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
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
        holder.name.setText(dataList.get(position));
        holder.icon.setContactName(dataList.get(position));

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
}
