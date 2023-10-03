package com.prepeez.toclearningstudent.adapter;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.prepeez.toclearningstudent.materialDialog.CourseListMaterialDialog;
import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.pojo.Course;

import java.util.ArrayList;

import static com.prepeez.toclearningstudent.activity.ChatActivity.detachDatabaseReadListener;
import static com.prepeez.toclearningstudent.activity.ChatActivity.init;
import static com.prepeez.toclearningstudent.activity.ChatActivity.mUserId;

/**
 * Created by Nana on 9/11/2017.
 */

public class CourseListMaterialDialogAdapter extends RecyclerView.Adapter<CourseListMaterialDialogAdapter.ViewHolder>  {
   public DatabaseReference reference;

    ArrayList <Course> courses;
    Activity activity;
    CourseListMaterialDialog courseListMaterialDialog;
    public CourseListMaterialDialogAdapter(ArrayList<Course> courses, CourseListMaterialDialog courseListMaterialDialog, Activity activity) {
        this.courses = courses;
        this.courseListMaterialDialog = courseListMaterialDialog;
        this.activity = activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course_material_dialog, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Course course = courses.get(position);
        holder.courseName.setText(course.getCourse());}
    @Override
    public int getItemCount() {
        return  courses.size();
    }
    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        int itemPosition = -1;
        TextView courseName;
        private Context mContext;
        public void onAttach(final Activity activity) {
            mContext = activity;
        }

        public ViewHolder(View view) {
         super(view);
            courseName = view.findViewById(R.id.courseHeader);
            courseName.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            PreferenceManager
                    .getDefaultSharedPreferences(activity.getApplicationContext())
                     .edit()
                    .putString(mUserId, courseName.getText().toString())
                    .apply();
            detachDatabaseReadListener();
            init();
            courseListMaterialDialog.dismiss();
        }
    }
}
