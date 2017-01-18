package com.example.jitu.searchexample1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
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
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.common.collect.Range;

import java.util.HashMap;
import java.util.Map;

public class ProviderRegister extends AppCompatActivity implements View.OnClickListener {

    private static final String REGISTER_URL = "http://hellohelps.com/HelloHelps/XXProvderRegister.php";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_MOBNO = "mobno";
    public static final String KEY_AREANAME = "areaname";
    public static final String KEY_USERINFO = "userinfo";
    public static final String KEY_NAME = "name";
    public static final String KEY_SERVICE= "service";
    public static final String KEY_CITY = "city";


    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextmobno;
    private EditText editTextareaname;
    private EditText editTextuserinfo;
    private EditText editTextname;
    private EditText editTextservice;
    private EditText editTextcity;

    private Button buttonRegister;
    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_register);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail= (EditText) findViewById(R.id.editTextEmail);
        editTextmobno= (EditText) findViewById(R.id.editTextMobno);
        editTextareaname= (EditText) findViewById(R.id.editTextAreaname);
        editTextuserinfo= (EditText) findViewById(R.id.editTextUserinfo);
        editTextservice= (EditText) findViewById(R.id.editTextService);
        editTextname= (EditText) findViewById(R.id.editTextName);
        editTextcity= (EditText) findViewById(R.id.editTextCity);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.editTextName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.editTextEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.editTextMobno, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
       // awesomeValidation.addValidation(this, R.id.editTextDob, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.nameerror);
        //awesomeValidation.addValidation(this, R.id.editTextAge, Range.closed(13, 60), R.string.ageerror);

        buttonRegister.setOnClickListener(this);
    }

    private void registerUser(){
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String mobno = editTextmobno.getText().toString().trim();
        final String areaname = editTextareaname.getText().toString().trim();
        final String userinfo = editTextuserinfo.getText().toString().trim();
        final String service = editTextservice.getText().toString().trim();
        final String name = editTextname.getText().toString().trim();
        final String city = editTextcity.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ProviderRegister.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProviderRegister.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,username);
                params.put(KEY_PASSWORD,password);
                params.put(KEY_EMAIL, email);
                params.put(KEY_MOBNO, mobno);
                params.put(KEY_AREANAME, areaname);
                params.put(KEY_USERINFO, userinfo);
                params.put(KEY_SERVICE, service);
                params.put(KEY_NAME, name);
                params.put(KEY_CITY, city);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successfull
        if (awesomeValidation.validate()) {
            Toast.makeText(this, "Validation Successfull", Toast.LENGTH_LONG).show();
            registerUser();
            //process the data further
        }
    }
    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            submitForm();
        }
    }
}
