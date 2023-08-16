package com.example.farmmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.farmmanager.R;
import com.example.farmmanager.Urls.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;

import java.util.HashMap;
import java.util.Objects;


public class SettingsActivity extends BottomSheetDialogFragment {

    SessionManager sessionManager;
    String getName, getPhone, dd;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {

        sessionManager = new SessionManager(requireActivity());
        HashMap<String, String> user = sessionManager.getUserDetail();
        getName = user.get(SessionManager.FULLNAME);
        getPhone = user.get(SessionManager.CONTACT);

        View v = inflater.inflate(R.layout.activity_settings,
                container, false);

        LinearLayout linear_logout = v.findViewById(R.id.linear_logout);
        TextView names = v.findViewById(R.id.names);
        TextView contact = v.findViewById(R.id.contact);
        MaterialCardView message = v.findViewById(R.id.message);
        MaterialCardView call = v.findViewById(R.id.call);

        names.setText(getName);
        contact.setText(getPhone);

        linear_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        requireActivity());
                alertDialog2.setTitle("Confirm to proceed");
                alertDialog2.setMessage("Are you sure you want to logout");
                alertDialog2.setPositiveButton("YES",
                        (dialog, which) -> sessionManager.logout());
                alertDialog2.setNegativeButton("NO",
                        (dialog, which) -> dialog.cancel());
                alertDialog2.show();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "099553232";
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
    public void onBackPressed()
    {
        return;
    }
}