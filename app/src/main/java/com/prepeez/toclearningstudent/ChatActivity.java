/**
 * Copyright Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.prepeez.toclearningstudent;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.prepeez.toclearningstudent.adapter.ChatAdapter;
import com.prepeez.toclearningstudent.materialDialog.CourseListMaterialDialog;
import com.prepeez.toclearningstudent.pojo.Course;
import com.prepeez.toclearningstudent.pojo.DateItem;
import com.prepeez.toclearningstudent.pojo.Message;
import com.prepeez.toclearningstudent.pojo.User;
import com.prepeez.toclearningstudent.utils.AlertDialogHelper;
import com.prepeez.toclearningstudent.utils.RecyclerItemClickListener;
import com.yalantis.ucrop.util.FileUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
//import me.leolin.shortcutbadger.ShortcutBadgeException;
//import me.leolin.shortcutbadger.ShortcutBadger;

public class ChatActivity extends AppCompatActivity implements AlertDialogHelper.AlertDialogListener {


    static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    public static final SimpleDateFormat sfd_date = new SimpleDateFormat("MMMM d, yyyy");
    public static final SimpleDateFormat sfd_file_name = new SimpleDateFormat("yyyyMMdd-hhmmss.FFFFFFFFF");
    public static final SimpleDateFormat sfd_time = new SimpleDateFormat("h:mm a");
    public static final String PREF_JSON_KEY = "pref_json_key";
    public static final String PREF_MSG_LENGTH_KEY = "pref_msg_length";
    public static final int DEFAULT_MSG_LENGTH = 140;
    public static final String MSG_LENGTH_KEY = "msg_length";
    public static final String JSON_KEY = "main_jason_string";
    public static final int RC_PLACE_PICKER = 102;
    public static final int RC_GALLERY = 13;
    public static final int RC_SIGN_IN = 1;
    public static final int RC_REGISTER = 44;
    public static final int TYPE_DATE = 200;
    public static final int TYPE_MESSAGE = 100;
    static final String CHOOSE_CLASS_CHAT = "Please choose a chat class from the menu.";
    static final String NO_CLASS_CHATS = "There are currently no class chats.";
    public static final String INTERNET_CONNECTION_NEEDED = "Internet connection needed.";
    static final String YOUR_DIALOG_TAG = "";
    static final String TAG = "ChatActivity";
    public static final String ANONYMOUS = "anonymous";
    public static final String PREF_USERID = "PREF_USERID";


    static RecyclerView mRecylerView;
    static LinearLayoutManager mLinearLayoutManager;
    static TextView statusMsg;
    static RelativeLayout controls;

    static ChatAdapter mChatAdapter;
    static ProgressBar mProgressBar;
    ImageButton mAttach;
    EmojiconEditText mMessageEditText;
    ImageButton mSendButton;
    ImageView emojiButton;
    ArrayList<Course> userCourses;
    public static Context context;
    public static Activity activity;
    public static ArrayList<Object> messages;
    static Menu search_menu;
    Menu context_menu;
    View rootView;
    EmojIconActions emojIcon;
    CardView cardview;
    RelativeLayout doc, gal, loc;
    LinearLayout txtMsgFrame;
    EmojiconTextView txtMsg;
    FrameLayout linkPrevFrame;
    LinearLayout linkPrevFrame2;
    ImageView linkImage;
    LinearLayout linkTextArea;
    TextView linkTitle;
    TextView linkDesc;
    ImageView close;
    static ImageView imageView;
    String link = "";

    static final int ALL_PERMISSIONS_RESULT = 107;
    ArrayList permissionsToRequest;
    ArrayList permissionsRejected = new ArrayList();
    ArrayList permissions = new ArrayList();

    static public String mUsername;
    public static String mUserId = "";

    // Firebase instance variables
    static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mClassDatabaseReference;
    static ChildEventListener mChildEventListener;
    public static FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseStorage mFirebaseStorage;
    public static StorageReference mChatPhotosStorageReference;
    public static StorageReference mChatDocsStorageReference;
    public static CourseListMaterialDialog courseListMaterialDialog;

    ActionMode mActionMode;
    boolean isMultiSelect = false;
    ArrayList<Object> multiselect_list = new ArrayList<>();
    AlertDialogHelper alertDialogHelper;

    public ArrayList<Object> selected_usersList = new ArrayList<>();
    public ArrayList<Object> consolidatedList = new ArrayList<>();
    ArrayList<String> docPaths;
    static Map msgInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();
        activity = this;


        alertDialogHelper = new AlertDialogHelper(this);
        emojiButton = findViewById(R.id.emoji_btn);

        mUsername = ANONYMOUS;
        courseListMaterialDialog = new CourseListMaterialDialog();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mChatPhotosStorageReference = mFirebaseStorage.getReference().child("chat_photos");
        mChatDocsStorageReference = mFirebaseStorage.getReference().child("chat_docs");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 14);
        }

        permissions.add(READ_EXTERNAL_STORAGE);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        //permissions.add(CAMERA);
        permissions.add(INTERNET);



        permissionsToRequest = findUnAskedPermissions(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    onSignedInInitialize(user.getDisplayName(), user.getUid());

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

        imageView = findViewById(R.id.imageView);
        doc = findViewById(R.id.doc);
        gal = findViewById(R.id.gal);
        loc = findViewById(R.id.loc);
        mProgressBar = findViewById(R.id.pbar_pic);
        mRecylerView = findViewById(R.id.recyrlerView);
        controls = findViewById(R.id.add);
        statusMsg = findViewById(R.id.statusMsg);
        mAttach = findViewById(R.id.attach);
        mMessageEditText = findViewById(R.id.messageEditText);
        mSendButton = findViewById(R.id.sendMessageButton);
        cardview = findViewById(R.id.card_view);
        rootView = findViewById(R.id.root_view);
        emojIcon = new EmojIconActions(this, rootView, mMessageEditText, emojiButton);
        emojIcon.ShowEmojIcon();

        txtMsgFrame = findViewById(R.id.txt_msg_frame);
        txtMsg = findViewById(R.id.txt_msg);
        linkPrevFrame = findViewById(R.id.link_prev_frame);
        linkPrevFrame2 = findViewById(R.id.link_prev_frame2);
        linkImage = findViewById(R.id.link_img);
        linkTextArea = findViewById(R.id.link_text_area);
        linkTitle = findViewById(R.id.link_title);
        linkDesc = findViewById(R.id.link_desc);
        close = findViewById(R.id.close);

        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e("Keyboard", "open");
            }

            @Override
            public void onKeyboardClose() {
                Log.e("Keyboard", "close");
            }
        });
        messages = new ArrayList<>();
        mChatAdapter = new ChatAdapter(messages, mUserId, this, mClassDatabaseReference);
        mLinearLayoutManager = new LinearLayoutManager(context);
        mRecylerView.setLayoutManager(mLinearLayoutManager);
        mRecylerView.setAdapter(mChatAdapter);
        mRecylerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecylerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect)
                    multi_select(position);
//                else
//                    Toast.makeText(getApplicationContext(), "Details Page", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect) {
                    multiselect_list = new ArrayList<Object>();
                    isMultiSelect = true;

                    if (mActionMode == null) {
                        mActionMode = startActionMode(mActionModeCallback);
                    }
                }

                multi_select(position);

            }
        }));
        // ImagePickerButton shows an image picker to upload a image for a message


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkPrevFrame.setVisibility(View.GONE);
            }
        });

        linkPrevFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!link.equals("")) {
                    Uri webpage = Uri.parse(link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            }
        });

        doc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FilePickerBuilder.getInstance().setMaxCount(10)
                        .setSelectedFiles(new ArrayList<String>())
                        //.setActivityTheme(R.style.AppTheme)
                        .pickFile(ChatActivity.this);

            }
        });

        gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Config config = new Config();
//                config.setCameraBtnImage(R.drawable.add);
//                config.setFlashOn(true);
//
//                ImagePickerActivity.setConfig(config);
                Intent intent = new Intent(context, ImagePickerActivity.class);
                startActivityForResult(intent, RC_GALLERY);
            }
        });

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                    Intent intent = intentBuilder.build(ChatActivity.this);
                    startActivityForResult(intent, RC_PLACE_PICKER);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        mAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardview.getVisibility() == View.GONE) {
                    cardview.setVisibility(View.VISIBLE);
                    //cardview.requestFocus();
                    //cardview.requestFocusFromTouch();
                } else {
                    cardview.setVisibility(View.GONE);
                }
            }
        });
        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setVisibility(View.VISIBLE);
                } else {
                    mSendButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMessageEditText.getText().toString().trim().length() > 0) {
                    Message message = new Message(
                            mMessageEditText.getText().toString(),
                            mUsername,
                            null,
                            mUserId,
                            null,
                            null,
                            (linkPrevFrame.getVisibility() == View.VISIBLE) ? linkTitle.getText().toString() : null,
                            (linkPrevFrame.getVisibility() == View.VISIBLE) ? linkDesc.getText().toString() : null,
                            (linkPrevFrame.getVisibility() == View.VISIBLE) ? link : null,
                            null,
                            null,
                            mClassDatabaseReference.child("messages").push().getKey(),
                            null,
                            null);
                    mClassDatabaseReference.child("messages").push().setValue(message);
                    mMessageEditText.setText("");
                }
            }

        });

//        mConnectivityChangeListener = new ConnectivityChangeListener() {
//            @Override
//            public void onConnectionChange(ConnectivityEvent event) {
//                if (event.getState().getValue() == ConnectivityState.CONNECTED) {
//                    // device has active internet connection
//                    //isConnected = true;
//
//                    if (statusMsg.getText().toString().equals(INTERNET_CONNECTION_NEEDED)) {
//                        statusMsg.setText("");
//                    }
//                    //Toast.makeText(ChatActivity.this, "CONNECTED", Toast.LENGTH_SHORT).show();
//                } else {
//                    // there is no active internet connection on this device
//                    //isConnected = false;
//                    statusMsg.setText(INTERNET_CONNECTION_NEEDED);
//                    //Toast.makeText(ChatActivity.this, "DISCONNECTED", Toast.LENGTH_SHORT).show();
//                }
//            }
//        };
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
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
            case RC_REGISTER:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
            case FilePickerConst.REQUEST_CODE_DOC:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        if (data != null) {
                            docPaths = new ArrayList<>();
                            docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));

                            for (final String selectedDocPath : docPaths) {
                                //StorageReference docRef = mChatDocsStorageReference.child(Uri.fromFile(new File(selectedDocPath)).getLastPathSegment());
                                File file = new File(selectedDocPath);
                                final Uri uri = Uri.fromFile(file);
                                String lastPathSegment = uri.getLastPathSegment();

                                String ext = selectedDocPath.substring(selectedDocPath.lastIndexOf("."));

                                String metaData = fileSize(file.length()) + " ∙ "+ ext.substring(1).toUpperCase();
                                String msgKey = mClassDatabaseReference.child("messages").push().getKey();
                                final Message message = new Message(null, mUsername, null, mUserId, "URI" + selectedDocPath, null, null, null, null, ext, null, msgKey, lastPathSegment.substring(0, lastPathSegment.lastIndexOf(".")), metaData);
                                //mClassDatabaseReference.child("messages").push().setValue(message);
                                mClassDatabaseReference.child("messages").child(msgKey).setValue(message);
                            }

                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        // some stuff that will happen if there's no result
                        break;
                }

                break;
            case RC_GALLERY:
                switch (resultCode) {
                    case Activity.RESULT_OK:

                        ArrayList<Uri> image_uris = data.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

                        for (final Uri selectedImageUri : image_uris) {
                            File file = new File(selectedImageUri.getPath());
                            String metaData = fileSize(file.length());
                            String msgKey = mClassDatabaseReference.child("messages").push().getKey();
                            final Message message = new Message(null, mUsername, "URI" + selectedImageUri.toString(), mUserId, null, null, null, null, null, null, null, msgKey, null, metaData);
                            mClassDatabaseReference.child("messages").child(msgKey).setValue(message);
                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        // some stuff that will happen if there's no result
                        break;
                }
                break;
            case RC_PLACE_PICKER:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Place place = PlacePicker.getPlace(context, data);
                        String latlng = place.getLatLng().toString();
                        final String mapUrl = "URI" + "https://maps.googleapis.com/maps/api/staticmap?center=" + latlng + "&zoom=13&size=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318&markers=color:red%7Clabel:C%7C40.718217,-73.998284&key=AIzaSyAMObL5E5wWkaV3mE9UAyJtPlCZIxiqU00";

                        place.getLatLng().toString();
                        Message message = new Message(
                                null,
                                mUsername,
                                null,
                                mUserId,
                                null,
                                latlng,
                                null,
                                null,
                                null,
                                null,
                                mapUrl,
                                mClassDatabaseReference.child("messages").push().getKey(),
                                null,
                                null);
                        mClassDatabaseReference.child("messages").push().setValue(message, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                final String msgKey = databaseReference.getKey();


                            }
                        });

                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
                break;
            default:
                break;

        }
    }

    @Override
    protected void onResume() {

        super.onResume();

        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

        //ConnectionBuddy.getInstance().registerForConnectivityEvents(this, mConnectivityChangeListener);

        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter((int) PreferenceManager.getDefaultSharedPreferences(this).getLong(PREF_MSG_LENGTH_KEY, DEFAULT_MSG_LENGTH))});
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        detachDatabaseReadListener();
        //ConnectionBuddy.getInstance().unregisterFromConnectivityEvents(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chat, menu);
        search_menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.sign_out_menu) {
            AuthUI.getInstance().signOut(this);
            Toast.makeText(this, "Signed out!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.sign_register) {
            startActivityForResult(new Intent(ChatActivity.this, AccountActivity.class), RC_REGISTER);
            return true;
        } else if (itemId == R.id.action_certifications) {
            startActivity(new Intent(ChatActivity.this, CertificationsActivity.class));

            return true;
        } else if (itemId == R.id.action_about_us) {
            startActivity(new Intent(ChatActivity.this, AboutUsActivity.class));

            return true;
        } else if (itemId == R.id.action_faqs) {
            startActivity(new Intent(ChatActivity.this, FaqsActivity.class));

            startActivity(new Intent(ChatActivity.this, SubscriptionActivity.class));

            return true;
        } else if (itemId == R.id.action_subscribe) {
            startActivity(new Intent(ChatActivity.this, SubscriptionActivity.class));

            return true;
        } else if (itemId == R.id.action_search) {
            MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {

                    if ((messages != null) && (mChatAdapter != null)) {
                        initSearchView1(messages, mChatAdapter);
                        filter(messages, "");
                    }

                    return true;
                }
            });
            //startActivity(new Intent(ChatActivity.this, AccountActivity.class));
            return true;
        } else if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.switch_chat) {
            User user = (User) AccountActivity.retrieveGSON(ChatActivity.this, "user", new User());
            if (user.getCourses() != null) {
                userCourses = user.getCourses();
                showCourseListMaterialDialogFrag();
            } else {
                Toast.makeText(context, "You are not registered for any class chats.", Toast.LENGTH_SHORT).show();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPositiveClick(int from) {
        if (from == 1) {
            if (multiselect_list.size() > 0) {
                for (int i = 0; i < multiselect_list.size(); i++) {
                    mClassDatabaseReference
                            .child("deleted")
                            .child(String.valueOf(((Message) multiselect_list.get(i)).getTimestampCreatedLong()))
                            .child(mFirebaseAuth.getCurrentUser().getUid()).setValue("true");
                    messages.remove(multiselect_list.get(i));
                }

                mChatAdapter.notifyDataSetChanged();

                if (mActionMode != null) {
                    mActionMode.finish();
                }
            }
        } else if (from == 2) {
//            if (mActionMode != null) {
//                mActionMode.finish();
//            }
//
//            SampleModel mSample = new SampleModel("Name"+user_list.size(),"Designation"+user_list.size());
//            messages.add(mSample);
//            mChatAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onNegativeClick(int from) {

    }

    @Override
    public void onNeutralClick(int from) {

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

    private void onSignedInInitialize(String username, String userId) {
        mUsername = username;
        mUserId = userId;

        FirebaseDatabase.getInstance().getReference().child("users")
                .child(mFirebaseAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        boolean userRegistered = dataSnapshot.exists();
                        if (userRegistered) {
                            mProgressBar.setVisibility(View.GONE);
                            User user = dataSnapshot.getValue(User.class);
                            AccountActivity.storeGSON(ChatActivity.this, "user", user);
                            boolean currentCourseNotSet = PreferenceManager.getDefaultSharedPreferences(context).getString(mUserId, "").equals("");

                            if (currentCourseNotSet) {
                                statusMsg.setText(CHOOSE_CLASS_CHAT);
                                userCourses = user.getCourses();
                                showCourseListMaterialDialogFrag();
                            } else {
                                init();
                            }
                        } else {
                            Intent intent = new Intent(ChatActivity.this, AccountActivity.class);
                            startActivityForResult(intent, RC_REGISTER);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
        detachDatabaseReadListener();
    }

    public static void attachDatabaseReadListener() {

        if (mChildEventListener == null) {

            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(final DataSnapshot msgSnapshot, String s) {
                    //mProgressBar.setVisibility(ProgressBar.VISIBLE);

                    statusMsg.setText("");

                    final Message message = msgSnapshot.getValue(Message.class);


                    java.util.Date time = new java.util.Date(message.getTimestampCreatedLong());

                    final String timeStamp = sfd_file_name.format(time);

                    mClassDatabaseReference.child("deleted").child(String.valueOf(message.getTimestampCreatedLong())).child(mFirebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot deletedSnapshot) {
                            boolean msgDeletedByUser = deletedSnapshot.exists();

                            if (messages.size() > 0) {

                                int size = messages.size();
                                for (int i = size; i > 0; i--) {

                                    Object existingObj = messages.get(i - 1);

                                    if (existingObj instanceof Message) {

                                        Message existingMsg = (Message) existingObj;

                                        if (message.getKey().equals(existingMsg.getKey())) {
                                            java.util.Date duplicateTime = new java.util.Date(existingMsg.getTimestampCreatedLong());
                                            String duplicateTimeStamp = sfd_file_name.format(duplicateTime);

                                            boolean isPhoto = existingMsg.getPhotoUrl() != null;
                                            boolean isDoc = existingMsg.getDocUrl() != null;
                                            boolean isMsg = existingMsg.getText() != null;

                                            if (isPhoto) {

                                                String imgFilepath = Environment.getExternalStorageDirectory() + "/2l/Pictures/Sent";
                                                File imgFile = new File(imgFilepath, "IMG-" + duplicateTimeStamp + ".jpeg");
                                                if (imgFile.exists()) {
                                                    File from = new File(imgFilepath, "IMG-" + duplicateTimeStamp + ".jpeg");
                                                    File to = new File(imgFilepath, "IMG-" + timeStamp + ".jpeg");
                                                    from.renameTo(to);
                                                }
                                            } else if (isDoc) {

                                                String pdfImgFilepath = Environment.getExternalStorageDirectory() + "/2l/PDF/";
                                                File pdfImgFile = new File(pdfImgFilepath, "PDF-" + duplicateTimeStamp + ".png");
                                                if (pdfImgFile.exists()) {
                                                    File from = new File(pdfImgFilepath, "PDF-" + duplicateTimeStamp + ".png");
                                                    File to = new File(pdfImgFilepath, "PDF-" + timeStamp + ".png");
                                                    from.renameTo(to);
                                                }
                                                String ext = existingMsg.getExt();
                                                String docFilepath = Environment.getExternalStorageDirectory() + "/2l/Documents/Sent";
                                                File docFile = new File(docFilepath, "DOC-" + duplicateTimeStamp + ext);
                                                if (docFile.exists()) {
                                                    File from = new File(docFilepath, "DOC-" + duplicateTimeStamp + ext);
                                                    File to = new File(docFilepath, "DOC-" + timeStamp + ext);
                                                    from.renameTo(to);
                                                }
                                            }
                                            messages.set(i - 1, message);
                                            mChatAdapter.notifyDataSetChanged();
                                            return;
                                        }
                                    }
                                }
                            }

                            if (!msgDeletedByUser) {

                                String photoUrl = message.getPhotoUrl();
                                String docUrl = message.getDocUrl();
                                String mapUrl = message.getMapUrl();
                                boolean unuploadedPhotoIsMine = photoUrl != null && photoUrl.substring(0, 3).equals("URI") && message.getUserId().equals(mUserId);
                                boolean unuploadedDocIsMine = docUrl != null && docUrl.substring(0, 3).equals("URI") && message.getUserId().equals(mUserId);
                                boolean unuploadedMapisMine = mapUrl != null && mapUrl.substring(0, 3).equals("URI") && message.getUserId().equals(mUserId);

                                if (unuploadedDocIsMine) {

                                    if (isExternalStorageWritable()) {
                                        String ext = docUrl.substring(docUrl.lastIndexOf("."));
                                        File file = new File(Environment.getExternalStorageDirectory() + "/2l/Documents", "Sent");
                                        if (!file.exists()) {
                                            file.mkdirs();
                                        }
                                        String destinationPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/2l/Documents/Sent/" + "DOC-" + timeStamp + ext;

                                        try {
                                            FileUtils.copyFile(docUrl.substring(3), destinationPath);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                if (unuploadedPhotoIsMine) {

                                    if (isExternalStorageWritable()) {
                                        String ext = photoUrl.substring(photoUrl.lastIndexOf("."));
                                        File file = new File(Environment.getExternalStorageDirectory() + "/2l/Pictures", "Sent");
                                        if (!file.exists()) {
                                            file.mkdirs();
                                        }
                                        String destinationPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/2l/Pictures/Sent/" + "IMG-" + timeStamp + ".jpeg";

                                        try {
                                            FileUtils.copyFile(photoUrl.substring(3), destinationPath);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                if (unuploadedMapisMine) {

                                    if (isExternalStorageWritable()) {

                                        final String finalMapUrl = mapUrl.substring(3);
                                        Glide.with(context)
                                                .load(finalMapUrl)
                                                .asBitmap()
                                                .listener(new RequestListener<String, Bitmap>() {
                                                    @Override
                                                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                                        return false;
                                                    }

                                                    @Override
                                                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {

                                                        java.util.Date time2 = new java.util.Date(message.getTimestampCreatedLong());
                                                        String timeStamp = sfd_file_name.format(time2);

                                                        File file = new File(Environment.getExternalStorageDirectory() + "/2l/Maps", "Sent");
                                                        if (!file.exists()) {
                                                            file.mkdirs();
                                                        }
                                                        String destinationPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/2l/Maps/Sent/" + "IMG-" + timeStamp + ".jpeg";

                                                        // Next, create your specific file for image storage:

                                                        File image = new File(destinationPath);
                                                        // After that, you just have to write the Bitmap thanks to its method compress such as:


                                                        boolean success = false;

                                                        // Encode the file as a JPEG image.
                                                        FileOutputStream outStream;
                                                        try {
                                                            outStream = new FileOutputStream(image);
                                                            /* 100 to keep full quality of the image */
                                                            resource.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                                                            outStream.flush();
                                                            outStream.close();
                                                            success = true;
                                                        } catch (FileNotFoundException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }

                                                        if (success) {
                                                            //Toast.makeText(activity, "Image saved with success", Toast.LENGTH_LONG).show();
                                                            Log.d(TAG, "Image saved with success");
                                                            String msgKey = msgSnapshot.getKey();
                                                            msgInfo = new HashMap();
                                                            msgInfo.put("mapUrl", finalMapUrl);
                                                            mClassDatabaseReference.child("messages").child(msgKey).updateChildren(msgInfo);
                                                            mClassDatabaseReference.child("messages").child(msgKey).child("timestampCreated").child("timestamp").addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    if (dataSnapshot.exists()) {
                                                                        Long timeStamp = (Long) dataSnapshot.getValue();
                                                                        for (Object obj : messages) {
                                                                            if (obj instanceof Message && ((Message) obj).getTimestampCreatedLong() == timeStamp) {
                                                                                ((Message) obj).setPhotoUrl(finalMapUrl);
                                                                                mChatAdapter.notifyDataSetChanged();
                                                                            }
                                                                        }
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {

                                                                }
                                                            });

                                                        } else {
                                                            //Toast.makeText(activity, "Error during image saving", Toast.LENGTH_LONG).show();
                                                            Log.d(TAG, "Error during image saving");
                                                        }
                                                        return false;
                                                    }
                                                })
                                                .into(imageView);
                                    }
                                }


                                Message currentMsg = msgSnapshot.getValue(Message.class);
                                Message prevMsg;
                                String prevDate = "", currentDate;
                                DateItem dateItem;
                                if (messages.size() > 0) {
                                    prevMsg = (Message) messages.get(messages.size() - 1);
                                    java.util.Date prevTime = new java.util.Date(prevMsg.getTimestampCreatedLong());
                                    prevDate = sfd_date.format(prevTime);
                                }
                                java.util.Date currentTime = new java.util.Date(currentMsg.getTimestampCreatedLong());
                                currentDate = sfd_date.format(currentTime);
                                String formattedDate = getFormattedDate(currentMsg.getTimestampCreatedLong());
                                dateItem = new DateItem(formattedDate);
                                String imgUrl = message.getPhotoUrl() != null ? message.getPhotoUrl() : "";
                                String documentUrl = message.getDocUrl() != null ? message.getDocUrl() : "";
                                String googleMapUrl = message.getMapUrl() != null ? message.getMapUrl() : "";
                                if (!(message.getText() == null && (imgUrl + documentUrl + googleMapUrl).substring(0, 3).equals("URI") && !message.getUserId().equals(mUserId))) {
                                    if (prevDate != null && !currentDate.equals(prevDate)) {
                                        messages.add(dateItem);
                                    }
                                    //mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                                    messages.add(currentMsg);
                                    mChatAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    initSearchView1(messages, mChatAdapter);

                    mLinearLayoutManager.scrollToPositionWithOffset(messages.size() - 1, 0);

                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

                public void onChildRemoved(DataSnapshot dataSnapshot) { }

                public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

                public void onCancelled(DatabaseError databaseError) { }
            };
            mClassDatabaseReference.child("messages").addChildEventListener(mChildEventListener);
        }
    }

    public void refreshAdapter() {
        mChatAdapter.selected_usersList = multiselect_list;
        mChatAdapter.consolidatedList = messages;
        mChatAdapter.notifyDataSetChanged();
    }

    public static void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mClassDatabaseReference.child("messages").removeEventListener(mChildEventListener);
            mChildEventListener = null;
            messages.clear();
            mChatAdapter.notifyDataSetChanged();

