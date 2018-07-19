package trivial.speckmussweg.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
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

    private static final String TABLE_BREAD = "bread";
    private static final String COLUMN_BREAD_ID = "_id";
    private static final String COLUMN_BREAD_NAME = "breadname";
    private static final String COLUMN_BREAD_CALORIES = "breadcalorie";
    private static final String COLUMN_BREAD_ISLONG = "islong";

    private static final String TABLE_CHEESE = "cheese";
    private static final String COLUMN_CHEESE_ID = "_id";
    private static final String COLUMN_CHEESE_NAME = "cheesename";
    private static final String COLUMN_CHEESE_CALORIES = "cheesecalorie";

    private static final String TABLE_MEAT = "meat";
    private static final String COLUMN_MEAT_ID = "_id";
    private static final String COLUMN_MEAT_NAME = "meatname";
    private static final String COLUMN_MEAT_CALORIES = "meatcalorie";

    private static final String TABLE_SALAD = "salad";
    private static final String COLUMN_SALAD_ID = "_id";
    private static final String COLUMN_SALAD_NAME = "saladname";
    private static final String COLUMN_SALAD_CALORIES = "saladcalorie";

    private static final String TABLE_EXTRA = "extra";
    private static final String COLUMN_EXTRA_ID = "_id";
    private static final String COLUMN_EXTRA_NAME = "extraname";
    private static final String COLUMN_EXTRA_CALORIES = "extracalorie";

    private static final String TABLE_SAUCE = "sauce";
    private static final String COLUMN_SAUCE_ID = "_id";
    private static final String COLUMN_SAUCE_NAME = "saucename";
    private static final String COLUMN_SAUCE_CALORIES = "saucecalorie";

    private static final String COLUMN_FOR_ALL_ID = "mealid";



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

        db.execSQL(
                "CREATE TABLE " + TABLE_BREAD + " ( " +
                        COLUMN_BREAD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_BREAD_NAME + " TEXT," +
                        COLUMN_BREAD_CALORIES + " INTEGER," +
                        COLUMN_FOR_ALL_ID + " INTEGER," +
                        COLUMN_BREAD_ISLONG + " BIT" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE " + TABLE_CHEESE + " ( " +
                        COLUMN_CHEESE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_CHEESE_NAME + " TEXT," +
                        COLUMN_CHEESE_CALORIES + " INTEGER," +
                        COLUMN_FOR_ALL_ID + " INTEGER" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE " + TABLE_MEAT + " ( " +
                        COLUMN_MEAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_MEAT_NAME + " TEXT," +
                        COLUMN_MEAT_CALORIES + " INTEGER," +
                        COLUMN_FOR_ALL_ID + " INTEGER" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE " + TABLE_SALAD + " ( " +
                        COLUMN_SALAD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_SALAD_NAME + " TEXT," +
                        COLUMN_SALAD_CALORIES + " INTEGER," +
                        COLUMN_FOR_ALL_ID + " INTEGER" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE " + TABLE_EXTRA + " ( " +
                        COLUMN_EXTRA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_EXTRA_NAME + " TEXT," +
                        COLUMN_EXTRA_CALORIES + " INTEGER," +
                        COLUMN_FOR_ALL_ID + " INTEGER" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE " + TABLE_SAUCE + " ( " +
                        COLUMN_SAUCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_SAUCE_NAME + " TEXT," +
                        COLUMN_SAUCE_CALORIES + " INTEGER," +
                        COLUMN_FOR_ALL_ID + " INTEGER" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BREAD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHEESE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALAD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXTRA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAUCE);

        onCreate(db);
    }

    // 1= long, 0 = small
    public void insertBread(int id, String name, int calorie, int subIsLong){
        ContentValues newLine = new ContentValues();
        newLine.put(COLUMN_FOR_ALL_ID, id);
        newLine.put(COLUMN_BREAD_NAME, name);
        newLine.put(COLUMN_BREAD_CALORIES, calorie);
        newLine.put(COLUMN_BREAD_ISLONG, subIsLong);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_BREAD, null, newLine);
    }

    public void insertCheese(int id, String name, int calorie){
        ContentValues newLine = new ContentValues();
        newLine.put(COLUMN_FOR_ALL_ID, id);
        newLine.put(COLUMN_CHEESE_NAME, name);
        newLine.put(COLUMN_CHEESE_CALORIES, calorie);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CHEESE, null, newLine);
    }

    public void insertMeat(int id, String name, int calorie){
        ContentValues newLine = new ContentValues();
        newLine.put(COLUMN_FOR_ALL_ID, id);
        newLine.put(COLUMN_MEAT_NAME, name);
        newLine.put(COLUMN_MEAT_CALORIES, calorie);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_MEAT, null, newLine);
    }

    public void insertSalad(int id, String name, int calorie){
        ContentValues newLine = new ContentValues();
        newLine.put(COLUMN_FOR_ALL_ID, id);
        newLine.put(COLUMN_SALAD_NAME, name);
        newLine.put(COLUMN_SALAD_CALORIES, calorie);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_SALAD, null, newLine);
    }

    public void insertExtras(int id, String name, int calorie){
        ContentValues newLine = new ContentValues();
        newLine.put(COLUMN_FOR_ALL_ID, id);
        newLine.put(COLUMN_EXTRA_NAME, name);
        newLine.put(COLUMN_EXTRA_CALORIES, calorie);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_EXTRA, null, newLine);
    }

    public void insertSauce(int id, String name, int calorie){
        ContentValues newLine = new ContentValues();
        newLine.put(COLUMN_FOR_ALL_ID, id);
        newLine.put(COLUMN_SAUCE_NAME, name);
        newLine.put(COLUMN_SAUCE_CALORIES, calorie);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_SAUCE, null, newLine);
    }

    public void deleteBread(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_FOR_ALL_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(id)};
        db.delete(TABLE_BREAD, where, whereArg);
    }

    public void deleteCheese(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_FOR_ALL_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(id)};
        db.delete(TABLE_CHEESE, where, whereArg);
    }

    public void deleteMeat(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_FOR_ALL_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(id)};
        db.delete(TABLE_MEAT, where, whereArg);
    }

    public void deleteSalad(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_FOR_ALL_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(id)};
        db.delete(TABLE_SALAD, where, whereArg);
    }

    public void deleteExtras(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_FOR_ALL_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(id)};
        db.delete(TABLE_EXTRA, where, whereArg);
    }

    public void deleteSauce(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_FOR_ALL_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(id)};
        db.delete(TABLE_SAUCE, where, whereArg);
    }

    public Cursor selectBread(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BREAD + " WHERE " +
                COLUMN_FOR_ALL_ID + "=" + id, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor selectCheese(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CHEESE + " WHERE " +
                COLUMN_FOR_ALL_ID + "=" + id, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor selectMeat(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MEAT + " WHERE " +
                COLUMN_FOR_ALL_ID + "=" + id, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor selectSalad(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SALAD + " WHERE " +
                COLUMN_FOR_ALL_ID + "=" + id, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor selectExtras(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXTRA + " WHERE " +
                COLUMN_FOR_ALL_ID + "=" + id, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor selectSauce(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SAUCE + " WHERE " +
                COLUMN_FOR_ALL_ID + "=" + id, null);
        cursor.moveToFirst();
        return cursor;
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