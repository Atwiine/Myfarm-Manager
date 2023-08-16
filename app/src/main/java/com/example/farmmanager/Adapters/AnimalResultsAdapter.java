package com.example.farmmanager.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.AnimalSection.AnimalResults;
import com.example.farmmanager.MatookeSection.AddThings;
import com.example.farmmanager.Modals.AnimalResultsModel;
import com.example.farmmanager.R;
import com.example.farmmanager.Urls.Urls;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalResultsAdapter extends RecyclerView.Adapter<AnimalResultsAdapter.MilkViewHolder> {
    Context context;
    public static List<AnimalResultsModel> mData;
    Urls urls;
    //    SessionManager sessionManager;
    String getTYPE;
    String getId, idPost, teacher;

    public AnimalResultsAdapter(Context context, List<AnimalResultsModel> mData) {
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
        view = LayoutInflater.from(context).inflate(R.layout.item_animalresults, null, false);
        return new MilkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MilkViewHolder holder, final int position) {

        AnimalResultsModel authorModel = mData.get(position);
        holder.tagnumber.setText(mData.get(position).getTagnumber());
        holder.gender.setText(mData.get(position).getGender());
        holder.id.setText(mData.get(position).getId());
        holder.date.setText(mData.get(position).getDate());
        holder.weight.setText(mData.get(position).getWeight());
        holder.checker.setText(mData.get(position).getChecker());
        holder.ptagnumber.setText(mData.get(position).getParent_tagnumber());
        holder.type.setText(mData.get(position).getType());
        holder.breed.setText(mData.get(position).getBreed());

//use the checker to see if the cow has any new borns attached to it
        String cc = holder.checker.getText().toString();
        if (cc.equals("Yes")) {
            holder.linear_parent.setVisibility(View.VISIBLE);
        } else {
            holder.linear_parent.setVisibility(View.GONE);
        }

        // check if type is equal to goat then hide the breed thing
        String bb = holder.type.getText().toString();
        if (bb.equals("Cattle")) {
            holder.linear_breed.setVisibility(View.VISIBLE);
        } else {
            holder.linear_breed.setVisibility(View.GONE);
        }

        //handle the showing of the new borns
        holder.cardnewborn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "coming soon", Toast.LENGTH_SHORT).show();
            }
        });


        /*DELLETING OPTIONS*/
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linear_delete.setVisibility(View.VISIBLE);
                final String reason = holder.reason.getText().toString();
                final String bid = holder.id.getText().toString();
                final String type = holder.type.getText().toString();

                if (reason.isEmpty()) {
                    holder.reason.setError("Reason required");
                } else {
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
                                                    Intent intent = new Intent(context, AnimalResults.class);
                                                    intent.putExtra("type", type);
                                                    context.startActivity(intent);
                                                    ((Activity) context).finish();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Toast.makeText(context, "Record not deleted, please try again " + e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }, error -> {
                                    Toast.makeText(context, "Record not deleted, please check your network and try again", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();

                                }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("id", bid);
                                        params.put("from", "Animals");
                                        params.put("reason", reason);
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

        TextView tagnumber, gender, id, date, weight, checker, ptagnumber, edit, delete, type, breed;
        LinearLayout linear_parent, linear_delete,linear_breed;
        MaterialCardView cardnewborn;
        EditText reason;

        public MilkViewHolder(@NonNull View itemView) {
            super(itemView);

            tagnumber = itemView.findViewById(R.id.tagnumber);
            gender = itemView.findViewById(R.id.gender);
            date = itemView.findViewById(R.id.date);
            id = itemView.findViewById(R.id.id);
            weight = itemView.findViewById(R.id.weight);
            checker = itemView.findViewById(R.id.checker);
            cardnewborn = itemView.findViewById(R.id.cardnewborn);
            linear_parent = itemView.findViewById(R.id.linear_parent);
            ptagnumber = itemView.findViewById(R.id.ptagnumber);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            type = itemView.findViewById(R.id.type);
            breed = itemView.findViewById(R.id.breed);
            linear_delete = itemView.findViewById(R.id.linear_delete);
            reason = itemView.findViewById(R.id.reason);
            linear_breed = itemView.findViewById(R.id.linear_breed);


            /*EDITING OPTIONS */
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent request = new Intent(context, AddThings.class);
                    request.putExtra("tagnumber", mData.get(getAdapterPosition()).getTagnumber());
                    request.putExtra("gender", mData.get(getAdapterPosition()).getGender());
                    request.putExtra("weight", mData.get(getAdapterPosition()).getWeight());
                    request.putExtra("id", mData.get(getAdapterPosition()).getId());
                    request.putExtra("ptagnumber", mData.get(getAdapterPosition()).getParent_tagnumber());
                    request.putExtra("type", mData.get(getAdapterPosition()).getType());
                    request.putExtra("checker", mData.get(getAdapterPosition()).getChecker());
                    request.putExtra("from", "Animal");
                    context.startActivity(request);
                }
            });


        }
    }
}
