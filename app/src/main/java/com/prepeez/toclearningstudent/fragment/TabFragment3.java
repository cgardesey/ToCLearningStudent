package com.prepeez.toclearningstudent.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.prepeez.toclearningstudent.activity.AccountActivity;
import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.adapter.CertificationAdapter;
import com.prepeez.toclearningstudent.pojo.Certification;
import com.prepeez.toclearningstudent.pojo.User;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by 2CLearning on 12/13/2017.
 */

public class TabFragment3 extends Fragment implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
    private static final String TAG = "TabFragment3";
    User user = new User();
    Spinner eduLevel;
    Button add, remove;
    RoundedImageView addcert;
    RecyclerView recyclerView;
    Context mContext;
    EditText cert,schoolname, nameofInst, homeadderess;
    TextView startdate, enddate;
    LinearLayout clickToAdd;
    CardView cardView;
    ArrayList<Certification> certificationArrayList;
    CertificationAdapter certificationAdapter;
    RecyclerView.LayoutManager layoutManager;
    SimpleDateFormat simpleDateFormat;
    boolean endDateClicked = true;

    static long startTime;
    static long endTime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_account3, container, false);

        //user = getArguments().getParcelable("user");
        user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);

        certificationArrayList  = new ArrayList<>();
        mContext= getContext();
        cardView = rootView.findViewById(R.id.cardView);
        cert = rootView.findViewById(R.id.cert);
        schoolname = rootView.findViewById(R.id.schoolname);
        homeadderess = rootView.findViewById(R.id.homeaddress);
        nameofInst = rootView.findViewById(R.id.nameofInst);
        clickToAdd = rootView.findViewById(R.id.clickToAdd);
        enddate = rootView.findViewById(R.id.enddate);
        startdate = rootView.findViewById(R.id.startdate);
        add = rootView.findViewById(R.id.add);
        remove = rootView.findViewById(R.id.remove);
        recyclerView = rootView.findViewById(R.id.recycle_location);
        eduLevel = rootView.findViewById(R.id.edulevel_spinner);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endDateClicked = true;
                showDate(1980, 0, 1, R.style.DatePickerSpinner);
            }
        });
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endDateClicked = false;
                showDate(1980, 0, 1, R.style.DatePickerSpinner);
            }
        });

        if (user != null){
            nameofInst.setText(user.getNameOfInstitution());
            eduLevel.setSelection(user.getHighestEducationalLevelId());
            if (user.getCertifications() != null) {
                certificationArrayList = user.getCertifications();
                certificationAdapter = new CertificationAdapter(certificationArrayList);
                layoutManager = new LinearLayoutManager(getActivity());

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                //  myrecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(certificationAdapter);
            }
        }
        else {
            user = new User();
        }

        clickToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cert.setText("");
                schoolname.setText("");
                startdate.setText("");
                enddate.setText("");
                cardView.setVisibility(View.GONE);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (certValidated()) {
                    String certification  = cert.getText().toString();
                    String schoolnametext  =  schoolname.getText().toString();
                    String startyear  = startdate.getText().toString();
                    String endyear = enddate.getText().toString();
                    Certification cert = new Certification(schoolnametext, startyear, endyear, certification);
                    if (certificationArrayList.contains(cert)) {
                        Toast.makeText(getContext(), "Certification already exist", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        certificationArrayList.add(cert);
                    }
                    certificationAdapter = new CertificationAdapter(certificationArrayList);
                    layoutManager = new LinearLayoutManager(getActivity());
                    TabFragment3.this.cert.setText("");
                    schoolname.setText("");
                    startdate.setText("");
                    enddate.setText("");
                    cardView.setVisibility(View.GONE);
                }

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                //  myrecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(certificationAdapter);
            }
        });
        return rootView;
    }

    public boolean validate (){
        boolean validated = true;
        if( TextUtils.isEmpty(nameofInst.getText())){
            nameofInst.setError(getString(R.string.error_field_required));
            validated = false;
        }
        else  {
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setNameOfInstitution(nameofInst.getText().toString().trim());
            AccountActivity.storeGSON(getContext(), "user", user);
        }
        if( eduLevel.getSelectedItemPosition() == 0){
            TextView errorText = (TextView)eduLevel.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            validated = false;
        }
        else {
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setHighestEducationalLevelId(eduLevel.getSelectedItemPosition());
            AccountActivity.storeGSON(getContext(), "user", user);
        }

        if (certificationArrayList.size() > 0){
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setCertifications(certificationArrayList);
            AccountActivity.storeGSON(getContext(), "user", user);
        }
        else {
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setCertifications(null);
            AccountActivity.storeGSON(getContext(), "user", user);
        }
        return validated;
    }

    public boolean certValidated (){
        boolean validated = true;
        if (TextUtils.isEmpty(cert.getText())){
            Toast.makeText(getContext(), "Please enter certification", Toast.LENGTH_SHORT).show();
            validated = false;
        }
        else if (TextUtils.isEmpty(schoolname.getText())){
            Toast.makeText(getContext(), "Please enter school name", Toast.LENGTH_SHORT).show();
            validated = false;
        }
        else if (TextUtils.isEmpty(startdate.getText())){
            Toast.makeText(getContext(), "Please enter start date", Toast.LENGTH_SHORT).show();
            validated = false;
        }
        else if (TextUtils.isEmpty(enddate.getText())){
            Toast.makeText(getContext(), "Please enter end date", Toast.LENGTH_SHORT).show();
            validated = false;
        }
        else if (startTime > endTime){
            Toast.makeText(getContext(), "End date cannot be greater than start date", Toast.LENGTH_SHORT).show();
            validated = false;
        }
        return  validated;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        if (endDateClicked) {
            enddate.setText(simpleDateFormat.format(calendar.getTime()));
            endTime = calendar.getTime().getTime();
        } else {
            startdate.setText(simpleDateFormat.format(calendar.getTime()));
            startTime = calendar.getTime().getTime();
        }
    }

    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(getContext())
                .callback(this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }
}