package com.cheng.robotchat.voicon.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.cheng.robotchat.voicon.model.User;

import java.util.ArrayList;

/**
 * Created by chientruong on 6/7/16.


/**
 * Created by Welcome on 4/8/2016.
 */
public class UserDataHelper extends SQLiteOpenHelper {
    private Context context;
    private final String TAG = getClass().getSimpleName();
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "articleManager";

    // Contacts table name
    private static final String TABLE_USER= "user";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME_FROM = "name_from";
    private static final String KEY_NAME_TO = "name_to";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_IMAGE_FROM = "image_from";
    private static final String KEY_IMAGE_TO= "image_to";
        private final ArrayList<User> content_list = new ArrayList<User>();

    public UserDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME_FROM + " TEXT,"
                + KEY_NAME_TO + " TEXT,"
                + KEY_LANGUAGE + " TEXT,"
                + KEY_IMAGE_TO +" BLOB,"
                + KEY_IMAGE_FROM + " BLOB"+ ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_FROM, user.getmNameFrom());
        values.put(KEY_NAME_TO, user.getmNameTo());
        values.put(KEY_LANGUAGE, user.getmLanguage());
        values.put(KEY_IMAGE_TO, user.getmImageTo());
        values.put(KEY_IMAGE_FROM, user.getmImageFrom());
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
        Toast.makeText(context, "Đã lưu thành công", Toast.LENGTH_SHORT).show();
    }

    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{KEY_ID,
                        KEY_NAME_FROM}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        User user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getBlob(4),cursor.getBlob(5));
        cursor.close();
        db.close();

        return user;
    }

    public ArrayList<User> getAllArticle() {
        try {
            content_list.clear();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_USER;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    User user= new User();
                    user.setId(Integer.parseInt(cursor.getString(0)));
                    user.setmNameFrom(cursor.getString(1));
                    user.setmNameTo(cursor.getString(2));
                    user.setmLanguage(cursor.getString(3));
                    user.setmImageFrom(cursor.getBlob(5));
                    user.setmImageTo(cursor.getBlob(4));
                    content_list.add(user);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return content_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_user", "" + e);
        }

        return content_list;
    }
    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
    public int deleteAll() {

        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.delete(TABLE_USER, null, null);
        db.close();
        return count;
    }
    public int UpdateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME_FROM, user.getmNameFrom());
        values.put(KEY_NAME_TO, user.getmNameTo());
        values.put(KEY_LANGUAGE, user.getmLanguage());
        values.put(KEY_IMAGE_TO, user.getmImageTo());
        values.put(KEY_IMAGE_FROM, user.getmImageFrom());

        // updating row
        return db.update(TABLE_USER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }

    public int GetCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return count;
    }

}