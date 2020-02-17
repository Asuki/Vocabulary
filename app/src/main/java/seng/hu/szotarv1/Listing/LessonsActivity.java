package seng.hu.szotarv1.Listing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import seng.hu.szotarv1.AddingElements.AddLessonActivity;
import seng.hu.szotarv1.AddingElements.AddNewWordActivity;
import seng.hu.szotarv1.DatabaseHelperLite;
import seng.hu.szotarv1.MainActivity;
import seng.hu.szotarv1.R;

public class LessonsActivity extends AppCompatActivity {

    ListView listViewLessons;
    Intent intent;
    TextView textViewBookTitleLessons;
    FloatingActionButton floatingActionButtonAddLesson;

    String bookTittle;
    DatabaseHelperLite dbLite;
    ArrayList<String> lessonList;


    public static final String ADD_LESSON_MODE = "add_lesson_mode";
    public static final String LESSON_MODE_ONE = "one";
    public static final String LESSON_MODE_ALL = "all";
    private static final int EDIT_REQUEST_CODE = 0;
    public static final String LESSON_NAME = "lesson_name";

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

        listViewLessons.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString(MainActivity.BOOK_TITLE, bookTittle);
                bundle.putString(LESSON_NAME, adapterView.getItemAtPosition(i).toString());
                Intent intentWordList = new Intent(LessonsActivity.this, WordListActivity.class);
                intentWordList.putExtras(bundle);
                startActivityForResult(intentWordList, EDIT_REQUEST_CODE);
//                Intent intentAddWord = new Intent(LessonsActivity.this, AddNewWordActivity.class);
//                startActivityForResult(intentAddWord, EDIT_REQUEST_CODE);
            }
        });

        floatingActionButtonAddLesson = findViewById(R.id.floatingActionButtonLessonList);
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
