package com.example.farmmanager.AnimalSection;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.Adapters.MilkResultsAdapter;
import com.example.farmmanager.Modals.MilkResultsModel;
import com.example.farmmanager.R;
import com.example.farmmanager.Urls.SessionManager;
import com.example.farmmanager.Urls.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MilkResults extends AppCompatActivity {

    RecyclerView recyclerView;
    List<MilkResultsModel> mData;
    MilkResultsAdapter adapter;
    TextView error_message_balance, no_message_balance, seeltecttime,totalss;
    SessionManager sessionManager;
    Urls urls;
    String getID,farmname;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milkresults);

        urls = new Urls();
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        farmname = user.get(SessionManager.FARMNAME);

        error_message_balance = findViewById(R.id.error_message_balance);
        no_message_balance = findViewById(R.id.no_message_balance);
        seeltecttime = findViewById(R.id.seeltecttime);
        totalss = findViewById(R.id.total);

        /*receive the selected time frame*/
        String selectedtime = getIntent().getStringExtra("time");
        seeltecttime.setText(selectedtime);
        /*check for the selected time frame and search against that and the current date*/
        switch (selectedtime) {
            case "Morning":
            case "Afternoon":
            case "Evening":
            case "":// for showing all the results
                loadMilkingResults(selectedtime);
                break;
        }


        recyclerView = findViewById(R.id.recyclerview_milking_results);
        mData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MilkResultsAdapter(this, mData);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        // SetOnRefreshListener on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                Clear();
                loadMilkingResults(selectedtime);
            }
        });

    }

    public void goBack(View view) {
        startActivity(new Intent(MilkResults.this, MilkActivity.class));
        finish();
    }

    private void loadMilkingResults(String selectedtime) {
        final ProgressDialog progressDialog = new ProgressDialog(MilkResults.this);
        progressDialog.setMessage("Loading results, please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOAD_MILKING_RESULTS,
                response -> {
                    progressDialog.dismiss();
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray tips = new JSONArray(response);
                        if (tips.length() == 0) {
                            no_message_balance.setVisibility(View.VISIBLE);
                        } else {
                            for (int i = 0; i < tips.length(); i++) {
                                JSONObject inputsObjects = tips.getJSONObject(i);

                                String id = inputsObjects.getString("id");
                                String total = inputsObjects.getString("total");
                                String home = inputsObjects.getString("home");// for home consumption
                                String diary = inputsObjects.getString("diary");// for diary consumption
                                String date = inputsObjects.getString("date");
                                String timesent = inputsObjects.getString("timesent");
                                String comment = inputsObjects.getString("comment");// what happened for the milk coming late
                                String totals =  inputsObjects.getString("totals");
                                totalss.setText(totals);

                                MilkResultsModel inputsModel =
                                        new MilkResultsModel(id, total, home, diary, comment, date,timesent
                                        );
                                mData.add(inputsModel);
                            }
                            adapter = new MilkResultsAdapter(MilkResults.this, mData);
                            recyclerView.setAdapter(adapter);
                            error_message_balance.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        error_message_balance.setVisibility(View.VISIBLE);
//                        error_message_balance.setText(e.toString());
                         Toast.makeText(this, "Something went wrong, swipe down to try again" , Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            progressDialog.dismiss();
            error_message_balance.setVisibility(View.VISIBLE);
//            error_message_balance.setText(error.toString());
            Toast.makeText(this, "Something went wrong, check your connection and please try again", Toast.LENGTH_SHORT).show();

        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("selectedtime", selectedtime);
                params.put("farmname", farmname);
                params.put("status", "no");
                params.put("fromdate", "");
                params.put("todate", " ");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    /*clears the recyclerview once a message is sent*/
    @SuppressLint("NotifyDataSetChanged")
    public void Clear() {
        mData.clear();
        adapter.notifyDataSetChanged();
    }

//    @Override
//    public void onBackPressed()
//    {
//        return;
//    }
}