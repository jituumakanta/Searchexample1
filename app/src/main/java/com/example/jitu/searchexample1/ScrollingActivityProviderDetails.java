package com.example.jitu.searchexample1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class ScrollingActivityProviderDetails extends AppCompatActivity {
    ImageView img;
    Bitmap bmp = null;
    URL url = null;
    TextView tvName,tvPublisher,tvUserinfo,tvNumber,tvEmail,tvArea,tvLandmark,tvAddress,tvReviews,tvWorkingtime,tvShopname;
    Button call;
    String Number;
    ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_provider_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        img = (ImageView) findViewById(R.id.imageView2);
        tvName = (TextView) findViewById(R.id.textView2);
        tvPublisher = (TextView) findViewById(R.id.textViewPublisher);
        tvUserinfo = (TextView) findViewById(R.id.textViewUserinfo);
        tvNumber = (TextView) findViewById(R.id.textViewNumber);
        tvEmail = (TextView) findViewById(R.id.textViewEmail);
        tvArea = (TextView) findViewById(R.id.textViewArea);
        tvLandmark = (TextView) findViewById(R.id.textViewLandmark);
        tvAddress = (TextView) findViewById(R.id.textViewAddress);
        tvReviews = (TextView) findViewById(R.id.textViewReviews);
        tvWorkingtime = (TextView) findViewById(R.id.textViewWorkingtime);
        tvShopname = (TextView) findViewById(R.id.textViewShopname);
        Bundle extras = getIntent().getExtras();
        String Name = extras.getString("Name");
        String Publisher = extras.getString("Publisher");
        String ImageUrl = extras.getString("ImageUrl");
        String Userinfo = extras.getString("Userinfo");
        Number = extras.getString("Number");
        String Email = extras.getString("Email");
        String Area = extras.getString("Area");
        String Landmark = extras.getString("Landmark");
        String Address = extras.getString("Address");
        String Reviews = extras.getString("Reviews");
        String Workingtime = extras.getString("Workingtime");
        String Shopname = extras.getString("Shopname");
        call=(Button)findViewById(R.id.call);
        Toast.makeText(getApplicationContext(), "Values are:\n First value: " + Name+ "\n Second Value: " + Publisher + "\n image is " + Userinfo, Toast.LENGTH_LONG).show();
        tvName.setText(Name);
        tvPublisher.setText(Publisher);
        // img.setText(ImageUrl);
        tvUserinfo.setText(Userinfo);
        tvNumber.setText(Number);
        tvEmail.setText(Email);
        tvArea.setText(Area);
        tvLandmark.setText(Landmark);
        tvAddress.setText(Address);
        tvReviews.setText(Reviews);
        tvWorkingtime.setText(Workingtime);
        tvShopname.setText(Shopname);
        mImageView=(ImageView)findViewById(R.id.imageView25);
        mImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // do stuff
                call();
            }

        });
        call.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
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
