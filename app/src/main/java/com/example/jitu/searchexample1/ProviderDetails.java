package com.example.jitu.searchexample1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;

import static com.example.jitu.searchexample1.R.id.call;

public class ProviderDetails extends AppCompatActivity {
    ImageView img;
    Bitmap bmp = null;
    URL url = null;
    TextView tvName,tvPublisher,tvUserinfo,tvNumber,tvEmail,tvArea;
    Button call;
    String Number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_details);
        img = (ImageView) findViewById(R.id.imageView2);
        tvName = (TextView) findViewById(R.id.textView2);
        tvPublisher = (TextView) findViewById(R.id.textViewPublisher);
        tvUserinfo = (TextView) findViewById(R.id.textViewUserinfo);
        tvNumber = (TextView) findViewById(R.id.textViewNumber);
        tvEmail = (TextView) findViewById(R.id.textViewEmail);
        tvArea = (TextView) findViewById(R.id.textViewArea);
        Bundle extras = getIntent().getExtras();
        String Name = extras.getString("Name");
        String Publisher = extras.getString("Publisher");
        String ImageUrl = extras.getString("ImageUrl");
        String Userinfo = extras.getString("Userinfo");
         Number = extras.getString("Number");
        String Email = extras.getString("Email");
        String Area = extras.getString("Area");
        call=(Button)findViewById(R.id.call);
        Toast.makeText(getApplicationContext(), "Values are:\n First value: " + Name+ "\n Second Value: " + Publisher + "\n image is " + Userinfo, Toast.LENGTH_LONG).show();
        tvName.setText(Name);
        tvPublisher.setText(Publisher);
      // img.setText(ImageUrl);
        tvUserinfo.setText(Userinfo);
        tvNumber.setText(Number);
        tvEmail.setText(Email);
        tvArea.setText(Area);

        call.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                call();
            }
        });
       /* try {
            url = new URL(value3);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        img.setImageBitmap(bmp);*/



        ImageRequest request = new ImageRequest(ImageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        img.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        img.setImageResource(R.drawable.image_load_error);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.Floatcall);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });
    }
    private void call() {
        //Toast.makeText(getApplicationContext(),Number,Toast.LENGTH_LONG).show();
        String uri = "tel:"+Number;
        Intent in=new Intent(Intent.ACTION_CALL, Uri.parse(uri));
        try{
            startActivity(in);
        }

        catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
        }
    }
}

