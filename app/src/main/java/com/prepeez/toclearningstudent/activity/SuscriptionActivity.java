package com.prepeez.toclearningstudent.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.adapter.SuscriptionAdapter;
import com.prepeez.toclearningstudent.pojo.Certification;
import com.prepeez.toclearningstudent.pojo.Suscription;

import java.util.ArrayList;

public class SuscriptionActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private ArrayList<Suscription> suscriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscription);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);

        SuscriptionAdapter SuscriptionAdapter = new SuscriptionAdapter(getSuscriptions());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                new LinearLayoutManager(this).getOrientation());
        recyclerView.setAdapter(SuscriptionAdapter);
        //recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private ArrayList<Suscription> getSuscriptions() {
        suscriptions.clear();
        suscriptions.add(new Suscription("ȼ10", "Sarter", "sec"));
        suscriptions.add(new Suscription("ȼ20", "Star", "sec"));
        suscriptions.add(new Suscription("ȼ30", "Supreme", "sec"));
        suscriptions.add(new Suscription("ȼ40", "Supper", "sec"));
        suscriptions.add(new Suscription("ȼ100", "Unlimited", "sec"));

        return suscriptions;
    }
}
