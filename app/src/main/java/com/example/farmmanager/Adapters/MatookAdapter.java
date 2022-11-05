package com.example.farmmanager.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmmanager.Employees.Employees;
import com.example.farmmanager.MatookeSection.AddThings;
import com.example.farmmanager.MatookeSection.Matooke;
import com.example.farmmanager.Modals.MatookeModel;
import com.example.farmmanager.R;
import com.example.farmmanager.Urls.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatookAdapter extends RecyclerView.Adapter<MatookAdapter.MilkViewHolder> {
    Context context;
    public static  List<MatookeModel> mData;
    Urls urls;

    public MatookAdapter(Context context, List<MatookeModel> mData) {
        this.context = context;
        this.mData = mData;
        urls = new Urls();
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public MilkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_matooke, null, false);
        return new MilkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MilkViewHolder holder, final int position) {

        MatookeModel authorModel = mData.get(position);
        holder.total_bunches.setText(mData.get(position).getNumberBanches());
        holder.id.setText(mData.get(position).getId());
        holder.date.setText(mData.get(position).getDate());


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
                                                Intent intent = new Intent(context, Matooke.class);
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
                                    params.put("from", "Matooke");
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

        TextView total_bunches, id,date,edit,delete;

        public MilkViewHolder(@NonNull View itemView) {
            super(itemView);

            total_bunches = itemView.findViewById(R.id.total_bunches);
            date = itemView.findViewById(R.id.date);
            id = itemView.findViewById(R.id.id);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);

            /*EDITING OPTIONS */
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent request = new Intent(context, AddThings.class);
                    request.putExtra("total_bunches", mData.get(getAdapterPosition()).getNumberBanches());
                    request.putExtra("date", mData.get(getAdapterPosition()).getDate());
                    request.putExtra("id", mData.get(getAdapterPosition()).getId());
                    context.startActivity(request);
                }
            });

        }
    }
}
