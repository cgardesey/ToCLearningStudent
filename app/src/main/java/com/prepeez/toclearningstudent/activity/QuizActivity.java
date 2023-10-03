package com.prepeez.toclearningstudent.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.prepeez.toclearningstudent.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import uk.co.senab.photoview.PhotoViewAttacher;

public class QuizActivity extends AppCompatActivity {

    String title = "";
    String level = "Basic Level", course = "Mathematics", year = "2005";
    ArrayList<String> listImages, pictures;
    int total;
    private String TEST_URL = "";
    private TableLayout tableLayout;
    private VideoView player;

    private ScrollView scrollView;
    private TextView mScoreView,mQuestionid;
    private ImageView mQuestionView;
    private LinearLayout linearLayout;
    private LinearLayout playerHolder;
    private TextView toggle;

    private String mAnswer;
    private int mScore = 0;
    private int mQuestionNumber = 1;
    Bitmap bitmap;
    RadioGroup rg;
    RadioButton rb;
    LinearLayout next, prev;
    private String YOUR_DIALOG_TAG = "";
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosStorageReference;
    public static String path = "Basic Level/Mathematics/m2005";
    public static String myurl;
    private static final String KEY_VIDEO_PLAYER_VISIBLE = "video_player_visible";
    private static  int video_player_visible = View.GONE;
    private static final String KEY_QUESTION_NUMBER = "questionNumber";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras.containsKey("Title")) {
            title = extras.getString("Title");
        }

        mFirebaseStorage = FirebaseStorage.getInstance();
        mChatPhotosStorageReference = mFirebaseStorage.getReference();
//        mChatPhotosStorageReference = mFirebaseStorage.getReference().child("assets");
//
//        String[] children = path.split("/");
//        for (String child: children) {
//            mChatPhotosStorageReference = mChatPhotosStorageReference.child(child);
//        }
        scrollView = findViewById(R.id.scrollView);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        linearLayout = findViewById(R.id.add);
        playerHolder = findViewById(R.id.player_holder);
        tableLayout = findViewById(R.id.tableLayout);
        toggle = findViewById(R.id.fullscreen);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, VideoActivity.class);
                intent.putExtra("seekposition", player.getCurrentPosition());
                startActivity(intent);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stopPlayback();
                updateQuestion2();
                rg.clearCheck();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stopPlayback();
                updateQuestion();
                rg.clearCheck();
            }
        });

        // Grabs a reference to the player view
        player = findViewById(R.id.player);

        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (tableLayout.getVisibility() == View.VISIBLE){
//                    tableLayout.setVisibility(View.GONE);
//                    ViewGroup.LayoutParams lp = player.getLayoutParams();
//                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    player.setLayoutParams(lp);
//                }
//                else if (tableLayout.getVisibility() == View.GONE){
//                    tableLayout.setVisibility(View.VISIBLE);
//                    ViewGroup.LayoutParams lp = player.getLayoutParams();
//                    lp.height = 300;
//                    player.setLayoutParams(lp);
//                }
                Toast.makeText(QuizActivity.this, "Player clicked", Toast.LENGTH_SHORT).show();
                            }
        });

        // Sets the source to the HTTP URL held in the TEST_URL variable.
        // To play files, you can use Uri.fromFile(new File("..."))
        player.setVideoURI(Uri.parse("android.resource://" +getPackageName()+ "/"+R.raw.breadboarding));
        // Sets whether or not the player will toggle fullscreen for its Activity when tapped.
        //player.setAutoFullscreen(true);
        //player.setSubmitTextRes(R.drawable.ic);

        //float vieoProportion = (float) player.getMeasuredHeight() / (float) player.getMeasuredWidth();
        //ViewGroup.LayoutParams lp = player.getLayoutParams();
        //lp.height = (int) ((float) lp.width * vieoProportion);
        //player.setLayoutParams(lp);



        // From here, the player view will show a progress indicator until the player is prepared.
        // Once it's prepared, the progress indicator goes away and the controls become enabled for the user to begin playback.


