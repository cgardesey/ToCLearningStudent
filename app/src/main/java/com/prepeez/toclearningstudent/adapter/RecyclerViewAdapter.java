package com.prepeez.toclearningstudent.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.aakira.expandablelayout.Utils;
import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.fragment.TabFragment5;
import com.prepeez.toclearningstudent.pojo.Course;
import com.prepeez.toclearningstudent.utils.ColorUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

//import static com.prepeez.toclearningstudent.fragment.TabFragment5.userCourses;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    static final String TAG = "RecyclerViewAdapter";

    JSONObject jsonObject;
    String jsonObjKey;
    int colorIndex;
    Activity activity;

    ArrayList<String> extractedNames = new ArrayList<>();
    Context context;
    static Toast mToast;
    int min, max;
    int totalCredit = 0;

    public RecyclerViewAdapter(JSONObject jsonObject, String jsonObjKey, int colorIndex, Activity activity) {
        this.jsonObject = jsonObject;
        this.jsonObjKey = jsonObjKey;
        this.colorIndex = colorIndex;
        this.activity = activity;

        populateList();
    }

    private void populateList() {
        extractedNames.clear();
        Iterator iterator = jsonObject.keys();
        while(iterator.hasNext()){
            String key = (String)iterator.next();
            boolean courseAlreadyChosen = false;
            Course[] userCourses = new Course[0];
            for (Course course : userCourses) {
                if (course.getCourse().equals(jsonObjKey + "/" + key)) {
                    courseAlreadyChosen = true;
                    if (jsonObjKey.substring(jsonObjKey.lastIndexOf("/") + 1).contains("Semester")) {
                        try {
                            totalCredit += jsonObject.getJSONObject(key).getInt("NUMBER OF CREDIT HOURS");
                            } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            if (!courseAlreadyChosen && !key.equals("CREDIT HOURS") && !key.equals("NUMBER OF CREDIT HOURS")) {

                extractedNames.add(key);
            }
            if (key.equals("CREDIT HOURS")){
                try {
                    JSONObject creditHrsObj = jsonObject.getJSONObject("CREDIT HOURS");
                    min = creditHrsObj.getInt("min");
                    max = creditHrsObj.getInt("max");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (jsonObjKey.substring(jsonObjKey.lastIndexOf("/") + 1).contains("Semester")) {
            extractedNames.add("---FOOTER---");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(this, LayoutInflater.from(context)
                .inflate(R.layout.recycler_view_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final String name = extractedNames.get(position);
        holder.setIsRecyclable(false);

        if (jsonObjKey.substring(jsonObjKey.lastIndexOf("/") + 1).contains("Semester")) {
            if (position == getItemCount() - 1){
                holder.footer.setVisibility(View.VISIBLE);

                holder.min.setText("Min: " + String.valueOf(min));
                holder.max.setText("Max: " + String.valueOf(max));
                holder.totalCredit.setText(String.valueOf(totalCredit));
            }
            else {
                holder.footer.setVisibility(View.GONE);
            }
        }

        if (name.equals("---FOOTER---")){
            holder.optionalCoursesHeader.setVisibility(View.VISIBLE);
            holder.course.setVisibility(View.GONE);
            holder.frameLayout.setVisibility(View.GONE);
        }
        else if (name.equals("COMPULSORY COURSES")){
            holder.optionalCoursesHeader.setVisibility(View.VISIBLE);
            holder.optionalCoursesHeader.setText("Compulsory Courses");
            holder.course.setVisibility(View.GONE);
            holder.frameLayout.setVisibility(View.GONE);
        }
        else if (name.equals("OPTIONAL COURSES")){
            holder.optionalCoursesHeader.setVisibility(View.VISIBLE);
            holder.optionalCoursesHeader.setText("Optional Courses");
            holder.course.setVisibility(View.GONE);
            holder.frameLayout.setVisibility(View.GONE);
        }
        else {
            holder.optionalCoursesHeader.setVisibility(View.GONE);
            holder.course.setVisibility(View.VISIBLE);
            holder.frameLayout.setVisibility(View.VISIBLE);
            holder.courseTitle.setText(name);

            if (name.length() > 6 && name.substring(0, 7).equals("(CLASS)")) {

                holder.courseTitle.setText(name.replace("(CLASS) ", ""));
                holder.add.setVisibility(View.VISIBLE);
                holder.arrow.setVisibility(View.GONE);

                try {
                    int numberOfCreditHrs = jsonObject.getJSONObject(name).getInt("NUMBER OF CREDIT HOURS");

                    holder.creditHr.setVisibility(View.VISIBLE);

                    holder.creditHr.setText(String.valueOf(numberOfCreditHrs));

                } catch (JSONException e) {
                    holder.creditHr.setVisibility(View.GONE);
                    e.printStackTrace();
                }

            }
            else {
                holder.add.setVisibility(View.GONE);
                holder.arrow.setVisibility(View.VISIBLE);

                holder.creditHr.setVisibility(View.GONE);
            }
        }
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (jsonObjKey.substring(jsonObjKey.lastIndexOf("/") + 1).contains("Semester")) {
                    int number_of_credit_hours = 0;
                    try {
                        number_of_credit_hours = jsonObject.getJSONObject(name).getInt("NUMBER OF CREDIT HOURS");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (totalCredit + number_of_credit_hours > max) {
                        if (mToast != null) {
                            mToast.cancel();
                        }
                        mToast = Toast.makeText(activity, "Total number of credit hours should not exceed " + String.valueOf(max), Toast.LENGTH_SHORT);
                        mToast.show();
                    } else {
                        boolean optionalCoursesExist = false;
                        boolean compulsoryCoursesExhausted = false;
                        int itemCount = getItemCount();
                        int optionalCourseIndex = -1;
                        for (int i = 0; i < itemCount; i++) {
                            if (extractedNames.get(i).equals("OPTIONAL COURSES")) {
                                optionalCoursesExist = true;
                                optionalCourseIndex = i;
                                if (i == 1) {
                                    compulsoryCoursesExhausted = true;
                                }
                            }
                        }
                        if (optionalCoursesExist) {
                            boolean optionalCourseClicked = position > optionalCourseIndex;
                            if (optionalCourseClicked) {
                                if (compulsoryCoursesExhausted) {
                                    TabFragment5.populateUsercourses(new Course(jsonObjKey + "/" + name));
                                    totalCredit = 0;
                                    populateList();
                                    notifyDataSetChanged();
                                }
                                else {
                                    if (mToast != null) {
                                        mToast.cancel();
                                    }
                                    mToast = Toast.makeText(activity, "Please choose all compulsory courses first.", Toast.LENGTH_SHORT);
                                    mToast.show();
                                }
                            } else {
                                TabFragment5.populateUsercourses(new Course(jsonObjKey + "/" + name));
                                totalCredit = 0;
                                populateList();
                                notifyDataSetChanged();
                            }
                        }
                        else {
                            TabFragment5.populateUsercourses(new Course(jsonObjKey + "/" + name));
                            totalCredit = 0;
                            populateList();
                            notifyDataSetChanged();
                        }
                    }
                }
                else {
                    TabFragment5.populateUsercourses(new Course(jsonObjKey + "/" + name));
                    totalCredit = 0;
                    populateList();
                    notifyDataSetChanged();
                }
            }
        });

        int backgroundColorForViewHolder = ColorUtils
                .getViewHolderBackgroundColorFromInstance(context, colorIndex);
        holder.itemView.setBackgroundColor(backgroundColorForViewHolder);

        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (holder.arrowPointingDown) {
                    createRotateAnimator(holder.arrow, 0f, 180f).start();

                    holder.linearLayout.setVisibility(View.VISIBLE);

                    if (holder.recyclerViewAdapter == null) {
                        try {
                            int index = colorIndex + 1;
                            if (index == 10) {
                                index = 0;
                            }
                            holder.recyclerViewAdapter = new RecyclerViewAdapter(jsonObject.getJSONObject(name), jsonObjKey + "/" + name, index, activity);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        holder.recyclerView.setAdapter(holder.recyclerViewAdapter);

                        if (name.contains("Semester")) {
                           // holder.header.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary2));
                            holder.header.setVisibility(View.VISIBLE);
                        }
                        else {
                            holder.header.setVisibility(View.GONE);
                        }
                    }
                } else {
                    createRotateAnimator(holder.arrow, 180f, 0f).start();

                    holder.linearLayout.setVisibility(View.GONE);
                }

                holder.arrowPointingDown = !holder.arrowPointingDown;
            }
        });


    }

    @Override
    public int getItemCount() {
        return extractedNames.size();
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}