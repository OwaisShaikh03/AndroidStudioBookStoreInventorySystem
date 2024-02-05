package com.bookstore.ca2project;// BookDbHelper.java


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "BookStore.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BookContract.BookEntry.TABLE_NAME + " (" +
                    BookContract.BookEntry._ID + " INTEGER PRIMARY KEY," +
                    BookContract.BookEntry.COLUMN_NAME_TITLE + " TEXT," +
                    BookContract.BookEntry.COLUMN_NAME_AUTHOR + " TEXT," +
                    BookContract.BookEntry.COLUMN_NAME_PRICE + " REAL," +
                    BookContract.BookEntry.COLUMN_NAME_QUANTITY + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BookContract.BookEntry.TABLE_NAME;

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing table
        db.execSQL("DROP TABLE IF EXISTS " + BookContract.BookEntry.TABLE_NAME);
        // Recreate the table
        onCreate(db);
    }
}
