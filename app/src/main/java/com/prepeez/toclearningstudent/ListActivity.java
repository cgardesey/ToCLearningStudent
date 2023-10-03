package com.prepeez.toclearningstudent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.prepeez.toclearningstudent.adapter.ListAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView editTextSearch;
    ArrayList<String> institutions;
    ListAdapter listAdapter;
    String title = "";
    public static JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        institutions = new ArrayList<>();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras.containsKey("Title")) {
            title = extras.getString("Title");
        }

//        institutions.add("KNUST");
//        institutions.add("UG");
//        institutions.add("UCC");
//        institutions.add("Ghana Telecom University");
//        institutions.add("Home Economics");
//        institutions.add("GARDINST");
//        institutions.add("APP CONSULT");

        Iterator iterator = jsonObject.keys();
        while(iterator.hasNext()){
            String key = (String)iterator.next();

            institutions.add(key);
        }


        recyclerView = findViewById(R.id.recyclerView);
        editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.setText( title);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdapter = new ListAdapter(institutions, title);

        recyclerView.setAdapter(listAdapter);


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
            startActivity(new Intent(ListActivity.this, ChatActivity.class));
            return true;
        } else if (itemId == R.id.sign_out_menu) {
            AuthUI.getInstance().signOut(this);
            Toast.makeText(this, "Signed out!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.sign_register) {
            startActivity(new Intent(ListActivity.this, AccountActivity.class));
            return true;
        } else if (itemId == R.id.action_certifications) {
            startActivity(new Intent(ListActivity.this, CertificationsActivity.class));

            return true;
        } else if (itemId == R.id.action_about_us) {
            startActivity(new Intent(ListActivity.this, AboutUsActivity.class));

            return true;
        } else if (itemId == R.id.action_faqs) {
            startActivity(new Intent(ListActivity.this, FaqsActivity.class));

            startActivity(new Intent(ListActivity.this, SubscriptionActivity.class));

            return true;
        } else if (itemId == R.id.action_subscribe) {
            startActivity(new Intent(ListActivity.this, SubscriptionActivity.class));

            return true;
        } else if (itemId == android.R.id.home) {//onBackPressed();
            Intent i = new Intent(ListActivity.this, LevelActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}