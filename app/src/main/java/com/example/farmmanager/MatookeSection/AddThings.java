package com.example.farmmanager.MatookeSection;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.AnimalSection.AnimalResults;
import com.example.farmmanager.AnimalSection.MilkActivity;
import com.example.farmmanager.AnimalSection.MilkResults;
import com.example.farmmanager.Employees.Employees;
import com.example.farmmanager.R;
import com.example.farmmanager.Urls.SessionManager;
import com.example.farmmanager.Urls.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddThings extends AppCompatActivity {
//implements Spinner.OnItemSelectedListener

    TextView chosentitle, textseletegender, iddAnimal, matookeid, milkid, IDEmployee, genderss, textseletmilkttime, textseletgender;
    Spinner categorycattle, categorygoat, timesent, gender, breed;
    Urls urls;
    LinearLayout addanimalsoption, addmatookeoption, addmilkption, li_ssoption,
            addemployeeoption, linear_pnumber, sendani, updateani, updateMatookes, addMatooke, addMilk, updateMilk,
            addEmployees, updateEmployee;
    CalendarView calendermatooke;
    EditText total_bunches, username, age, home_litres, diary_litres, comment_milk,
            total_litres, contact, nextkincontact, address, nextkin, tagnumber, weight, parenttagnumber, role, selectedGender,
            dateMatooke, selectedMilkTime;
    String from;
    String selectedtypes, ccc;
    AlertDialog.Builder builder;
    LottieAnimationView animationView;
    Dialog loadingUI;
    DatePickerDialog datePickerDialog;
    SessionManager sessionManager;
    String getID, farmname, finaltt;
    View vvvv, mmmm, ggggg;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addthings);

        builder = new AlertDialog.Builder(this);
        urls = new Urls();
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        farmname = user.get(SessionManager.FARMNAME);
        chosentitle = findViewById(R.id.chosentitle);


        /*receive the selected */
        selectedtypes = getIntent().getStringExtra("type");
        from = getIntent().getStringExtra("from");// who sent the intent


        addanimalsoption = findViewById(R.id.addanimalsoption);
        addmatookeoption = findViewById(R.id.addmatookeoption);
        addmilkption = findViewById(R.id.addmilkption);
        addemployeeoption = findViewById(R.id.addemployeeoption);

        /****
         * START  EMPLOYEES*/
//        section for employees
        username = findViewById(R.id.username);
        age = findViewById(R.id.age);
        contact = findViewById(R.id.contact);
        nextkincontact = findViewById(R.id.nextkincontact);
        address = findViewById(R.id.address);
        nextkin = findViewById(R.id.nextkin);
        gender = findViewById(R.id.gender);
        role = findViewById(R.id.role);
        updateEmployee = findViewById(R.id.updateEmployee);
        addEmployees = findViewById(R.id.addEmployees);
        IDEmployee = findViewById(R.id.IDEmployee);
        genderss = findViewById(R.id.genderss);
        ggggg = findViewById(R.id.ggggg);
        textseletgender = findViewById(R.id.textseletgender);




        /*handle the incoming intents */
        String empID = getIntent().getStringExtra("emid");
        if (empID != null && !empID.isEmpty()) {
            IDEmployee.setText(getIntent().getStringExtra("emid"));
            address.setText(getIntent().getStringExtra("address"));
            role.setText(getIntent().getStringExtra("role"));
            username.setText(getIntent().getStringExtra("name"));
            contact.setText(getIntent().getStringExtra("number"));
            nextkin.setText(getIntent().getStringExtra("nextofkin"));
            nextkincontact.setText(getIntent().getStringExtra("contactnextofkim"));
            age.setText(getIntent().getStringExtra("age"));
            genderss.setText(getIntent().getStringExtra("gender"));
            genderss.setVisibility(View.VISIBLE);
            addemployeeoption.setVisibility(View.VISIBLE);
            updateEmployee.setVisibility(View.VISIBLE);
            addEmployees.setVisibility(View.GONE);
            gender.setVisibility(View.GONE);
            chosentitle.setText("Edit employee record");
        }

        genderss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender.setVisibility(View.VISIBLE);
                ggggg.setVisibility(View.VISIBLE);
                textseletgender.setVisibility(View.VISIBLE);
            }
        });

        /****
         * END EMPLOYEES HANDLE*/


        /****
         * START ANIMAL HANDLE*/
