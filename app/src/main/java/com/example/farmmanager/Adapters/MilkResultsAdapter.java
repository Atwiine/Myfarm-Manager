package com.example.farmmanager.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.AnimalSection.MilkResults;
import com.example.farmmanager.Employees.Employees;
import com.example.farmmanager.MatookeSection.AddThings;
import com.example.farmmanager.Modals.MilkResultsModel;

import com.example.farmmanager.R;
//import com.example.farm.Urls.SessionManager;
import com.example.farmmanager.Urls.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MilkResultsAdapter extends RecyclerView.Adapter<MilkResultsAdapter.MilkViewHolder> {
    Context context;
    public static  List<MilkResultsModel> mData;
    Urls urls;
//    SessionManager sessionManager;
    String  getTYPE;
    String getId, idPost, teacher;

    public MilkResultsAdapter(Context context, List<MilkResultsModel> mData) {
        this.context = context;
        this.mData = mData;
        urls = new Urls();
//        sessionManager = new SessionManager(context);
//        HashMap<String, String> user = sessionManager.getUserDetail();
//        getId = user.get(SessionManager.ID);
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public MilkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_milkresults, null, false);
        return new MilkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MilkViewHolder holder, final int position) {

        MilkResultsModel authorModel = mData.get(position);
        holder.home_litres.setText(mData.get(position).getHome());
        holder.diary_litres.setText(mData.get(position).getDiary());
        holder.id.setText(mData.get(position).getId());
        holder.date.setText(mData.get(position).getDate());
        holder.total_litres.setText(mData.get(position).getTotal());
        holder.comment.setText(mData.get(position).getComment());
        holder.timesent.setText(mData.get(position).getTime());


        /*check to see if there is a comment and show the comment section*/
        String cc = holder.comment.getText().toString();
        if (cc.isEmpty()){
            holder.linear_comment.setVisibility(View.GONE);
        }else {
            holder.linear_comment.setVisibility(View.VISIBLE);
        }

        //check which timeslot it is and then display the right signal
        String tt = holder.timesent.getText().toString();
        switch (tt) {
            case "Morning":
                holder.relmorning.setVisibility(View.VISIBLE);
                break;
            case "Afternoon":
                holder.relafternoon.setVisibility(View.VISIBLE);
                break;
            case "Evening":
                holder.relevening.setVisibility(View.VISIBLE);
                break;
        }

        /*DELLETING OPTIONS*/
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String bid = holder.id.getText().toString();
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setTitle("You are about to delete this record.")
                        .setMessage("Are you sure you want to delete this record from your collection permanently?. Please note that once deleted, it cannot be undone")
                        .setCancelable(false)
                        .setIcon(R.drawable.ic_warning)
                        .setPositiveButton("YES", (dialog, which) -> {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.DELETE_FILES_URL,
                                    response -> {
                                        try {
                                            Log.i("tagconvertstr", "[" + response + "]");
                                            JSONObject object = new JSONObject(response);
                                            String success = object.getString("success");
                                            if (success.equals("1")) {
                                                Log.i("tagconvertstr", "[" + response + "]");

                                                Toast.makeText(context, "Record deleted successfully,", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(context, MilkResults.class);
                                                intent.putExtra("time",tt);
                                                context.startActivity(intent);
                                                ((Activity) context).finish();
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(context, "Record not deleted, please try again ", Toast.LENGTH_LONG).show();

                                        }
                                    }, error -> {
                                Toast.makeText(context, "Record not deleted, please check your network and try again", Toast.LENGTH_LONG).show();
                                dialog.dismiss();

                            }) {

                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id", bid);
                                    params.put("from", "Milk");
                                    return params;

                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            requestQueue.add(stringRequest);
                        })
                        .setNegativeButton("NO", (dialog, which) -> dialog.dismiss());
                //Creating dialog box
                android.app.AlertDialog dialog = builder.create();
                dialog.show();

            }

        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void clear() {
        int size = mData.size();
        if (size > 0) {
            mData.subList(0, size).clear();

            notifyItemRangeRemoved(0, size);
        }
    }
    public class MilkViewHolder extends RecyclerView.ViewHolder {

        TextView home_litres, diary_litres, id,date,total_litres,comment,timesent,edit,delete;
        LinearLayout linear_comment;
        RelativeLayout relevening,relafternoon,relmorning;

        public MilkViewHolder(@NonNull View itemView) {
            super(itemView);

            home_litres = itemView.findViewById(R.id.home_litres);
            diary_litres = itemView.findViewById(R.id.diary_litres);
            date = itemView.findViewById(R.id.date);
            id = itemView.findViewById(R.id.id);
            total_litres = itemView.findViewById(R.id.total_litres);
            comment = itemView.findViewById(R.id.comment);
            linear_comment = itemView.findViewById(R.id.linear_comment);
            relevening = itemView.findViewById(R.id.relevening);
            relafternoon = itemView.findViewById(R.id.relafternoon);
            relmorning = itemView.findViewById(R.id.relmorning);
            timesent = itemView.findViewById(R.id.timesent);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            /*EDITING OPTIONS */
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent request = new Intent(context, AddThings.class);
                    request.putExtra("total", mData.get(getAdapterPosition()).getTotal());
                    request.putExtra("home", mData.get(getAdapterPosition()).getHome());
                    request.putExtra("diary", mData.get(getAdapterPosition()).getDiary());
                    request.putExtra("id", mData.get(getAdapterPosition()).getId());
                    request.putExtra("date", mData.get(getAdapterPosition()).getDate());
                    request.putExtra("timesent", mData.get(getAdapterPosition()).getTime());
                    request.putExtra("comment", mData.get(getAdapterPosition()).getComment());
                    request.putExtra("from", "MilkEdit");
                    context.startActivity(request);
                }
            });

        }
    }
}
