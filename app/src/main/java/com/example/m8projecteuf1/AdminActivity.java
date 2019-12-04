package com.example.m8projecteuf1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class AdminActivity extends AppCompatActivity {

    public static String sharedPreferencesFile = "SharedPreferencesProjecte";
    Realm realm;
    RealmResults<User> realmResults;
    ArrayList<User> userList;
    ArrayAdapter<User> adapter;
    ArrayList<String> userListNoms = new ArrayList<>();
    ArrayAdapter<String> adapterNoms;
    CustomAdapter customAdapter;
    Spinner sp;
    String userEsborrar;
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        realm = Realm.getDefaultInstance();
        sp = findViewById(R.id.spinnerDelete);
        ImageView iv = findViewById(R.id.imageViewFragment);
        TextView tv = findViewById(R.id.textViewUserName);
        iv.setImageResource(R.drawable.admin);
        tv.setText("Admin");

        realmResults = realm.where(User.class).findAll();
        userList = new ArrayList<>();
        for (User user : realmResults) {
            userList.add(user);
        }

        customAdapter = new CustomAdapter(
                this,
                R.layout.user_item,
                userList
        );

        adapterNoms = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userListNoms);

        listView = findViewById(R.id.listView);
        listView.setAdapter(customAdapter);
        sp.setAdapter(adapterNoms);
        selectAll(null);
    }

    public void selectAll(View v) {
        realmResults = realm.where(User.class).findAll();

        userList.clear();
        for (User user : realmResults) {
            userList.add(user);
        }
        customAdapter.notifyDataSetChanged();

        userListNoms.clear();
        for (User user : userList) {
            userListNoms.add(user.getName());
        }
        adapterNoms.notifyDataSetChanged();
    }

    public void onEsborrarClick(View view) {

        try {
            userEsborrar = sp.getSelectedItem().toString();

            realm.beginTransaction();
            User user = realm.where(User.class).equalTo("name", userEsborrar).findFirst();
            String userName = user.getName();
            user.deleteFromRealm();
            realm.commitTransaction();

            SharedPreferences.Editor editor = getSharedPreferences(sharedPreferencesFile, MODE_PRIVATE).edit();
            editor.remove(userName);
            editor.commit();
            Toast.makeText(AdminActivity.this, userEsborrar + " deleted succesfully.", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            Toast.makeText(this, "There are no users in the database.", Toast.LENGTH_SHORT).show();
        }

        selectAll(null);
    }
}