//        animal section
        tagnumber = findViewById(R.id.tagnumber);
        weight = findViewById(R.id.weight);
        parenttagnumber = findViewById(R.id.parenttagnumber);
        categorycattle = findViewById(R.id.categorycattle);
        categorygoat = findViewById(R.id.categorygoat);
        linear_pnumber = findViewById(R.id.linear_pnumber);
        selectedGender = findViewById(R.id.selectedGender);
        iddAnimal = findViewById(R.id.iddAnimal);
        sendani = findViewById(R.id.sendani);
        updateani = findViewById(R.id.updateani);
        textseletegender = findViewById(R.id.textseletegender);
        vvvv = findViewById(R.id.vvvv);
        breed = findViewById(R.id.breed); /// add the breed option


        String wwe = getIntent().getStringExtra("checker");
        if (wwe != null && !wwe.isEmpty()) {
            tagnumber.setText(getIntent().getStringExtra("tagnumber"));
            parenttagnumber.setText(getIntent().getStringExtra("ptagnumber"));
            weight.setText(getIntent().getStringExtra("weight"));
            iddAnimal.setText(getIntent().getStringExtra("id"));
            categorygoat.setVisibility(View.GONE);
            categorycattle.setVisibility(View.GONE);
            if (parenttagnumber.getText().toString().equals("0")) {
                linear_pnumber.setVisibility(View.GONE);
            }
            selectedGender.setVisibility(View.VISIBLE);
            textseletegender.setVisibility(View.VISIBLE);
            vvvv.setVisibility(View.VISIBLE);

            selectedGender.setText(getIntent().getStringExtra("gender"));
            sendani.setVisibility(View.GONE);
            updateani.setVisibility(View.VISIBLE);

        }

        selectedGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedtypes.equals("Cattle")) {
                    categorycattle.setVisibility(View.VISIBLE);
                    breed.setVisibility(View.VISIBLE);
                    categorygoat.setVisibility(View.GONE);
                    Toast.makeText(AddThings.this, "kjkjkj", Toast.LENGTH_SHORT).show();
                } else if (selectedtypes.equals("Goat")) {
                    categorygoat.setVisibility(View.VISIBLE);
                    breed.setVisibility(View.GONE);
                    categorycattle.setVisibility(View.GONE);
                }

            }
        });
        /****
         * END ANIMAL HANDLE*/

        /****
         * START MATOOKE HANDLE*/
//        matooke sedtion
        total_bunches = findViewById(R.id.total_bunches);
        dateMatooke = findViewById(R.id.dateMatooke);
        addMatooke = findViewById(R.id.addMatooke);
        updateMatookes = findViewById(R.id.updateMatookes);
        matookeid = findViewById(R.id.matookeid);

        /*handle the incoming matooke intents*/
        String mID = getIntent().getStringExtra("id");
        if (mID != null && !mID.isEmpty()) {
            total_bunches.setText(getIntent().getStringExtra("total_bunches"));
            matookeid.setText(getIntent().getStringExtra("id"));
            dateMatooke.setText(getIntent().getStringExtra("date"));
            addmatookeoption.setVisibility(View.VISIBLE);
            updateMatookes.setVisibility(View.VISIBLE);
            addMatooke.setVisibility(View.GONE);
            chosentitle.setText("Edit matooke record");
        }


        dateMatooke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddThings.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dateMatooke.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        /****
         * END MATOOKE HANDLE*/

        /****
         * START MILK HANDLE*/
