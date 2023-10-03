package com.prepeez.toclearningstudent.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.adapter.ChooseAccessTypeAdapter;
import com.prepeez.toclearningstudent.pojo.AccessType;

import java.util.ArrayList;

public class ChooseAccessTypeActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private ArrayList<AccessType> accessTypes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_access_type);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);

        ChooseAccessTypeAdapter chooseAccessTypeAdapter = new ChooseAccessTypeAdapter(getAccessTypes());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                new LinearLayoutManager(this).getOrientation());
        recyclerView.setAdapter(chooseAccessTypeAdapter);
        //recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private ArrayList<AccessType> getAccessTypes() {
        accessTypes.clear();
        accessTypes.add(new AccessType(1, "Access only", "sec", "Per day"));
        accessTypes.add(new AccessType(3, "Access with data", "sec", "Per day"));
        accessTypes.add(new AccessType(14, "Access only", "sec", "Per week"));
        accessTypes.add(new AccessType(27, "Access with data", "sec", "Per week"));
accessTypes.add(new AccessType(59, "Access only", "sec", "Per month"));
        accessTypes.add(new AccessType(119, "Access with data", "sec", "Per month"));

        return accessTypes;
    }
}
