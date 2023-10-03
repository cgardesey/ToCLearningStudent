package com.prepeez.toclearningstudent;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.prepeez.toclearningstudent.adapter.PayOptionsAdapter;
import com.prepeez.toclearningstudent.pojo.PayOption;
import com.prepeez.toclearningstudent.utils.DividerItemDecoration;

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


public class SubscriptionActivity extends AppCompatActivity {

    static RecyclerView mRecyclerView;
    static ProgressBar pBar;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        context = this;

        mRecyclerView = findViewById(R.id.recyclerView);
        pBar = findViewById(R.id.pBar);
        //MyPost post = (MyPost) new MyPost().execute();
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        PostRequest post = new PostRequest();
        post.execute("invoice/payoptions");

    }

    public static class PostRequest extends AsyncTask<String,Void,String> {

        final static String baseUrl = "https://app.slydepay.com.gh/api/merchant/";

        static String response;
        static String relativeUrl;

        @Override
        protected void onPreExecute() {
            pBar.setVisibility(View.VISIBLE);
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
                        jsonObject.put("amount", 2.50);
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
            pBar.setVisibility(View.GONE);
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
                                ArrayList<PayOption> payOptions = new ArrayList<PayOption>();
                                for (int i = 0; i < length; i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    PayOption payOption = new PayOption();

                                    payOption.setName(jsonObject.getString("name"));
                                    payOption.setShortName(jsonObject.getString("shortName"));
                                    //payOption.setMaximumAmount(jsonObject.getInt("maximumAmount"));
                                    payOption.setActive(jsonObject.getBoolean("active"));
                                    //payOption.setReason(jsonObject.getString("reason"));
                                    payOption.setLogoUrl(jsonObject.getString("logourl"));

                                    if (payOption.isActive()) {
                                        payOptions.add(payOption);
                                    }
                                }
                                mRecyclerView.setAdapter(new PayOptionsAdapter(payOptions));

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
                        Toast.makeText(context, json.getString("errorMessage"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

