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
import com.prepeez.toclearningstudent.pojo.Suscription;

import java.util.ArrayList;

/**
 * Created by Nana on 9/11/2017.
 */

public class SuscriptionAdapter extends RecyclerView.Adapter<SuscriptionAdapter.ViewHolder>  {


   ArrayList <Suscription> Suscriptions;
    private Context mContext;

    public SuscriptionAdapter(ArrayList<Suscription> Suscriptions)
    {
        this.Suscriptions = Suscriptions;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_suscription, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Suscription Suscription = Suscriptions.get(position);
        holder.amonunt.setText(Suscription.getAmount());
        holder.start_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return  Suscriptions.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        int itemPosition = -1;
        TextView amonunt, primary_description, start_now;
        private Context mContext;
        public void onAttach(final Activity activity) {
            mContext = activity;
        }

        public ViewHolder(View view)
        {
         super(view);

            amonunt = view.findViewById(R.id.amount);
            primary_description = view.findViewById(R.id.primary_description);
            start_now = view.findViewById(R.id.start_now);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

        }
    }
}
