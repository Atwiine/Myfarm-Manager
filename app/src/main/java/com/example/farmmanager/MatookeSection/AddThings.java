package com.example.farmmanager.MatookeSection;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.Adapters.MatookAdapter;
import com.example.farmmanager.AnimalSection.AnimalResults;
import com.example.farmmanager.AnimalSection.MilkActivity;
import com.example.farmmanager.AnimalSection.MilkResults;
import com.example.farmmanager.Employees.Employees;
import com.example.farmmanager.Modals.MatookeModel;
import com.example.farmmanager.R;
import com.example.farmmanager.Urls.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddThings extends AppCompatActivity {


    TextView chosentitle, selecteddate, heading, no_message_balance_selected_alpha, total;
    Spinner categorycattle, categorygoat, timesent,gender;
    Urls urls;
    LinearLayout addanimalsoption, addmatookeoption, addmilkption, addemployeeoption;
    CalendarView calendermatooke;
    EditText total_bunches, username, age, home_litres,diary_litres,comment_milk,
            total_litres,contact,nextkincontact,address,nextkin
            ,tagnumber,weight,parenttagnumber,role;
    String from;
    String selectedtypes,ccc;
    AlertDialog.Builder builder;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addthings);

        builder = new AlertDialog.Builder(this);
        urls = new Urls();
        chosentitle = findViewById(R.id.chosentitle);

        addanimalsoption = findViewById(R.id.addanimalsoption);
        addmatookeoption = findViewById(R.id.addmatookeoption);
        addmilkption = findViewById(R.id.addmilkption);
        addemployeeoption = findViewById(R.id.addemployeeoption);


//        section for employees
        username = findViewById(R.id.username);
        age = findViewById(R.id.age);
        contact = findViewById(R.id.contact);
        nextkincontact = findViewById(R.id.nextkincontact);
        address = findViewById(R.id.address);
        nextkin = findViewById(R.id.nextkin);
        gender = findViewById(R.id.gender);
        role = findViewById(R.id.role);

//        animal section
        tagnumber = findViewById(R.id.tagnumber);
        weight = findViewById(R.id.weight);
        parenttagnumber = findViewById(R.id.parenttagnumber);
        categorycattle = findViewById(R.id.categorycattle);
        categorygoat = findViewById(R.id.categorygoat);

//        matooke sedtion
        total_bunches = findViewById(R.id.total_bunches);
        calendermatooke = findViewById(R.id.calendermatooke);
        selecteddate = findViewById(R.id.selecteddate);


//        milk section
        total_litres = findViewById(R.id.total_litres);
        home_litres = findViewById(R.id.home_litres);
        timesent = findViewById(R.id.timesent);
        comment_milk = findViewById(R.id.comment_milk);
        diary_litres = findViewById(R.id.diary_litres);


        /*receive the selected */
         selectedtypes = getIntent().getStringExtra("type");
        from = getIntent().getStringExtra("from");// who sent the intent