//        milk section
        total_litres = findViewById(R.id.total_litres);
        home_litres = findViewById(R.id.home_litres);
        timesent = findViewById(R.id.timesent);
        comment_milk = findViewById(R.id.comment_milk);
        diary_litres = findViewById(R.id.diary_litres);
        addMilk = findViewById(R.id.addMilk);
        updateMilk = findViewById(R.id.updateMilk);
        milkid = findViewById(R.id.milkid);
        selectedMilkTime = findViewById(R.id.selectedMilkTime);
        textseletmilkttime = findViewById(R.id.textseletmilkttime);
        mmmm = findViewById(R.id.mmmm);


        /*handle the incoming milk intents*/
        String milkID = getIntent().getStringExtra("id");
        if (milkID != null && !milkID.isEmpty()) {
            total_litres.setText(getIntent().getStringExtra("total"));
            home_litres.setText(getIntent().getStringExtra("home"));
            selectedMilkTime.setText(getIntent().getStringExtra("timesent"));
            comment_milk.setText(getIntent().getStringExtra("comment"));
            diary_litres.setText(getIntent().getStringExtra("diary"));
            milkid.setText(getIntent().getStringExtra("id"));
            addmatookeoption.setVisibility(View.GONE);
            selectedMilkTime.setVisibility(View.VISIBLE);


            updateMilk.setVisibility(View.VISIBLE);
            addMilk.setVisibility(View.GONE);
            timesent.setVisibility(View.GONE);
            chosentitle.setText("Edit milk record");
        }

        selectedMilkTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timesent.setVisibility(View.VISIBLE);
                mmmm.setVisibility(View.VISIBLE);
                textseletmilkttime.setVisibility(View.VISIBLE);
            }
        });


