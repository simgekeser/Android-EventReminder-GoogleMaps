package com.example.myapplication.Login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.AppDatabase;
import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {

    AppDatabase db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_register, container, false);


        final EditText id=(EditText)v.findViewById(R.id.id_register);
        final EditText name =(EditText)v.findViewById(R.id.name_register);
        final EditText surname=(EditText)v.findViewById(R.id.surname_register);
        final EditText password=(EditText)v.findViewById(R.id.password_register);
        final EditText email=(EditText)v.findViewById(R.id.email_register);

        Button register=(Button)v.findViewById(R.id.button_register);
        Button cancel=(Button)v.findViewById(R.id.button_cancelregister);


        db=AppDatabase.getAppDatabase(getContext());

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(id.length()==0){
                    id.setError("cant be empty");
                }else if(name.length()==0){
                    name.setError("cant be empty");
                }else if(surname.length()==0){
                    surname.setError("cant be empty");
                }else if(email.length()==0){
                    email.setError("cant be empty");
                }else if(password.length()==0){
                    surname.setError("cant be empty");
                }else{


                    Event event =new Event(id.getText().toString(),name.getText().toString(),surname.getText().toString(),email.getText().toString(),password.getText().toString());

                    db.myDao().insertLogin(event);

                    FragmentManager manager= getFragmentManager();
                    FragmentTransaction txn = manager.beginTransaction();
                    txn.replace(R.id.linearLogin , new Login_fragment()).commit();
                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager= getFragmentManager();
                FragmentTransaction txn = manager.beginTransaction();
                txn.replace(R.id.linearLogin , new Login_fragment()).commit();
            }
        });

        return v;
    }

}
