package com.example.myapplication;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Login.Event;
import com.example.myapplication.Login.Login_fragment;
import com.example.myapplication.Login.Register;
import com.google.android.gms.maps.model.LatLng;

public class Dialog_ extends DialogFragment implements AdapterView.OnItemSelectedListener {


    String [] categories = {"please select a type","concert","sport","meeting","other"};
    String text;

    AppDatabase db;



    public android.app.Dialog onCreateDialog(Bundle savedInstanceState){

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog, null);

        Button save = (Button)view.findViewById(R.id.save);
        Button cancel = (Button)view.findViewById(R.id.cancel);

        final EditText event = (EditText)view.findViewById(R.id.event);
        final EditText detail =(EditText)view.findViewById(R.id.detail);
        final Spinner spinner =(Spinner)view.findViewById(R.id.spinner);

        db = AppDatabase.getAppDatabase(getContext());


//------------------------------------Spinner-------------------------------


        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,categories);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);


        //-----------------------------------------------------------------

//------------------------------------------------------------------------------------
        alert.setView(view);
        final android.app.Dialog dialog = alert.create();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (event.length() == 0) {
                    event.setError("event cant be empty");
                } else if (detail.length() == 0) {
                    detail.setError("Detail cant be empty");
                } else if (spinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "you need to choose a type", Toast.LENGTH_SHORT).show();

                } else {

                    LatLng ll = MapFragment.marker.getPosition();
                    double lat=  ll.latitude;
                    double lng=  ll.longitude;

                    Register register = new Register();
                    Login_fragment login_fragment= new Login_fragment();



                   Info info = new Info(event.getText().toString(), detail.getText().toString(), spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString(),lat,lng,login_fragment.username);


                    db.myDao().insertAll(info);
                    //  db.MyDao().updateUsers(info);
                    dialog.dismiss();
                }

            }
        });


        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
//-----------------------------------------------------------------------------------------

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
        // Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
