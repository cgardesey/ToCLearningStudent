package com.prepeez.toclearningstudent.adapter;

/**
 * Created by Nana on 11/10/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prepeez.toclearningstudent.activity.ListActivity;
import com.prepeez.toclearningstudent.R;

import org.json.JSONException;

import java.util.ArrayList;

import static com.prepeez.toclearningstudent.activity.LevelActivity.mainJsonObject;
import static com.prepeez.toclearningstudent.activity.ListActivity.jsonObject;

/**
 * Created by Belal on 6/6/2017.
 */

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {

    ArrayList<String> names;
    Activity activity;

    public LevelAdapter(ArrayList<String> names, Activity activity) {
        this.names = names;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_list, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewName.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

      TextView textViewName;
      CardView cardView;
      public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            textViewName = itemView.findViewById(R.id.textViewName);
            itemView.setOnClickListener(this);
        }

      @Override
      public void onClick(View view) {
          //activity.finish();
          try {
              jsonObject = mainJsonObject.getJSONObject(textViewName.getText().toString());
          } catch (JSONException e) {
              e.printStackTrace();
          }

          Intent intent  = new Intent(view.getContext(), ListActivity.class);
          intent.putExtra("Title", textViewName.getText().toString());
          view.getContext().startActivity(intent);
      }

      @Override
      public boolean onLongClick(View view) {
          return false;
      }
  }
}