//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//
//        if(extras.containsKey("Level")) {
//
//            title = extras.getString("Level");
//        }
//        if(extras.containsKey("Course")) {
//
//            course = extras.getString("Course");
//        }
//        if(extras.containsKey("Year")) {
//
//            year = extras.getString("Year");
//        }

        String[] images = new String[0];
        try {
            images = this.getAssets().list(level + "/" + course + "/" + "m" + year);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        listImages = new ArrayList<String>(Arrays.asList(images));
        Collections.sort(listImages);
        total = listImages.size();
   //  Toast.makeText(this, Integer.toString(total), Toast.LENGTH_LONG).show();

        rg = findViewById(R.id.radio_group);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        final TextView ans = findViewById(R.id.ans);
         mQuestionid = findViewById(R.id.questionid);



        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(KEY_VIDEO_PLAYER_VISIBLE)){
                playerHolder.setVisibility(savedInstanceState.getInt(KEY_VIDEO_PLAYER_VISIBLE, View.GONE));            }
            if (savedInstanceState.containsKey(KEY_QUESTION_NUMBER)){
                savedInstanceState.getInt(KEY_QUESTION_NUMBER, 1);
            }
        }


        ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ans.getText().equals("Click to See explanation to answer")) {
                    ans.setText(getResources().getString(R.string.click_to_hide_ans));
                    playerHolder.setVisibility(View.VISIBLE);
                    video_player_visible = View.VISIBLE;


                    if (scrollView != null) {
                        Handler h = new Handler();

                        h.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                scrollView.smoothScrollTo(0, scrollView.getBottom());
                            }
                        }, 250); // 250 ms delay
                    }


                } else {
                    ans.setText(getResources().getString(R.string.click_to_see_ans));
                    player.pause();
                    playerHolder.setVisibility(View.GONE);
                    video_player_visible = View.GONE;
                }
            }
        });
        //mScoreView = (TextView)findViewById(R.id.score);
        mQuestionView = findViewById(R.id.question);

        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(mQuestionView);
        photoViewAttacher.setScale((float) 1.5);
        //photoViewAttacher.update();
        //photoViewAttacher.setScale(2);
        //Toast.makeText(this, String.valueOf(photoViewAttacher.getScale()), Toast.LENGTH_SHORT).show();
       //photoViewAttacher.setScaleType(ImageView.ScaleType.CENTER_CROP);
        updateQuestion();
