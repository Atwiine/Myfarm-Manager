package com.example.farmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.AnimalSection.AnimalManagement;
import com.example.farmmanager.Chat.SinglesChat;
import com.example.farmmanager.Employees.Employees;
import com.example.farmmanager.MatookeSection.MilkMatookeMgt;
import com.example.farmmanager.Register.Login;
import com.example.farmmanager.Urls.SessionManager;
import com.example.farmmanager.Urls.Urls;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    SessionManager sessionManager;
    String CONTACT,farmname;
    LottieAnimationView animationView;
    Dialog loading;
    Urls urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urls = new Urls();
        loading = new Dialog(this);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        CONTACT = user.get(SessionManager.CONTACT);
        farmname = user.get(SessionManager.FARMNAME);

        /**add the option for continuos checking to see if the manager's profile is still registered
         * and if it has been cancelled the app should tell him and logout him out */
        checking();
//        loading = new Dialog(this);
//        loading.setContentView(R.layout.noaccount);
//        animationView = loading.findViewById(R.id.animationView);
//        animationView.setVisibility(View.VISIBLE);
//        animationView
//                .playAnimation();
//        loading.show();
    }



    /**checking part*/
    private void checking() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOGIN_URL,
                response -> {
                    Log.i("tagconvertstr", "[" + response + "]");
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        @SuppressLint("NotConstructor") String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("login");
                        if (success.equals("0")) {
                            loading = new Dialog(this);
                            loading.setContentView(R.layout.noaccount);
                            animationView = loading.findViewById(R.id.animationView);
                            MaterialCardView gotit = loading.findViewById(R.id.gotit);
                            animationView.setVisibility(View.VISIBLE);
                            animationView
                                    .playAnimation();

                            gotit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    sessionManager.logout();
                                }
                            });
                            loading.setCancelable(false);
                            loading.setCanceledOnTouchOutside(false);
                            Objects.requireNonNull(loading.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            loading.show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Status check failed", Toast.LENGTH_SHORT).show();
                    }
                },

                error -> {
                    Toast.makeText(MainActivity.this, "Status check failed", Toast.LENGTH_SHORT).show();
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("password", CONTACT);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void openAnimalManagement(View view) {
        startActivity(new Intent(MainActivity.this, AnimalManagement.class));
    }

    public void openMatMilkManagement(View view) {
        startActivity(new Intent(MainActivity.this, MilkMatookeMgt.class));
    }

    public void openEmployeeManagement(View view) {
        startActivity(new Intent(MainActivity.this, Employees.class));

    }

    public void openChat(View view) {
        Toast.makeText(this, "Coming soon!!!", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(MainActivity.this, SinglesChat.class));

    }
    public void openSettings(View view) {
        SettingsActivity settingsActivity = new SettingsActivity();
        settingsActivity.show(getSupportFragmentManager(),
                "ModalBottomSheet");
    }
    @Override
    public void onBackPressed()
    {
//        Intent a = new Intent(Intent.ACTION_MAIN);
//        a.addCategory(Intent.CATEGORY_HOME);
//        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(a);

        finishAffinity();
        finish();
    }
}