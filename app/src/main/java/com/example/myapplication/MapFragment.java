package com.example.myapplication;

import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    static GoogleMap map;
    static FloatingActionButton fb;


    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.map_fragment, container, false);



//----------------------Floating action button---------------------------


        fb= (FloatingActionButton)v.findViewById(R.id.fab);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        selectLocation();

            }
        });

          return v;
    }
//------------------------------------------------------------------------
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    static Marker marker;
//-------------------------------selecting a location, clicking on marker--------------------------------
    public void selectLocation() {


        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {


                map.getUiSettings().setMapToolbarEnabled(false);

                if(marker != null){
                    marker.remove();
                }

                marker =  map.addMarker(new MarkerOptions().position(latLng));
               /* Geocoder gc= new Geocoder(new MapsActivity());
                LatLng ll = marker.getPosition();
                double lat= ll.latitude;
                double lng= ll.longitude;

                List<Address> list =null;

                try{
                    list=gc.getFromLocation(lat,lng,1);
                }catch(IOException e){
                    e.printStackTrace();
                }
                Address add = list.get(0);
                marker.setTitle(add.getLocality());
                marker.showInfoWindow();*/


                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude, latLng.longitude), 2.0f));

                floatingAction();

 }
        });

    }
//-------------------Changing Floating Action Button and Creating a new Dialog_-------------------------

    public void floatingAction(){

        fb.setImageResource(R.drawable.ic_check_black_24dp);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(getContext(), "tik clicked", Toast.LENGTH_SHORT).show();


                Dialog_ dia = new Dialog_();
                dia.show(getFragmentManager(),"d");
            }
        });

    }
//-------------------Map-----------------------------
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map= googleMap;

    }
}