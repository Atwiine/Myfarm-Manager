package com.example.farmmanager.Chat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.farmmanager.Adapters.SingleChatAdapter;
import com.example.farmmanager.Adapters.fChatAdapter;
import com.example.farmmanager.MainActivity;
import com.example.farmmanager.Modals.ChatModel;
import com.example.farmmanager.Modals.fChatModel;
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

public class SinglesChat extends AppCompatActivity {

    RecyclerView recyclerView, frecyclerView;
    List<ChatModel> mData;
    List<fChatModel> fmData;
    SingleChatAdapter adapter;
    fChatAdapter fadapter;
    TextView messageText;
    SessionManager sessionManager;
    String getId;
    String usernames;
    String farmname;
    TextView error_message,sName,sID;
    ProgressBar progressBar;
    ImageView sendBtn,image_nochat,sImage;
    TextView fphone, you, patient, no_chat,check_new,sending_chat,notsending_chat;
    Urls urls;
    Handler mHandler;
    ProgressDialog mProgressBar;
    /***
     * remove the un wanted toasts and correct the ones needed*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singles_chat);

        urls = new Urls();
        //handle session manager
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        usernames = user.get(SessionManager.FULLNAME);
        farmname = user.get(SessionManager.FARMNAME);

        fphone = findViewById(R.id.farmer_phone);
        you = findViewById(R.id.you);
        patient = findViewById(R.id.farmer);
        no_chat = findViewById(R.id.no_chat);
        progressBar = findViewById(R.id.progressBar);
        error_message = findViewById(R.id.error_message);
        messageText = findViewById(R.id.messageArea);
        sendBtn = findViewById(R.id.sendButton);
        image_nochat = findViewById(R.id.image_nochat);

//        sName = findViewById(R.id.sName);
//        sID = findViewById(R.id.sID);
//        check_new = findViewById(R.id.check_new);
        sending_chat = findViewById(R.id.sending_chat);
        notsending_chat = findViewById(R.id.notsending_chat);



        /*update new posts to seen*/
//        String bww = check_new.getText().toString();
//        if (!bww.isEmpty()){
//            updateNew();
//        }

        
        sendBtn.setOnClickListener(view -> {
            String text_sms = messageText.getText().toString();
            if (text_sms.isEmpty()) {
                messageText.setError("type something for message");
            } else {
                sendSMS();
            }
        });


        //handle recyclerview
        recyclerView = findViewById(R.id.recycle_messages);
        mData = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recycle_messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter = new SingleChatAdapter(getApplicationContext(), mData);

//        mHandler = new Handler();
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                getSMS();
//
//                mHandler.postDelayed(this, 1000);
//            }
//        };
//
//        mHandler.post(runnable);


        mHandler=new Handler();
//        mProgressBar= new ProgressDialog(SinglesChat.this);
//        mProgressBar.setMax(100);
//        mProgressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        mProgressBar.show();
        new Thread(new Runnable() {
            @Override
            public void run() {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        Clear();
                        getSMS();
                        mHandler.postDelayed(this, 1000);
//                        Toast.makeText(SinglesChat.this, "aaaaaa", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    private void sendSMS() {

        sending_chat.setVisibility(View.VISIBLE);
        final String scontact = usernames;
        final String message = messageText.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.SEND_SINGLE_CHAT,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject object = new JSONObject(response);
                        String success = object.getString("success");
                        if (success.equals("1")) {
                            sending_chat.setVisibility(View.GONE);
                            messageText.setText("");
                            getSMS();
                            Clear();

                        } else if (success.equals("0")) {
                            sending_chat.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Message Not sent", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        sending_chat.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Something went wrong, please try again " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            sending_chat.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Something went wrong, please check your connection and try again " + error.toString(), Toast.LENGTH_SHORT).show();
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sender", scontact);//name of the sender
                params.put("userid", getId);//their id
                params.put("message", message);// and message
                params.put("farmname", farmname);// and message
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void updateNew() {

       final String reciever_id = sID.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.USEENPRIATE_CHAT,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject object = new JSONObject(response);
                        String success = object.getString("success");
                        if (success.equals("1")) {
//                            getSMS();
//                            Clear();
                        } else if (success.equals("0")) {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(), "update failed " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "update failedss " + error.toString(), Toast.LENGTH_SHORT).show();
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userid", getId);//use your own id to update if seen messages and has to be equal to
                // receiver's id
                    params.put("reciever_id", reciever_id);
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getSMS() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.GET_SINGLE_MESSAGES,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray sms = new JSONArray(response);
                        if (sms.length() == 0) {
                            progressBar.setVisibility(View.GONE);
                            error_message.setVisibility(View.GONE);
                            image_nochat.setVisibility(View.VISIBLE);

                        } else {
                            for (int i = 0; i < sms.length(); i++) {
                                JSONObject smsObjects = sms.getJSONObject(i);

                                String message = smsObjects.getString("message");
                                String time = smsObjects.getString("time");
                                String sender = smsObjects.getString("sender");
                                String senderid = smsObjects.getString("userid");

                                progressBar.setVisibility(View.GONE);
                                error_message.setVisibility(View.INVISIBLE);
                                image_nochat.setVisibility(View.GONE);
                                ChatModel chatModel = new ChatModel(message,
                                        time,
                                        "reciever_name",
                                        sender,
                                        "image",
                                        senderid);
                                mData.add(chatModel);
                            }
                        }

                        adapter = new SingleChatAdapter(getApplicationContext(), mData);
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        error_message.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "something went wrong, swipe down to try again", Toast.LENGTH_LONG).show();

                    }

                }, error -> {
            progressBar.setVisibility(View.GONE);
            error_message.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "could not load messages, please check your internet connection and try again" , Toast.LENGTH_LONG).show();
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("farmname", farmname);// and message
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
//        adapter.notifyItemChanged(mData.size());
    }

    public void goBack(View view) {
        startActivity(new Intent(SinglesChat.this,MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed()
    {
        return;
    }
}