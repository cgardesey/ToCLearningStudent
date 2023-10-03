package com.prepeez.toclearningstudent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.prepeez.toclearningstudent.R;

import java.util.HashMap;
import java.util.Map;

import static com.prepeez.toclearningstudent.activity.ChatActivity.DEFAULT_MSG_LENGTH;
import static com.prepeez.toclearningstudent.activity.ChatActivity.JSON_KEY;
import static com.prepeez.toclearningstudent.activity.ChatActivity.MSG_LENGTH_KEY;
import static com.prepeez.toclearningstudent.activity.ChatActivity.PREF_JSON_KEY;
import static com.prepeez.toclearningstudent.activity.ChatActivity.PREF_MSG_LENGTH_KEY;

//import com.samsung.spensdk3.example.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";


    private ProgressBar intro_progress;
    private RelativeLayout activity_intro;

    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    Long msg_length = 0L;
    String json = "";

    public static final String DEFAULT_JSON = "{\n" +
            "    \"Undergraduate Level\":\n" +
            "\t{\n" +
            "\t\t\"KNUST\":\n" +
            "\t\t{\n" +
            "\t\t\t\"College of Engineering\":\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"Computer Engineering\":\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"Year 1\":\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"Semester 1\":\n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"CREDIT HOURS\":\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"min\":6,\n" +
            "\t\t\t\t\t\t\t\t\"max\":9\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"COMPULSORY COURSES\":true,\n" +
            "\t\t\t\t\t\t\t\"(CLASS) Software Engineering 1\":\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"NUMBER OF CREDIT HOURS\":3,\n" +
            "\t\t\t\t\t\t\t\t\"Past Questions\":\n" +
            "\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\"2006\":{\"2006\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2007\":{\"2007\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2008\":{\"2008\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2009\":{\"2009\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2010\":{\"2010\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2011\":{\"2011\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2012\":{\"2012\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2013\":{\"2013\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2014\":{\"2014\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2015\":{\"2015\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2016\":{\"2016\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2017\":{\"2017\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2018\":{\"2018\":true}\n" +
            "\t\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\t\"C++ Programing\":\n" +
            "\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\"Live Lecture\":\n" +
            "\t\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 1\": \"Data types\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 2\": \"Loops\", \n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 3\": \"Conditions\"\n" +
            "\t\t\t\t\t\t\t\t\t}, \n" +
            "\t\t\t\t\t\t\t\t\t\"Recorded Lecture\":\n" +
            "\t\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 1\": \"Data types\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 2\": \"Loops\", \n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 3\": \"Conditions\"\n" +
            "\t\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"(CLASS) Basic Electronics\":\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"NUMBER OF CREDIT HOURS\":3,\n" +
            "\t\t\t\t\t\t\t\t\"Past Questions\":\n" +
            "\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\"2006\":{\"2006\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2007\":{\"2007\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2008\":{\"2008\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2009\":{\"2009\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2010\":{\"2010\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2011\":{\"2011\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2012\":{\"2012\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2013\":{\"2013\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2014\":{\"2014\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2015\":{\"2015\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2016\":{\"2016\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2017\":{\"2017\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2018\":{\"2018\":true}\n" +
            "\t\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\t\"C++ Programing\":\n" +
            "\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\"Live Lecture\":\n" +
            "\t\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 1\": \"Data types\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 2\": \"Loops\", \n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 3\": \"Conditions\"\n" +
            "\t\t\t\t\t\t\t\t\t}, \n" +
            "\t\t\t\t\t\t\t\t\t\"Recorded Lecture\":\n" +
            "\t\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 1\": \"Data types\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 2\": \"Loops\", \n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 3\": \"Conditions\"\n" +
            "\t\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"OPTIONAL COURSES\":true,\n" +
            "\t\t\t\t\t\t\t\"(CLASS) Introduction to IT\":\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"NUMBER OF CREDIT HOURS\":3,\n" +
            "\t\t\t\t\t\t\t\t\"Past Questions\":\n" +
            "\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\"2006\":{\"2006\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2007\":{\"2007\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2008\":{\"2008\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2009\":{\"2009\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2010\":{\"2010\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2011\":{\"2011\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2012\":{\"2012\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2013\":{\"2013\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2014\":{\"2014\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2015\":{\"2015\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2016\":{\"2016\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2017\":{\"2017\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2018\":{\"2018\":true}\n" +
            "\t\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\t\"C++ Programing\":\n" +
            "\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\"Live Lecture\":\n" +
            "\t\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 1\": \"Data types\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 2\": \"Loops\", \n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 3\": \"Conditions\"\n" +
            "\t\t\t\t\t\t\t\t\t}, \n" +
            "\t\t\t\t\t\t\t\t\t\"Recorded Lecture\":\n" +
            "\t\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 1\": \"Data types\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 2\": \"Loops\", \n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 3\": \"Conditions\"\n" +
            "\t\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"(CLASS) Data Structures and Algorithms\":\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"NUMBER OF CREDIT HOURS\":3,\n" +
            "\t\t\t\t\t\t\t\t\"Past Questions\":\n" +
            "\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\"2006\":{\"2006\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2007\":{\"2007\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2008\":{\"2008\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2009\":{\"2009\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2010\":{\"2010\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2011\":{\"2011\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2012\":{\"2012\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2013\":{\"2013\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2014\":{\"2014\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2015\":{\"2015\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2016\":{\"2016\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2017\":{\"2017\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2018\":{\"2018\":true}\n" +
            "\t\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\t\"C++ Programing\":\n" +
            "\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\"Live Lecture\":\n" +
            "\t\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 1\": \"Data types\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 2\": \"Loops\", \n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 3\": \"Conditions\"\n" +
            "\t\t\t\t\t\t\t\t\t}, \n" +
            "\t\t\t\t\t\t\t\t\t\"Recorded Lecture\":\n" +
            "\t\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 1\": \"Data types\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 2\": \"Loops\", \n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 3\": \"Conditions\"\n" +
            "\t\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"Semester 2\":\n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"CREDIT HOURS\":\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"min\":19,\n" +
            "\t\t\t\t\t\t\t\t\"max\":21\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\"(CLASS) Software Engineering 2\":\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"NUMBER OF CREDIT HOURS\":3,\n" +
            "\t\t\t\t\t\t\t\t\"Past Questions\":\n" +
            "\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\"2006\":{\"2006\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2007\":{\"2007\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2008\":{\"2008\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2009\":{\"2009\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2010\":{\"2010\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2011\":{\"2011\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2012\":{\"2012\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2013\":{\"2013\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2014\":{\"2014\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2015\":{\"2015\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2016\":{\"2016\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2017\":{\"2017\":true},\n" +
            "\t\t\t\t\t\t\t\t\t\"2018\":{\"2018\":true}\n" +
            "\t\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t\t\"C++ Programing\":\n" +
            "\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\"Live Lecture\":\n" +
            "\t\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 1\": \"Data types\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 2\": \"Loops\", \n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 3\": \"Conditions\"\n" +
            "\t\t\t\t\t\t\t\t\t}, \n" +
            "\t\t\t\t\t\t\t\t\t\"Recorded Lecture\":\n" +
            "\t\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 1\": \"Data types\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 2\": \"Loops\", \n" +
            "\t\t\t\t\t\t\t\t\t\t\"Lecture 3\": \"Conditions\"\n" +
            "\t\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t}\t\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t},\n" +
            "\t\"Secondary Level\":\n" +
            "\t{\n" +
            "\t\t\"PRESEC - Legon\":\n" +
            "\t\t{\n" +
            "\t\t\t\"Science\":\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"Elective Subjects\":\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"(CLASS) Elective Mathematics\":\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"SHS 1\":\n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"Live Lecture\":\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"Lecture 1\": \"Trigonometry\",\n" +
            "\t\t\t\t\t\t\t\t\"Lecture 2\": \"Deferential Calculus\", \n" +
            "\t\t\t\t\t\t\t\t\"Lecture 3\": \"Integral Calculus\"\n" +
            "\t\t\t\t\t\t\t}, \n" +
            "\t\t\t\t\t\t\t\"Recorded Lecture\":\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"Lecture 1\": \"Trigonometry\",\n" +
            "\t\t\t\t\t\t\t\t\"Lecture 2\": \"Deferential Calculus\", \n" +
            "\t\t\t\t\t\t\t\t\"Lecture 3\": \"Integral Calculus\"\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"Past Questions\":\n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"2006\":{\"2006\":true},\n" +
            "\t\t\t\t\t\t\t\"2007\":{\"2007\":true},\n" +
            "\t\t\t\t\t\t\t\"2008\":{\"2008\":true},\n" +
            "\t\t\t\t\t\t\t\"2009\":{\"2009\":true},\n" +
            "\t\t\t\t\t\t\t\"2010\":{\"2010\":true},\n" +
            "\t\t\t\t\t\t\t\"2011\":{\"2011\":true},\n" +
            "\t\t\t\t\t\t\t\"2012\":{\"2012\":true},\n" +
            "\t\t\t\t\t\t\t\"2013\":{\"2013\":true},\n" +
            "\t\t\t\t\t\t\t\"2014\":{\"2014\":true},\n" +
            "\t\t\t\t\t\t\t\"2015\":{\"2015\":true},\n" +
            "\t\t\t\t\t\t\t\"2016\":{\"2016\":true},\n" +
            "\t\t\t\t\t\t\t\"2017\":{\"2017\":true},\n" +
            "\t\t\t\t\t\t\t\"2018\":{\"2018\":true}\n" +
            "\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t},\n" +
            "\n" +
            "\t\t\t\t\t\"(CLASS) Physics\":\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"SHS 1\":\n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"Live Lecture\":\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"Lecture 1\": \"Concepts of matter\",\n" +
            "\t\t\t\t\t\t\t\t\"Lecture 2\": \"Fundamental and derived quantities and units\", \n" +
            "\t\t\t\t\t\t\t\t\"Lecture 3\": \"Position, distance and displacement\"\n" +
            "\t\t\t\t\t\t\t}, \n" +
            "\t\t\t\t\t\t\t\"Recorded Lecture\":\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"Lecture 1\": \"Concepts of matter\",\n" +
            "\t\t\t\t\t\t\t\t\"Lecture 2\": \"Fundamental and derived quantities and units\", \n" +
            "\t\t\t\t\t\t\t\t\"Lecture 3\": \"Position, distance and displacement\"\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"Past Questions\":\n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"2006\":{\"2006\":true},\n" +
            "\t\t\t\t\t\t\t\"2007\":{\"2007\":true},\n" +
            "\t\t\t\t\t\t\t\"2008\":{\"2008\":true},\n" +
            "\t\t\t\t\t\t\t\"2009\":{\"2009\":true},\n" +
            "\t\t\t\t\t\t\t\"2010\":{\"2010\":true},\n" +
            "\t\t\t\t\t\t\t\"2011\":{\"2011\":true},\n" +
            "\t\t\t\t\t\t\t\"2012\":{\"2012\":true},\n" +
            "\t\t\t\t\t\t\t\"2013\":{\"2013\":true},\n" +
            "\t\t\t\t\t\t\t\"2014\":{\"2014\":true},\n" +
            "\t\t\t\t\t\t\t\"2015\":{\"2015\":true},\n" +
            "\t\t\t\t\t\t\t\"2016\":{\"2016\":true},\n" +
            "\t\t\t\t\t\t\t\"2017\":{\"2017\":true},\n" +
            "\t\t\t\t\t\t\t\"2018\":{\"2018\":true}\n" +
            "\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t},\n" +
            "\n" +
            "\t\t\t\t\t\"(CLASS) Chemistry\":\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"SHS 1\":\n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"Live Lecture\":\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"Lecture 1\": \"Structure of the Atom\",\n" +
            "\t\t\t\t\t\t\t\t\"Lecture 2\": \"Periodic Chemistry\", \n" +
            "\t\t\t\t\t\t\t\t\"Lecture 3\": \"Bonding\"\n" +
            "\t\t\t\t\t\t\t}, \n" +
            "\t\t\t\t\t\t\t\"Recorded Lecture\":\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"Lecture 1\": \"Structure of the Atom\",\n" +
            "\t\t\t\t\t\t\t\t\"Lecture 2\": \"Periodic Chemistry\", \n" +
            "\t\t\t\t\t\t\t\t\"Lecture 3\": \"Bonding\"\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"Past Questions\":\n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"2006\":{\"2006\":true},\n" +
            "\t\t\t\t\t\t\t\"2007\":{\"2007\":true},\n" +
            "\t\t\t\t\t\t\t\"2008\":{\"2008\":true},\n" +
            "\t\t\t\t\t\t\t\"2009\":{\"2009\":true},\n" +
            "\t\t\t\t\t\t\t\"2010\":{\"2010\":true},\n" +
            "\t\t\t\t\t\t\t\"2011\":{\"2011\":true},\n" +
            "\t\t\t\t\t\t\t\"2012\":{\"2012\":true},\n" +
            "\t\t\t\t\t\t\t\"2013\":{\"2013\":true},\n" +
            "\t\t\t\t\t\t\t\"2014\":{\"2014\":true},\n" +
            "\t\t\t\t\t\t\t\"2015\":{\"2015\":true},\n" +
            "\t\t\t\t\t\t\t\"2016\":{\"2016\":true},\n" +
            "\t\t\t\t\t\t\t\"2017\":{\"2017\":true},\n" +
            "\t\t\t\t\t\t\t\"2018\":{\"2018\":true}\n" +
            "\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"Core Subjects\":\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"(CLASS) Core Mathematics\":\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"Live Lecture\":\n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"Lecture 1\": \"Trigonometry\",\n" +
            "\t\t\t\t\t\t\t\"Lecture 2\": \"Deferential Calculus\", \n" +
            "\t\t\t\t\t\t\t\"Lecture 3\": \"Integral Calculus\"\n" +
            "\t\t\t\t\t\t}, \n" +
            "\t\t\t\t\t\t\"Recorded Lecture\":\n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"Lecture 1\": \"Trigonometry\",\n" +
            "\t\t\t\t\t\t\t\"Lecture 2\": \"Deferential Calculus\", \n" +
            "\t\t\t\t\t\t\t\"Lecture 3\": \"Integral Calculus\"\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"Past Questions\":\n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"2006\":{\"2006\":true},\n" +
            "\t\t\t\t\t\t\t\"2007\":{\"2007\":true},\n" +
            "\t\t\t\t\t\t\t\"2008\":{\"2008\":true},\n" +
            "\t\t\t\t\t\t\t\"2009\":{\"2009\":true},\n" +
            "\t\t\t\t\t\t\t\"2010\":{\"2010\":true},\n" +
            "\t\t\t\t\t\t\t\"2011\":{\"2011\":true},\n" +
            "\t\t\t\t\t\t\t\"2012\":{\"2012\":true},\n" +
            "\t\t\t\t\t\t\t\"2013\":{\"2013\":true},\n" +
            "\t\t\t\t\t\t\t\"2014\":{\"2014\":true},\n" +
            "\t\t\t\t\t\t\t\"2015\":{\"2015\":true},\n" +
            "\t\t\t\t\t\t\t\"2016\":{\"2016\":true},\n" +
            "\t\t\t\t\t\t\t\"2017\":{\"2017\":true},\n" +
            "\t\t\t\t\t\t\t\"2018\":{\"2018\":true}\n" +
            "\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t\t\n" +
            "\t},\n" +
            "\t\"Basic Level\":\n" +
            "\t{\n" +
            "\t\t\"Corpus Christ\":\n" +
            "\t\t{\n" +
            "\t\t\t\"(CLASS) Mathematics\":\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"JHS 1\":\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"Live Lecture\":\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"Indies\": \"Indies\",\n" +
            "\t\t\t\t\t\t\"Mensuration\":\"Mensuration\", \n" +
            "\t\t\t\t\t\t\"Quadratic Equations\":\"Quadratic Equations\"\n" +
            "\t\t\t\t\t}, \n" +
            "\t\t\t\t\t\"Recorded Lecture\":\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"Indies\": \"Indies\",\n" +
            "\t\t\t\t\t\t\"Mensuration\":\"Mensuration\", \n" +
            "\t\t\t\t\t\t\"Quadratic Equations\":\"Quadratic Equations\"\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"Past Questions\":\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"2006\":{\"2006\":true},\n" +
            "\t\t\t\t\t\"2007\":{\"2007\":true},\n" +
            "\t\t\t\t\t\"2008\":{\"2008\":true},\n" +
            "\t\t\t\t\t\"2009\":{\"2009\":true},\n" +
            "\t\t\t\t\t\"2010\":{\"2010\":true},\n" +
            "\t\t\t\t\t\"2011\":{\"2011\":true},\n" +
            "\t\t\t\t\t\"2012\":{\"2012\":true},\n" +
            "\t\t\t\t\t\"2013\":{\"2013\":true},\n" +
            "\t\t\t\t\t\"2014\":{\"2014\":true},\n" +
            "\t\t\t\t\t\"2015\":{\"2015\":true},\n" +
            "\t\t\t\t\t\"2016\":{\"2016\":true},\n" +
            "\t\t\t\t\t\"2017\":{\"2017\":true},\n" +
            "\t\t\t\t\t\"2018\":{\"2018\":true}\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t\t\n" +
            "\t},\n" +
            "\t\"Vocational\":\n" +
            "\t{\n" +
            "\t\t\"(CLASS) Fresh Yogurt Production\":\n" +
            "\t\t{\n" +
            "\t\t\t\"Live Lecture\":\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"Lecture 1\":\"Lecture 1\",\n" +
            "\t\t\t\t\"Lecture 2\":\"Lecture 1\", \n" +
            "\t\t\t\t\"Lecture 3\":\"Lecture 1\"\n" +
            "\t\t\t}, \n" +
            "\t\t\t\"Recorded Lecture\":\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"Lecture 1\":\"Lecture 1\",\n" +
            "\t\t\t\t\"Lecture 2\":\"Lecture 1\", \n" +
            "\t\t\t\t\"Lecture 3\":\"Lecture 1\"\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t}\n" +
            " }";


    private int progressStatus = 0;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        final ProgressBar pb = findViewById(R.id.pb);

        this.activity_intro= findViewById(R.id.activity_intro);

        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);

        // Define default config values. Defaults are used when fetched config values are not
        // available. Eg: if an error occurred fetching values from the server.
        msg_length = PreferenceManager.getDefaultSharedPreferences(this).getLong(PREF_MSG_LENGTH_KEY, DEFAULT_MSG_LENGTH);
