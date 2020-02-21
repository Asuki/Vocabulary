package seng.hu.szotarv1.Listing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

import seng.hu.szotarv1.AddingElements.AddNewWordActivity;
import seng.hu.szotarv1.DatabaseHelperLite;
import seng.hu.szotarv1.Editors.EditWordActivity;
import seng.hu.szotarv1.MainActivity;
import seng.hu.szotarv1.R;
import seng.hu.szotarv1.WordData;
import seng.hu.szotarv1.WordDataAdapter;

public class WordListActivity extends AppCompatActivity {

    ListView listViewWordList;
    Intent intent;
    DatabaseHelperLite dbLite;
    ArrayList<WordData> wordList;
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
                bundle.putString(MainActivity.ADD_WORD_MODE, MainActivity.WORD_MODE_ONE);
                Log.i(TAG, "onClick: ln = " + lessonName);
                intentAddWord.putExtras(bundle);
                startActivityForResult(intentAddWord, EDIT_REQUEST_CODE);
            }
        });

        listViewWordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentWord = new Intent(WordListActivity.this, EditWordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(EditWordActivity.WORD_ID, wordList.get(i).getId());
                intentWord.putExtras(bundle);
                startActivityForResult(intentWord, EDIT_REQUEST_CODE);
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
        while (data.moveToNext()){
            String wordTmp = data.getString(DatabaseHelperLite.WORD_POSITION);
            String meaningTmp = data.getString(DatabaseHelperLite.MEANING_POSITION);
            String wordId = String.valueOf(data.getInt(DatabaseHelperLite.ID_POSITION));
            wordList.add(new WordData(wordId, wordTmp, meaningTmp));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fillWordList();
        populateListView();
    }

    private void populateListView() {
        ArrayAdapter adapter = new WordDataAdapter(this.getBaseContext(), R.layout.adapter_view_double_col, wordList);
        Log.d(TAG, "populateListView: cnt = " + wordList.size());
        listViewWordList.setAdapter(adapter);
    }
}
