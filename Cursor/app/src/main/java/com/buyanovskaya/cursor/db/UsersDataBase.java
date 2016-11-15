package com.buyanovskaya.cursor.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.buyanovskaya.cursor.model.User;
import com.buyanovskaya.cursor.utils.AConstants;

import java.util.ArrayList;

public class UsersDataBase {

    private static final String DB_CREATE =
            "create table " + AConstants.DB_TABLE + "(" +
                    AConstants.COLUMN_ID + " integer primary key autoincrement, " +
                    AConstants.COLUMN_FIRST_NAME + " text, " +
                    AConstants.COLUMN_LAST_NAME + " text" +
                    ");";

    private final Context myCtx;


    private DBHelper mDBHelper;
    private SQLiteDatabase sqLiteDatabase;

    public UsersDataBase(Context ctx) {
        myCtx = ctx;
    }


    public void open() {
        mDBHelper = new DBHelper(myCtx, AConstants.DB_NAME, null, AConstants.DB_VERSION);
        sqLiteDatabase = mDBHelper.getWritableDatabase();
    }


    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }


    public ArrayList<User> getStartingData() {
        return getElements(0, AConstants.DEFAULT_ITEMS_COUNT);
    }

    public ArrayList<User> getElements(int startId, int itemsCount) {
        ArrayList<User> listOfUsers = new ArrayList<>();

        for (int i = startId; i < startId + itemsCount; i++) {
            String[] args={String.valueOf(i)};
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from " + AConstants.DB_TABLE
                    + " WHERE _id=?", args);

            if (cursor != null && cursor.moveToFirst()) {
                User user = new User();
                user.setFirstName(cursor.getString(1));
                user.setLastName(cursor.getString(2));
                listOfUsers.add(user);
                cursor.close();
            }
        }
        return listOfUsers;
    }
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);

            ContentValues cv = new ContentValues();
            for (int i = 0; i < 1000; i++) {
                cv.put(AConstants.COLUMN_FIRST_NAME, "first_name "+i );
                cv.put(AConstants.COLUMN_LAST_NAME, "last_name "+i );
                db.insert(AConstants.DB_TABLE, null, cv);
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

}