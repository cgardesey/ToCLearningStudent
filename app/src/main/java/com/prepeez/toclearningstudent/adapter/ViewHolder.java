package com.prepeez.toclearningstudent.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.utils.DividerItemDecoration;

public class ViewHolder extends RecyclerView.ViewHolder {

    private RecyclerViewAdapter recyclerViewHolder;
    TextView optionalCoursesHeader;
    FrameLayout frameLayout;
    LinearLayout course;
    TextView creditHourHeader;
    TextView courseHeader;
    TextView courseTitle;
    TextView creditHr;
    RecyclerView recyclerView = null;
    LinearLayout linearLayout;
    RecyclerViewAdapter recyclerViewAdapter;
    RelativeLayout add, arrow;
    LinearLayout header, footer;
    TextView min, max, totalCredit;

    boolean arrowPointingDown = true;

    public ViewHolder(RecyclerViewAdapter recyclerViewHolder, View v) {
        super(v);
        this.recyclerViewHolder = recyclerViewHolder;

        optionalCoursesHeader = v.findViewById(R.id.optionalCoursesHeader);
        frameLayout = v.findViewById(R.id.frameLayout);
        course = v.findViewById(R.id.course);
        courseHeader = v.findViewById(R.id.courseHeader);
        creditHourHeader = v.findViewById(R.id.creditHourHeader);

        courseTitle = v.findViewById(R.id.courseTitle);
        creditHr = v.findViewById(R.id.creditHr);

        add = v.findViewById(R.id.add);
        arrow = v.findViewById(R.id.arrow);

        linearLayout = v.findViewById(R.id.linearLayout);
        header = v.findViewById(R.id.header);
        footer = v.findViewById(R.id.footer);
        min = v.findViewById(R.id.min);
        max = v.findViewById(R.id.max);
        totalCredit = v.findViewById(R.id.totalCredit);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerViewHolder.activity));
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerViewHolder.activity));
    }
}
