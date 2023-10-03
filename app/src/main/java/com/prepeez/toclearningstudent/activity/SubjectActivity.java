package com.prepeez.toclearningstudent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.adapter.SubjectAdapter;
import com.prepeez.toclearningstudent.pojo.Subject;

import java.util.ArrayList;


public class SubjectActivity extends AppCompatActivity {
    private ArrayList<Subject> subjects = new ArrayList<>();
    private String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            subject = this.getIntent().getStringExtra("subject");
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SubjectAdapter subjectAdapter = new SubjectAdapter(getsubjects(), this, SubjectActivity.this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                new LinearLayoutManager(this).getOrientation());
        recyclerView.setAdapter(subjectAdapter);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private ArrayList<Subject> getsubjects() {

        switch (subject) {
            case "Mathematics":
                subjects.add(new Subject(R.drawable.screenshot_1, "Maths"));
                subjects.add(new Subject(R.drawable.screenshot_1, "English"));
                subjects.add(new Subject(R.drawable.screenshot_1, "Science"));
                subjects.add(new Subject(R.drawable.screenshot_1, "ICT"));
                subjects.add(new Subject(R.drawable.screenshot_1, "Maths"));
                subjects.add(new Subject(R.drawable.screenshot_1, "Technical Skills"));

            default:
                subjects.add(new Subject(R.drawable.screenshot_1, "Lecture 1: The Breadboard (Part 1)"));
                subjects.add(new Subject(R.drawable.screenshot_1, "Lecture 2: The Breadboard (Part 2)"));
                subjects.add(new Subject(R.drawable.screenshot_2, "Arduino IDE"));
        }
        return subjects;
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
            startActivity(new Intent(SubjectActivity.this, ChatActivity.class));
            return true;
        } else if (itemId == R.id.sign_out_menu) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Signed out!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.sign_register) {
            startActivity(new Intent(SubjectActivity.this, AccountActivity.class));
            return true;
        } else if (itemId == R.id.action_certifications) {
            startActivity(new Intent(SubjectActivity.this, CertificationsActivity.class));

            return true;
        } else if (itemId == R.id.action_about_us) {
            startActivity(new Intent(SubjectActivity.this, AboutUsActivity.class));

            return true;
        } else if (itemId == R.id.action_faqs) {
            startActivity(new Intent(SubjectActivity.this, FaqsActivity.class));
            return true;
        } else if (itemId == R.id.action_subscribe) {
            startActivity(new Intent(SubjectActivity.this, OldSubscriptionActivity.class));
            return true;
        } else if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
