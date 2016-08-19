package com.home.vlas.sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.home.vlas.sqlite.Helper.DatabaseHelper;
import com.home.vlas.sqlite.model.Tag;
import com.home.vlas.sqlite.model.ToDo;

import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        db = new DatabaseHelper(getApplicationContext());

        // Creating tags
        Tag tag1 = new Tag("Shopping");
        Tag tag2 = new Tag("Important");
        Tag tag3 = new Tag("Watchlist");
        Tag tag4 = new Tag("Not Now");

        // Inserting tags in db
        long tag1_id = db.createTag(tag1);
        long tag2_id = db.createTag(tag2);
        long tag3_id = db.createTag(tag3);
        long tag4_id = db.createTag(tag4);

        showData(String.valueOf(db.getAllTags().size()));

        // Creating ToDos
        ToDo ToDo1 = new ToDo("iPhone 5S", 0);
        ToDo ToDo2 = new ToDo("Galaxy Note II", 0);
        ToDo ToDo3 = new ToDo("Whiteboard", 0);

        ToDo ToDo4 = new ToDo("Riddick", 0);
        ToDo ToDo5 = new ToDo("Prisoners", 0);
        ToDo ToDo6 = new ToDo("The Croods", 0);
        ToDo ToDo7 = new ToDo("Insidious: Chapter 2", 0);

        ToDo ToDo8 = new ToDo("Don't forget to call MOM", 0);
        ToDo ToDo9 = new ToDo("Collect money from John", 0);

        ToDo ToDo10 = new ToDo("Repair a car", 0);
        ToDo ToDo11 = new ToDo("Take database backup", 0);

        // Inserting ToDos in db
        // Inserting ToDos under "Shopping" Tag
        long ToDo1_id = db.createToDo(ToDo1, new long[]{tag1_id});
        long ToDo2_id = db.createToDo(ToDo2, new long[]{tag1_id});
        long ToDo3_id = db.createToDo(ToDo3, new long[]{tag1_id});

        // Inserting ToDos under "Watchlist" Tag
        long ToDo4_id = db.createToDo(ToDo4, new long[]{tag3_id});
        long ToDo5_id = db.createToDo(ToDo5, new long[]{tag3_id});
        long ToDo6_id = db.createToDo(ToDo6, new long[]{tag3_id});
        long ToDo7_id = db.createToDo(ToDo7, new long[]{tag3_id});

        // Inserting ToDos under "Important" Tag
        long ToDo8_id = db.createToDo(ToDo8, new long[]{tag2_id});
        long ToDo9_id = db.createToDo(ToDo9, new long[]{tag2_id});

        // Inserting ToDos under "Not Now" Tag
        long ToDo10_id = db.createToDo(ToDo10, new long[]{tag4_id});
        long ToDo11_id = db.createToDo(ToDo11, new long[]{tag4_id});

        showData(String.valueOf(db.getToDoCount()));

        // "Post new Article" - assigning this under "Important" Tag
        // Now this will have - "Not Now" and "Important" Tags
        db.createToDoTag(ToDo10_id, tag2_id);

        // Getting all tag names
        showData("Getting All Tags");

        List<Tag> allTags = db.getAllTags();
        for (Tag tag : allTags) {
            Log.i("Tag Name: ", tag.getTagName());
        }

        // Getting all ToDos
        showData("Getting All ToDos");

        List<ToDo> allToDos = db.getAllToDos();
        for (ToDo ToDo : allToDos) {
            Log.i("ToDo: ", ToDo.getNote());
        }

        // Getting ToDos under "Watchlist" tag name
        Log.i("ToDo", "Get ToDos under single Tag name");

        List<ToDo> tagsWatchList = db.getAllToDosByTag(tag3.getTagName());
        for (ToDo ToDo : tagsWatchList) {
            Log.i("ToDo Watchlist", ToDo.getNote());
        }

        // Deleting a ToDo item
        Log.i("Delete ToDo8", "Deleting a ToDo");
        Log.i(TAG, "Tag Count Before Deleting: " + db.getToDoCount());

        db.deleteToDo(ToDo8_id);

        Log.i(TAG, "Tag Count After Deleting: " + db.getToDoCount());

        // Deleting all ToDos under "Shopping" tag
        Log.i(TAG,
                "Tag Count Before Deleting 'Shopping' ToDos: "
                        + db.getToDoCount());

        db.deleteTag(tag1, true);

        Log.i(TAG,
                "Tag Count After Deleting 'Shopping' ToDos: "
                        + db.getToDoCount());

        // Updating tag name
        tag3.setTagName("Movies to watch");
        db.updateTag(tag3);

        // close database connection
        db.closeDB();

    }

    private void showData(String data) {
        Log.i(TAG, "===================");
        Log.i(TAG, data);
        Log.i(TAG, "===================");
    }
}
