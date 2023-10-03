package com.prepeez.toclearningstudent.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.makeramen.roundedimageview.RoundedImageView;
import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.adapter.PhoneAdapter;
import com.prepeez.toclearningstudent.pojo.Phone;
import com.prepeez.toclearningstudent.pojo.User;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static com.prepeez.toclearningstudent.activity.AccountActivity.retrieveGSON;
import static com.prepeez.toclearningstudent.activity.AccountActivity.storeGSON;

/**
 * Created by 2CLearning on 12/13/2017.
 */

public class TabFragment2 extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "TabFragment2";
    private static final int ALL_PERMISSIONS_RESULT =107 ;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    User user = new User();
    LinearLayout parentLayout;
    TextView clicktoaddTextView;
    EditText emailAddress, homeAddress;
    EditText location;
    IntlPhoneInput intlPhoneInput;
    ImageView pickplace;
    ImageView opendate;
    RoundedImageView imageView;
    RecyclerView recyclerView;
    Spinner spinner, maritalstatus;
    PhoneAdapter phoneAdapter;
    TextView dateofbirth;
    LinearLayout clickToAdd;
    CardView cardView;
    Button remove, add;
    ArrayList<Phone> phoneArrayList;
    RecyclerView.LayoutManager layoutManager;
    Context mContext;
    SimpleDateFormat simpleDateFormat;
    public static int PLACE_PICKER_REQUEST = 100;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_account2, container, false);
        //user = getArguments().getParcelable("user");
        user = (User) retrieveGSON(getContext(), "user", user);

        clicktoaddTextView = rootView.findViewById(R.id.clicktoaddTextView);
        remove = rootView.findViewById(R.id.remove);
        add = rootView.findViewById(R.id.add);
        cardView = rootView.findViewById(R.id.cardView);
        emailAddress = rootView.findViewById(R.id.emailaddress);
        homeAddress = rootView.findViewById(R.id.homeaddress);

        pickplace = rootView.findViewById(R.id.locPicker);
        phoneArrayList = new ArrayList<>();
        mContext = getContext();
        opendate = rootView.findViewById(R.id.dateselect);
        imageView = rootView.findViewById(R.id.profile_imgview);
        dateofbirth = rootView.findViewById(R.id.dateofbirth);
        clickToAdd = rootView.findViewById(R.id.clickToAdd);
        intlPhoneInput = rootView.findViewById(R.id.phonenumber);
        intlPhoneInput.setDefault();
        spinner = rootView.findViewById(R.id.spinner2);
        maritalstatus = rootView.findViewById(R.id.spinner3);
        recyclerView = rootView.findViewById(R.id.recycle_location);
        final RecyclerView recyclerView = rootView.findViewById(R.id.recycle_location);
        location = rootView.findViewById(R.id.homeaddress);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        permissions.add(READ_EXTERNAL_STORAGE);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_PHONE_STATE);

        permissionsToRequest = findUnAskedPermissions(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        if (user != null){
            maritalstatus.setSelection(user.getMaritalStatusId());
            dateofbirth.setText(user.getDateOfBirth());
            homeAddress.setText(user.getHomeAdress());
            emailAddress.setText(user.getEmailAddress());
            if (user.getContacts() != null) {
                phoneArrayList = user.getContacts();
                phoneAdapter = new PhoneAdapter(phoneArrayList);
                layoutManager = new LinearLayoutManager(getActivity());

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                //  myrecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(phoneAdapter);
            }
        }
        else  {
            user = new User();
        }

        opendate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate(1980, 0, 1, R.style.DatePickerSpinner);
            }

    });

        clickToAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {

                String type = spinner.getSelectedItem().toString();
                String number = intlPhoneInput.getNumber();

                Phone phoneNumber = new Phone(number, type);

                if(intlPhoneInput.isValid()) {
                    if( spinner.getSelectedItemPosition() == 0){
                        Toast.makeText(getContext(), "Please select contact type", Toast.LENGTH_SHORT).show();
                    }

                    else if (phoneArrayList.contains(phoneNumber)) {
                        Toast.makeText(getContext(), "Number already exist!", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        phoneArrayList.add(phoneNumber);
                        phoneAdapter = new PhoneAdapter(phoneArrayList);
                        layoutManager = new LinearLayoutManager(getActivity());

                        spinner.setSelection(0);
                        intlPhoneInput.setNumber("");
                        //intlPhoneInput = (IntlPhoneInput) rootView.findViewById(R.id.phonenumber);
                        cardView.setVisibility(View.GONE);
                        //clicktoaddTextView.setTextColor(808080);
                        clicktoaddTextView.setError(null);

                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        //  myrecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(phoneAdapter);
                        //cardView.setVisibility(View.GONE);
                    }
                }
                else {
                    Toast.makeText(getContext(), "Invalid Number!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardView.setVisibility(View.GONE);
            }
        });

        pickplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                    Intent intent = intentBuilder.build((Activity) mContext);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // txtData = (TextView)view.findViewById(R.id.txtData);
    }

    public void displayReceivedData(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
       // txtData.setText("Data received: "+message);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST)
        {

            if(resultCode == RESULT_OK)
            {

                final Place place = PlacePicker.getPlace(mContext, data);
                final CharSequence name = place.getName();
                final CharSequence address = place.getAddress();
                String attributions = (String) place.getAttributions();
                if (attributions == null) {
                    attributions = "";
                }


                location.setText("");
                location.setText(name);
                location.setError(null);
                //  mAddress.setText(address);
                //  mAttributions.setText(Html.fromHtml(attributions));


            }
            else
            {
                Toast.makeText(mContext, "No location selected",
                        Toast.LENGTH_LONG).show();
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
        else {
            Toast.makeText(mContext, "No location selected",
                    Toast.LENGTH_LONG).show();
        }

    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissions) {
                    if (hasPermission(perms)) {

                    } else {

                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(String.valueOf(permissionsRejected.get(0)))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                //Log.d("API123", "permisionrejected " + permissionsRejected.size());

                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (mContext.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(mContext)
                .setMessage(message)
                .setPositiveButton(this.getResources().getString(R.string.ok), okListener)
                .setNegativeButton(this.getResources().getString(R.string.cancel), null)
                .create()
                .show();
    }


    public boolean validate (){
        boolean validated = true;
        if( TextUtils.isEmpty(emailAddress.getText())){
            emailAddress.setError(getString(R.string.error_field_required));
            validated = false;
        }
        else {
            user = (User) retrieveGSON(getContext(), "user", user);
            user.setEmailAddress(emailAddress.getText().toString().trim());
            storeGSON(getContext(), "user", user);
        }
        if( TextUtils.isEmpty(dateofbirth.getText())){
            dateofbirth.setError(getString(R.string.error_field_required));
            validated = false;
        }
        else {
            user = (User) retrieveGSON(getContext(), "user", user);
            user.setDateOfBirth(dateofbirth.getText().toString().trim());
            storeGSON(getContext(), "user", user);
        }
        if( TextUtils.isEmpty(homeAddress.getText())){
            homeAddress.setError(getString(R.string.error_field_required));
            validated = false;
        }
        else {
            user = (User) retrieveGSON(getContext(), "user", user);
            user.setHomeAdress(homeAddress.getText().toString().trim());
            storeGSON(getContext(), "user", user);
        }
        if( maritalstatus.getSelectedItemPosition() == 0){
            TextView errorText = (TextView)maritalstatus.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            validated = false;
        }
        else {
            user = (User) retrieveGSON(getContext(), "user", user);
            user.setMaritalStatusId(maritalstatus.getSelectedItemPosition());
            storeGSON(getContext(), "user", user);
        }
        if (phoneArrayList.size() == 0){
            clicktoaddTextView.setError(clicktoaddTextView.getText().toString());
            //clicktoaddTextView.setTextColor(Color.RED);
            validated = false;
        }
        else {
            user = (User) retrieveGSON(getContext(), "user", user);
            user.setContacts(phoneArrayList);
            storeGSON(getContext(), "user", user);
        }
        if (!isEmailValid(emailAddress.getText().toString().trim())){
            emailAddress.setError("Invalid email!");
            validated = false;
        }
        else {
            user = (User) retrieveGSON(getContext(), "user", user);
            user.setEmailAddress(emailAddress.getText().toString().trim());
            storeGSON(getContext(), "user", user);
        }
        return validated;
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

    public User getUser() {
        return user;
    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        dateofbirth.setText(simpleDateFormat.format(calendar.getTime()));
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
