package com.prepeez.toclearningstudent.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.pojo.Course;

import java.util.ArrayList;

import static com.prepeez.toclearningstudent.fragment.TabFragment5.removeUserCourse;

/**
 * Created by Nana on 9/11/2017.
 */

public class MyCourselistAdapter extends RecyclerView.Adapter<MyCourselistAdapter.ViewHolder> {


    ArrayList<Course> courses;
    private Context mContext;

    public MyCourselistAdapter(ArrayList<Course> courses) {
        this.courses = courses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycourse_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Course course = courses.get(position);
        holder.courseTextView.setText(course.getCourse());
        holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int itemPosition = -1;
        TextView courseTextView;
        RelativeLayout remove;
        private Context mContext;

        public void onAttach(final Activity activity) {
            mContext = activity;
        }

        public ViewHolder(View view) {
            super(view);

            remove = view.findViewById(R.id.add);
            courseTextView = view.findViewById(R.id.phoneNumber);

            remove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int pos = getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION) {
                removeUserCourse(pos);
                notifyDataSetChanged();
            }
        }
    }
}
