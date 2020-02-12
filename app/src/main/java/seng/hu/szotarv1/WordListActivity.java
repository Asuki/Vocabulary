package seng.hu.szotarv1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class WordListActivity extends AppCompatActivity {

    ListView listViewWordList;
    Intent intent;
    DatabaseHelperLite dbLite;
    ArrayList<String> wordList;
    FloatingActionButton floatingActionButtonAddWord;
    private static final int EDIT_REQUEST_CODE = 0;
    private static final String TAG = "WordListActivity";
    String bookTittle;
    String lessonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        initItems();



        floatingActionButtonAddWord = findViewById(R.id.floatingActionButtonWordList);
        floatingActionButtonAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddWord = new Intent(WordListActivity.this, AddNewWordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(MainActivity.BOOK_TITLE, bookTittle);
                bundle.putString(AddNewWordActivity.LESSON_NAME_WORD, lessonName);
                Log.i(TAG, "onClick: ln = " + lessonName);
                intentAddWord.putExtras(bundle);
                startActivityForResult(intentAddWord, EDIT_REQUEST_CODE);
            }
        });

        populateListView();
    }

    private void initItems(){

        wordList = new ArrayList<>();
        listViewWordList = findViewById(R.id.listViewWordList);
        intent = getIntent();
        bookTittle = intent.getStringExtra(MainActivity.BOOK_TITLE);
        lessonName = intent.getStringExtra(LessonsActivity.LESSON_NAME);
        Log.i(TAG, "initItems: book tittle = " + bookTittle);
        dbLite = new DatabaseHelperLite(getBaseContext());
        fillWordList();

    }

    private void fillWordList(){
        wordList.clear();
        Cursor data = dbLite.getAllWordsOfLesson(bookTittle, lessonName);
        int i = 0;
        while (data.moveToNext()){
            String tmp = data.getString(DatabaseHelperLite.WORD_POSITION);
            wordList.add(tmp);
            Log.i(TAG, "fillWordList: index = " + i++ + "|" + tmp);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fillWordList();
        populateListView();
    }

    private void populateListView() {
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, wordList);
        Log.d(TAG, "populateListView: cnt = " + wordList.size());
        listViewWordList.setAdapter(adapter);
    }
}
