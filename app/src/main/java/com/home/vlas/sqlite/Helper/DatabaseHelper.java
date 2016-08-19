package com.home.vlas.sqlite.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG=DatabaseHelper.class.getSimpleName();
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="contactsManager";

    private static final String TABLE_TODO="todos";
    private static final String TABLE_TAG="tags";
    private static final String TABLE_TODO_TAG="todo_tags";

    private static final String KEY_ID="id";
    private static final String KEY_CREATED_AT="created_at";

    private static final String KEY_TODO = "todo";
    private static final String KEY_STATUS = "status";

    private static final String KEY_TAG_NAME = "tag_name";

    private static final String KEY_TODO_ID = "todo_id";
    private static final String KEY_TAG_ID = "tag_id";

    private static final String CREATE_TABLE_TODO="CREATE TABLE "
            +TABLE_TODO
            +"("+KEY_ID+" INTEGER PRIMARY KEY,"
            +KEY_TODO +"TEXT,"
            +KEY_STATUS+"INTEGER,"
            +KEY_CREATED_AT+"DATETIME"+")";

    private static final String CREATE_TABLE_TAG = "CREATE TABLE "
            + TABLE_TAG
            + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TAG_NAME + " TEXT,"
            + KEY_CREATED_AT
            + " DATETIME" + ")";

    private static final String CREATE_TABLE_TODO_TAG = "CREATE TABLE "
            + TABLE_TODO_TAG
            + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TODO_ID + " INTEGER,"
            + KEY_TAG_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TODO);
        db.execSQL(CREATE_TABLE_TAG);
        db.execSQL(CREATE_TABLE_TODO_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TODO);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TAG);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TODO_TAG);

        onCreate(db);
    }
}
