package seng.hu.szotarv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LessonsActivity extends AppCompatActivity {

    ListView listViewLessons;
    Intent intent;
    TextView textViewBookTitleLessons;
    String bookTitle;
    DatabaseHelperLite dbLite;
    ArrayList<String> lessonList;

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
