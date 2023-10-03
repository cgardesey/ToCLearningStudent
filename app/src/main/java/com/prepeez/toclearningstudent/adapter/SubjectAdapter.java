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
import com.prepeez.toclearningstudent.activity.VideoActivity;
import com.prepeez.toclearningstudent.pojo.Subject;

import java.util.ArrayList;


public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder> {

    ArrayList<Subject> subjects = new ArrayList<>();
    private Activity activity;
    private String TEST_URL = "";

    public SubjectAdapter(ArrayList<Subject> subjects, Context context, Activity activity) {
        this.subjects = subjects;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_subject, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.subject.setText(subjects.get(position).getDesc());
        holder.image.setImageResource(subjects.get(position).getImages());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0)
                    TEST_URL = "android.resource://" +activity.getPackageName()+ "/"+R.raw.breadboarding;
                if (position == 1)
                    TEST_URL = "android.resource://" +activity.getPackageName()+ "/"+R.raw.breadboarding;
                if (position == 2)
                    TEST_URL = "android.resource://" +activity.getPackageName()+ "/"+R.raw.breadboarding;
                v.getContext().startActivity(new Intent(activity, VideoActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subject;
        ImageView image;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subject);
            image = itemView.findViewById(R.id.image_view);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
