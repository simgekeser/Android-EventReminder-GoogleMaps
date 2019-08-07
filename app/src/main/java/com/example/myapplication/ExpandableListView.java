package com.example.myapplication;

import java.io.IOException;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.myapplication.MapFragment.map;
import static com.example.myapplication.MapFragment.marker;


class ExpandableListAdapter extends BaseExpandableListAdapter {



    List<Info> users;
    private Context _context;
    private String _listDataChild;
    String childText;
    String event_header = null;
    AppDatabase db,db_location;



    String [] categories = {"please select a type","concert","sport","meeting","other"};


    public ExpandableListAdapter(Context context, List<Info> users, String detail) {

        this._context= context;
        this._listDataChild= detail;
        this.users= users;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {



        childText = (String) users.get(groupPosition).getDetail();




        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

//DETAILBUTTON--------------------------------------------------------------------------------------
        Button detail = (Button)convertView.findViewById(R.id.button_detail);


        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) _context).closeDrawer();//before creating detail dialog,close drawer

                LayoutInflater infalInflater = (LayoutInflater) _context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = infalInflater.inflate(R.layout.detail_dialog, null);


                Button update =(Button)v.findViewById(R.id.update);
                Button cancel = (Button)v.findViewById(R.id.cancel);
                final EditText event = (EditText)v.findViewById(R.id.event_detail);
                final EditText detail =(EditText)v.findViewById(R.id.detail_detail);
                final Spinner spinner =(Spinner)v.findViewById(R.id.spinner_detail);

//--------------------------------------------------------------------------------------------------

                final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(_context,android.R.layout.simple_spinner_item,categories);

                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter);


//--------------------------------------------------------------------------------------------------



                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_context);


                         //   info.setId(groupPosition);
                            detail.setText(childText);
                            event.setText(users.get(groupPosition).getEvent());

                                if(users.get(groupPosition).getSpinner().equals("meeting")){

                                    spinner.setSelection(3);
                                }
                                 else if(users.get(groupPosition).getSpinner().equals("concert")){

                                    spinner.setSelection(1);
                                }
                                 else if(users.get(groupPosition).getSpinner().equals("sport")){

                                    spinner.setSelection(2);
                                }
                                 else {

                                    spinner.setSelection(4);
                                }

                db = AppDatabase.getAppDatabase(_context);//set the context to database

                alertDialogBuilder.setView(v);

                final AlertDialog alertDialog = alertDialogBuilder.create();

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            alertDialog.dismiss();
                    }

                });


//UPDATEBUTTON--------------------------------------------------------------------------------------
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Toast.makeText(_context,childText+"    "+ users.get(groupPosition).getSpinner(),Toast.LENGTH_LONG).show();

                        if(event.length()==0){
                            event.setError("event cant be empty");
                        }
                        else if(detail.length()==0){
                            detail.setError("Detail cant be empty");
                        }
                        else if(spinner.getSelectedItemPosition()==0){
                            Toast.makeText(_context,"you need to choose a type",Toast.LENGTH_SHORT).show();

                        }
                        else{
                             db.myDao().updateItem(users.get(groupPosition).getId(),event.getText().toString(),detail.getText().toString(), spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString());
                                alertDialog.dismiss();

                        }
                    }
                });

                alertDialog.show();

               // Toast.makeText(_context,childText+"    "+ users.get(groupPosition).getSpinner(),Toast.LENGTH_LONG).show();


            }
        });

//LOCATIONBUTTON--------------------------------------------------------------------------------------------------------
        Button location=(Button)convertView.findViewById(R.id.button_location);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) _context).closeDrawer();//calling closeDrawer() method from main activiy


                db_location = AppDatabase.getAppDatabase(_context);


                if(marker != null){
                    marker.remove();
                }

                marker = map.addMarker(new MarkerOptions().position(new LatLng(users.get(groupPosition).getLocation().lat, users.get(groupPosition).getLocation().lit)).draggable(true));

                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude), 2.0f));
                map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

                    @Override
                    public void onMarkerDragStart(Marker arg0) {

                    }

                    @SuppressWarnings("unchecked")
                    @Override
                    public void onMarkerDragEnd(Marker arg0) {
                        Log.d("System out", "onMarkerDragEnd...");
                        map.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));

                         new AlertDialog.Builder(_context)
                                .setMessage("Are you sure you want to update your location ?")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        db_location.myDao().updateLocation(users.get(groupPosition).getId(),marker.getPosition().latitude,marker.getPosition().longitude);
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })

                                .show();
                    }

                    @Override
                    public void onMarkerDrag(Marker arg0) {
                    }
                });
//----------------------------------------------------------------------------------------------------------------------------------------------------



            }
        });


        return convertView;
    }


    @Override
    public Object getGroup(int groupPosition) {

        return users.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return users.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

         event_header=users.get(groupPosition).getEvent();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

                TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);

                lblListHeader.setTypeface(null, Typeface.BOLD);
                lblListHeader.setText(event_header);// set Event name

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}