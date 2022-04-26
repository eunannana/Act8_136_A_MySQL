package com.example.act8_136_a_mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TemanBaru extends AppCompatActivity {
    private EditText tNama, tTelpon;
    private Button simpanBtn;
    String nm, tlp;
    int success;

    private static String url_insert = "http://10.0.2.2/umyTI/tambahtm.php";
    private static final String TAG = TemanBaru.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teman_baru);

        tNama = findViewById(R.id.tietNama);
        tTelpon = findViewById(R.id.tietTelpon);
        simpanBtn = findViewById(R.id.buttonSave);

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();
            }
        });
    }
    public void SaveData()
    {
    if(tNama.getText().toString().equals("") || tTelpon.getText().toString().equals("")){
        Toast.makeText(TemanBaru.this, "All data is required!", Toast.LENGTH_SHORT).show();
    }else{
        nm = tNama.getText().toString();
        tlp = tTelpon.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest strReq = new StringRequest(Request.Method.POST, url_insert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "response : " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Toast.makeText(TemanBaru.this, "Successfully saved Data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TemanBaru.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error : " + error.getMessage());
                Toast.makeText(TemanBaru.this,"Failed to Save Data", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("nama", nm);
                params.put("telpon", tlp);

                return params;
            }
        };
        requestQueue.add(strReq);
    }
    }
}
