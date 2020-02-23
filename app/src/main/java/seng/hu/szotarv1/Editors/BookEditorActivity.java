package seng.hu.szotarv1.Editors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Locale;

import seng.hu.szotarv1.DatabaseHelperLite;
import seng.hu.szotarv1.MainActivity;
import seng.hu.szotarv1.R;

public class BookEditorActivity extends AppCompatActivity {

    EditText editTextBookTitle;
//    EditText editTextLanguage1;
    EditText editTextLanguage2;
    ImageView buttonReady;
    Spinner spinnerLanguage;

    Intent intent;
    DatabaseHelperLite dbLite;
    Cursor data;

    // The original data of the book
    String bookTitle;
    String language1;
    String language2;
    Locale[] languages;
    ArrayList<String> languageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_editor);

        initItem();
        populateSpinner();
        setData();
    }

    /**
     * Initializing items.
     */
    private void initItem(){

        editTextBookTitle = findViewById(R.id.editTextBookTitleBookEdit);
//        editTextLanguage1 = findViewById(R.id.editTextLanguage1BookEdit);
        editTextLanguage2 = findViewById(R.id.editTextLanguage2BookEdit);
        buttonReady = findViewById(R.id.buttonReadyBookEdit);
        intent = getIntent();

        bookTitle = intent.getStringExtra(MainActivity.BOOK_TITLE);
        dbLite = new DatabaseHelperLite(getBaseContext());
        data = dbLite.getBookByTitle(bookTitle);
        data.moveToFirst();
        language1 = data.getString(DatabaseHelperLite.LANGUAGE_1_POSITION);
        language2 = data.getString(DatabaseHelperLite.LANGUAGE_2_POSITION);
        languages = Locale.getAvailableLocales();
        languageList = new ArrayList<>();
        for(int i = 0; i < languages.length; i++){
            languageList.add(languages[i].getDisplayName());
        }
        spinnerLanguage = findViewById(R.id.spinnerFirstLanguageBookEditor);
        setEditTextInputTypes();
    }

    private void populateSpinner(){
        Collections.sort(languageList);
        ArrayList<String> actList = new ArrayList<>();
        actList.add(language1);
        actList.addAll(languageList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, actList);
        spinnerLanguage.setAdapter(adapter);
    }

    private void setEditTextInputTypes() {
        editTextBookTitle.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
//        editTextLanguage1.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        editTextLanguage2.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
    }

    private void setData(){
        editTextBookTitle.setText(bookTitle);
//        editTextLanguage1.setText(language1);
        editTextLanguage2.setText(language2);
    }

    public void modifyReadyBookEdit(View view){
        if (
//                editTextLanguage1.getText().toString().equals("") ||
        editTextLanguage2.getText().toString().equals("") ||
        editTextBookTitle.getText().toString().equals("")){
            Toast.makeText(getBaseContext(), getString(R.string.all_fields_are_required_error),
                    Toast.LENGTH_LONG).show();
        } else {
            dbLite.updateBookData(bookTitle, language1, language2,
                    editTextBookTitle.getText().toString(),
                    spinnerLanguage.getSelectedItem().toString(),
                    editTextLanguage2.getText().toString(),
                    getBaseContext());
            finish();
        }
    }
}
