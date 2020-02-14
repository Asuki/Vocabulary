package seng.hu.szotarv1.AddingElements;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import seng.hu.szotarv1.DatabaseHelperLite;
import seng.hu.szotarv1.MainActivity;
import seng.hu.szotarv1.R;

public class AddNewWordActivity extends AppCompatActivity {

    Spinner spinnerBook;
    Spinner spinnerLesson;
    EditText editTextWord;
    EditText editTextMeaning;
    Button buttonAdd;

    DatabaseHelperLite dbLite;
    ArrayList<String> bookList;
    ArrayList<String> lessonList;
    Intent intent;
    String lessonName;
    String bookTittle;
    boolean listAll;

    public static final String LESSON_NAME_WORD = "lesson name word";
    private static final String TAG = "AddNewWordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_word);

        initItems();
        setData();
    }

    private void initItems(){
        spinnerBook = findViewById(R.id.spinnerBookTittleAddWord);
        spinnerBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                refillLessonsSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerLesson = findViewById(R.id.spinnerLessonNameAddWord);
        editTextWord = findViewById(R.id.editTextWordAddWord);
        editTextMeaning = findViewById(R.id.editTextMeaningAddWord);
        buttonAdd = findViewById(R.id.buttonAddAddWord);

        dbLite = new DatabaseHelperLite(getBaseContext());
        intent = getIntent();
        bookList = new ArrayList<>();
        lessonList = new ArrayList<>();

        listAll = intent.getStringExtra(MainActivity.ADD_WORD_MODE).equals(MainActivity.WORD_MODE_ALL);
    }

    private void setData(){

        if (!listAll) {
            lessonName = intent.getStringExtra(LESSON_NAME_WORD);
            bookTittle = intent.getStringExtra(MainActivity.BOOK_TITLE);
            lessonList.add(lessonName);
            bookList.add(bookTittle);
        }
        else {
            Cursor cursorBook = dbLite.getBooks();
            while (cursorBook.moveToNext()){
                bookList.add(cursorBook.getString(DatabaseHelperLite.NAME_POSITION));
            }
            Log.d(TAG, "setData: bookList size = " + bookList.size());
            Log.d(TAG, "setData: first book = " + bookList.get(0));
            refillLessonsSpinner(bookList.get(0));
        }
        Log.d(TAG, "setData: |" + lessonName + "|");
        ArrayAdapter adapterLesson = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, lessonList);
        spinnerLesson.setAdapter(adapterLesson);
        ArrayAdapter arrayAdapterBook = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, bookList);
        spinnerBook.setAdapter(arrayAdapterBook);
    }

    public void addWord(View view){
        dbLite.addWord(editTextWord.getText().toString(), editTextMeaning.getText().toString(),
                "", spinnerLesson.getSelectedItem().toString(),
                spinnerBook.getSelectedItem().toString());
        finish();
    }

    private void refillLessonsSpinner(){
        Cursor cursorLesson = dbLite.getLessonsOfBook(spinnerBook.getSelectedItem().toString());
        lessonList.clear();
        while (cursorLesson.moveToNext()){
            lessonList.add(cursorLesson.getString(DatabaseHelperLite.NAME_POSITION));
        }
        ArrayAdapter adapterLesson = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, lessonList);
        spinnerLesson.setAdapter(adapterLesson);
    }

    private void refillLessonsSpinner(String bookTittle){
        Cursor cursorLesson = dbLite.getLessonsOfBook(bookTittle);
        lessonList.clear();
        while (cursorLesson.moveToNext()){
            lessonList.add(cursorLesson.getString(DatabaseHelperLite.NAME_POSITION));
        }
        ArrayAdapter adapterLesson = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, lessonList);
        spinnerLesson.setAdapter(adapterLesson);
    }
}
