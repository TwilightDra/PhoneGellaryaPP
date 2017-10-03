package com.example.td.phonegellary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //String dbName = "tempDB.db";
        //SQLiteDatabase db = openOrCreateDatabase(dbName, 0, null);
    }

    public void to_PhotoM(View view){
        Intent intent1= new Intent(MainActivity.this, PhotoManage.class);
        startActivity(intent1);
    }
    public void to_Camera(View view){
        Intent intent1= new Intent(MainActivity.this, Camera.class);
        startActivity(intent1);
    }
    public void to_Login(View view){
        Intent intent1= new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent1);
    }
}
