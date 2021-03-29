package com.dumb.dumb_deaf_system.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dumb.dumb_deaf_system.Network.dbhandler;
import com.dumb.dumb_deaf_system.R;
import com.dumb.dumb_deaf_system.models.model_Talksby_Cat;

import java.util.List;

public class adapter_talks_By_Cat extends RecyclerView.Adapter<adapter_talks_By_Cat.holder> {

    List<model_Talksby_Cat> list;
    Context context;
    ontalkclick montalk;

    public void onclick(ontalkclick montalk){
        this.montalk=montalk;
    }

    public interface ontalkclick{
        public void ontalksclick(String id,String file_type,String description,String category,String date,String gif,String video,String audio,String urdu_description,String title);
    }

    public adapter_talks_By_Cat(List<model_Talksby_Cat> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layoutfortalks,parent,false);
        return new adapter_talks_By_Cat.holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        final String[] id = new String[1];
        final String[] category = new String[1];
        final String[] detail = new String[1];
        final String[] type = new String[1];
        final String[] gif = new String[1];
        final String[] video = new String[1];
        final String[] date = new String[1];
        final String[] audio = new String[1];
        final String[] urdu_description = new String[1];
        final String[] title = new String[1];

        dbhandler dbhandlers=new dbhandler(context);
        String duplicate=dbhandlers.checkforduplicateincart(list.get(position).getId());
        dbhandlers.close();
        if (duplicate.equals("yes")){
            holder.add.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_favorite_24, 0);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                id[0] =list.get(position).getId();
                category[0] =list.get(position).getCategory();
                detail[0] =list.get(position).getDescription();
                audio[0] =list.get(position).getAudio();
                type[0] =list.get(position).getFile_type();
                gif[0] =list.get(position).getGif();
                video[0] =list.get(position).getVideo();
                urdu_description[0] =list.get(position).getUrdu_description();
                title[0] =list.get(position).getTitle();
                date[0] =list.get(position).getDate();

                montalk.ontalksclick(id[0], type[0], detail[0], category[0], date[0], gif[0], video[0], audio[0], urdu_description[0], title[0]);

            }
        });


        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbhandler dbhandler=new dbhandler(context);
                String id=list.get(position).getId();
                String title=list.get(position).getTitle();
                String gif=list.get(position).getGif();
                String description=list.get(position).getDescription();
                String video=list.get(position).getVideo();
                String urdu=list.get(position).getUrdu_description();
                String audio=list.get(position).getAudio();
                String type=list.get(position).getFile_type();
                String category=list.get(position).getCategory();
                String result=dbhandler.addtofav(id,title,gif,description,video,urdu,audio,type,category);
                dbhandler.close();
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                if (result.equals("Added to favourites")){
                    holder.add.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_favorite_24, 0);

                }
            }
        });
        
        holder.haeding.setText(list.get(position).getTitle());
        holder.detail.setText(list.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder{

        Button add;
        TextView haeding,detail;
        public holder(@NonNull View itemView) {
            super(itemView);

            add=itemView.findViewById(R.id.add);
            haeding=itemView.findViewById(R.id.heading);
            detail=itemView.findViewById(R.id.detail);

        }
    }
}
