package com.example.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseOpenHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "MyMessageDatabase";
    public static final int VERSION_NUM = 2;

    public static final String TABLE_NAME = "MESSAGES";
    public static final String COL_ID = "_id";
    public static final String COL_SEND = "SEND";
    public static final String COL_MESSAGE= "MESSAGE";


    public MyDatabaseOpenHelper(Activity ctx){

        super(ctx, DATABASE_NAME, null, VERSION_NUM );
    }

    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_SEND + " INTEGER, " + COL_MESSAGE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("Database upgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("Database downgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }

    public long insert(Message msg) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues ctx = new ContentValues();
        ctx.put(MyDatabaseOpenHelper.COL_MESSAGE, msg.getMessage());
        ctx.put(MyDatabaseOpenHelper.COL_SEND, msg.isSent ? 1 : 0);
        return db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, ctx);
    }

    public ArrayList<Message> getAllMessages() {
        ArrayList<Message> messages = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        String [] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_SEND, MyDatabaseOpenHelper.COL_MESSAGE};
        Cursor results = db.rawQuery("SELECT * FROM " + MyDatabaseOpenHelper.TABLE_NAME, null);


        Log.d("Database version number", "" + db.getVersion());
        Log.d("Number of columns", "" + results.getColumnCount());
        Log.d("Results in cursor", "" + results.getCount());

        String[] columnNames = results.getColumnNames();
        for(int i = 0; i < columnNames.length; i++)
        {
            Log.d("Column Name", "" + columnNames[i]);
        }

        int messageColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_MESSAGE);
        int sendColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_SEND);
        int idColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);

        while(results.moveToNext()) {
            boolean boo = false;
            if(results.getInt(sendColumnIndex) == 1){
                boo = true;
            }

            String m = results.getString(messageColumnIndex);
            long id = results.getLong(idColIndex);

            messages.add(new Message(m, boo, id));

            Log.i("Message", "msg=" + results.getString(2) + ", type=" + results.getString(1));
        }

        return messages;
    }
}



