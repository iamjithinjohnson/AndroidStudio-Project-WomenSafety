package com.example.jithin.womensafety;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Friendsactivity extends AppCompatActivity {
    ArrayList<ContactData> ContactDataArrayList;
    ArrayList<String> a;
    private ExampleAdapter rvAdapter;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;



    EditText name,number;
    Spinner type;
    LinearLayout insert;
    String na,phn,ty,con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendsactivity);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        recyclerView = (RecyclerView) findViewById(R.id.or);
        fetchingJSON();

       // setInsertButton();

        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.number);
        type = (Spinner) findViewById(R.id.type);
        insert = (LinearLayout) findViewById(R.id.insert);

        con = SharedPrefManager.getInstance(this).getKeyUsername();

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(i, 1);
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  setInsertButton();
                }
            });


    }

    private void fetchingJSON(){

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.URL_CONTACT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("Response : ",response);
                        try {
                            ContactDataArrayList = new ArrayList<>();

                            JSONArray array =  new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject obj = array.getJSONObject(i);

                                ContactDataArrayList.add(new ContactData(
                                        obj.getString("id"),
                                        obj.getString("friendname"),
                                        obj.getString("contact"),
                                        obj.getString("type")

                                ));

                            }
                            setupRecycler();

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String, String> params = new HashMap<>();
                params.put("username", con);

                return params;
            }

        };

        // RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private  void setInsertButton(){
        na = name.getText().toString();
        phn = number.getText().toString();
        ty = type.getSelectedItem().toString();

        if (TextUtils.isEmpty(na)){
            name.setError("Enter name");
            return;
        }
        else if (TextUtils.isEmpty(phn)){
            number.setError("Enter number");
            return;
        }
        else if (!isPhoneValid(phn)){
            number.setError("Invalid number");
            return;
        }

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(),"Successfully Inserted", Toast.LENGTH_LONG).show();
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
                params.put("friendname", na);
                params.put("contact", phn);
                params.put("type", ty);
                params.put("username", con);


                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }



 //   private void setInsertButton() {

 //   }



    public static boolean isPhoneValid(String s) {
        Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }
    private void loadData() {

    }

    private void setupRecycler(){

        rvAdapter = new ExampleAdapter(this, ContactDataArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contactsData = data.getData();
                CursorLoader loader = new CursorLoader(this, contactsData, null, null, null, null);
                Cursor c = loader.loadInBackground();
                if (c.moveToFirst()) {

                    String nam = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String num = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    name.setText(nam);
                    number.setText(num);
                }
            }
        }
    }





}