//        chosentitle.setText(selectedtypes);


        /**ANIMAL SECTION*/
        /*find out who sent the intent and then show their layout*/
        if (from.equals("Animal")) {
            addanimalsoption.setVisibility(View.VISIBLE);
        }

        /*find out which animal was selected and then show the drop options*/
        if (selectedtypes.equals("Cattle")) {
            categorycattle.setVisibility(View.VISIBLE);
            categorygoat.setVisibility(View.GONE);
            chosentitle.setText("Add Cattle");
        } else if (selectedtypes.equals("Goat")) {
            categorygoat.setVisibility(View.VISIBLE);
            chosentitle.setText("Add Goats");
            categorycattle.setVisibility(View.GONE);

        }

        /**MATOOKE SECTION*/
        if (from.equals("Matooke")) {
            addmatookeoption.setVisibility(View.VISIBLE);
            chosentitle.setText("Add matooke bunches");
        }

        /**MILK SECTION*/
        if (from.equals("Milk")) {
            addmilkption.setVisibility(View.VISIBLE);
            chosentitle.setText("Add milked litres");
        }


        /**MILK SECTION*/
        if (from.equals("Employees")) {
            addemployeeoption.setVisibility(View.VISIBLE);
        }

    }

    /*handle the going back part*/
    public void goBack(View view) {

        switch (from) {
            case "Cattle": {
                String type = "Cattle";
                Intent gg = new Intent(AddThings.this, AnimalResults.class);
                gg.putExtra("type", type);
                startActivity(gg);
                finish();
                break;
            }
            case "Goat": {
                String type = "Goat";
                Intent gg = new Intent(AddThings.this, AnimalResults.class);
                gg.putExtra("type", type);
                startActivity(gg);
                finish();
                break;
            }
            case "Matooke":
                startActivity(new Intent(AddThings.this, Matooke.class));
                finish();
                break;
            case "Milk":
                startActivity(new Intent(AddThings.this, MilkActivity.class));
                finish();
                break;
            case "Employees":
                startActivity(new Intent(AddThings.this, Employees.class));
                finish();
                break;
        }


    }

    /*for adding animals*
     * so the logic is that we will see which animal was selected and then use that to show the gender options
     * then also on the sending part .... we are going to send with the above option
     * then on the parent tag number,,, if this section is filled in ,,, we are going to add it in the db
     * and then on the checker part indicate Yes for this animal is a new born */
    public void sendAnimals(View view) {
        builder.setMessage("Are you sure you want to submit this information") .setTitle("Double your information before submitting");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to submit this information ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        sendAnimals();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Double your information before submitting");
        alert.show();


    }


    /*for adding matooke ...
     * will be getting the total bunches cut and the date that they were cut on */
    public void sendMatooke(View view) {
        builder.setMessage("Are you sure you want to submit this information") .setTitle("Double your information before submitting");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to submit this information ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        sendMatooke();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Double your information before submitting");
        alert.show();
    }

    public void sendMilk(View view) {
        builder.setMessage("Are you sure you want to submit this information") .setTitle("Double your information before submitting");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to submit this information ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        sendMilk();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Double your information before submitting");
        alert.show();
    }

    public void sendEmployees(View view) {
        builder.setMessage("Are you sure you want to submit this information") .setTitle("Double your information before submitting");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to submit this information ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        sendEmployees();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Double your information before submitting");
        alert.show();
    }






    /*functions for sending things */
    private void sendAnimals() {

        //work on the spinner thing
        //.....

        String sgoat = String.valueOf(categorygoat.getSelectedItem());
        String scattle = String.valueOf(categorycattle.getSelectedItem());
        String tNo = tagnumber.getText().toString().trim();
        String ptNo = parenttagnumber.getText().toString().trim();
        String wei = weight.getText().toString().trim();

        /*check for gender*/
        String gg = "";
        if (sgoat.equals("Buck") || sgoat.equals("Nanny")){
            gg = sgoat;
        }else if (scattle.equals("Bull") || scattle.equals("Cow")){
            gg = scattle;
        }

        Toast.makeText(this, "gg " + gg, Toast.LENGTH_SHORT).show();

        if (!ptNo.isEmpty()){
            ccc = "Yes";
        }else {
             ccc = "No";
        }


        final ProgressDialog progressDialog = new ProgressDialog(AddThings.this);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();

        String finalGg = gg;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.SEND_ANIMAL_RESULTS,
                response -> {
                    progressDialog.dismiss();
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
//                          check which type it is and then load the page with that
                            if (selectedtypes.equals("Goat")){
                                String type = "Goat";
                                Intent ggs = new Intent(AddThings.this, AnimalResults.class);
                                ggs.putExtra("type", type);
                                startActivity(ggs);
                                finish();
                            }else if (selectedtypes.equals("Cattle")){
                                String type = "Cattle";
                                Intent ggs = new Intent(AddThings.this, AnimalResults.class);
                                ggs.putExtra("type", type);
                                startActivity(ggs);
                                finish();
                            }

                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                         Toast.makeText(this, "Something went wrong, please try again" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            progressDialog.dismiss();
            Toast.makeText(this, "Something went wrong, check your connection and try again please try again", Toast.LENGTH_SHORT).show();

        }) {
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("type", selectedtypes);
                params.put("parent_tagnumber", ptNo);
                params.put("tagnumber", tNo);
                params.put("weight", wei);
                params.put("checkere", ccc);
//                if (!sgoat.isEmpty()){
                    params.put("gender", finalGg);
//                }else {
//                    params.put("gender", scattle);
//                }
//
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void sendMatooke() {

        String totalbunches = total_bunches.getText().toString().trim();

        calendermatooke.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selecteddate.setText(dayOfMonth + "/" + month + "/" + year);
                Toast.makeText(AddThings.this, "date " + selecteddate.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        final ProgressDialog progressDialog = new ProgressDialog(AddThings.this);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.SEND_MATOOKE,
                response -> {
                    progressDialog.dismiss();
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddThings.this,Matooke.class));
                            finish();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            progressDialog.dismiss();
            Toast.makeText(this, "Something went wrong, check your connection and try again please try again", Toast.LENGTH_SHORT).show();

        }) {
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("total", totalbunches);
                params.put("date_cut", selecteddate.getText().toString());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void sendMilk() {

        String ts = String.valueOf(timesent.getSelectedItem());
        String hl = home_litres.getText().toString().trim();
        String cm = comment_milk.getText().toString().trim();
        String dl = diary_litres.getText().toString().trim();
        String tl = total_litres.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(AddThings.this);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.SEND_MILKING_RESULTS,
                response -> {
                    progressDialog.dismiss();
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddThings.this, MilkResults.class));
                            finish();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            progressDialog.dismiss();
            Toast.makeText(this, "Something went wrong, check your connection and try again please try again", Toast.LENGTH_SHORT).show();

        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("total", tl);
                params.put("home", hl);
                params.put("diary", dl);
                params.put("comment", cm);
                params.put("timesent", ts);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void sendEmployees() {

        String genders = String.valueOf(gender.getSelectedItem());
        String usernames = username.getText().toString().trim();
        String ages = age.getText().toString().trim();
        String contacts = contact.getText().toString().trim();
        String nextkincontacts = nextkincontact.getText().toString().trim();
        String addresss = address.getText().toString().trim();
        String nextkins = nextkin.getText().toString().trim();
        String roles = role.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(AddThings.this);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.SEND_EMPLOYEES,
                response -> {
                    progressDialog.dismiss();
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddThings.this, Employees.class));
                            finish();
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            progressDialog.dismiss();
            Toast.makeText(this, "Something went wrong, check your connection and try again please try again", Toast.LENGTH_SHORT).show();

        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", usernames);
                params.put("number", contacts);
                params.put("age", ages);
                params.put("nextofkin", nextkins);
                params.put("contactnextofkim", nextkincontacts);
                params.put("role", roles);
                params.put("address", addresss);
                params.put("gender", genders);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}