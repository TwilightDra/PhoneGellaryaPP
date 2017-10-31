package com.example.td.phonegellary;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
//import android.net.Uri;
import android.os.Debug;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.os.Environment;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.ImageView;
//import android.provider.MediaStore;
import android.database.Cursor;
import java.text.SimpleDateFormat;

import  com.example.td.phonegellary.util.RuntimeUtil;

public class filesystest extends Activity {

    private int READ_STORAGE_PERMISSION_CODE = 23;
    private int WRITE_STORAGE_PERMISSION_CODE = 24;
    final int TAKE_PICTURE=1;
    private  Button btnTakePic=null;
    private ImageView iv=null;
    public String dbName;
    public SQLiteDatabase db;
    /*String imageFilePath;
    File imageFile;
    Uri imageFileUri;*/
    final int REQUEST_CODE_GALLERY = 999;
    private int IdCheck;


    public void openDB(){
        String dbName = "PhotoGalleryDB.db";
        db = openOrCreateDatabase(dbName, 0, null);

        if(!db.isOpen()) {
            Toast.makeText(this, "Db Open Error", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filesystest);
        btnTakePic=(Button) findViewById(R.id.btn_TakePic);
        btnTakePic.setOnClickListener(onClickListener);
        iv=(ImageView) findViewById(R.id.imageViewTest);
        //imageFilePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/picture.jpg";
        //imageFile=new File(imageFilePath);
        //imageFileUri = Uri.fromFile(imageFile);

        openDB();

        db.beginTransaction();
        try{

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "ImageData"
                    + " (ID integer PRIMARY KEY, Image BINARY, TagDate varchar(10));");

            //db.execSQL("INSERT INTO PhoneBook (name , phoneNumber) VALUES ('Tom','6045555555');");
            //db.execSQL("INSERT INTO PhoneBook (name , phoneNumber) VALUES ('Biyuntao','2222666600');");
            db.execSQL("INSERT INTO ImageData (ID , Image, TagDate) VALUES (0,'DummyTest123456789','2017-09-26');");
            //db.execSQL("DROP TABLE PhoneBook;");
            db.setTransactionSuccessful();
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT)
                    .show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        finally
        {
            db.endTransaction();
        }
        //---------------------------
        Cursor dbCursor = null;
        StringBuilder builder = new StringBuilder();

        if(!db.isOpen()) {
            Toast.makeText(this, "Db Open Error", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        //Now retrieve and display the record
        try{
            dbCursor = db.query( "ImageData", null, null, null, null, null, null);

            int idCol = dbCursor.getColumnIndex("ID");
            int imageCol = dbCursor.getColumnIndex("Image");

            dbCursor.moveToPosition(1);
            String id1 = dbCursor.getString(idCol);
            Toast.makeText(this, id1 + "  checked" , Toast.LENGTH_SHORT).show();
            byte[] in=dbCursor.getBlob(dbCursor.getColumnIndex("Image"));
            //Bitmap bmpout=BitmapFactory.decodeByteArray(in,0,in.length);
            ByteArrayInputStream stream=new ByteArrayInputStream(in);
            iv.setImageDrawable(Drawable.createFromStream(stream,"Image"));
            if(dbCursor.getCount()!=0){
                dbCursor.moveToLast();
                IdCheck=dbCursor.getInt(idCol);
//                Toast.makeText(this, "IDcheck:"+IdCheck, Toast.LENGTH_SHORT).show();
            }else{
                IdCheck=0;
            }
            if (dbCursor != null) {
                dbCursor.moveToFirst();
                if (dbCursor.getCount() != 0) {
                    int i = 0;
                    do {
                        i++;

                        String id = dbCursor.getString(idCol);
                        String Bdata    = dbCursor.getString(imageCol);
                        Toast.makeText(this, id + "  " + Bdata, Toast.LENGTH_SHORT).show();

                    } while (dbCursor.moveToNext());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    private final View.OnClickListener onClickListener= new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Log.d("blah","1111");

            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            //intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_GALLERY);

            //startActivity(intent);
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==REQUEST_CODE_GALLERY){
            if(resultCode==RESULT_OK){
                //BitmapFactory.Options bmpFO=new BitmapFactory.Options();
                //bmpFO.inJustDecodeBounds=false;
                //Bundle bundle=data.getExtras();
                try{
                    Bitmap photo=(Bitmap)data.getExtras().get("data");
                    iv.setImageBitmap(photo);
                    ByteArrayOutputStream bst = new ByteArrayOutputStream();
                    ((BitmapDrawable)iv.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG,100,bst);
                    //Bitmap photo=(Bitmap)data.getExtras().get("data");
                    //photo.compress(Bitmap.CompressFormat.PNG, 100, bst);

                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    String date=sdf.format(new java.util.Date());

                    Object[] args=new Object[]{bst.toByteArray()};
                    Toast.makeText(this, bst.toByteArray().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "|"+date+"|", Toast.LENGTH_SHORT).show();

                    db.execSQL("INSERT INTO ImageData(Image) values(?)", args);
                    db.execSQL("UPDATE ImageData SET TagDate = '"+date+"' WHERE ID = "+IdCheck+1+";");
                    //Toast.makeText(this, bst.toString(), Toast.LENGTH_SHORT).show();

                    //db.execSQL("INSERT INTO ImageData (ID , Image, TagDate) VALUES (null,'"+ args +"','"+date+"');");

                        bst.close();
                        db.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                //Bitmap photo=BitmapFactory.decodeFile(imageFilePath, bmpFO);

            }

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public void dbclean(View view){
        db.execSQL("DROP TABLE ImageData;");
    }
    public void to_Request(View view){
        Intent intent1= new Intent(filesystest.this, cameraTest.class);
        startActivity(intent1);
    }
}
