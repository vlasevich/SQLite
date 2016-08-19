package com.home.vlas.sqlite.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.home.vlas.sqlite.model.Tag;
import com.home.vlas.sqlite.model.ToDo;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();
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

    public long createToDo(ToDo toDo, long[] tagIds) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TODO, toDo.getNote());
        contentValues.put(KEY_STATUS, toDo.getStatus());
        contentValues.put(KEY_CREATED_AT, toDo.getCreated_at());

        long todoId = db.insert(TABLE_TODO, null, contentValues);

        for (long tagId : tagIds) {
            createToDoTag(todoId, tagId);
        }

        return todoId;
    }

    public ToDo getToDo(long todoId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_TODO
                + "WHERE " + KEY_ID + "=" + todoId;
        Log.i(TAG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) {
            c.moveToFirst();
        }
        ToDo toDo = new ToDo();
        toDo.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        toDo.setNote(c.getString(c.getColumnIndex(KEY_TODO)));
        toDo.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

        return toDo;
    }

    public List<ToDo> getAllToDos() {
        List<ToDo> todoList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TODO;
        Log.i(TAG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                ToDo toDo = new ToDo();
                toDo.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                toDo.setNote(c.getString(c.getColumnIndex(KEY_TODO)));
                toDo.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                todoList.add(toDo);
            } while (c.moveToNext());
        }
        return todoList;
    }

    public List<ToDo> getAllToDosByTag(String tagName) {
        List<ToDo> toDoList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TODO + "td, " + TABLE_TAG + " tg, " + TABLE_TODO_TAG + " tt, "
                + "WHERE tg." + KEY_TAG_NAME + " = '" + tagName + "'"
                + " AND tg." + KEY_ID + " = tt." + KEY_TAG_ID
                + " AND td." + KEY_ID + " = tt." + KEY_TODO_ID;

        Log.i(TAG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                ToDo toDo = new ToDo();
                toDo.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                toDo.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
                toDo.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
                toDoList.add(toDo);
            } while (c.moveToNext());
        }
        return toDoList;
    }

    public int updateToDo(ToDo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TODO, todo.getNote());
        values.put(KEY_STATUS, todo.getStatus());

        return db.update(TABLE_TODO, values, KEY_ID + " =?", new String[]{String.valueOf(todo.getId())});
    }

    public void deleteToDo(long toDoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_ID + "=?", new String[]{String.valueOf(toDoId)});
    }

    //////TAG///////
    public long createTag(Tag tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TAG_NAME, tag.getTagName());
        values.put(KEY_CREATED_AT, getDateTime());

        long tagId = db.insert(TABLE_TAG, null, values);
        return tagId;
    }

    public List<Tag> getAllTags() {
        List<Tag> tagList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TAG;
        Log.i(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Tag t = new Tag();
                t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                t.setTagName(c.getString(c.getColumnIndex(KEY_TAG_NAME)));
                tagList.add(t);
            } while (c.moveToNext());
        }
        return tagList;
    }

    public int updateTag(Tag tag) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TAG_NAME, tag.getTagName());

        return db.update(TABLE_TAG, values, KEY_ID + " = ?",
                new String[]{String.valueOf(tag.getId())});
    }

    public void deleteTag(Tag tag, boolean shouldDeleteAllTagTodos) {
        SQLiteDatabase db = this.getWritableDatabase();
        // before deleting tag
        // check if todos under this tag should also be deleted
        if (shouldDeleteAllTagTodos) {
            List<ToDo> allTagsToDos = getAllToDosByTag(tag.getTagName());

            //delete all todos
            for (ToDo toDo : allTagsToDos) {
                //delete toDo
                deleteToDo(toDo.getId());
            }
        }
        db.delete(TABLE_TAG, KEY_ID + "=?", new String[]{String.valueOf(tag.getId())});
    }

    public int updateNoteTag(long id, long tag_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TAG_ID, tag_id);

        return db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    private String getDateTime() {
        return null;
    }

    private void createToDoTag(long todoId, long tagId) {
    }


}
