package com.example.td.phonegellary;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.database.Cursor;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    public String dbName;
    public SQLiteDatabase db;
    private Button btnTakePic;
    private ImageView iv;
    private int IdCheck;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTakePic=(Button) findViewById(R.id.btn_Camera);
        btnTakePic.setOnClickListener(onClickListener);
        openDB();
        createTB();
        checkTB();
        iv=(ImageView) findViewById(R.id.imageViewTake);
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
    public void to_CreateTest(View view){
        Intent intent1= new Intent(MainActivity.this, filesystest.class);
        startActivity(intent1);
    }
    public void openDB(){
        dbName = "PhotoGalleryDB.db";
        db = openOrCreateDatabase(dbName, 0, null);

        if(!db.isOpen()) {
            Toast.makeText(this, "Db Open Error", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
    }
    public void createTB(){
        db.beginTransaction();
        try{

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "ImageData"
                    + " (ID integer PRIMARY KEY, Image BINARY, TagDate varchar(10));");

            db.execSQL("INSERT INTO ImageData (ID , Image, TagDate) VALUES (0,'DummyTest123456789','2017-09-26');");
            //db.execSQL("DROP TABLE PhoneBook;");
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            db.endTransaction();
        }
        if(!db.isOpen()) {
            Toast.makeText(this, "Db Open Error", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
    }
    public void checkTB(){
        Cursor dbCursor = db.query( "ImageData", null, null, null, null, null, null);
        int idCol = dbCursor.getColumnIndex("ID");
        if(dbCursor.getCount()!=0){
            dbCursor.moveToLast();
            IdCheck=dbCursor.getInt(idCol);
                Toast.makeText(this, "IDcheck:"+IdCheck, Toast.LENGTH_SHORT).show();
        }else{
            IdCheck=0;
        }
    }
    private final View.OnClickListener onClickListener= new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE_GALLERY);
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==REQUEST_CODE_GALLERY){
            if(resultCode==RESULT_OK){
                try{
                    Bitmap photo=(Bitmap)data.getExtras().get("data");
                    iv.setImageBitmap(photo);
                    ByteArrayOutputStream bst = new ByteArrayOutputStream();
                    ((BitmapDrawable)iv.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG,100,bst);

                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    String date=sdf.format(new java.util.Date());

                    Object[] args=new Object[]{bst.toByteArray()};
                    //Toast.makeText(this, bst.toByteArray().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, IdCheck+1+"|"+date+"|", Toast.LENGTH_SHORT).show();

                    db.execSQL("INSERT INTO ImageData(Image) values(?)", args);
                    db.execSQL("UPDATE ImageData SET TagDate = '"+date+"' WHERE ID = "+IdCheck+1+";");
                    //Toast.makeText(this, bst.toString(), Toast.LENGTH_SHORT).show();

                    bst.close();
                    //db.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
