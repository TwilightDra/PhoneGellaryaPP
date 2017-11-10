package com.example.td.phonegellary;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.io.ByteArrayInputStream;
import java.util.Calendar;

import com.example.td.phonegellary.filesystest;

public class PhotoManage extends AppCompatActivity {
    int mYear, mMonth, mDay;
    private String[] idArray = new String[9];
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private ImageView iv6;
    private ImageView iv7;
    private ImageView iv8;
    private ImageView iv9;
    private CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8,cb9;
    TextView text_conditionDisp;
    TextView text_addTag;
    final int DATE_DIALOG = 1;
    Button btn_Timepick;
    public SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_manage);
        btn_Timepick = (Button) findViewById(R.id.btn_timePicker);
        text_conditionDisp = (TextView) findViewById(R.id.textConditionDisp);
        text_addTag=(TextView)findViewById(R.id.editText3);
        iv1=(ImageView) findViewById(R.id.imageView1);
        iv2=(ImageView) findViewById(R.id.imageView2);
        iv3=(ImageView) findViewById(R.id.imageView3);
        iv4=(ImageView) findViewById(R.id.imageView4);
        iv5=(ImageView) findViewById(R.id.imageView5);
        iv6=(ImageView) findViewById(R.id.imageView6);
        iv7=(ImageView) findViewById(R.id.imageView7);
        iv8=(ImageView) findViewById(R.id.imageView8);
        iv9=(ImageView) findViewById(R.id.imageView9);
        cb1=(CheckBox)findViewById(R.id.checkBox1);
        cb2=(CheckBox)findViewById(R.id.checkBox2);
        cb3=(CheckBox)findViewById(R.id.checkBox3);
        cb4=(CheckBox)findViewById(R.id.checkBox4);
        cb5=(CheckBox)findViewById(R.id.checkBox5);
        cb6=(CheckBox)findViewById(R.id.checkBox6);
        cb7=(CheckBox)findViewById(R.id.checkBox7);
        cb8=(CheckBox)findViewById(R.id.checkBox8);
        cb9=(CheckBox)findViewById(R.id.checkBox9);


        btn_Timepick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        imageClean();
        openDB();

    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
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
    public void display() {
        if(mDay<10) {
            text_conditionDisp.setText(new StringBuffer().append(mYear).append("-").append(mMonth+1).append("-0").append(mDay));
        }else{
            text_conditionDisp.setText(new StringBuffer().append(mYear).append("-").append(mMonth+1).append("-").append(mDay));
        }
    }
    /*public void to_Search(View view){
        Intent intent1= new Intent(PhotoManage.this, Search.class);
        startActivity(intent1);
    }*/
    public void openDB(){
        String dbName = "PhotoGalleryDB.db";
        db = openOrCreateDatabase(dbName, 0, null);

        if(!db.isOpen()) {
            Toast.makeText(this, "Db Open Error", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
    }

    public void searchPic(View view){
        imageClean();
        //Toast.makeText(this,"day"+mDay, Toast.LENGTH_SHORT).show();
        try{
            Cursor dbCursor = db.query( "ImageData", null, null, null, null, null, null);
            int idCol = dbCursor.getColumnIndex("ID");
            int imageCol = dbCursor.getColumnIndex("Image");
            int tdayCol = dbCursor.getColumnIndex("TagDate");
            int tkeyCol = dbCursor.getColumnIndex("TagKeyword");
            if (dbCursor != null) {
                dbCursor.moveToFirst();
                if (dbCursor.getCount() != 0) {
                    int i = 0; int index=1;
                    do {
                        i++;
                        String id = dbCursor.getString(idCol);
                        String tday = dbCursor.getString(tdayCol);
                        String tkey=dbCursor.getString(tkeyCol);
                        //Toast.makeText(this, id + "  " + tday+" "+tkey, Toast.LENGTH_SHORT).show();
                        String cheak=text_conditionDisp.getText().toString();
                        if(tkey==null)  tkey="";
                        if(tday.equals(cheak) ||tkey.equals(cheak)){
                            idArray[index-1]=id;
                            byte[] in=dbCursor.getBlob(dbCursor.getColumnIndex("Image"));
                            ByteArrayInputStream stream=new ByteArrayInputStream(in);
                            if(index==1) iv1.setImageDrawable(Drawable.createFromStream(stream,"Image"));
                            else if(index==2) iv2.setImageDrawable(Drawable.createFromStream(stream,"Image"));
                            else if(index==3) iv3.setImageDrawable(Drawable.createFromStream(stream,"Image"));
                            else if(index==4) iv4.setImageDrawable(Drawable.createFromStream(stream,"Image"));
                            else if(index==5) iv5.setImageDrawable(Drawable.createFromStream(stream,"Image"));
                            else if(index==6) iv6.setImageDrawable(Drawable.createFromStream(stream,"Image"));
                            else if(index==7) iv7.setImageDrawable(Drawable.createFromStream(stream,"Image"));
                            else if(index==8) iv8.setImageDrawable(Drawable.createFromStream(stream,"Image"));
                            else if(index==9) iv9.setImageDrawable(Drawable.createFromStream(stream,"Image"));
                            index+=1;
                        }
                    } while (dbCursor.moveToNext());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setTag(View view){
        removeChecked();
        try{
            String TagTxt=text_addTag.getText().toString();
            for(int i=0;i<9;i++){
                if(idArray[i].equals("0")){}
                else{
                    String dbexec="UPDATE ImageData SET TagKeyword ='"+TagTxt+"' WHERE ID = "+idArray[i]+";";
                    db.execSQL(dbexec);
                }
            }
         }catch (Exception e){
            Toast.makeText(this, "Invalid Input, or SQL cannot access", Toast.LENGTH_SHORT).show();
         }
    }
    public void removeChecked(){
        if(!cb1.isChecked()) idArray[0]="0";
        if(!cb2.isChecked()) idArray[1]="0";
        if(!cb3.isChecked()) idArray[2]="0";
        if(!cb4.isChecked()) idArray[3]="0";
        if(!cb5.isChecked()) idArray[4]="0";
        if(!cb6.isChecked()) idArray[5]="0";
        if(!cb7.isChecked()) idArray[6]="0";
        if(!cb8.isChecked()) idArray[7]="0";
        if(!cb9.isChecked()) idArray[8]="0";
    }
    public void imageClean() {
        iv1.setImageDrawable(null);
        iv2.setImageDrawable(null);
        iv3.setImageDrawable(null);
        iv4.setImageDrawable(null);
        iv5.setImageDrawable(null);
        iv6.setImageDrawable(null);
        iv7.setImageDrawable(null);
        iv8.setImageDrawable(null);
        iv9.setImageDrawable(null);
    }
}
