package com.prepeez.toclearningstudent;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.prepeez.toclearningstudent.adapter.LevelAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.prepeez.toclearningstudent.ChatActivity.PREF_JSON_KEY;
import static com.prepeez.toclearningstudent.SplashScreenActivity.DEFAULT_JSON;

public class LevelActivity extends AppCompatActivity {


    private static final String TAG = "LevelActivity";

    private NestedScrollView scrollView;
    RecyclerView recyclerView;
    TextView titleTextView;
    ArrayList<String> names;
    ViewPagerCarouselView viewPagerCarouselView;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    LevelAdapter adapter;

    public static JSONObject mainJsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        names = new ArrayList<>();

//        names.add("Basic Level");
//        names.add("Secondary Level");
//        names.add("Undergraduate");
//        names.add("Vocational");
//        names.add("Technical");
//        names.add("Professional ICT");
//        names.add("Languages");
//        names.add("Family and Relationships");
//        names.add("Electronics");

        try {

            mainJsonObject = new JSONObject(PreferenceManager.getDefaultSharedPreferences(this).getString(PREF_JSON_KEY, DEFAULT_JSON));

            Iterator iterator = mainJsonObject.keys();
            while(iterator.hasNext()){
                String key = (String)iterator.next();
                names.add(key);
            }

            Log.d(TAG, mainJsonObject.toString());

        } catch (Throwable t) {
            Log.e(TAG, "Could not parse malformed JSON");
        }

        scrollView = findViewById(R.id.scrollView);
        recyclerView = findViewById(R.id.recyclerView);
        titleTextView = findViewById(R.id.editTextSearch);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LevelAdapter(names, this);
        recyclerView.setAdapter(adapter);

        long carouselSlideInterval = 3000; // 3 SECONDS
        int [] imageResourceIds = {R.drawable.girl_write, R.drawable.lady_smile, R.drawable.raise,R.drawable.slimm, R.drawable.teacher_write};
        viewPagerCarouselView = findViewById(R.id.carousel_view);
        viewPagerCarouselView.setData(getSupportFragmentManager(), imageResourceIds, carouselSlideInterval);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_search) {
            startActivity(new Intent(LevelActivity.this, ChatActivity.class));
            return true;
        } else if (itemId == R.id.sign_out_menu) {
            AuthUI.getInstance().signOut(this);
            Toast.makeText(this, "Signed out!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.sign_register) {
            startActivity(new Intent(LevelActivity.this, AccountActivity.class));
            return true;
        } else if (itemId == R.id.action_certifications) {
            startActivity(new Intent(LevelActivity.this, CertificationsActivity.class));

            return true;
        } else if (itemId == R.id.action_about_us) {
            startActivity(new Intent(LevelActivity.this, AboutUsActivity.class));

            return true;
        } else if (itemId == R.id.action_faqs) {
            startActivity(new Intent(LevelActivity.this, FaqsActivity.class));

            startActivity(new Intent(LevelActivity.this, SubscriptionActivity.class));
            return true;
        } else if (itemId == R.id.action_subscribe) {
            startActivity(new Intent(LevelActivity.this, SubscriptionActivity.class));
            return true;
        } else if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}