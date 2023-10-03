package com.prepeez.toclearningstudent.adapter;

/**
 * Created by Nana on 11/10/2017.
 */

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prepeez.toclearningstudent.activity.ListActivity;
import com.prepeez.toclearningstudent.activity.QuizActivity;
import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.activity.VideoActivity;

import org.json.JSONException;

import java.util.ArrayList;

import static com.prepeez.toclearningstudent.activity.ListActivity.jsonObject;

/**
 * Created by Belal on 6/6/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private static final String YOUR_DIALOG_TAG ="" ;
    private ArrayList<String> names;

    String title = "";

    public ListAdapter(ArrayList<String> names, String title) {
        this.names = names;
        this.title = title;
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

  public   class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView textViewName;

      public   ViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            itemView.setOnClickListener(this);
        }

      @Override
      public void onClick(View view) {

          boolean isPasco = isInteger(textViewName.getText().toString());
          boolean isLiveLec = textViewName.getText().toString().equals("Live Lecture");
          boolean isRecLec = textViewName.getText().toString().equals("Recorded Lecture");



          if (isPasco) {
              Intent intent  = new Intent(view.getContext(), QuizActivity.class);
              intent.putExtra("Title", title + "/" + textViewName.getText().toString());
              view.getContext().startActivity(intent);
          }
          else if (isLiveLec || isRecLec) {
              Intent intent  = new Intent(view.getContext(), VideoActivity.class);
              intent.putExtra("Title", title + "/" + textViewName.getText().toString());
              view.getContext().startActivity(intent);
          }
          else {
              try {
                  jsonObject = jsonObject.getJSONObject(textViewName.getText().toString());
              } catch (JSONException e) {
                  e.printStackTrace();
              }

              Intent intent  = new Intent(view.getContext(), ListActivity.class);
              intent.putExtra("Title", title + "/" + textViewName.getText().toString());
              view.getContext().startActivity(intent);
          }


      }

      @Override
      public boolean onLongClick(View view) {
          return false;
      }

      private boolean isInteger(String myString) {
          try {
              Integer.parseInt(myString);
              return true;
          } catch (NumberFormatException e) {
              return false;
          }
      }
  }
}
