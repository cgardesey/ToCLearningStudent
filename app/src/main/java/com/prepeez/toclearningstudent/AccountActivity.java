package com.prepeez.toclearningstudent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.greysonparrelli.permiso.Permiso;
import com.greysonparrelli.permiso.PermisoActivity;
import com.prepeez.toclearningstudent.adapter.AccountPageAdapter;
import com.prepeez.toclearningstudent.fragment.TabFragment1;
import com.prepeez.toclearningstudent.fragment.TabFragment2;
import com.prepeez.toclearningstudent.fragment.TabFragment3;
import com.prepeez.toclearningstudent.fragment.TabFragment4;
import com.prepeez.toclearningstudent.fragment.TabFragment5;
import com.prepeez.toclearningstudent.pojo.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.prepeez.toclearningstudent.ChatActivity.ANONYMOUS;
import static com.prepeez.toclearningstudent.ChatActivity.RC_SIGN_IN;
import static com.prepeez.toclearningstudent.ChatActivity.mUsername;

public class AccountActivity extends PermisoActivity implements TabFragment5.SendMessage{
    static final String TAG = "AccountActivity";

    boolean close = false;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosStorageReference;
    ViewPager mViewPager;
    AccountPageAdapter accountPageAdapter;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private static TextView statusMsg;

    public User user = new User();
    public static final String PREF_IMAGE_URI = "image_uri";

    RelativeLayout rootview;
    ProgressBar progressBar;

    String tag1 = "android:switcher:" + R.id.pageques + ":" + 0;
    String tag2 = "android:switcher:" + R.id.pageques + ":" + 1;
    String tag3 = "android:switcher:" + R.id.pageques + ":" + 2;
    String tag4 = "android:switcher:" + R.id.pageques + ":" + 3;
    String tag5 = "android:switcher:" + R.id.pageques + ":" + 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Permiso.getInstance().setActivity(this);

