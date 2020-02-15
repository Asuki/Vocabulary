package seng.hu.szotarv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import seng.hu.szotarv1.Listing.LessonsActivity;

public class EditWordActivity extends AppCompatActivity {

    Intent intentWord;
    String wordId;
    String word;
    String meaning;
    DatabaseHelperLite dbLite;
    public static final String WORD_ID = "word_id";

    EditText editTextWord;
    EditText editTextMeaning;
    Button buttonReady;
    Button buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);

        initItems();
    }

    private void initItems(){
        editTextWord = findViewById(R.id.editTextWordWordEdit);
        editTextMeaning = findViewById(R.id.editTextMeaningWordEdit);
        buttonReady = findViewById(R.id.buttonWordEditReady);
        buttonDelete = findViewById(R.id.buttonWordDelete);

        intentWord = getIntent();
        wordId = getIntent().getStringExtra(WORD_ID);
        dbLite = new DatabaseHelperLite(getBaseContext());

        Cursor data = dbLite.getWord(wordId);
        while (data.moveToNext()){
            editTextWord.setText(data.getString(DatabaseHelperLite.WORD_POSITION));
            editTextMeaning.setText(data.getString(DatabaseHelperLite.MEANING_POSITION));
        }
    }

    public void modifyWord(View view){
        if(editTextWord.getText().toString().equals("") ||
        editTextMeaning.getText().toString().equals("")) {
            Toast.makeText(getBaseContext(), getString(R.string.all_fields_are_required_error),
                    Toast.LENGTH_LONG);
        }
        else {
            word = editTextWord.getText().toString();
            meaning = editTextMeaning.getText().toString();
            dbLite.updateWord(wordId, word, meaning);
            finish();
        }
    }

    public  void deleteWord(View view){
        dbLite.deleteWord(wordId);
        finish();
    }
}
