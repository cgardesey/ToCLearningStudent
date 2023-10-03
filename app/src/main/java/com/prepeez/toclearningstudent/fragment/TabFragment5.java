package com.prepeez.toclearningstudent.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.prepeez.toclearningstudent.activity.AccountActivity;
import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.adapter.viewHolder.MyCourselistAdapter;
import com.prepeez.toclearningstudent.adapter.CourseListAdapter;
import com.prepeez.toclearningstudent.pojo.Course;
import com.prepeez.toclearningstudent.pojo.User;
import com.prepeez.toclearningstudent.util.DividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.prepeez.toclearningstudent.activity.ChatActivity.PREF_JSON_KEY;
import static com.prepeez.toclearningstudent.activity.SplashScreenActivity.DEFAULT_JSON;

/**
 * Created by 2CLearning on 12/13/2017.
 */

public class TabFragment5 extends Fragment {
    private static final String TAG = "TabFragment5";
    User user = new User();
    RoundedImageView roundedImageView;
    static LinearLayout hidelist;
    TextView hideListText;
    FloatingActionButton fab;
    static TextView selectedCoursesTextView;
    static RecyclerView courseListRecyclerView;
    static Context mContext;
    public static ArrayList<Course> myCourses;
    static MyCourselistAdapter myCourselistAdapter;
    static CourseListAdapter courseListAdapter;
    static RecyclerView.LayoutManager mylayoutManager;
    static RecyclerView myCoursesRecyclerView;

    SendMessage SM;

    JSONObject jsonObject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(com.prepeez.toclearningstudent.R.layout.fragment_account5, container, false);

        try {
            jsonObject = new JSONObject(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(PREF_JSON_KEY, DEFAULT_JSON));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
        mContext = getContext();

        this.fab = rootView.findViewById(R.id.document);
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SM.sendData();
            }
        });

        myCoursesRecyclerView = rootView.findViewById(R.id.userCoursesRecyclerView);
        hidelist = rootView.findViewById(R.id.hidelist);
        selectedCoursesTextView = rootView.findViewById(R.id.selectedcourses);
        hideListText = rootView.findViewById(R.id.hideListText);
        roundedImageView = rootView.findViewById(R.id.expand1);

        if (user != null){
            myCourses = new ArrayList<>();
            if (user.getCourses() != null) {
                hidelist.setVisibility(View.VISIBLE);
                for (Course course : user.getCourses()) {
                myCourses.add(course);
                }
            }
            myCourselistAdapter = new MyCourselistAdapter(myCourses);
            mylayoutManager = new LinearLayoutManager(mContext);
            myCoursesRecyclerView.setLayoutManager(mylayoutManager);
            myCoursesRecyclerView.setHasFixedSize(true);
            myCoursesRecyclerView.setItemAnimator(new DefaultItemAnimator());
            myCoursesRecyclerView.setAdapter(myCourselistAdapter);
            selectedCoursesTextView.setText(myCourses.size() + " course(s) selected");
        }
        else  {
            user = new User();
        }

        courseListRecyclerView = rootView.findViewById(R.id.recyclerView);
        courseListRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        courseListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        courseListAdapter = new CourseListAdapter(jsonObject, "", 0, getActivity());
        courseListRecyclerView.setAdapter(courseListAdapter);

        hidelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myCoursesRecyclerView.getVisibility() == View.GONE) {
                    String uri = "@drawable/collapse";  // where myresource (without the extension) is the file

                    int imageResource = getResources().getIdentifier(uri, null, mContext.getPackageName());

                    Drawable res = getResources().getDrawable(imageResource);
                    roundedImageView.setImageDrawable(res);
                    myCoursesRecyclerView.setVisibility(View.VISIBLE);
                    hideListText.setText(getResources().getString(R.string.hide_list));
                } else if (myCoursesRecyclerView.getVisibility() == View.VISIBLE) {
                    String uri = "@drawable/expand";  // where myresource (without the extension) is the file

                    int imageResource = getResources().getIdentifier(uri, null, mContext.getPackageName());
                    hideListText.setText(getResources().getString(R.string.unhide_list));
                    Drawable res = getResources().getDrawable(imageResource);
                    roundedImageView.setImageDrawable(res);
                    myCoursesRecyclerView.setVisibility(View.GONE);
                }
            }
        });

        return rootView;
    }

    public interface SendMessage {
        void sendData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            SM = (SendMessage) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    public static void populateUsercourses(Course course) {
        selectedCoursesTextView.setError(null);
        myCourses.add(course);
        myCourselistAdapter.notifyDataSetChanged();
        selectedCoursesTextView.setText(myCourses.size() + " courses selected");
        hidelist.setVisibility(View.VISIBLE);
    }

    public static void removeUserCourse(int position) {
        myCourses.remove(position);
        selectedCoursesTextView.setText(myCourses.size() + " courses selected");
        courseListAdapter.notifyDataSetChanged();
        if (myCourses.size() > 0) {
            hidelist.setVisibility(View.VISIBLE);
        }
        else {
            hidelist.setVisibility(View.GONE);
        }
    }

    public boolean validate (){
        boolean validated = true;
        if (myCourses.size() == 0){
            selectedCoursesTextView.setError(selectedCoursesTextView.getText().toString());
            validated = false;
        }
        else {
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setCourses(myCourses);
            AccountActivity.storeGSON(getContext(), "user", user);
        }
        return validated;
    }

    public User getUser() {
        return user;
    }
}
