package com.prepeez.toclearningstudent.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.activity.SubscriptionActivity;
import com.prepeez.toclearningstudent.pojo.AccessType;

import java.util.ArrayList;

/**
 * Created by Nana on 9/11/2017.
 */

public class ChooseAccessTypeAdapter extends RecyclerView.Adapter<ChooseAccessTypeAdapter.ViewHolder>  {


   ArrayList <AccessType> accessTypes;
    private Context mContext;

    public static AccessType clickedAccessType;

    public ChooseAccessTypeAdapter(ArrayList<AccessType> accessTypes)
    {
        this.accessTypes = accessTypes;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view  = LayoutInflater.from(mContext).inflate(R.layout.list_item_choose_access_type, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final AccessType accessType = accessTypes.get(position);
        String amt = "ȼ" + String.valueOf((int) accessType.getAmount());
        holder.amonunt.setText(amt);
        holder.duration.setText(accessType.getDuration());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedAccessType = accessType;
                mContext.startActivity(new Intent(mContext, SubscriptionActivity.class));
                ((Activity)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return  accessTypes.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        int itemPosition = -1;
        TextView amonunt, primary_description, start_now, duration;
        private Context mContext;
        public void onAttach(final Activity activity) {
            mContext = activity;
        }

        public ViewHolder(View view)
        {
         super(view);

            amonunt = view.findViewById(R.id.amount);
            primary_description = view.findViewById(R.id.primary_description);
            duration = view.findViewById(R.id.duration);
            start_now = view.findViewById(R.id.start_now);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

        }
    }
}
