package com.prepeez.toclearningstudent.materialDialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.adapter.CourseListMaterialDialogAdapter;
import com.prepeez.toclearningstudent.pojo.Course;

import java.util.ArrayList;


/**
 * Created by Nana on 10/22/2017.
 */

public class CourseListMaterialDialog extends DialogFragment {
    RecyclerView mRecyclerView;
    CourseListMaterialDialogAdapter courseListMaterialDialogAdapter;

    private ArrayList<Course> courses;

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.material_dialog_course_list,null);
        mRecyclerView = view.findViewById(R.id.inner_recyclerView);

        courseListMaterialDialogAdapter = new CourseListMaterialDialogAdapter(getCourses(),this, getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(courseListMaterialDialogAdapter);
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
       // doneBtn.setOnClickListener(doneAction);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
      //  builder.setCancelable(false);
        return builder.create();
    }
}