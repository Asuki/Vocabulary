package seng.hu.szotarv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LessonsActivity extends AppCompatActivity {

    ListView listViewLessons;
    Intent intent;
    TextView textViewBookTitleLessons;

    String bookTitle;
    DatabaseHelperLite dbLite;
    ArrayList<String> lessonList;

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
        bookTitle = intent.getStringExtra(MainActivity.BOOK_TITLE);
        textViewBookTitleLessons = findViewById(R.id.textViewBookTitleLessons);
        textViewBookTitleLessons.setText(bookTitle);
        dbLite = new DatabaseHelperLite(getBaseContext());
        lessonList = new ArrayList<>();

        listViewLessons.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString(MainActivity.BOOK_TITLE, bookTitle);
                bundle.putString(LESSON_NAME, adapterView.getItemAtPosition(i).toString());
                Intent intentWordList = new Intent(LessonsActivity.this, WordListActivity.class);
                intentWordList.putExtras(bundle);
                startActivityForResult(intentWordList, EDIT_REQUEST_CODE);
//                Intent intentAddWord = new Intent(LessonsActivity.this, AddNewWordActivity.class);
//                startActivityForResult(intentAddWord, EDIT_REQUEST_CODE);
            }
        });
    }

    private void populateListView(){
        Cursor data = dbLite.getLessonsOfBook(bookTitle);
        while (data.moveToNext()){
            lessonList.add(data.getString(DatabaseHelperLite.NAME_POSITION));
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lessonList);
        listViewLessons.setAdapter(adapter);
    }
}
