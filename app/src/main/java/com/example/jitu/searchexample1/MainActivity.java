package com.example.jitu.searchexample1;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.rating;
import static com.example.jitu.searchexample1.R.id.recyclerView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final int CONNECTION_TIMEOUT = 10000;//////////////////////////////////searchviews
    public static final int READ_TIMEOUT = 15000;
    private SimpleCursorAdapter myAdapter;
    SearchView searchView = null;
    List<String> arealist;
    String[] areaData;
    static String str;//////////////////////////////////////////////////////////////////search views
    private List<SuperHeroes> listSuperHeroes;//////////////////////////////////////// Recycle views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;///////////////////////////////////////////// Recycle views

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);////Initializing recycle Views
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //Initializing our superheroes list
        listSuperHeroes = new ArrayList<>();//////////////////////////////Initializing recycle Views
        ///////////////////////////////////////////////////////////////////////////////////////////
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ////////////////////////////////////////////////////////////////////////////////////////////
        final String[] from = new String[]{"fishName"};
        final int[] to = new int[]{android.R.id.text1};
        // setup SimpleCursorAdapter
        myAdapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        arealist = new ArrayList<String>();
        getAreaList();
        getServiceData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        // Get Search item from action bar and Get Search service
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final MenuItem searchItem1 = menu.findItem(R.id.action_settings);
        //searchItem1.setTitle("nmg");
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            searchView.setIconified(false);
            searchView.setSuggestionsAdapter(myAdapter);
            // Getting selected (clicked) item suggestion
            searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
                @Override
                public boolean onSuggestionClick(int position) {

                    // Add clicked text to search box
                    CursorAdapter ca = searchView.getSuggestionsAdapter();
                    Cursor cursor = ca.getCursor();
                    cursor.moveToPosition(position);
                    searchView.setQuery(cursor.getString(cursor.getColumnIndex("fishName")), false);
                    str = cursor.getString(cursor.getColumnIndex("fishName"));
                    //  Toast.makeText(MainActivity.this,cursor.getString(cursor.getColumnIndex("fishName")) , Toast.LENGTH_LONG).show();
                    searchItem1.setTitle(str);
                    return true;
                }

                @Override
                public boolean onSuggestionSelect(int position) {
                    return true;
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    // Filter data
                    final MatrixCursor mc = new MatrixCursor(new String[]{BaseColumns._ID, "fishName"});
                    for (int i = 0; i < areaData.length; i++) {
                        if (areaData[i].toLowerCase().startsWith(s.toLowerCase()))
                            mc.addRow(new Object[]{i, areaData[i]});
                    }
                    myAdapter.changeCursor(mc);
                    return false;
                }
            });
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this,"dfs",Toast.LENGTH_LONG).show();
            Intent i=new Intent(getApplicationContext(),ProviderRegister.class);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {


        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (searchView != null) {
                searchView.clearFocus();
            }

            // User entered text and pressed search button. Perform task ex: fetching data from database and display

        }
    }

    private void getAreaList() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Config.DATA_GETAREA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray j;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONArray(response);
                            //Storing the Array of JSON String to our JSON Array
                            // result = j.getJSONArray(Config.JSON_ARRAY);
                            //Calling method getStudents to get the students from the JSON Array
                            getPraseAreaList(j);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getPraseAreaList(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                arealist.add(json.getString(Config.TAG_AREANAME));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        areaData = arealist.toArray(new String[arealist.size()]);
        // Toast.makeText(MainActivity.this, "dddd" + areaData[2], Toast.LENGTH_LONG).show();

    }

    private void getServiceData() {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);

        //Creating a json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DATA_GETALLIMAGES,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing progress dialog
                        loading.dismiss();

                        //calling method to parse json array
                        parseServiceData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating request queue
        //RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
       // requestQueue.add(jsonArrayRequest);
        CustomVolleyRequest.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    private void parseServiceData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            SuperHeroes superHero = new SuperHeroes();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                superHero.setImageUrl(json.getString(Config.TAG_IMAGE_URL));
                superHero.setName(json.getString(Config.TAG_NAME));
                superHero.setRank(json.getString(Config.TAG_RANK));
                ArrayList<String> powers = new ArrayList<String>();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            listSuperHeroes.add(superHero);
        }

        //Finally initializing our adapter
        adapter = new CardAdapter(listSuperHeroes, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }

    public String getNamee() {
        return str;
    }

}
