package com.dumb.dumb_deaf_system.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dumb.dumb_deaf_system.R;
import com.dumb.dumb_deaf_system.models.modelcategories;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.holder> implements Filterable {
    List<modelcategories> list;
    Context context;
    List<modelcategories> filterlistArray;

    oncategoryclick oncategoryclick;

    public AdapterCategories(List<modelcategories> list, Context context) {
        this.list = list;
        this.context = context;
        this.filterlistArray = new ArrayList<>(list);
    }

    public void setOnCatClickListener(oncategoryclick listener){
        this.oncategoryclick=  listener;
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private Filter listFilter=new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<modelcategories> filteredList=new ArrayList<>();
            if(constraint==null || constraint.length()==0)
            {
                filteredList.addAll(filterlistArray);
            }
            else
            {
                String filterInput=constraint.toString().toLowerCase().trim();
                for(modelcategories listModels:filterlistArray){
                    if(listModels.getCategory().toLowerCase().contains(filterInput))
                    {
                        filteredList.add(listModels);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

        public interface oncategoryclick{
        public void oncat(String id,String name);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_for_categories,parent,false);
        return new holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        holder.name.setText(list.get(position).getCategory().substring(0,1).toUpperCase()+list.get(position).getCategory().substring(1));
        if (list.get(position).getCategory().length()>=16){
            holder.name.setText(list.get(position).getCategory().substring(0,1).toUpperCase()+list.get(position).getCategory().substring(1,15)+"...");
        }


        Glide.with(context).load(list.get(position).getImage()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oncategoryclick.oncat(list.get(position).getId(),list.get(position).getCategory());
            }
        });
    }
            @Override
    public int getItemCount() {
        return list.size();
    }

        public class holder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name;

        public holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.title);
            img=itemView.findViewById(R.id.img);
        }
    }
}
