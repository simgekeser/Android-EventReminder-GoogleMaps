package com.example.myapplication;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.myapplication.Login.Login_fragment;

import java.util.List;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

   // RecyclerView recyclerView;
   // RecyclerView.Adapter adapter;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    String listDataChild;
    Toolbar toolbar;

    AppDatabase db;
    Login_fragment login_fragment=new Login_fragment();
    public DrawerLayout drawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationDrawer();
        createMapFragment();
     // createRecyclerView();
        createExpandableListView();


    }



    private void navigationDrawer() {

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void createExpandableListView() {

        db=AppDatabase.getAppDatabase(getApplicationContext());

        db.myDao().getAllInfo(login_fragment.username).observe(this, new Observer<List<Info>>() {
            @Override
            public void onChanged(@Nullable List<Info> infos) {

                expListView = (ExpandableListView) findViewById(R.id.lvExp);
                listAdapter = new ExpandableListAdapter(MainActivity.this,infos,listDataChild);

//closing previous item ------------------------------------------------------------------------------------
                final int[] prevExpandPosition = {-1};
                expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    @Override
                    public void onGroupExpand(int groupPosition) { //when item is clicked, close previous expandable listView item

                        if (prevExpandPosition[0] >= 0 && prevExpandPosition[0] != groupPosition) {
                            expListView.collapseGroup(prevExpandPosition[0]);
                        }
                        prevExpandPosition[0] = groupPosition;
                    }
                });

                expListView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();


            }
        });
    }


    private void createMapFragment() {

        MapFragment mapFragment = new MapFragment();
        FragmentManager manager= getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.linearLayout, mapFragment).commit(); //create mapFragment

    }

     /*  private void createRecyclerView() {
        //  recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        //  users = new ArrayList<>();



        //    recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db.MyDao().getAllInfo().observe(this, new Observer<List<Info>>() {
            @Override
            public void onChanged(@Nullable List<Info> infos) {

                adapter = new RecyclerAdapter(this,infos);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


    }*/

    public void closeDrawer() {
        drawer.closeDrawers(); //it closes drawer
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int button_id= item.getGroupId();
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
        return true;
    }

}
