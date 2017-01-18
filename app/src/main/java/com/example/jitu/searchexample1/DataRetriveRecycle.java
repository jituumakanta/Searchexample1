package com.example.jitu.searchexample1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.jitu.searchexample1.R.id.call;

public class DataRetriveRecycle extends AppCompatActivity {

    private List<SuperHeroes> listSuperHeroes1;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter1;
    //private RequestQueue requestQueue;
    private int requestCount = 1;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_retrive_recycle);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listSuperHeroes1 = new ArrayList<>();
       // requestQueue = Volley.newRequestQueue(this);
        getData();
        adapter1 = new CardAdapter1(listSuperHeroes1, this);
        recyclerView.setAdapter(adapter1);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLastItemDisplaying(recyclerView)) {
                    getData();
                }
            }
        });

      /*  call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(getApplicationContext(),"call",Toast.LENGTH_LONG).show();
            }
        });*/
    }

    private JsonArrayRequest getDataFromServer(int requestCount) {
        //Initializing ProgressBar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        //Displaying Progressbar
        progressBar.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Bundle extras = getIntent().getExtras();
        String city_name3 = extras.getString("Value41");
        String service_name3 = extras.getString("Value21");
        Toast.makeText(DataRetriveRecycle.this, city_name3 + " " + service_name3, Toast.LENGTH_LONG).show();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        String url = Config.DATA_XDATARETRIVERECYCLE + String.valueOf(requestCount) + "&Provider_City=" + city_name3 + "&Provider_Name=" + service_name3;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parseData(response);
                progressBar.setVisibility(View.GONE);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        Toast.makeText(DataRetriveRecycle.this, "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });
        return jsonArrayRequest;
    }

    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
       // requestQueue.add(getDataFromServer(requestCount));
        CustomVolleyRequest.getInstance(this).addToRequestQueue(getDataFromServer(requestCount));

        //Incrementing the request counter
        requestCount++;
    }

    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            SuperHeroes superHero1 = new SuperHeroes();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);
                //Adding data to the superhero object
                superHero1.setImageUrl(json.getString(Config.TAG_IMAGE_URL1));
                superHero1.setName(json.getString(Config.TAG_Provider_City1));
                superHero1.setPublisher(json.getString(Config.TAG_Provider_Name1));
                superHero1.setUserinfo(json.getString(Config.TAG_Userinfo1));
               // superHero1.setNumber(json.getString(Config.TAG_Number1));
               // superHero1.setEmail(json.getString(Config.TAG_Email1));
               // superHero1.setArea(json.getString(Config.TAG_Area1));
               // superHero1.setLandmark(json.getString(Config.TAG_Landmark1));
               // superHero1.setAddress(json.getString(Config.TAG_Address1));
              //  superHero1.setReviews(json.getString(Config.TAG_Reviews1));
              //  superHero1.setWorkingtime(json.getString(Config.TAG_Workingtime1));
              //  superHero1.setShopname(json.getString(Config.TAG_Shopname1));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            listSuperHeroes1.add(superHero1);
        }
        //Notifying the adapter that data has been added or changed
        adapter1.notifyDataSetChanged();
    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }


}


