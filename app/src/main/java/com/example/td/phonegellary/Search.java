package com.example.td.phonegellary;

import android.content.Intent;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

public class Search extends AppCompatActivity {
    int mYear, mMonth, mDay;
    Button btn;
    TextView dateDisplay;
    final int DATE_DIALOG = 1;
    private ImageView image1;
    private  String picPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btn = (Button) findViewById(R.id.btn_time);
        dateDisplay = (TextView) findViewById(R.id.dateDisplay);


        image1=(ImageView) findViewById(R.id.imageView1);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }
    public void display() {
        dateDisplay.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize=2;

        if(mDay==1){
            picPath="/mnt/user/0/primary/a1.jpg";
            Bitmap bm = BitmapFactory.decodeFile(picPath, options);
            image1.setImageBitmap(bm);
        }else if(mDay==2){
            picPath="/sdcard/a2.jpg";
            Bitmap bm = BitmapFactory.decodeFile(picPath, options);
            image1.setImageBitmap(bm);
        }else if(mDay==3){
            picPath="/sdcard/a3.jpg";
            Bitmap bm = BitmapFactory.decodeFile(picPath, options);
            image1.setImageBitmap(bm);
        }
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };
    public void to_SelectL(View view){
        Intent intent1= new Intent(Search.this, SelectLocation.class);
        startActivity(intent1);
    }
    /*public void to_SelectT(View view){
        Intent intent1= new Intent(Search.this, TestActivity.class);
        startActivity(intent1);
    }*/
}
