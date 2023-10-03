package com.prepeez.toclearningstudent.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.activity.OldSubscriptionActivity;
import com.prepeez.toclearningstudent.pojo.PayOption;

import java.util.ArrayList;

/**
 * Created by Nana on 9/11/2017.
 */

public class PayOptionsAdapter extends RecyclerView.Adapter<PayOptionsAdapter.ViewHolder>  {


   ArrayList <PayOption> payOptions;
    private Context mContext;

    public PayOptionsAdapter(ArrayList<PayOption> payOptions)
    {
        this.payOptions = payOptions;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pay_options,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final PayOption payOption = payOptions.get(position);
        holder.payOption.setText(String.valueOf(position+ 1) + ". " + payOption.getName());
        holder.payOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OldSubscriptionActivity.PostRequest post = new OldSubscriptionActivity.PostRequest();
                post.execute("invoice/create", "2.50", "123456787654");
            }
        });
    }

    @Override
    public int getItemCount() {
        return  payOptions.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        int itemPosition = -1;
        TextView payOption;
        private Context mContext;
        public void onAttach(final Activity activity) {
            mContext = activity;
        }

        public ViewHolder(View view)
        {
         super(view);

            payOption = view.findViewById(R.id.payOption);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

        }
    }
}
