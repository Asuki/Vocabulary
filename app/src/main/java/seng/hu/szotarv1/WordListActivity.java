package seng.hu.szotarv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class WordListActivity extends AppCompatActivity {

//    Intent intent;
//    ListView listViewLanguageWordList;
//    DatabaseHelperLite dbLite;
//    ArrayList<WordData> wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_word_list2);
//
//        initItems();
//        populateListView();
    }

//    private void initItems(){
//        intent = getIntent();
//        listViewLanguageWordList = findViewById(R.id.listViewLanguageWordList);
//        dbLite = new DatabaseHelperLite(getBaseContext());
//    }
//
//    private void populateListView(){
//        wordList = new ArrayList<>();
//        Cursor data = dbLite.getAllWords(intent.getStringExtra());
//        while (data.moveToNext()){
//            String wordTmp = data.getString(DatabaseHelperLite.WORD_POSITION);
//            String meaningTmp = data.getString(DatabaseHelperLite.MEANING_POSITION);
//            wordList.add(new WordData(wordTmp, meaningTmp));
//        }
//        ArrayAdapter adapter = new WordDataAdapter(this.getBaseContext(), R.layout.adapter_view_double_col, wordList);
//        listViewLanguageWordList.setAdapter(adapter);
//    }
}
