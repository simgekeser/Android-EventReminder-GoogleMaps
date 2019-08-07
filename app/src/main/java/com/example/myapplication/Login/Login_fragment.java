package com.example.myapplication.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.AppDatabase;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import static android.content.Context.MODE_PRIVATE;


public class Login_fragment extends Fragment {

        AppDatabase db;

        Button button_login;
        Button button_register;

        public static String username;
        private SharedPreferences mPrefs;


        CheckBox checkBox;
        private EditText mail;
        private EditText pass;

public static final String PREFS_NAME = "PrefsFile";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_login_fragment, container, false);

        button_login =(Button)view.findViewById(R.id.login_button);
        button_register=(Button)view.findViewById(R.id.register_button);

        checkBox =(CheckBox)view.findViewById(R.id.checkBox);

        mail=(EditText)view.findViewById(R.id.email_login);
        pass=(EditText)view.findViewById(R.id.password_login);

        db=AppDatabase.getAppDatabase(getContext());
        mPrefs = getActivity().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);


        getPreferecesData();


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkBox.isChecked()){

                    Boolean boolisChecked = checkBox.isChecked();
                    SharedPreferences.Editor editor = mPrefs.edit();

                    editor.putString("pref_name",mail.getText().toString());
                    editor.putString("pref_pass",pass.getText().toString());
                    editor.putBoolean("pref_check",boolisChecked);
                    editor.apply();


                }else {
                    mPrefs.edit().clear().apply();
                }

                Event event = db.myDao().user(mail.getText().toString(),pass.getText().toString());

                if(event!=null) {

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    username = mail.getText().toString();//set maileditText to username to use from another class

                    mail.getText().clear();
                    pass.getText().clear();

                } else{
                    Toast.makeText(getActivity(),"failed",Toast.LENGTH_LONG).show();
                }

            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               FragmentManager manager= getFragmentManager();
                FragmentTransaction txn = manager.beginTransaction();
                txn.replace(R.id.linearLogin , new Register()).commit();//create registerFragment into content login

            }
        });
        return view;
    }

    private void getPreferecesData() {  //set username and password into editText

        SharedPreferences sp = getActivity().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        if(sp.contains("pref_name")) {
            String u = sp.getString("pref_name","not found");
            mail.setText(u.toString());
        }
        if(sp.contains("pref_pass")) {
            String u = sp.getString("pref_pass","not found");
            pass.setText(u.toString());
        }
        if(sp.contains("pref_check")){
            Boolean b= sp.getBoolean("pref_check",false);
            checkBox.setChecked(b);
        }
    }

}