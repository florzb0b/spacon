package trivial.speckmussweg.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SpeckMussWeg.db";
    private static final String TABLE_PROFILE = "profile";
    private static final String COLUMN_PROFILE_ID = "_id";
    private static final String COLUMN_PROFILE_FIRSTNAME = "firstname";
    private static final String COLUMN_PROFILE_LASTNAME = "lastname";
    private static final String COLUMN_PROFILE_GENDER = "gender";
    private static final String COLUMN_PROFILE_BIRTHDAY = "birthday";
    private static final String COLUMN_PROFILE_HEIGHT = "height";
    private static final String COLUMN_PROFILE_WEIGHT = "weight";
    private static final String COLUMN_PROFILE_PICTURE = "picture";

    public MyDatabase(Context cxt) {
        super(cxt, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_PROFILE + " ( " +
                        COLUMN_PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_PROFILE_FIRSTNAME + " TEXT," +
                        COLUMN_PROFILE_LASTNAME + " TEXT," +
                        COLUMN_PROFILE_GENDER + " BIT," +
                        COLUMN_PROFILE_BIRTHDAY + " TEXT," +
                        COLUMN_PROFILE_HEIGHT + " TEXT," +
                        COLUMN_PROFILE_WEIGHT + " TEXT," +
                        COLUMN_PROFILE_PICTURE + " TEXT" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        onCreate(db);
    }

    public Long insertNewProfile(String firstname, String lastname, byte gender, String birthday,
                          String height, String weight, String picture_res) {
        ContentValues newLine = new ContentValues();
        newLine.put(COLUMN_PROFILE_FIRSTNAME, firstname);
        newLine.put(COLUMN_PROFILE_LASTNAME, lastname);
        newLine.put(COLUMN_PROFILE_GENDER, gender);
        newLine.put(COLUMN_PROFILE_BIRTHDAY, birthday);
        newLine.put(COLUMN_PROFILE_HEIGHT, height);
        newLine.put(COLUMN_PROFILE_WEIGHT, weight);
        newLine.put(COLUMN_PROFILE_PICTURE, picture_res);
        SQLiteDatabase db = this.getWritableDatabase();
        //if result expected
        return db.insert(TABLE_PROFILE, null, newLine);
    }

    public void deleteProfile(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_PROFILE_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(id)};
        db.delete(TABLE_PROFILE, where, whereArg);
    }

    public Cursor selectAllProfiles(String orderBy) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PROFILE + " ORDER BY " + orderBy, null);
        cursor.moveToFirst();
        return cursor;
    }

   public Cursor selectProfile(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PROFILE + " WHERE " +
                COLUMN_PROFILE_ID + "=" + id, null);
        cursor.moveToFirst();
        return cursor;
    }

    public void updateProfile(int id, String firstname, String lastname, byte gender, String birthday,
                       String height, String weight, String picture_res) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateLine = new ContentValues();
        updateLine.put(COLUMN_PROFILE_FIRSTNAME, firstname);
        updateLine.put(COLUMN_PROFILE_LASTNAME, lastname);
        updateLine.put(COLUMN_PROFILE_GENDER, gender);
        updateLine.put(COLUMN_PROFILE_BIRTHDAY, birthday);
        updateLine.put(COLUMN_PROFILE_HEIGHT, height);
        updateLine.put(COLUMN_PROFILE_WEIGHT, weight);
        updateLine.put(COLUMN_PROFILE_PICTURE, picture_res);
        db.update(TABLE_PROFILE, updateLine, "_id= " + id, null);
    }

}