//        Toast.makeText(this, PreferenceManager.getDefaultSharedPreferences(this).getString(PREF_JSON_KEY, DEFAULT_JSON), Toast.LENGTH_SHORT).show();
        json = PreferenceManager.getDefaultSharedPreferences(this).getString(PREF_JSON_KEY, DEFAULT_JSON);

        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put(MSG_LENGTH_KEY, msg_length);
        defaultConfigMap.put(JSON_KEY, json);
        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);
        fetchConfig();

                progressStatus = 0;

                /*
                    A Thread is a concurrent unit of execution. It has its own call stack for
                    methods being invoked, their arguments and local variables. Each application
                    has at least one thread running when it is started, the main thread,
                    in the main ThreadGroup. The runtime keeps its own threads
                    in the system thread group.
                */
                // Start the lengthy operation in a background thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(progressStatus < 100){
                            // Update the progress status
                            progressStatus +=1;

                            // Try to sleep the thread for 20 milliseconds
                            try{
                                Thread.sleep(40);
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }

                            // Update the progress bar
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    pb.setProgress(progressStatus);
                                }
                            });
                        }
                        if(progressStatus ==  100)
                        {
                            startActivity(new Intent(SplashScreenActivity.this, LevelActivity.class));
                            finish();
                            return;
                        }
                    }
                }).start(); // Start the operation

    }

    public void fetchConfig() {
        long cacheExpiration = 3600; // 1 hour in seconds
        // If developer mode is enabled reduce cacheExpiration to 0 so that each fetch goes to the
        // server. This should not be used in release builds.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Make the fetched config available
                        // via FirebaseRemoteConfig get<type> calls, e.g., getLong, getString.
                        mFirebaseRemoteConfig.activateFetched();

                        // Update the EditText length limit with
                        // the newly retrieved values from Remote Config.
                        applyRetrievedLengthLimit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // An error occurred when fetching the config.
                        Log.w(TAG, "Error fetching config", e);

                        // Update the EditText length limit with
                        // the newly retrieved values from Remote Config.
                        applyRetrievedLengthLimit();
                    }
                });
    }

    private void applyRetrievedLengthLimit() {
//        msg_length = mFirebaseRemoteConfig.getLong(MSG_LENGTH_KEY);
//        json = mFirebaseRemoteConfig.getString(JSON_KEY);

        Long rc_msg_length = mFirebaseRemoteConfig.getLong(MSG_LENGTH_KEY);
        String rc_json = mFirebaseRemoteConfig.getString(JSON_KEY);

        msg_length = rc_msg_length == 0L ? msg_length : rc_msg_length;
        json = rc_json.equals("") ? json : rc_json;

        PreferenceManager

                .getDefaultSharedPreferences(SplashScreenActivity.this)
                .edit()
                .putLong(PREF_MSG_LENGTH_KEY, msg_length)
                .putString(PREF_JSON_KEY, json)
                .apply();

    }
}
