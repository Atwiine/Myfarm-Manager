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
import com.example.farmmanager.Employees.Employees;
import com.example.farmmanager.MatookeSection.AddThings;
import com.example.farmmanager.Modals.EmployeeModel;
import com.example.farmmanager.R;
import com.example.farmmanager.Urls.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MilkViewHolder> {
    static Context context;
    public static  List<EmployeeModel> mData;
    Urls urls;

    public EmployeeAdapter(Context context, List<EmployeeModel> mData) {
        this.context = context;
        this.mData = mData;
        urls = new Urls();
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public MilkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_employess, null, false);
        return new MilkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MilkViewHolder holder, final int position) {

        EmployeeModel authorModel = mData.get(position);
        holder.name.setText(mData.get(position).getName());
        holder.id.setText(mData.get(position).getId());
        holder.number.setText(mData.get(position).getNumber());
        holder.role.setText(mData.get(position).getRole());
        holder.address.setText(mData.get(position).getAddress());
        holder.gender.setText(mData.get(position).getGender());
        holder.nextofkin.setText(mData.get(position).getNextofkin());
        holder.contactnextofkim.setText(mData.get(position).getContactnextofkim());
        holder.age.setText(mData.get(position).getAge());

        String rr = holder.role.getText().toString();
        if (rr.equals("Manager")){
            holder.linear_manager.setVisibility(View.GONE);
//            holder.role.getResources().getColor(R.color.redoringal);
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
                                                Intent intent = new Intent(context, Employees.class);
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
                                    params.put("from", "Employees");
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
    public static class MilkViewHolder extends RecyclerView.ViewHolder {

        TextView name,number,role,address,gender,id,nextofkin,contactnextofkim,edit,delete,age;
        RelativeLayout linear_manager;

        public MilkViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.names);
            number = itemView.findViewById(R.id.phone);
            role = itemView.findViewById(R.id.role);
            address = itemView.findViewById(R.id.address);
            gender = itemView.findViewById(R.id.gender);
            id = itemView.findViewById(R.id.id);
            nextofkin = itemView.findViewById(R.id.nextofkin);
            contactnextofkim = itemView.findViewById(R.id.contactnextofkim);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            age = itemView.findViewById(R.id.age);
            linear_manager = itemView.findViewById(R.id.linear_manager);

            /*EDITING OPTIONS */
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent request = new Intent(context, AddThings.class);
                    request.putExtra("name", mData.get(getAdapterPosition()).getName());
                    request.putExtra("number", mData.get(getAdapterPosition()).getNumber());
                    request.putExtra("role", mData.get(getAdapterPosition()).getRole());
                    request.putExtra("emid", mData.get(getAdapterPosition()).getId());
                    request.putExtra("address", mData.get(getAdapterPosition()).getAddress());
                    request.putExtra("gender", mData.get(getAdapterPosition()).getGender());
                    request.putExtra("nextofkin", mData.get(getAdapterPosition()).getNextofkin());
                    request.putExtra("contactnextofkim", mData.get(getAdapterPosition()).getContactnextofkim());
                    request.putExtra("age", mData.get(getAdapterPosition()).getAge());
                    request.putExtra("from", "EmployeeEdit");
                    context.startActivity(request);
                }
            });

        }
    }
}
