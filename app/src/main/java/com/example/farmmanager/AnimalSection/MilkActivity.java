package com.example.farmmanager.AnimalSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmmanager.MatookeSection.AddThings;
import com.example.farmmanager.MatookeSection.Matooke;
import com.example.farmmanager.MatookeSection.MilkMatookeMgt;
import com.example.farmmanager.R;
import com.google.android.material.card.MaterialCardView;

public class MilkActivity extends AppCompatActivity {

    ImageView closemilktimelin;
    MaterialCardView milktimeline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk);

        closemilktimelin = findViewById(R.id.closemilktimelin);
        milktimeline = findViewById(R.id.milktimeline);

        closemilktimelin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                milktimeline.setVisibility(View.GONE);
            }
        });
    }

    public void goBack(View view) {
        startActivity(new Intent(MilkActivity.this, MilkMatookeMgt.class));
    }


    public void openMilkTimeline(View view) {
        milktimeline.setVisibility(View.VISIBLE);
    }

    public void openMilkMorning(View view) {
String mmm = "Morning";
Intent mm = new Intent(MilkActivity.this,MilkResults.class);
mm.putExtra("time",mmm);
startActivity(mm);
    }

    public void openMilkAfternoon(View view) {
        String mmm = "Afternoon";
        Intent mm = new Intent(MilkActivity.this,MilkResults.class);
        mm.putExtra("time",mmm);
        startActivity(mm);
    }

    public void openMilkEvening(View view) {
        String mmm = "Evening";
        Intent mm = new Intent(MilkActivity.this,MilkResults.class);
        mm.putExtra("time",mmm);
        startActivity(mm);
    }

    public void openMilkReports(View view) {
        /**gets all the results without separating them... remember to add a filter option*/
        String mmm = "";
        Intent mm = new Intent(MilkActivity.this,MilkResults.class);
        mm.putExtra("time",mmm);
        startActivity(mm);    }

    public void openAddMilk(View view) {
        String from = "Milk";
        Intent gg = new Intent(MilkActivity.this, AddThings.class);
        gg.putExtra("type","");
        gg.putExtra("from",from);
        startActivity(gg);

    }
}