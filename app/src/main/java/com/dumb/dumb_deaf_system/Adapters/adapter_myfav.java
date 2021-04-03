package com.dumb.dumb_deaf_system.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dumb.dumb_deaf_system.R;
import com.dumb.dumb_deaf_system.talk.details;
import com.dumb.dumb_deaf_system.models.model_mytalks;

import java.util.List;

public class adapter_myfav extends RecyclerView.Adapter<adapter_myfav.holder> {

    List<model_mytalks> list;
    Context context;
    del mdel;

    public adapter_myfav(List<model_mytalks> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public interface del{
        public void delete(String id);
    }
    public void setMdel(del del){
        mdel=del;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new adapter_myfav.holder(LayoutInflater.from(context).inflate(R.layout.layout_myfav,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        holder.head.setText(list.get(position).getTitle());
        holder.detail.setText(list.get(position).getDescription());

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdel.delete(list.get(position).getId());
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, details.class);
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("type",list.get(position).getFile_type());
                intent.putExtra("gif",list.get(position).getGif());
                intent.putExtra("video",list.get(position).getVideo());
                intent.putExtra("date",list.get(position).getDate());
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("category",list.get(position).getCategory());
                intent.putExtra("audio",list.get(position).getAudio());
                intent.putExtra("details",list.get(position).getDescription());
                intent.putExtra("urdu",list.get(position).getUrdu_description());
                context.startActivity(intent);




            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder{

        TextView head,detail;
        ImageView del;

        public holder(@NonNull View itemView) {
            super(itemView);

            head=itemView.findViewById(R.id.heading);
            del=itemView.findViewById(R.id.dell);
            detail=itemView.findViewById(R.id.detail);
        }
    }
}
