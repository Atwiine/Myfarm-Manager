package com.example.farmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.farmmanager.AnimalSection.AnimalManagement;
import com.example.farmmanager.Chat.SinglesChat;
import com.example.farmmanager.Employees.Employees;
import com.example.farmmanager.MatookeSection.MilkMatookeMgt;
import com.example.farmmanager.Urls.SessionManager;

public class MainActivity extends AppCompatActivity {
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
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
}