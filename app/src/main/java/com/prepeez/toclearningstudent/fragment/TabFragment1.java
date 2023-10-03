package com.prepeez.toclearningstudent.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.kbeanie.multipicker.api.entity.ChosenVideo;
import com.makeramen.roundedimageview.RoundedImageView;
import com.noelchew.multipickerwrapper.library.MultiPickerWrapper;
import com.noelchew.multipickerwrapper.library.ui.MultiPickerWrapperSupportFragment;
import com.prepeez.toclearningstudent.activity.AccountActivity;
import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.pojo.User;
import com.prepeez.toclearningstudent.util.PixelUtil;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


/**
 * Created by 2CLearning on 12/13/2017.
 */

public class TabFragment1 extends MultiPickerWrapperSupportFragment {
    private static final String TAG = "TabFragment1";
    public RoundedImageView profilePic;
    private EditText firstName, lastName, otherNames;
    private Spinner title, gender;
    private static final String PREF_IMAGE_URI = "image_uri";
    private static final String PREF_IMAGE_PATH = "image_path";
    public User user = new User();
    private static final int ALL_PERMISSIONS_RESULT =107 ;
    Context mContext;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private static final int LOAD_IMAGE_RESULTS = 999 ;
    private FloatingActionButton addimage, gal, cam, rem;
    LinearLayout controls;
    private ImageView opendate;

    private DatePicker datePicker;
    private EditText eventlocation;
    private Button pickplace;
    private ProgressBar progressBar;

    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosStorageReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_account1, container, false);
        //user = getArguments().getParcelable("user");
        user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
        mFirebaseStorage = FirebaseStorage.getInstance();
        mChatPhotosStorageReference = mFirebaseStorage.getReference().child("profile_photos");

        profilePic = rootView.findViewById(R.id.profile_imgview);
        firstName = rootView.findViewById(R.id.first_name);
        lastName = rootView.findViewById(R.id.last_name);
        otherNames = rootView.findViewById(R.id.other_names);
        title = rootView.findViewById(R.id.title_spinner);
        gender = rootView.findViewById(R.id.gender_spinner);
        progressBar = rootView.findViewById(R.id.progress_bar);
        addimage = rootView.findViewById(R.id.addimage);
        controls = rootView.findViewById(R.id.add);
        gal = rootView.findViewById(R.id.gal);
        cam = rootView.findViewById(R.id.cam);
        rem = rootView.findViewById(R.id.rem);
        opendate = rootView.findViewById(R.id.dateselect);

        mContext = getContext();
        permissions.add(READ_EXTERNAL_STORAGE);
        permissions.add(WRITE_EXTERNAL_STORAGE);


        permissionsToRequest = findUnAskedPermissions(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controls.getVisibility() == View.VISIBLE) {
                    controls.setVisibility(View.GONE);

                } else {
                    controls.setVisibility(View.VISIBLE);
                }
            }
        });
        gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                multiPickerWrapper.getPermissionAndPickSingleImageAndCrop(imgOptions(), 1, 1);
            }
        });
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                multiPickerWrapper.getPermissionAndTakePictureAndCrop(imgOptions(), 1, 1);
            }
        });
        rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePic.setImageDrawable(null);
            }
        });
        init();
        return rootView;
    }

    @Override
    protected MultiPickerWrapper.PickerUtilListener getMultiPickerWrapperListener() {
        return multiPickerWrapperListener;
    }

    MultiPickerWrapper.PickerUtilListener multiPickerWrapperListener = new MultiPickerWrapper.PickerUtilListener() {
        @Override
        public void onPermissionDenied() {
            // do something here
        }

        @Override
        public void onImagesChosen(List<ChosenImage> list) {

            String imagePath = list.get(0).getOriginalPath();
            Uri pickedImageUri = Uri.fromFile(new File(imagePath));
            // do something here with filePath or uri


            PreferenceManager
                    .getDefaultSharedPreferences(getContext())
                    .edit()
                    .putString(PREF_IMAGE_URI, pickedImageUri.toString())
                    .putString(PREF_IMAGE_PATH, imagePath)
                    .apply();
            profilePic.setImageDrawable(null);
            profilePic.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        }

        @Override
        public void onVideosChosen(List<ChosenVideo> list) {

        }

        @Override
        public void onError(String s) {
            //Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    };

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
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public boolean validate (){
        boolean validated = true;

        if( profilePic.getDrawable() == null){
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setProfilePicUrl(null);
            AccountActivity.storeGSON(getContext(), "user", user);
        }

        if( TextUtils.isEmpty(firstName.getText())){
            firstName.setError(getString(R.string.error_field_required));
            validated = false;
        }
        else {
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setFirstName(firstName.getText().toString().trim());
            AccountActivity.storeGSON(getContext(), "user", user);
        }
        if( TextUtils.isEmpty(lastName.getText())){
            lastName.setError(getString(R.string.error_field_required));
            validated = false;
        }
        else {
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setLastName(lastName.getText().toString().trim());
            AccountActivity.storeGSON(getContext(), "user", user);
        }
        if( title.getSelectedItemPosition() == 0){
            TextView errorText = (TextView)title.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            validated = false;
        }
        else {
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setTitleId(title.getSelectedItemPosition());
            AccountActivity.storeGSON(getContext(), "user", user);
        }
        if( gender.getSelectedItemPosition() == 0){
            TextView errorText = (TextView)gender.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            validated = false;
        }
        else {
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setGenderId(gender.getSelectedItemPosition());
            AccountActivity.storeGSON(getContext(), "user", user);
        }
        if(TextUtils.isEmpty(otherNames.getText())){
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setOtherNames(null);
            AccountActivity.storeGSON(getContext(), "user", user);
        }
        else {
            user = (User) AccountActivity.retrieveGSON(getContext(), "user", user);
            user.setOtherNames(otherNames.getText().toString().trim());
            AccountActivity.storeGSON(getContext(), "user", user);
        }
        return validated;
    }

    public void init() {
        if (user != null){
            String url = user.getProfilePicUrl();
            if (url != null) {
                profilePic.setVisibility(View.INVISIBLE);
                addimage.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Glide.with(profilePic.getContext())
                        .load(url)
                        .asBitmap()
                        .listener(new RequestListener<String, Bitmap>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                profilePic.setVisibility(View.VISIBLE);
                                addimage.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(profilePic);
            }
            else {
//                String imagePath = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(PREF_IMAGE_PATH, "");
//                if (!imagePath.equals("")) {
//                    profilePic.setImageBitmap(BitmapFactory.decodeFile(imagePath));
//                }
                profilePic.setImageBitmap(null);
            }
            title.setSelection(user.getTitleId());
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            otherNames.setText(user.getOtherNames());
            gender.setSelection(user.getGenderId());
        }
        else {
            profilePic.setVisibility(View.VISIBLE);
            addimage.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    public User getUser() {
        return user;
    }

    private UCrop.Options imgOptions () {
        UCrop.Options options = new UCrop.Options();
        options.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        options.setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        options.setCropFrameColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        options.setCropFrameStrokeWidth(PixelUtil.dpToPx(getContext(), 4));
        options.setCropGridColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        options.setCropGridStrokeWidth(PixelUtil.dpToPx(getContext(), 2));
        options.setActiveWidgetColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        options.setToolbarTitle("Crop Image");

        // set rounded cropping guide
        options.setCircleDimmedLayer(true);
        return options;
    }
}