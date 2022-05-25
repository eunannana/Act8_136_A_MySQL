package com.example.act8_136_a_mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class edit_teman extends AppCompatActivity {
    TextView idText;
    EditText edNama, edTelp;
    Button edButton;
    String nma, tlp, id, namaEd, teleponEd;
    int success;

    private static String url_update = "http://20200140136.praktikumtiumy.com/updatetm.php";
    private static final String TAG = edit_teman.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teman);

        idText = findViewById(R.id.textId);
        edNama = findViewById(R.id.edNama);
        edTelp = findViewById(R.id.edTelp);
        edButton = findViewById(R.id.editButton);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("kunci1");
        nma = bundle.getString("kunci2");
        tlp = bundle.getString("kunci3");

        idText.setText("ID : " + id);
        edNama.setText(nma);
        edTelp.setText(tlp);

        edButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditData();
                }
            }
        );
    }
    public void EditData() {
        namaEd = edNama.getText().toString();
        teleponEd = edTelp.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringReq = new StringRequest(Request.Method.POST, url_update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response : " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Toast.makeText(edit_teman.this, "Successfully Edit Data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(edit_teman.this, "FAILED", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error : " + error.getMessage());
                Toast.makeText(edit_teman.this, "FAILED TO EDIT DATA", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("nama", namaEd);
                params.put("telpon", teleponEd);

                return params;
            }
        };
        requestQueue.add(stringReq);
        CallHomeActivity();
    }
    public void CallHomeActivity()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

}