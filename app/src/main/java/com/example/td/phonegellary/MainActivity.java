package com.example.td.phonegellary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
