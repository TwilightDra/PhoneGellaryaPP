package com.example.td.phonegellary;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import android.database.Cursor;

public class filesystest extends AppCompatActivity {

    private int READ_STORAGE_PERMISSION_CODE = 23;
    private int WRITE_STORAGE_PERMISSION_CODE = 24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filesystest);
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
}
