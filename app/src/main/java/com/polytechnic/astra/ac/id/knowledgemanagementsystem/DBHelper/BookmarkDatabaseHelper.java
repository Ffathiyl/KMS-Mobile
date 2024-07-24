package com.polytechnic.astra.ac.id.knowledgemanagementsystem.DBHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BookmarkDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "KnowledgeDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_BOOKMARKS = "bookmarks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_KEY = "key";
    private static final String COLUMN_PRO_ID = "pro_id";
    private static final String COLUMN_NAMA_KATEGORI = "nama_kategori";

    public BookmarkDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_BOOKMARKS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAMA_KATEGORI + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKMARKS);
        onCreate(db);
    }

    public List<String> getAllBookmarks() {
        List<String> bookmarks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_BOOKMARKS, new String[]{COLUMN_NAMA_KATEGORI}, null, null, null, null, null);
            int columnIndex = cursor.getColumnIndex(COLUMN_NAMA_KATEGORI);

            if (columnIndex == -1) {
                // Handle the case where the column does not exist
                throw new RuntimeException("Column " + COLUMN_NAMA_KATEGORI + " does not exist in the database.");
            }

            while (cursor.moveToNext()) {
                bookmarks.add(cursor.getString(columnIndex));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., log the error, notify the user)
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return bookmarks;
    }

    public boolean addBookmark(String namaKategori) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA_KATEGORI, namaKategori);
        long result = db.insert(TABLE_BOOKMARKS, null, values);
        return result != -1;
    }

    public boolean removeBookmark(String namaKategori) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_BOOKMARKS, COLUMN_NAMA_KATEGORI + "=?", new String[]{namaKategori});
        return result > 0;
    }

    public boolean isBookmarked(String namaKategori) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKMARKS, new String[]{COLUMN_ID}, COLUMN_NAMA_KATEGORI + "=?", new String[]{namaKategori}, null, null, null);
        boolean isBookmarked = cursor.getCount() > 0;
        cursor.close();
        return isBookmarked;
    }
}