//        photoViewAttacher.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                android.app.FragmentManager fm = getFragmentManager();
//                ViewImageDialog courseListMaterialDialog = new ViewImageDialog();
//                  courseListMaterialDialog.putdata(bitmap);
//                courseListMaterialDialog.show(fm,YOUR_DIALOG_TAG);
//                return false;
//            }
//
//            @Override
//            public boolean onDoubleTapEvent(MotionEvent e) {
//                return false;
//            }
//        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_VIDEO_PLAYER_VISIBLE, video_player_visible);
        outState.putInt(KEY_QUESTION_NUMBER, mQuestionNumber);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_search) {
            startActivity(new Intent(QuizActivity.this, ChatActivity.class));
            return true;
        } else if (itemId == R.id.sign_out_menu) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Signed out!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.sign_register) {
            startActivity(new Intent(QuizActivity.this, AccountActivity.class));
            return true;
        } else if (itemId == R.id.action_certifications) {
            startActivity(new Intent(QuizActivity.this, CertificationsActivity.class));

            return true;
        } else if (itemId == R.id.action_about_us) {
            startActivity(new Intent(QuizActivity.this, AboutUsActivity.class));

            return true;
        } else if (itemId == R.id.action_faqs) {
            startActivity(new Intent(QuizActivity.this, FaqsActivity.class));
            return true;
        } else if (itemId == R.id.action_subscribe) {
            startActivity(new Intent(QuizActivity.this, OldSubscriptionActivity.class));

            return true;
        } else if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Make sure the player stops playing if the user presses the home button.
        player.pause();
    }

    private void updateQuestion() {
        InputStream inputStream = null;
        mQuestionid.setText(title + "/Question:"+String.valueOf(mQuestionNumber));
        try {
            inputStream = this.getAssets().open(level + "/" + course + "/" + "m" + year +"/"+listImages.get(mQuestionNumber));
        } catch (IOException e) {

        }
        //1  Toast.makeText(this, listImages.get(mQuestionNumber), Toast.LENGTH_LONG).show();
        Drawable drawable = Drawable.createFromStream(inputStream, null);

        //  Toast.makeText(this, "Hello", Toast.LENGTH_LONG).show();
        mQuestionView.setImageDrawable(drawable);
        BitmapDrawable   bitmapDrawable =(BitmapDrawable) mQuestionView.getDrawable();
        bitmap = bitmapDrawable.getBitmap();
        //  mQuestionView.setScaleType(ImageView.ScaleType.FIT_XY);
        initializeRadioButtons();

        mQuestionNumber++;
        if (mQuestionNumber == total){
            mQuestionNumber = 1;
            rg.setVisibility(View.VISIBLE);
        }
        if( mQuestionNumber > 40){
            rg.setVisibility(View.GONE);
        }
        else {
            rg.setVisibility(View.VISIBLE);
        }
    }

    private void updateQuestion2() {
        InputStream inputStream = null;
        mQuestionid.setText(course + " : " +year + " /  Question:"+String.valueOf(mQuestionNumber));
        try {
            inputStream = this.getAssets().open(level + "/" + course + "/" + "m" + year +"/"+listImages.get(mQuestionNumber));
        } catch (IOException e) {

        }
        //1  Toast.makeText(this, listImages.get(mQuestionNumber), Toast.LENGTH_LONG).show();
        Drawable drawable = Drawable.createFromStream(inputStream, null);

        //  Toast.makeText(this, "Hello", Toast.LENGTH_LONG).show();
        mQuestionView.setImageDrawable(drawable);
        BitmapDrawable   bitmapDrawable =(BitmapDrawable) mQuestionView.getDrawable();
        bitmap = bitmapDrawable.getBitmap();
        //  mQuestionView.setScaleType(ImageView.ScaleType.FIT_XY);
        initializeRadioButtons();

        if (mQuestionNumber != 1) {
            mQuestionNumber--;
        }
        if (mQuestionNumber == total){
            mQuestionNumber = 1;
            rg.setVisibility(View.VISIBLE);
        }
//        if( mQuestionNumber == 41){
//            rg.setVisibility(View.GONE);
//        }
    }

    private void updateScore(int point) {
        if (mQuestionNumber == 0){
            mScore = 0;
        }
        mScoreView.setText("" + mScore);
    }

    public void rbclick(View view) {
        initializeRadioButtons();
        int radiobuttonid = rg.getCheckedRadioButtonId();
        rb = findViewById(radiobuttonid);
        if (rb.getText().toString().contains("A")){
//            mScore = mScore + 1;
//            updateScore(mScore);
//            updateQuestion();
//            //This line of code is optiona
            //rb.setTextColor(Color.GREEN);
            rb.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_tick, 0);

            //Toast.makeText(QuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();

        }else {
            //Toast.makeText(QuizActivity.this, "Wrong!\nThe correct ans. is: A", Toast.LENGTH_SHORT).show();
//            updateQuestion();
            rb.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_red_24dp, 0);
            ((RadioButton) rg.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_tick, 0);

            //rb.setTextColor(Color.RED);
        }
    }

    private void initializeRadioButtons(){
//        rg.clearCheck();
        int count = rg.getChildCount();

        for (int i=0;i<count;i++) {
            View o = rg.getChildAt(i);
            if (o instanceof RadioButton) {
                ((RadioButton) o).setTextColor(Color.BLACK);
                ((RadioButton) o).setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        }
    }
}
