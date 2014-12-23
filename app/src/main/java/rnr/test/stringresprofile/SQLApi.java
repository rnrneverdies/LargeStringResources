package rnr.test.stringresprofile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Emanuel on 23/12/2014.
 */
public class SQLApi {

    private SQLiteDatabase database;
    private SQLiteDatabase readeable;

    private MySQLiteHelper dbHelper;
    private SQLiteDatabase read;
    public SQLApi(Context context){
        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
        readeable = dbHelper.getReadableDatabase();
    }

    public long createRecords(String id, String name){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ID, id);
        values.put(MySQLiteHelper.COLUMN_STRING, name);
        return database.insert(MySQLiteHelper.TABLE_COMMENTS, null, values);
    }

    public String getByName(String name) {
        String[] cols = new String[] {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_STRING};

        Cursor cursor = readeable.query(MySQLiteHelper.TABLE_COMMENTS, cols, MySQLiteHelper.COLUMN_ID + " = ?",
                            new String[] { name }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        return cursor.getString(1);
    }
}


