package com.dumb.dumb_deaf_system.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dumb.dumb_deaf_system.R;
import com.dumb.dumb_deaf_system.models.model_mytalks;

import java.util.List;

public class adaptermytalks extends RecyclerView.Adapter<adaptermytalks.holder> {

    Context context;
    List<model_mytalks> list;

    onclick montalk;

    public adaptermytalks(Context context, List<model_mytalks> list) {
        this.context = context;
        this.list = list;
    }

    public void onclick(onclick montalk){
        this.montalk=montalk;
    }

    public interface onclick{
        public void onclick(String id,String file_type,String description,String user_id,String category,String date,String gif,String video,String audio,String urdu_description,String title);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_for_mytalks,parent,false);
        return new adaptermytalks.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.description.setText(list.get(position).getDescription());
        holder.date.setText("Added at: "+list.get(position).getDate());
        holder.category.setText("Category: "+list.get(position).getCategory());

        String id,file_type,description,user_id,category,date,gif,video,audio,urdu_description,title;

        category=list.get(position).getCategory();
        user_id=list.get(position).getUser_id();
        audio=list.get(position).getAudio();
        gif=list.get(position).getGif();
        video=list.get(position).getVideo();
        date=list.get(position).getDate();
        id=list.get(position).getId();
        description=list.get(position).getDescription();
        file_type=list.get(position).getFile_type();
        urdu_description=list.get(position).getUrdu_description();
        title=list.get(position).getTitle();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montalk.onclick(id,file_type,description,user_id,category,date,gif,video,audio,urdu_description,title);
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder{

        TextView title,description,date,category;
        public holder(@NonNull View itemView) {
            super(itemView);

            category=itemView.findViewById(R.id.category);
            title=itemView.findViewById(R.id.heading);
            date=itemView.findViewById(R.id.date);
            description=itemView.findViewById(R.id.detail);

        }

    }
}
