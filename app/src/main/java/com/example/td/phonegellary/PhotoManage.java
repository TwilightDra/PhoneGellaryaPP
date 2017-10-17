package com.example.td.phonegellary;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.io.ByteArrayInputStream;

import com.example.td.phonegellary.filesystest;

public class PhotoManage extends AppCompatActivity {
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    public SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_manage);
        iv1=(ImageView) findViewById(R.id.imageView5);
        iv2=(ImageView) findViewById(R.id.imageView6);
        iv3=(ImageView) findViewById(R.id.imageView4);
        openDB();
        try{
            Cursor dbCursor = db.query( "ImageData", null, null, null, null, null, null);
            int idCol = dbCursor.getColumnIndex("ID");
//            int imageCol = dbCursor.getColumnIndex("Image");
            int tdCol = dbCursor.getColumnIndex("TagDate");
            if (dbCursor != null) {
                dbCursor.moveToFirst();
                if (dbCursor.getCount() != 0) {
                    int i = 0; int index=1;
                    do {
                        i++;
                        String id = dbCursor.getString(idCol);
                        String td = dbCursor.getString(tdCol);
                        Toast.makeText(this, id + "  " + td, Toast.LENGTH_SHORT).show();
                        if(dbCursor.getString(tdCol)=="2017-10-17"){
                            byte[] in=dbCursor.getBlob(dbCursor.getColumnIndex("Image"));
                            ByteArrayInputStream stream=new ByteArrayInputStream(in);
                            if(index==1) iv1.setImageDrawable(Drawable.createFromStream(stream,"Image"));
                            else if(index==2) iv2.setImageDrawable(Drawable.createFromStream(stream,"Image"));
                            else if(index==3) iv3.setImageDrawable(Drawable.createFromStream(stream,"Image"));
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
    public void to_Search(View view){
        Intent intent1= new Intent(PhotoManage.this, Search.class);
        startActivity(intent1);
    }
    public void openDB(){
        String dbName = "PhotoGalleryDB.db";
        db = openOrCreateDatabase(dbName, 0, null);

        if(!db.isOpen()) {
            Toast.makeText(this, "Db Open Error", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
    }

}
