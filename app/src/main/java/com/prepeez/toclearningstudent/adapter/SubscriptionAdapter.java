package com.prepeez.toclearningstudent.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.pojo.Subscription;

import java.util.ArrayList;

import static com.prepeez.toclearningstudent.activity.SubscriptionActivity.*;
import static com.prepeez.toclearningstudent.adapter.ChooseAccessTypeAdapter.clickedAccessType;

/**
 * Created by Nana on 9/11/2017.
 */

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder>  {


   ArrayList <Subscription> subscriptions;
    private Context mContext;

    public SubscriptionAdapter(ArrayList<Subscription> subscriptions)
    {
        this.subscriptions = subscriptions;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_subscription,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Subscription subscription = subscriptions.get(position);
        holder.subscription.setText(String.valueOf(position+ 1) + ". " + subscription.getName());
        holder.subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String amt = String.format("%.2f", clickedAccessType.getAmount() + 1);
                new SlydepayRequest().execute("invoice/create", amt, "123456787654");
            }
        });
    }

    @Override
    public int getItemCount() {
        return  subscriptions.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        int itemPosition = -1;
        Button subscription;
        private Context mContext;
        public void onAttach(final Activity activity) {
            mContext = activity;
        }

        public ViewHolder(View view)
        {
         super(view);

            subscription = view.findViewById(R.id.subscription);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

        }
    }
}
