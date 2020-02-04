package com.artificialsoft.recyclerviewsearch;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemsHolder> implements Filterable
{
   ArrayList<Items> items;
   ArrayList<Items> copyItems;
   OnItemClickListener onItemClickListener; //here implement your code for button

    public interface OnItemClickListener
    {
        void onButtonClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override //views for recyclerview
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_view_layout, parent, false);
        return new ItemsHolder(view, onItemClickListener);
    }

    //itemadapter constructor
    public ItemAdapter(ArrayList<Items> items)
    {
        this.items = items;
        copyItems = new ArrayList<>(items);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position)
    {
        //getting item position from items array
        Items currentItems = items.get(position);

        //setting text of that class by bindviewholder
        holder.textView.setText(currentItems.itemName);
        holder.textView1.setText(currentItems.contactDetails);
        Glide.with(holder.imageView.getContext()).load(currentItems.profileURL).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //to search from recyclerView
    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    public Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Items> filterItemList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) //if search bar has nothing to search
            {
                filterItemList.addAll(copyItems);
            }
            else
            {
                String patternSearch = constraint.toString().toLowerCase().trim();

                for (Items items: copyItems)
                {
                    if (items.getItemName().toLowerCase().contains(patternSearch)) //comparing with the pattern
                    {
                        filterItemList.add(items);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterItemList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            items.clear();
            items.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    //setting text to textView of recyclerview
    public static class ItemsHolder extends RecyclerView.ViewHolder
    {
        TextView textView;
        TextView textView1;
        Button button;
        ImageView imageView;
        public ItemsHolder(@NonNull final View itemView, final OnItemClickListener listener)
        {
            super(itemView);
            textView = itemView.findViewById(R.id.recycler_text);
            button = itemView.findViewById(R.id.test_button);
            textView1 = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.profile_image);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (listener!= null)
                    {
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            listener.onButtonClick(position);
                        }
                    }
                }
            });
        }
    }
}
