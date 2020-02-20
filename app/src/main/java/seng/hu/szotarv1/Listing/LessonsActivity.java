package seng.hu.szotarv1.Listing;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import seng.hu.szotarv1.AddingElements.AddLessonActivity;
import seng.hu.szotarv1.DatabaseHelperLite;
import seng.hu.szotarv1.Editors.BookEditorActivity;
import seng.hu.szotarv1.MainActivity;
import seng.hu.szotarv1.R;
import seng.hu.szotarv1.ui.EditLessonActivity;

public class LessonsActivity extends AppCompatActivity {

    ListView listViewLessons;
    Intent intent;
    TextView textViewBookTitleLessons;
    FloatingActionButton floatingActionButtonAddLesson;
    FloatingActionButton floatingActionButtonEditMode;
    FloatingActionMenu fabMenu;

    String bookTittle;
    DatabaseHelperLite dbLite;
    ArrayList<String> lessonList;


    public static final String ADD_LESSON_MODE = "add_lesson_mode";
    public static final String LESSON_MODE_ONE = "one";
    public static final String LESSON_MODE_ALL = "all";
    private static final int EDIT_REQUEST_CODE = 0;
    public static final String LESSON_NAME = "lesson_name";
    private boolean editMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        initItems();
        populateListView();
    }

    private void initItems(){
        listViewLessons = findViewById(R.id.listViewLessonList);
        listViewLessons.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        intent = getIntent();
        bookTittle = intent.getStringExtra(MainActivity.BOOK_TITLE);
        textViewBookTitleLessons = findViewById(R.id.textViewBookTitleLessons);
        textViewBookTitleLessons.setText(bookTittle);
        dbLite = new DatabaseHelperLite(getBaseContext());
        lessonList = new ArrayList<>();
        editMode = false;

        fabMenu = findViewById(R.id.fab_menuEditLesson);

        listViewLessons.setOnItemClickListener(new ListView.OnItemClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!editMode) {
                    Bundle bundle = new Bundle();
                    bundle.putString(MainActivity.BOOK_TITLE, bookTittle);
                    bundle.putString(LESSON_NAME, adapterView.getItemAtPosition(i).toString());
                    Intent intentWordList = new Intent(LessonsActivity.this, WordListActivity.class);
                    intentWordList.putExtras(bundle);
                    startActivityForResult(intentWordList, EDIT_REQUEST_CODE);
                } else {
                    Intent intentLessonEdit = new Intent(getBaseContext(), EditLessonActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(MainActivity.BOOK_TITLE, bookTittle);
                    bundle.putString(LessonsActivity.LESSON_NAME, adapterView.getItemAtPosition(i).toString());
                    intentLessonEdit.putExtras(bundle);
                    startActivityForResult(intentLessonEdit, EDIT_REQUEST_CODE);
                    floatingActionButtonEditMode.setColorNormal(getColor(R.color.colorPrimary));
                    listViewLessons.setBackgroundColor(getColor(R.color.white));
                    editMode = false;
                }
            }
        });

        floatingActionButtonEditMode = findViewById(R.id.fabEditModeLessonList);
        floatingActionButtonEditMode.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                if (editMode) {
                    editMode = false;
                    floatingActionButtonEditMode.setColorNormal(getColor(R.color.colorPrimary));
                    listViewLessons.setBackgroundColor(getColor(R.color.white));
                    populateListView();
                }
                else {
                    editMode = true;
                    floatingActionButtonEditMode.setColorNormal(getColor(R.color.colorAccent));
                    listViewLessons.setBackgroundColor(getColor(R.color.light_orange));
                    populateListView();
                }

            }
        });

        floatingActionButtonAddLesson = findViewById(R.id.fabAddLessonLessonList);
        floatingActionButtonAddLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddWord = new Intent(LessonsActivity.this, AddLessonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(MainActivity.BOOK_TITLE, bookTittle);
                bundle.putString(ADD_LESSON_MODE, LESSON_MODE_ONE);
                intentAddWord.putExtras(bundle);
                startActivityForResult(intentAddWord, EDIT_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        populateListView();
    }

    private void populateListView(){
        lessonList.clear();
        Cursor data = dbLite.getLessonsOfBook(bookTittle);
        while (data.moveToNext()){
            lessonList.add(data.getString(DatabaseHelperLite.NAME_POSITION));
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lessonList);
        listViewLessons.setAdapter(adapter);
    }
}
