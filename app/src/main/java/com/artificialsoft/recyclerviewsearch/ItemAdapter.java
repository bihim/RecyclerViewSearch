package com.artificialsoft.recyclerviewsearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemsHolder> implements Filterable
{
    ArrayList<Items> items;
    ArrayList<Items> copyItems;

    @NonNull
    @Override //views for recyclerview
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_view_layout, parent, false);
        return new ItemsHolder(view);
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
        public ItemsHolder(@NonNull View itemView)
        {
            super(itemView);
            textView = itemView.findViewById(R.id.recycler_text);
        }
    }
}
