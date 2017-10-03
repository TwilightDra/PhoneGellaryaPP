package com.example.td.phonegellary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PhotoManage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_manage);
    }
    public void to_Search(View view){
        Intent intent1= new Intent(PhotoManage.this, Search.class);
        startActivity(intent1);
    }
}
