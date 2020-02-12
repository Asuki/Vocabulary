package seng.hu.szotarv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

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
        spinnerLesson = findViewById(R.id.spinnerLessonNameAddWord);
        editTextWord = findViewById(R.id.editTextWordAddWord);
        editTextMeaning = findViewById(R.id.editTextMeaningAddWord);
        buttonAdd = findViewById(R.id.buttonAddAddWord);

        dbLite = new DatabaseHelperLite(getBaseContext());
        intent = getIntent();
        bookList = new ArrayList<>();
        lessonList = new ArrayList<>();

        lessonName = intent.getStringExtra(LESSON_NAME_WORD);
        bookTittle = intent.getStringExtra(MainActivity.BOOK_TITLE);
    }

    private void setData(){
        lessonList.add(lessonName);
        bookList.add(bookTittle);

        Log.d(TAG, "setData: |" + lessonName + "|");
        ArrayAdapter adapterLesson = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, lessonList);
        spinnerLesson.setAdapter(adapterLesson);
        ArrayAdapter arrayAdapterBook = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, bookList);
        spinnerBook.setAdapter(arrayAdapterBook);
    }

    public void addWord(View view){
        dbLite.addWord(editTextWord.getText().toString(), editTextMeaning.getText().toString(),
                "", intent.getStringExtra(LESSON_NAME_WORD),
                intent.getStringExtra(MainActivity.BOOK_TITLE));
        finish();
    }
}
