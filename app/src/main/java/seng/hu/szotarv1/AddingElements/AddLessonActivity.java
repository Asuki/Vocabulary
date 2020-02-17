package seng.hu.szotarv1.AddingElements;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import seng.hu.szotarv1.DatabaseHelperLite;
import seng.hu.szotarv1.Listing.LessonsActivity;
import seng.hu.szotarv1.MainActivity;
import seng.hu.szotarv1.R;

public class AddLessonActivity extends AppCompatActivity {

    private static final String TAG = "AddLessonActivity";
    ArrayList<String> bookList;
    DatabaseHelperLite dbLite;
    Spinner spinnerBook;
    EditText editTextLessonName;

    Intent intent;
    boolean listAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);
        initItems();
        fillSpinner();
    }

    private void initItems(){
        bookList = new ArrayList<>();
        dbLite = new DatabaseHelperLite(getBaseContext());
        intent = getIntent();
        Log.i(TAG, "initItems: add_lesson_mode = " + intent.getStringExtra(LessonsActivity.ADD_LESSON_MODE));
        listAll = intent.getStringExtra(LessonsActivity.ADD_LESSON_MODE).equals(LessonsActivity.LESSON_MODE_ALL);
        spinnerBook = findViewById(R.id.spinnerBookLesson);
        editTextLessonName = findViewById(R.id.editTextLessonNameLesson);
        editTextLessonName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
    }

    private void fillSpinner(){
        Log.d(TAG, "fillSpinner: listing all book? = " + listAll);
        if (listAll) {
            Cursor data = dbLite.getBooks();
            while (data.moveToNext()) {
                bookList.add(data.getString(DatabaseHelperLite.NAME_POSITION));
                Log.d(TAG, "fillSpinner: book was added: " + data.getString(DatabaseHelperLite.NAME_POSITION));
            }
            if (0 == bookList.size()) {
                Toast.makeText(getBaseContext(), getString(R.string.add_book_first_error), Toast.LENGTH_LONG).show();
            }
        } else {
            bookList.add(intent.getStringExtra(MainActivity.BOOK_TITLE));
        }
        Log.d(TAG, "fillSpinner: bookList was filled. Books count: " + bookList.size());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookList);
        spinnerBook.setAdapter(adapter);
    }

    public void addLesson(View view){
        if (editTextLessonName.getText().toString().equals(""))
            Toast.makeText(getBaseContext(), getString(R.string.all_fields_are_required_error), Toast.LENGTH_LONG).show();
        else {
            //Toast.makeText(getBaseContext(), spinnerBook.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            dbLite.addLesson(editTextLessonName.getText().toString(), spinnerBook.getSelectedItem().toString());
            finish();
        }
    }
}
