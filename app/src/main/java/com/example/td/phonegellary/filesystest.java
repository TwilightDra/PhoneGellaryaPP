package com.example.td.phonegellary;

import java.io.File;
import java.io.ByteArrayOutputStream;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Debug;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.ImageView;
import android.provider.MediaStore;
import android.database.Cursor;

public class filesystest extends Activity {

    private int READ_STORAGE_PERMISSION_CODE = 23;
    private int WRITE_STORAGE_PERMISSION_CODE = 24;
    final int TAKE_PICTURE=1;
    private  Button btnTakePic=null;
    private ImageView iv=null;
    String imageFilePath;
    File imageFile;
    Uri imageFileUri;
    final int REQUEST_CODE_GALLERY = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("blah","2222");
        setContentView(R.layout.activity_filesystest);
        btnTakePic=(Button) findViewById(R.id.btn_TakePic);
        btnTakePic.setOnClickListener(onClickListener);
        iv=(ImageView) findViewById(R.id.imageViewTest);
        imageFilePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/picture.jpg";
        imageFile=new File(imageFilePath);
        imageFileUri = Uri.fromFile(imageFile);


        String dbName = "tempDB.db";
        SQLiteDatabase db = openOrCreateDatabase(dbName, 0, null);

        if(!db.isOpen()) {
            Toast.makeText(this, "Db Open Error", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        db.beginTransaction();
        try{

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "PhoneBook"
                    + " (name VARCHAR PRIMARY KEY, phoneNumber VARCHAR);");

            db.execSQL("INSERT INTO PhoneBook (name , phoneNumber) VALUES ('Tom','6045555555');");
            db.execSQL("INSERT INTO PhoneBook (name , phoneNumber) VALUES ('Biyuntao','2222666600');");
//            db.execSQL("DROP TABLE PhoneBook;");
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
        Cursor dbCursor = null;
        StringBuilder builder = new StringBuilder();

        if(!db.isOpen()) {
            Toast.makeText(this, "Db Open Error", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        //Now retrieve and display the record
        try{
            dbCursor = db.query( "PhoneBook", null, null, null, null, null, null);

            int nameCol = dbCursor.getColumnIndex("name");
            int numberCol = dbCursor.getColumnIndex("phoneNumber");

            if (dbCursor != null) {
                dbCursor.moveToFirst();
                if (dbCursor.getCount() != 0) {
                    int i = 0;
                    do {
                        i++;

                        String name = dbCursor.getString(nameCol);
                        String number    = dbCursor.getString(numberCol);
                        Toast.makeText(this, name + "  " + number, Toast.LENGTH_SHORT).show();

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
            /*
            ActivityCompat.requestPermissions(
                    filesystest.this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_GALLERY
            );*/
            /*Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ContentValues contentValues=new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, imageFilePath);
            imageFileUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
            if(intent.resolveActivity(getPackageManager())!=null){
                startActivityForResult(intent, TAKE_PICTURE);
            }*/
            //startActivity(intent);
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==REQUEST_CODE_GALLERY){
            if(resultCode==RESULT_OK){
                BitmapFactory.Options bmpFO=new BitmapFactory.Options();
                bmpFO.inJustDecodeBounds=false;
                //Bundle bundle=data.getExtras();
                Bitmap photo=(Bitmap)data.getExtras().get("data");
                //Bitmap photo=BitmapFactory.decodeFile(imageFilePath, bmpFO);
                iv.setImageBitmap(photo);
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
}
