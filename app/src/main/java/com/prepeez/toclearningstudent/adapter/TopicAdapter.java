package com.prepeez.toclearningstudent.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.activity.SubjectActivity;
import com.prepeez.toclearningstudent.pojo.Topic;

import java.util.ArrayList;


public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.MyViewHolder> {

    ArrayList<Topic> topics = new ArrayList<>();
    private Activity activity;

    public TopicAdapter(ArrayList<Topic> topics, Context context, Activity activity) {
        this.topics = topics;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_topic, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.topic.setText(topics.get(position).getDesc());
        holder.image.setImageResource(topics.get(position).getImages());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(activity, SubjectActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView topic;
        ImageView image;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.topic);
            image = itemView.findViewById(R.id.image_view);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
