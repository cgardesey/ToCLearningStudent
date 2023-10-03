package com.prepeez.toclearningstudent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.prepeez.toclearningstudent.adapter.TopicAdapter;
import com.prepeez.toclearningstudent.pojo.Topic;

import java.util.ArrayList;


public class TopicActivity extends AppCompatActivity {
    private ArrayList<Topic> topics = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TopicAdapter topicAdapter = new TopicAdapter(getTopics(), this, TopicActivity.this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                new LinearLayoutManager(this).getOrientation());
        recyclerView.setAdapter(topicAdapter);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private ArrayList<Topic> getTopics() {

        topics.add(new Topic(R.drawable.screenshot_1, "Primary 1"));
        topics.add(new Topic(R.drawable.screenshot_1, "Primary 2"));
        topics.add(new Topic(R.drawable.screenshot_1, "Primary 3"));
        topics.add(new Topic(R.drawable.screenshot_1, "Primary 4"));
        topics.add(new Topic(R.drawable.screenshot_1, "Primary 5"));
        topics.add(new Topic(R.drawable.screenshot_1, "Primary 6"));
        topics.add(new Topic(R.drawable.screenshot_1, "JHS 1"));
        topics.add(new Topic(R.drawable.screenshot_1, "JHS 2"));
        topics.add(new Topic(R.drawable.screenshot_1, "JHS 3"));
        return topics;
    }
}
