package com.prepeez.toclearningstudent.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.prepeez.toclearningstudent.R;

/**
 * Created by 2CLearning on 2/8/2018.
 */

public class DateViewHolder extends RecyclerView.ViewHolder {
    TextView date;
    public DateViewHolder(View v) {
        super(v);
        date = itemView.findViewById(R.id.date);
    }
}
