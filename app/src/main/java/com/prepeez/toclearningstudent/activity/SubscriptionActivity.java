package com.prepeez.toclearningstudent.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.adapter.SubscriptionAdapter;
import com.prepeez.toclearningstudent.pojo.Subscription;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.prepeez.toclearningstudent.constants.Const.randomAlphaNumeric;


public class SubscriptionActivity extends AppCompatActivity {

    static RecyclerView mRecyclerView;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        context = this;

        //MyPost post = (MyPost) new MyPost().execute();
        mRecyclerView = findViewById(R.id.recyclerView);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        new SlydepayRequest().execute("invoice/payoptions");
    }

    public static class SlydepayRequest extends AsyncTask<String,Void,String> {

        final static String baseUrl = "https://app.slydepay.com.gh/api/merchant/";

        static String response;
        static String relativeUrl;


        private ProgressDialog asyncPd;

        @Override
        protected void onPreExecute() {
            asyncPd = new ProgressDialog(context);
            asyncPd.setTitle("Loading");
            asyncPd.setMessage("Please wait...");
            asyncPd.setCancelable(false);
            asyncPd.setIndeterminate(true);

            asyncPd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            response = null;
            relativeUrl = strings[0];
            try {
                URL url = new URL(baseUrl + relativeUrl); //Enter URL here
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                httpURLConnection.connect();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("emailOrMobileNumber", context.getResources().getString(R.string.EMAIL_OR_MOBILE_NUMBER));
                jsonObject.put("merchantKey", context.getResources().getString(R.string.MERCHANT_KEY));

                switch (relativeUrl)
                {
                    case "invoice/create":
                        double amt = Double.parseDouble(strings[1]);
                        jsonObject.put("amount", amt);
                        jsonObject.put("orderCode", strings[2]);
//                        jsonObject.put("orderCode", 2.50);
//                        jsonObject.put("sendInvoice", 2.50);
//                        jsonObject.put("payOption", 2.50);
//                        jsonObject.put("customerName", 2.50);
//                        jsonObject.put("customerMobileNumber", 2.50);
//                        jsonObject.put("amount", 2.50);
//                        jsonObject.put("amount", 2.50);
                        break;
                    default:
                        break;
                }

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(jsonObject.toString());
                wr.flush();
                wr.close();

                DataInputStream inputStream = new DataInputStream(httpURLConnection.getInputStream());
                response = inputStream.readLine();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            asyncPd.dismiss();

            PreferenceManager
                    .getDefaultSharedPreferences(context)
                    .edit()
                    .putString("SUBSCRIPTION_KEY", randomAlphaNumeric(12))
                    .apply();

            if (response != null && !response.equals("")) {

                try {
                    JSONObject json = new JSONObject(response);
                    boolean request_succeeded = json.getBoolean("success");

                    if (request_succeeded) {
                        JSONArray jsonArray = json.getJSONArray("result");

                        switch (relativeUrl)
                        {
                            case "invoice/payoptions":
                                int length = jsonArray.length();
                                ArrayList<Subscription> subscriptions = new ArrayList<Subscription>();
                                for (int i = 0; i < length; i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    Subscription subscription = new Subscription();

                                    subscription.setName(jsonObject.getString("name"));
                                    subscription.setShortName(jsonObject.getString("shortName"));
                                    //subscription.setMaximumAmount(jsonObject.getInt("maximumAmount"));
                                    subscription.setActive(jsonObject.getBoolean("active"));
                                    //subscription.setReason(jsonObject.getString("reason"));
                                    subscription.setLogoUrl(jsonObject.getString("logourl"));

                                    if (subscription.isActive()) {
                                        subscriptions.add(subscription);
                                    }
                                }
                                mRecyclerView.setAdapter(new SubscriptionAdapter(subscriptions));

                                break;
                            case "invoice/create":
                                    Log.d("wotiriso", response);

                                break;
                            default:
                                break;
                        }


                    }
                    else {
                        Log.d("wotiriso", "Error");
                        Toast.makeText(context, "Subscription successfull!", Toast.LENGTH_SHORT).show();
                        ((Activity)context).finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

