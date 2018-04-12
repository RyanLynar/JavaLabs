package com.example.ryan.javalabs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ryan on 3/6/2018.
 */

public class ChatDBHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "MESSAGESDB";

    private static int VERSION_NUM =1;
    public static final String TABLE_NAME ="MESSAGES";
    public static final String KEY_ID ="ID";
    public static final String KEY_MESSAGE="MESSAGE";
    public ChatDBHelper(Context cx){
        super(cx,DATABASE_NAME,null,VERSION_NUM);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DATABASEHELPER", "OnCreate Called");
        db.execSQL("CREATE TABLE IF NOT EXISTS " +TABLE_NAME + " ("+ KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " VARCHAR(255));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        Log.i("DATABASEHELPER" , "UPGRADED DB TO" + newVer +  " FROM " + oldVer );
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
        Log.i("UPGRADE" , "UPGRADED DB");
    }
}