//            messages = new ArrayList<>();
//            mChatAdapter = new ChatAdapter(messages, mUserId, activity, mClassDatabaseReference);
//            mRecylerView.setLayoutManager(new LinearLayoutManager(context));
//            mRecylerView.setAdapter(mChatAdapter);
        }
    }

    public static void initSearchView1(final ArrayList<Object> searchevents, final ChatAdapter seAdapter) {
        final android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) search_menu.findItem(R.id.action_search).getActionView();

        searchView.setSubmitButtonEnabled(true);

        // set hint and the text colors

        EditText txtSearch = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        txtSearch.setHint("Search...");
        txtSearch.setHintTextColor(Color.DKGRAY);
        // txtSearch.setText("Search....");
        //   txtSearch.setTextColor(getResources().getColor(R.color.colorPrimary));


        // set the cursor

        AutoCompleteTextView searchTextView = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            // mCursorDrawableRes.set(searchTextView, R.drawable.bg_gradient); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                final ArrayList<Object> filteredModelList = filter(searchevents, query);
                seAdapter.setFilter(filteredModelList);

                //   EventFrag.filter(filteredModelList);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                ArrayList<Object> filteredModelList = new ArrayList<Object>();
                filteredModelList = filter(searchevents, newText);
                seAdapter.setFilter(filteredModelList);
                // EventFrag.filter(filteredModelList);

                // mChatAdapter = new MessageAdapter(context, R.layout.item_message, filteredModelList, mUserId);
                // mMessageListView.getAdapter().
                //  mMessageListView.setAdapter(mChatAdapter);
                Log.d("Text", "Canging" + String.valueOf(filteredModelList.size()));
                return true;
            }

        });
    }

    private static ArrayList<Object> filter(ArrayList<Object> models, String search_txt) {

        search_txt = search_txt.toLowerCase();
        final ArrayList<Object> filteredModelList = new ArrayList<>();
        for (Object model : models) {

            //   final String text = model.getName().toLowerCase();
            if (model instanceof Message && ((Message) model).getText() != null) {
                final String text1 = ((Message) model).getText().toLowerCase();

                if (text1.contains(search_txt)) {
                    filteredModelList.add(model);
                }
            }
        }
        return filteredModelList;
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_multi_select, menu);
            context_menu = menu;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.action_delete) {
                alertDialogHelper.showAlertDialog("", "Delete?", "DELETE", "CANCEL", 1, false);
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            multiselect_list = new ArrayList<Object>();
            refreshAdapter();
        }
    };

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
                return (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private void showCourseListMaterialDialogFrag() {
        android.app.FragmentManager fm = getFragmentManager();
        courseListMaterialDialog.setCancelable(true);
        courseListMaterialDialog.setCourses(userCourses);

        if(courseListMaterialDialog != null && courseListMaterialDialog.isAdded()) {
            // no need to call dialog.show(ft, "DatePicker");
        } else {
            courseListMaterialDialog.show(fm, YOUR_DIALOG_TAG);
        }
    }

    public void multi_select(int position) {
        if (mActionMode != null) {
            if (multiselect_list.contains(messages.get(position)))
                multiselect_list.remove(messages.get(position));
            else
                multiselect_list.add(messages.get(position));

            if (multiselect_list.size() > 0)
                mActionMode.setTitle("" + multiselect_list.size());
            else
                mActionMode.setTitle("");

            refreshAdapter();

        }
    }

    public static void init() {

        final String currentCourse = PreferenceManager.getDefaultSharedPreferences(context).getString(mUserId, "");
        boolean currentCourseSet = !currentCourse.equals("");

        if (currentCourseSet) {
            mClassDatabaseReference = FirebaseDatabase.getInstance().getReference().child(currentCourse);
            statusMsg.setText(NO_CLASS_CHATS);
            controls.setVisibility(View.VISIBLE);
            mRecylerView.setVisibility(View.VISIBLE);
            //mProgressBar.setVisibility(View.VISIBLE);
            attachDatabaseReadListener();

        } else {
            mClassDatabaseReference = null;
            statusMsg.setText(CHOOSE_CLASS_CHAT);
            controls.setVisibility(View.INVISIBLE);
            mRecylerView.setVisibility(View.INVISIBLE);
            //mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    public static String getFormattedDate(long timeInMillis) {
        String formattedDate = "";
        Calendar cal = Calendar.getInstance();
        long timeNowInMillis = cal.getTimeInMillis();
        cal.add(Calendar.DATE, -1);
        long timeYestInMillis = cal.getTimeInMillis();
        SimpleDateFormat sfd_year = new SimpleDateFormat("yyyy");
        SimpleDateFormat sfd_month = new SimpleDateFormat("MMMM d");
        if (sfd_date.format(new java.util.Date(timeInMillis)).equals(sfd_date.format(new java.util.Date(timeNowInMillis)))) {
            formattedDate = "Today";
        } else if (sfd_date.format(new java.util.Date(timeInMillis)).equals(sfd_date.format(new java.util.Date(timeYestInMillis)))) {
            formattedDate = "Yesterday";
        } else if (sfd_year.format(new java.util.Date(timeInMillis)).equals(sfd_year.format(new java.util.Date(timeNowInMillis)))) {
            formattedDate = sfd_month.format(new java.util.Date(timeInMillis));
        } else {
            formattedDate = sfd_date.format(new java.util.Date(timeInMillis));
        }
        return formattedDate;
    }

    public static String fileSize(long length) {
        String file_size;
        if (length < 1000L) {
            file_size = String.valueOf(length) + " B";
        } else if (length >= 1000L && length < 1000000L) {
            file_size = String.valueOf(length / 1000L) + " KB";
        } else {
            file_size = String.valueOf(length / 1000000L) + " MB";
        }
        return file_size;
    }
}
