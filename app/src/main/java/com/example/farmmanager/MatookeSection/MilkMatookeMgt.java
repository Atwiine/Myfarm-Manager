package com.example.farmmanager.MatookeSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmmanager.AnimalSection.MilkActivity;
import com.example.farmmanager.MainActivity;
import com.example.farmmanager.R;
import com.example.farmmanager.Urls.Urls;

public class MilkMatookeMgt extends AppCompatActivity {

    //    SessionManager sessionManager;
    Urls urls;
    String getID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matokmgt);
        urls = new Urls();
    }

    public void goBack(View view) {
        startActivity(new Intent(MilkMatookeMgt.this, MainActivity.class));
    }

    public void openMilkSection(View view) {
        startActivity(new Intent(MilkMatookeMgt.this, MilkActivity.class));
    }

    public void openMatookeSection(View view) {
        startActivity(new Intent(MilkMatookeMgt.this, Matooke.class));
    }
//    @Override
//    public void onBackPressed()
//    {
//        return;
//    }
}