/****
 * END MILK HANDLE*/


        /**ANIMAL SECTION*/
        /*find out who sent the intent and then show their layout*/
        if (from != null && from.equals("Animal")) {
            addanimalsoption.setVisibility(View.VISIBLE);
            addmatookeoption.setVisibility(View.GONE);
        }

        /*find out which animal was selected and then show the drop options*/
        if (selectedtypes != null && selectedtypes.equals("Cattle")) {
            categorycattle.setVisibility(View.VISIBLE);
            breed.setVisibility(View.VISIBLE);
            categorygoat.setVisibility(View.GONE);
            chosentitle.setText("Add cattle record");
            if (wwe != null && !wwe.isEmpty()) {
                chosentitle.setText("Edit cattle record");
            }
        } else if (selectedtypes != null && selectedtypes.equals("Goat")) {
            categorygoat.setVisibility(View.VISIBLE);
            breed.setVisibility(View.GONE);
            chosentitle.setText("Add goat record");
            categorycattle.setVisibility(View.GONE);
            if (wwe != null && !wwe.isEmpty()) {
                chosentitle.setText("Edit goat record");
            }
        }

        /**MATOOKE SECTION*/
        if (from != null && from.equals("Matooke")) {
            addmatookeoption.setVisibility(View.VISIBLE);
            chosentitle.setText("Add plantain bunches");

        } else if (from != null && from.equals("MatookeEdit")) {
            addmatookeoption.setVisibility(View.VISIBLE);
            chosentitle.setText("Edit plantain records");
        }

        /**MILK SECTION*/
        if (from != null && from.equals("Milk")) {
            addmilkption.setVisibility(View.VISIBLE);
            chosentitle.setText("Add milked litres");
        } else if (from != null && from.equals("MilkEdit")) {
            addmilkption.setVisibility(View.VISIBLE);
            chosentitle.setText("Edit milk records");
        }


        /**Employees SECTION*/
        if (from != null && from.equals("Employees")) {
            addemployeeoption.setVisibility(View.VISIBLE);
            chosentitle.setText("Add employee profile");
        } else if (from != null && from.equals("EmployeeEdit")) {
            addemployeeoption.setVisibility(View.VISIBLE);
            chosentitle.setText("Edit employee profile");
        }

    }


    /**
     * handling spinners
     */
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        selectedMilkTime.setText(timesent.getSelectedItem().toString());
//        selectedGender .setText(categorycattle.getSelectedItem().toString());
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }


    /*handle the going back part*/
    public void goBack(View view) {

        switch (from) {
            case "Animal": {

                if (selectedtypes != null && selectedtypes.equals("Cattle")) {
                    String type = "Cattle";
                    Intent gg = new Intent(AddThings.this, AnimalResults.class);
                    gg.putExtra("type", type);
                    startActivity(gg);
                    finish();

                } else if (selectedtypes != null && selectedtypes.equals("Goat")) {
                    String type = "Goat";
                    Intent gg = new Intent(AddThings.this, AnimalResults.class);
                    gg.putExtra("type", type);
                    startActivity(gg);
                    finish();

                }
                break;
            }

            case "Matooke":
            case "MatookeEdit": {
                startActivity(new Intent(AddThings.this, Matooke.class));
                finish();
                break;
            }
            case "Milk":
            case "MilkEdit": {
                startActivity(new Intent(AddThings.this, MilkActivity.class));
                finish();
                break;
            }
            case "Employees":
            case "EmployeeEdit": {
                startActivity(new Intent(AddThings.this, Employees.class));
                finish();
                break;
            }
//            case "Employees": {
//                startActivity(new Intent(AddThings.this, Employees.class));
//                finish();
//                break;
//            }
        }

//    }
    }

    /*for adding animals*
     * so the logic is that we will see which animal was selected and then use that to show the gender options
     * then also on the sending part .... we are going to send with the above option
     * then on the parent tag number,,, if this section is filled in ,,, we are going to add it in the db
     * and then on the checker part indicate Yes for this animal is a new born */
    public void sendAnimals(View view) {
        builder.setMessage("Are you sure you want to submit this information").setTitle("Double check your information before submitting");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to submit this information ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
        alert.setTitle("Double check your information before submitting");
        alert.show();


    }


    /*for adding matooke ...
     * will be getting the total bunches cut and the date that they were cut on */
    public void sendMatooke(View view) {
        builder.setMessage("Are you sure you want to submit this information").setTitle("Double check your information before submitting");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to submit this information ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
        alert.setTitle("Double check your information before submitting");
        alert.show();
    }

    public void sendMilk(View view) {
        builder.setMessage("Are you sure you want to submit this information").setTitle("Double check your information before submitting");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to submit this information ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
        alert.setTitle("Double check your information before submitting");
        alert.show();
    }

    public void sendEmployees(View view) {
        builder.setMessage("Are you sure you want to submit this information").setTitle("Double check your information before submitting");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to submit this information ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
        alert.setTitle("Double check your information before submitting");
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
        String bbbr = "";

        // check to see if the selected animal is cattle or goat and if its cattle .... then use the breed option
        if (selectedtypes.equals("Cattle")) {
            bbbr = String.valueOf(breed.getSelectedItem());
        } else {
            bbbr = "";
        }

        /*check for gender*/
        String gg = "";
        if (sgoat.equals("Buck") || sgoat.equals("Nanny")) {
            gg = sgoat;
        } else if (scattle.equals("Bull") || scattle.equals("Cow")) {
            gg = scattle;
        }

        if (!ptNo.isEmpty()) {
            ccc = "Yes";
        } else {
            ccc = "No";
        }


        loadingUI = new Dialog(this);
        loadingUI.setContentView(R.layout.right);
        animationView = loadingUI.findViewById(R.id.animationView);
        animationView.setVisibility(View.VISIBLE);
        animationView
                .playAnimation();
        loadingUI.setCancelable(false);
        loadingUI.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(loadingUI.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingUI.show();

        String finalGg = gg;
        String finalBbbr = bbbr;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.SEND_ANIMAL_RESULTS,
                response -> {
                    loadingUI.dismiss();
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
//                          check which type it is and then load the page with that
                            if (selectedtypes.equals("Goat")) {
                                String type = "Goat";
                                Intent ggs = new Intent(AddThings.this, AnimalResults.class);
                                ggs.putExtra("type", type);
                                startActivity(ggs);
                                finish();
                            } else if (selectedtypes.equals("Cattle")) {
                                String type = "Cattle";
                                Intent ggs = new Intent(AddThings.this, AnimalResults.class);
                                ggs.putExtra("type", type);
                                startActivity(ggs);
                                finish();
                            }

                        }
                    } catch (JSONException e) {
                        loadingUI.dismiss();
                        e.printStackTrace();
                        Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            loadingUI.dismiss();
            Toast.makeText(this, "Something went wrong, check your connection and try again please try again", Toast.LENGTH_SHORT).show();

        }) {
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("type", selectedtypes);
                params.put("parent_tagnumber", ptNo);
                params.put("tagnumber", tNo);
                params.put("weight", wei);
                params.put("checkere", ccc);
                params.put("gender", finalGg);
                params.put("farmname", farmname);
                params.put("breed", finalBbbr);// animal breed
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void sendMatooke() {

        String totalbunches = total_bunches.getText().toString().trim();
        String dateMatookes = dateMatooke.getText().toString().trim();


        loadingUI = new Dialog(this);
        loadingUI.setContentView(R.layout.right);
        animationView = loadingUI.findViewById(R.id.animationView);
        animationView.setVisibility(View.VISIBLE);
        animationView
                .playAnimation();
        loadingUI.setCancelable(false);
        loadingUI.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(loadingUI.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingUI.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.SEND_MATOOKE,
                response -> {
                    loadingUI.dismiss();
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddThings.this, Matooke.class));
                            finish();
                        }
                    } catch (JSONException e) {
                        loadingUI.dismiss();
                        e.printStackTrace();
                        Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            loadingUI.dismiss();
            Toast.makeText(this, "Something went wrong, check your connection and try again please try again", Toast.LENGTH_SHORT).show();

        }) {
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("total", totalbunches);
                params.put("date_cut", dateMatookes);
                params.put("farmname", farmname);

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

        loadingUI = new Dialog(this);
        loadingUI.setContentView(R.layout.right);
        animationView = loadingUI.findViewById(R.id.animationView);
        animationView.setVisibility(View.VISIBLE);
        animationView
                .playAnimation();
        loadingUI.setCancelable(false);
        loadingUI.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(loadingUI.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingUI.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.SEND_MILKING_RESULTS,
                response -> {
                    loadingUI.dismiss();
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                            Toast.makeText(this, "time is " + ts, Toast.LENGTH_SHORT).show();

                            Intent cc = new Intent(AddThings.this, MilkResults.class);
                            cc.putExtra("time", ts);
                            startActivity(cc);
                            finish();
                        }
                    } catch (JSONException e) {
                        loadingUI.dismiss();
                        e.printStackTrace();
                        Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            loadingUI.dismiss();
            Toast.makeText(this, "Something went wrong, check your connection and try again please try again", Toast.LENGTH_SHORT).show();

        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("total", tl);
                params.put("home", hl);
                params.put("diary", dl);
                params.put("comment", cm);
                params.put("timesent", ts);
                params.put("farmname", farmname);

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

        loadingUI = new Dialog(this);
        loadingUI.setContentView(R.layout.right);
        animationView = loadingUI.findViewById(R.id.animationView);
        animationView.setVisibility(View.VISIBLE);
        animationView
                .playAnimation();
        loadingUI.setCancelable(false);
        loadingUI.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(loadingUI.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingUI.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.SEND_EMPLOYEES,
                response -> {
//                    progressDialog.dismiss();
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
                        loadingUI.dismiss();
                        e.printStackTrace();
                        Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            loadingUI.dismiss();
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
                params.put("farmname", farmname);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void updateAnimals(View view) {
        builder.setMessage("Are you sure you want to submit this information").setTitle("Double check your information before submitting");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to submit this information ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        updateAnimalss();
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
        alert.setTitle("Double check your information before submitting");
        alert.show();
    }

    public void updateMatooke(View view) {
        builder.setMessage("Are you sure you want to submit this information").setTitle("Double check your information before submitting");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to submit this information ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        updateMatooke();
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
        alert.setTitle("Double check your information before submitting");
        alert.show();

    }

    public void updateMilk(View view) {
        builder.setMessage("Are you sure you want to submit this information").setTitle("Double check your information before submitting");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to submit this information ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        updateMilk();
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
        alert.setTitle("Double check your information before submitting");
        alert.show();
    }

    public void updateEmployees(View view) {
        builder.setMessage("Are you sure you want to submit this information").setTitle("Double check your information before submitting");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to submit this information ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        updateEmployess();
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
        alert.setTitle("Double check your information before submitting");
        alert.show();
    }

    /*functions for updating things*/

    private void updateAnimalss() {
        loadingUI = new Dialog(this);
        loadingUI.setContentView(R.layout.right);
        animationView = loadingUI.findViewById(R.id.animationView);
        animationView.setVisibility(View.VISIBLE);
        animationView
                .playAnimation();
        loadingUI.show();

        String sgoat = String.valueOf(categorygoat.getSelectedItem());
        String scattle = String.valueOf(categorycattle.getSelectedItem());
        String tNo = tagnumber.getText().toString().trim();
        String ptNo = parenttagnumber.getText().toString().trim();
        String wei = weight.getText().toString().trim();
        String idds = iddAnimal.getText().toString().trim();

        /*check for gender*/
        if (sgoat.equals("Buck") || sgoat.equals("Nanny")) {
            selectedGender.setText(sgoat);
//            Toast.makeText(this, "selected " + selectedGender.getText().toString(), Toast.LENGTH_SHORT).show();
        } else if (scattle.equals("Bull") || scattle.equals("Cow")) {
            selectedGender.setText(scattle);
//            Toast.makeText(this, "selected " + selectedGender.getText().toString(), Toast.LENGTH_SHORT).show();

        }

        if (!ptNo.isEmpty()) {
            ccc = "Yes";
        } else {
            ccc = "No";
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.UPDATE_PROFILE,
                response -> {
                    try {

                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            loadingUI.dismiss();
                            Toast.makeText(getApplicationContext(), "Updated successful", Toast.LENGTH_SHORT).show();
                            Intent cc = new Intent(AddThings.this, AnimalResults.class);
                            cc.putExtra("type", selectedtypes);
                            startActivity(cc);
                            finish();

                        }
                        loadingUI.setCancelable(false);
                        loadingUI.setCanceledOnTouchOutside(false);
                        Objects.requireNonNull(loadingUI.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                        loadingUI.show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        loadingUI.dismiss();
                        Toast.makeText(getApplicationContext(), "Updated not successful, please try again " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    loadingUI.dismiss();
                    Toast.makeText(getApplicationContext(), "Updated not successful, please check your internet connection and try again " + error.toString(), Toast.LENGTH_SHORT).show();
                }
        ) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("parent_tagnumber", ptNo);
                params.put("tagnumber", tNo);
                params.put("weight", wei);
                params.put("checkere", "Animals");
                params.put("id", idds);
                params.put("gender", selectedGender.getText().toString());
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void updateMatooke() {
        loadingUI = new Dialog(this);
        loadingUI.setContentView(R.layout.right);
        animationView = loadingUI.findViewById(R.id.animationView);
        animationView.setVisibility(View.VISIBLE);
        animationView
                .playAnimation();
        loadingUI.show();

        String totalbunches = total_bunches.getText().toString().trim();
        String mid = matookeid.getText().toString().trim();
        String dateMatookes = dateMatooke.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.UPDATE_PROFILE,
                response -> {
                    try {

                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            loadingUI.dismiss();
                            Toast.makeText(getApplicationContext(), "Update successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddThings.this, Matooke.class));
                            finish();
                        }
                        loadingUI.setCancelable(false);
                        loadingUI.setCanceledOnTouchOutside(false);
                        Objects.requireNonNull(loadingUI.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        loadingUI.show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        loadingUI.dismiss();
                        Toast.makeText(getApplicationContext(), "Updated not successful, please try again " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    loadingUI.dismiss();
                    Toast.makeText(getApplicationContext(), "Updated not successful, please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                }
        ) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("total", totalbunches);
                params.put("date_cut", dateMatookes);
                params.put("id", mid);
                params.put("checkere", "Matooke");
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void updateMilk() {
        loadingUI = new Dialog(this);
        loadingUI.setContentView(R.layout.right);
        animationView = loadingUI.findViewById(R.id.animationView);
        animationView.setVisibility(View.VISIBLE);
        animationView
                .playAnimation();
        loadingUI.show();


        String hl = home_litres.getText().toString().trim();
        String cm = comment_milk.getText().toString().trim();
        String dl = diary_litres.getText().toString().trim();
        String tl = total_litres.getText().toString().trim();
        String mmid = milkid.getText().toString().trim();

        String tt = timesent.getSelectedItem().toString();
        if (tt.equals("Select time")) {
            finaltt = selectedMilkTime.getText().toString();
        } else {
            finaltt = timesent.getSelectedItem().toString();
        }
//        selectedMilkTime.setText(tt);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.UPDATE_PROFILE,
                response -> {
                    try {

                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            loadingUI.dismiss();
                            Toast.makeText(getApplicationContext(), "Update successful", Toast.LENGTH_SHORT).show();

                            Intent mm = new Intent(AddThings.this, MilkResults.class);
                            mm.putExtra("time", selectedMilkTime.getText().toString());
                            startActivity(mm);
                            finish();

                        }
                        loadingUI.setCancelable(false);
                        loadingUI.setCanceledOnTouchOutside(false);
                        Objects.requireNonNull(loadingUI.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        loadingUI.show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        loadingUI.dismiss();
                        Toast.makeText(getApplicationContext(), "Updated not successful, please try again " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    loadingUI.dismiss();
                    Toast.makeText(getApplicationContext(), "Updated not successful, please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                }
        ) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("total", tl);
                params.put("home", hl);
                params.put("diary", dl);
                params.put("comment", cm);
                params.put("timesent", finaltt);
                params.put("id", mmid);
                params.put("checkere", "Milk");
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void updateEmployess() {
        loadingUI = new Dialog(this);
        loadingUI.setContentView(R.layout.right);
        animationView = loadingUI.findViewById(R.id.animationView);
        animationView.setVisibility(View.VISIBLE);
        animationView
                .playAnimation();
        loadingUI.show();

        String aa = address.getText().toString().trim();
        String uu = username.getText().toString().trim();
        String cc = contact.getText().toString().trim();
        String nx = nextkin.getText().toString().trim();
        String rr = role.getText().toString().trim();
        String nxc = nextkincontact.getText().toString().trim();
        String idds = IDEmployee.getText().toString();
        String ages = age.getText().toString().trim();
        String genders = String.valueOf(gender.getSelectedItem());
        genderss.setText(genders);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.UPDATE_PROFILE,
                response -> {
                    try {

                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            loadingUI.dismiss();
                            Toast.makeText(getApplicationContext(), "Update successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddThings.this, Employees.class));
                            finish();

                        }
                        loadingUI.setCancelable(false);
                        loadingUI.setCanceledOnTouchOutside(false);
                        Objects.requireNonNull(loadingUI.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                        loadingUI.show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        loadingUI.dismiss();
                        Toast.makeText(getApplicationContext(), "Updated not successful, please try again " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    loadingUI.dismiss();
                    Toast.makeText(getApplicationContext(), "Updated not successful, please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                }
        ) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("address", aa);
                params.put("name", uu);
                params.put("contact", cc);
                params.put("nextkin", nx);
                params.put("nextkincontact", nxc);
                params.put("age", ages);
                params.put("role", rr);
                params.put("id", idds);
                params.put("gender", genderss.getText().toString());
                params.put("checkere", "Employess");
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

//    @Override
//    public void onBackPressed() {
//        return;
//    }

}