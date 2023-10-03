package com.prepeez.toclearningstudent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.prepeez.toclearningstudent.R;

import static com.prepeez.toclearningstudent.activity.ChatActivity.PREF_JSON_KEY;
import static com.prepeez.toclearningstudent.activity.ChatActivity.PREF_MSG_LENGTH_KEY;

public class ChooseLocationActivity extends AppCompatActivity {

    private RadioGroup radioLocationGroup;
    private RadioButton radioLocationButton;

    private Button back, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);

        radioLocationGroup = findViewById(R.id.radioLocationGroup);

        back = findViewById(R.id.back);
        next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("cyril", "next pressed");
                // get selected radio button from radioGroup
                int selectedId = radioLocationGroup.getCheckedRadioButtonId();
                Log.d("cyril", String.valueOf(selectedId));
                if (selectedId != -1) {
                    // find the radiobutton by returned id
                    radioLocationButton = (RadioButton) findViewById(selectedId);

                    String location = radioLocationButton.getText().toString();

                    PreferenceManager
                            .getDefaultSharedPreferences(ChooseLocationActivity.this)
                            .edit()
                            .putString("LOCATION_KEY", location)
                            .apply();
                    startActivity(new Intent(ChooseLocationActivity.this, LevelActivity.class));
                    finish();//0205690370
                } else {
                    Toast.makeText(ChooseLocationActivity.this, "Please select a location.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

    }
}
