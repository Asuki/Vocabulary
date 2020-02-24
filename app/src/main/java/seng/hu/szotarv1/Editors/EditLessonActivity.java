package seng.hu.szotarv1.Editors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import seng.hu.szotarv1.DatabaseHelperLite;
import seng.hu.szotarv1.Listing.LessonsActivity;
import seng.hu.szotarv1.MainActivity;
import seng.hu.szotarv1.R;

public class EditLessonActivity extends AppCompatActivity {

    private Intent intent;
    private DatabaseHelperLite databaseHelperLite;

    EditText editTextLessonName;
    Spinner spinnerBooks;
    String lessonName;
    String bookTittle;
    ArrayList<String> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lesson);
        initItems();
        populateSpinner();
    }

    private void initItems(){
        editTextLessonName = findViewById(R.id.editTextLessonNameEditLesson);
        spinnerBooks = findViewById(R.id.spinnerBookListEditLesson);
        intent = getIntent();
        lessonName = intent.getStringExtra(LessonsActivity.LESSON_NAME);
        bookList = new ArrayList<>();
        bookTittle = intent.getStringExtra(MainActivity.BOOK_TITLE);
        editTextLessonName.setText(lessonName);
        databaseHelperLite = new DatabaseHelperLite(getBaseContext());
    }

    public void modifyLesson(View view){
        if (editTextLessonName.getText().toString().equals("")){
            Toast.makeText(getBaseContext(),
                    getString(R.string.all_fields_are_required_error),
                    Toast.LENGTH_LONG);
        } else {
            databaseHelperLite.updateLessonData(lessonName,
                    editTextLessonName.getText().toString(),
                    bookTittle,
                    spinnerBooks.getSelectedItem().toString());
            finish();
        }
    }

    private void populateSpinner(){
        bookList.clear();
        Cursor data = databaseHelperLite.getBooks();
        while (data.moveToNext()){
            bookList.add(data.getString(DatabaseHelperLite.NAME_POSITION));
        }
        Collections.sort(bookList);
        ArrayList<String> actList = new ArrayList<>();
        actList.add(bookTittle);
        actList.addAll(bookList);
        ArrayAdapter adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, actList);
        spinnerBooks.setAdapter(adapter);
    }
}