        setContentView(R.layout.activity_account);
        mFirebaseStorage = FirebaseStorage.getInstance();
        mChatPhotosStorageReference = mFirebaseStorage.getReference().child("profile_photos");
        rootview = findViewById(R.id.root);
        mFirebaseAuth = FirebaseAuth.getInstance();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        statusMsg = findViewById(R.id.statusMsg);
        progressBar = findViewById(R.id.pbar_pic);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    // User is signed in
                    onSignedInInitialize();
                } else {
                    // User is signed out
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .setTheme(R.style.AppTheme)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        Permiso.getInstance().setActivity(this);
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    // * must add this *
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Permiso.getInstance().onRequestPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void sendData() {


        TabFragment1 tabFrag1 = (TabFragment1) getSupportFragmentManager().findFragmentByTag(tag1);
        TabFragment2 tabFrag2 = (TabFragment2) getSupportFragmentManager().findFragmentByTag(tag2);
        TabFragment3 tabFrag3 = (TabFragment3) getSupportFragmentManager().findFragmentByTag(tag3);
        TabFragment4 tabFrag4 = (TabFragment4) getSupportFragmentManager().findFragmentByTag(tag4);
        TabFragment5 tabFrag5 = (TabFragment5) getSupportFragmentManager().findFragmentByTag(tag5);

        if (tabFrag1 != null && tabFrag2 != null && tabFrag3 != null && tabFrag4 != null && tabFrag5 != null) {
            tabFrag1.validate();
            tabFrag2.validate();
            tabFrag3.validate();
            tabFrag4.validate();
            tabFrag5.validate();
            if (tabFrag1.validate() && tabFrag2.validate() && tabFrag3.validate() && tabFrag4.validate() && tabFrag5.validate()) {
                String uriString = PreferenceManager.getDefaultSharedPreferences(AccountActivity.this).getString(PREF_IMAGE_URI, "");
                if (!uriString.equals("")) {
                    StorageReference photoRef = mChatPhotosStorageReference.child(Uri.parse(uriString).getLastPathSegment());
                    // Upload file to Firebase Storage
                    photoRef.putFile(Uri.parse(PreferenceManager.getDefaultSharedPreferences(AccountActivity.this).getString(PREF_IMAGE_URI, "")))
                            .addOnSuccessListener(AccountActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // When the image has successfully uploaded, we get its download URL
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    user = (User) retrieveGSON(AccountActivity.this, "user", user);
                                    user.setProfilePicUrl(downloadUrl.toString());
                                    user.setUserId(mFirebaseAuth.getCurrentUser().getUid());
                                    user.setUserRegistered(1);

                                    storeGSON(AccountActivity.this, "user", user);
                                    updateUserDetails();
                                }
                            });
                } else {
                    user = (User) retrieveGSON(AccountActivity.this, "user", user);
                    user.setUserId(mFirebaseAuth.getCurrentUser().getUid());
                    user.setUserRegistered(1);
                    storeGSON(AccountActivity.this, "user", user);

                    updateUserDetails();
                }
            }
            else {
                Toast.makeText(this, "Please correct errors!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateUserDetails() {
        Map userMap = new HashMap();
        userMap.put("userId",user.getUserId());
        userMap.put("profilePicUrl",user.getProfilePicUrl());
        userMap.put("titleId",user.getTitleId());
        userMap.put("firstName",user.getFirstName());
        userMap.put("lastName",user.getLastName());
        userMap.put("otherNames",user.getOtherNames());
        userMap.put("genderId",user.getGenderId());
        userMap.put("dateOfBirth",user.getDateOfBirth());
        userMap.put("homeAdress",user.getHomeAdress());
        userMap.put("maritalStatusId",user.getMaritalStatusId());
        userMap.put("contacts",user.getContacts());
        userMap.put("emailAddress",user.getEmailAddress());
        userMap.put("highestEducationalLevelId",user.getHighestEducationalLevelId());
        userMap.put("nameOfInstitution",user.getNameOfInstitution());
        userMap.put("certifications",user.getCertifications());
        userMap.put("employmentStatusId",user.getEmploymentStatusId());
        userMap.put("yearsOfExperienceId",user.getYearsOfExperienceId());
        userMap.put("companyOfEmployment",user.getCompanyOfEmployment());
        userMap.put("jobTitle",user.getJobTitle());
        userMap.put("courses",user.getCourses());
        userMap.put("userRegistered",user.getUserRegistered());
        mDatabaseReference.child("users").child(mFirebaseAuth.getCurrentUser().getUid()).updateChildren(userMap);

        Toast.makeText(AccountActivity.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
        Intent i = new Intent();
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    private void showTwoButtonSnackbar() {

        // Create the Snackbar
        LinearLayout.LayoutParams objLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final Snackbar snackbar = Snackbar.make(rootview, "Exit?", Snackbar.LENGTH_INDEFINITE);

        // Get the Snackbar layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        // Inflate our courseListMaterialDialog view
        View snackView = getLayoutInflater().inflate(R.layout.snackbar, null);



        TextView textViewOne = snackView.findViewById(R.id.first_text_view);
        textViewOne.setText("Yes");
        textViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
                close= true;
                AccountActivity.this.onBackPressed();

                //  finish();
            }
        });

        final TextView textViewTwo = snackView.findViewById(R.id.second_text_view);

        textViewTwo.setText("No");
        textViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Deny", "showTwoButtonSnackbar() : deny clicked");
                snackbar.dismiss();



            }
        });

        // Add our courseListMaterialDialog view to the Snackbar's layout
        layout.addView(snackView, objLayoutParams);

        // Show the Snackbar
        snackbar.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_SIGN_IN:
                IdpResponse response = IdpResponse.fromResultIntent(data);
                switch (resultCode) {
                    case RESULT_OK:
                        // Sign-in succeeded
                        Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        // Sign in failed
                        if (response == null) {
                            // User pressed back button
                            //showSnackbar(R.string.sign_in_cancelled);
                            Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                            //showSnackbar(R.string.no_internet_connection);
                            Toast.makeText(this, "Internet connection needed", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            //showSnackbar(R.string.unknown_error);
                            Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Sign-in error: ", response.getError());
                        }
                        break;
                }
                break;

            default:
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if(close)
            super.onBackPressed();
//        if (statusMsg.getVisibility() == View.GONE) {
//            showTwoButtonSnackbar();
//        } else {
//            super.onBackPressed();
//        }

        showTwoButtonSnackbar();
    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
    }

    private void onSignedInInitialize() {
        mDatabaseReference.child("users").child(mFirebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user = dataSnapshot.getValue(User.class);
                }
                storeGSON(AccountActivity.this, "user", user);
                if (accountPageAdapter == null) {
                    accountPageAdapter = new AccountPageAdapter(getSupportFragmentManager(), user);
                    mViewPager = findViewById(R.id.pageques);
                    mViewPager.setAdapter(accountPageAdapter);
                    mViewPager.setOffscreenPageLimit(4);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void storeGSON(Context context, String Key, Object obj){
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        prefsEditor.putString(Key, json);
        prefsEditor.commit();
    }

    public static Object retrieveGSON(Context context, String Key, Object obj){
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = appSharedPrefs.getString(Key, "");
        obj = gson.fromJson(json, obj.getClass());
        return obj;
    }
}
