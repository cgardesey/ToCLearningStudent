package com.prepeez.toclearningstudent.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.prepeez.toclearningstudent.activity.AccountActivity;
import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.pojo.User;

/**
 * Created by 2CLearning on 12/13/2017.
 */

public class TabFragment4 extends Fragment {
    private static final String TAG = "TabFragment4";
    private User user = new User();
    private Spinner employmentStatus, proffessionalExp;
    private EditText companyName, jobTitle;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_account4, container, false);

        //user = getArguments().getParcelable("user");
        user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);

        employmentStatus = rootView.findViewById(R.id.employmentstatus_spinner);
        proffessionalExp = rootView.findViewById(R.id.prefessionalexperience_spinner);
        companyName = rootView.findViewById(R.id.companyofemployment);
        jobTitle = rootView.findViewById(R.id.jobtitle);
        if (user != null){
            employmentStatus.setSelection(user.getEmploymentStatusId());
            proffessionalExp.setSelection(user.getYearsOfExperienceId());
            companyName.setText(user.getCompanyOfEmployment());
            jobTitle.setText(user.getJobTitle());
        }
        else {
            user = new User();
        }

        return rootView;
    }
    public boolean validate (){
        boolean validated = true;
        if(TextUtils.isEmpty(companyName.getText())){
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setCompanyOfEmployment(null);
            AccountActivity.storeGSON(getContext(), "user", user);
        }
        else {
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setCompanyOfEmployment(companyName.getText().toString().trim());
            AccountActivity.storeGSON(getContext(), "user", user);
        }

        if( TextUtils.isEmpty(jobTitle.getText())){
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setJobTitle(null);
            AccountActivity.storeGSON(getContext(), "user", user);
        }
        else {
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setJobTitle(jobTitle.getText().toString().trim());
            AccountActivity.storeGSON(getContext(), "user", user);
        }

        if( employmentStatus.getSelectedItemPosition() == 0){
            TextView errorText = (TextView)employmentStatus.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            validated = false;
        }
        else {
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setEmploymentStatusId(employmentStatus.getSelectedItemPosition());
            AccountActivity.storeGSON(getContext(), "user", user);
        }
        if( proffessionalExp.getSelectedItemPosition() == 0){
            TextView errorText = (TextView)proffessionalExp.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            validated = false;
        }
        else {
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setYearsOfExperienceId(proffessionalExp.getSelectedItemPosition());
            AccountActivity.storeGSON(getContext(), "user", user);
        }
        return validated;
    }
    public User getUser() {
        return user;
    }
}
