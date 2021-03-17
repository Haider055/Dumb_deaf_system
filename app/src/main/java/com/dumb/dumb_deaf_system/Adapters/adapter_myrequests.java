package com.dumb.dumb_deaf_system.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dumb.dumb_deaf_system.R;
import com.dumb.dumb_deaf_system.models.model_myrequests;

import java.util.List;

public class adapter_myrequests extends RecyclerView.Adapter<adapter_myrequests.holder> {


    List<model_myrequests> list;
    Context context;

    public adapter_myrequests(List<model_myrequests> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new adapter_myrequests.holder( LayoutInflater.from(context).inflate(R.layout.layout_myrequests,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        holder.date.setText(list.get(position).getDate());
        holder.description.setText(list.get(position).getDescription());
        holder.title.setText(list.get(position).getTitle());
        holder.type.setText(list.get(position).getViewer());
        holder.status.setText(list.get(position).getRequest_status());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder{

        TextView status,title,description,date,type;

        public holder(@NonNull View itemView) {
            super(itemView);

            status=itemView.findViewById(R.id.status);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            date=itemView.findViewById(R.id.date);
            type=itemView.findViewById(R.id.type);

        }
    }
}
