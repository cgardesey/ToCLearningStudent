package com.prepeez.toclearningstudent.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.pojo.Certification;

import java.util.ArrayList;

/**
 * Created by Nana on 9/11/2017.

 */
public class CertificationAdapter extends RecyclerView.Adapter<CertificationAdapter.ViewHolder>  {


   ArrayList <Certification> events;
    private Context mContext;

    public CertificationAdapter(ArrayList<Certification> events)
    {
        this.events = events;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_certification,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Certification cur_event = events.get(position);
        String institute = cur_event.getInstitution();
        holder.institutename.setText(institute);
        holder.dateposted.setText(cur_event.getStartDate() + " - " + cur_event.getEndDate());
        mContext =   holder.dateposted.getContext();
        holder.titleofcerticate.setText(cur_event.getCertification());}
    @Override
    public int getItemCount() {
        return  events.size();
    }
    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        TextView titleofcerticate, institutename,dateposted;
        ImageView deletepost;

        public ViewHolder(View view)
        {
         super(view);
            deletepost = view.findViewById(R.id.close);
            titleofcerticate = view.findViewById(R.id.phoneNumber);
            dateposted = view.findViewById(R.id.institutedate);
            institutename = view.findViewById(R.id.institutename);
            deletepost.setOnClickListener(this);
          //  itemView.setOnLongClickListener(this);
        }
//        @Override
//        public void onClick(View v) {
//            int pos = getAdapterPosition();
//
//            // check if item still exists
//            if(pos != RecyclerView.NO_POSITION){
//                courses.remove(pos);
//                notifyDataSetChanged();
//                //  RvDataItem clickedDataItem = dataItems.get(pos);
//                //    Toast.makeText(v.getContext(), "You clicked " + String.valueOf(courses.get(pos)) , Toast.LENGTH_SHORT).show();
//            }
//           // snackbar.show();
//        }

        @Override
        public void onClick(final View v) {
            Snackbar snackbar=     Snackbar.make(v,"Delete Entry?", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("YES", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        events.remove(pos);
                        notifyDataSetChanged();
                        //  RvDataItem clickedDataItem = dataItems.get(pos);
                        //    Toast.makeText(v.getContext(), "You clicked " + String.valueOf(courses.get(pos)) , Toast.LENGTH_SHORT).show();
                    }

                    notifyDataSetChanged();
                }
            });
            snackbar.show();
        }
    }
}
