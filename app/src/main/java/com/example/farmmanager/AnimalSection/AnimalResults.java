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
import com.example.farmmanager.Adapters.AnimalResultsAdapter;
import com.example.farmmanager.MatookeSection.AddThings;
import com.example.farmmanager.Modals.AnimalResultsModel;
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


public class AnimalResults extends AppCompatActivity {

    RecyclerView recyclerView;
    List<AnimalResultsModel> mData;
    AnimalResultsAdapter adapter;
    TextView error_message_balance, no_message_balance, selectedtype,pickedoption,total;
    SessionManager sessionManager;
    Urls urls;
    String getID,farmname;
    SwipeRefreshLayout swipeRefreshLayout;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animalresults);

        urls = new Urls();
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        farmname = user.get(SessionManager.FARMNAME);

        error_message_balance = findViewById(R.id.error_message_balance);
        no_message_balance = findViewById(R.id.no_message_balance);
        selectedtype = findViewById(R.id.selectedtype);
        pickedoption = findViewById(R.id.pickedoption);
        total = (TextView) findViewById(R.id.total);
        /*receive the selected time frame*/
        String selectedtypes = getIntent().getStringExtra("type");
        selectedtype.setText(selectedtypes);
        pickedoption.setText(selectedtypes);

        if (pickedoption.getText().toString().equals("Goat")){
            pickedoption.setText("Goats");
        }


//change the modal and use the sent intent to search shit against it
        recyclerView = findViewById(R.id.recyclerview_animal_results);
        mData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AnimalResultsAdapter(this, mData);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        // SetOnRefreshListener on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                Clear();
                loadResults(selectedtypes);
            }
        });

        /*loading the results*/
        loadResults(selectedtypes);
    }

    public void goBack(View view) {
        startActivity(new Intent(AnimalResults.this, AnimalManagement.class));
    }


    private void loadResults(String selectedtype) {
        final ProgressDialog progressDialog = new ProgressDialog(AnimalResults.this);
        progressDialog.setMessage("Loading results, please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOAD_ANIMAL_RESULTS,
                response -> {
                    progressDialog.dismiss();
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray tips = new JSONArray(response);
                        if (tips.length() == 0) {
                            no_message_balance.setVisibility(View.VISIBLE);
                            total.setText("0");
                        } else {
                            for (int i = 0; i < tips.length(); i++) {
                                JSONObject inputsObjects = tips.getJSONObject(i);
                                String id = inputsObjects.getString("id");
                                String tagnumber = inputsObjects.getString("tagnumber");
                                String gender = inputsObjects.getString("gender");
                                String weight = inputsObjects.getString("weight");
                                String date = inputsObjects.getString("date");
                                String checker = inputsObjects.getString("checkere");//for checking if this animal has young ones
                                String type = inputsObjects.getString("type");//for checking if this animal has young ones
                                String totals = inputsObjects.getString("total");
                                total.setText(totals);
                                String parent_tagnumber = inputsObjects.getString("parent_tagnumber");
                                String breed = inputsObjects.getString("breed");

                                AnimalResultsModel inputsModel =
                                        new AnimalResultsModel(tagnumber, gender, id,date,weight,checker,parent_tagnumber,type,breed
                                        );
                                mData.add(inputsModel);
                            }
                            adapter = new AnimalResultsAdapter(AnimalResults.this, mData);
                            recyclerView.setAdapter(adapter);
                            error_message_balance.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(this, "Something went wrong swipe down to try again", Toast.LENGTH_SHORT).show();
                        error_message_balance.setVisibility(View.VISIBLE);
//                        error_message_balance.setText(e.toString());
                    }
                }, error -> {
            progressDialog.dismiss();
            error_message_balance.setVisibility(View.VISIBLE);
//            error_message_balance.setText(error.toString());
            Toast.makeText(this, "Something went wrong, check your connection and please try again", Toast.LENGTH_SHORT).show();

        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("selectedtype", selectedtype);
                params.put("status", "no");
                params.put("farmname", farmname);
                params.put("fromdate", "");
                params.put("todate", " ");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void openAddAnimals(View view) {
        String type = pickedoption.getText().toString();
        if (type.equals("Goats")){
            type = "Goat";
        }
        String from = "Animal";
        Intent gg = new Intent(AnimalResults.this, AddThings.class);
        gg.putExtra("type",type);
        gg.putExtra("from",from);
        startActivity(gg);
    }
    /*clears the recyclerview once a message is sent*/
    @SuppressLint("NotifyDataSetChanged")
    public void Clear() {
        mData.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed()
    {
        return;
    }
}