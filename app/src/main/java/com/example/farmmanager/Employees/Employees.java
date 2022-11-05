package com.example.farmmanager.Employees;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.Adapters.EmployeeAdapter;
import com.example.farmmanager.AnimalSection.MilkActivity;
import com.example.farmmanager.MainActivity;
import com.example.farmmanager.MatookeSection.AddThings;
import com.example.farmmanager.Modals.EmployeeModel;
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

public class Employees extends AppCompatActivity {

    RecyclerView recyclerView;
    List<EmployeeModel> mData;
    EmployeeAdapter adapter;
    TextView error_message_balance, no_message_balance, total;
    SessionManager sessionManager;
    Urls urls;
    String getID,farmname;
    SwipeRefreshLayout swipeRefreshLayout;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        urls = new Urls();
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        farmname = user.get(SessionManager.FARMNAME);

        error_message_balance = findViewById(R.id.error_message_balance);
        no_message_balance = findViewById(R.id.no_message_balance);


        recyclerView = findViewById(R.id.recyclerview_employee_results);
        mData = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmployeeAdapter(this, mData);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        // SetOnRefreshListener on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                Clear();
                loadEmployees();
            }
        });

        loadEmployees();
    }

    public void goBack(View view) {
        startActivity(new Intent(Employees.this, MainActivity.class));
    }

    private void loadEmployees() {
        final ProgressDialog progressDialog = new ProgressDialog(Employees.this);
        progressDialog.setMessage("Loading employees, please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOAD_EMPLOYEES,
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
                                String name = inputsObjects.getString("name");
                                String number = inputsObjects.getString("number");
                                String role = inputsObjects.getString("role");
                                String address = inputsObjects.getString("address");
                                String gender = inputsObjects.getString("gender");
                                String nextofkin = inputsObjects.getString("nextofkin");
                                String contactnextofkim = inputsObjects.getString("contactnextofkim");
                                String age = inputsObjects.getString("age");

                                EmployeeModel inputsModel =
                                        new EmployeeModel(id, name, number, role, address, gender,nextofkin,contactnextofkim,age
                                        );
                                mData.add(inputsModel);
                            }
                            adapter = new EmployeeAdapter(Employees.this, mData);
                            recyclerView.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        error_message_balance.setVisibility(View.VISIBLE);
                        error_message_balance.setText(e.toString());
                        // Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            progressDialog.dismiss();
            error_message_balance.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "Something went wrong, check your connection and try again please try again", Toast.LENGTH_SHORT).show();

        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("farmname", farmname);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void openAddEmployees(View view) {
        String from = "Employees";
        Intent gg = new Intent(Employees.this, AddThings.class);
        gg.putExtra("type","");
        gg.putExtra("from",from);
        startActivity(gg);

    }
    /*clears the recyclerview once a message is sent*/
    @SuppressLint("NotifyDataSetChanged")
    public void Clear() {
        mData.clear();
        adapter.notifyDataSetChanged();
    }
}