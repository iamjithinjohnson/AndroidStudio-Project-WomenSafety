package com.example.jithin.womensafety;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Viewfriend extends AppCompatActivity {
    String phon,nam,typ;
    TextView phone,name,type;
    ArrayList<Exampleitem> mExampleList;
    LinearLayout delete;
    String con;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfriend);

        progressDialog = new ProgressDialog(this);

        con = SharedPrefManager.getInstance(this).getKeyUsername();

        phone = (TextView) findViewById(R.id.phone);
        name = (TextView) findViewById(R.id.name);
        type = (TextView) findViewById(R.id.type);
        delete = (LinearLayout) findViewById(R.id.delete);

        Intent i=getIntent() ;
        typ=i.getStringExtra("type");
        nam=i.getStringExtra("name");
        phon=i.getStringExtra("no");


        type.setText("Type : "+typ);
        name.setText("Name : "+nam);
        phone.setText("Number : "+phon);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();

            }
        });
    }

    private void remove() {

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.URL_REMOVE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("Response : ",response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(getApplicationContext(),Friendsactivity.class));
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String, String> params = new HashMap<>();
                params.put("contact", phon);
                params.put("username", con);

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

}
