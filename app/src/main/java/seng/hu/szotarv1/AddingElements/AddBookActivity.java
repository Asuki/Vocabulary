package seng.hu.szotarv1.AddingElements;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import seng.hu.szotarv1.DatabaseHelperLite;
import seng.hu.szotarv1.R;

public class AddBookActivity extends AppCompatActivity {

    EditText editTextBookTitle;
    EditText editTextLanguage2;
    ImageView buttonReady;
    DatabaseHelperLite dbLite;
    Spinner spinnerLanguages;

    Locale[] languages;
    ArrayList<String> languageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        initItems();
        populateSpinner();
    }

    private void initItems(){
        editTextBookTitle = findViewById(R.id.editTextBookTitleEdit);
        editTextLanguage2 = findViewById(R.id.editTextBookLanguage2Edit);
        buttonReady = findViewById(R.id.buttonReadyEdit);
        dbLite = new DatabaseHelperLite(getBaseContext());


        languages = Locale.getAvailableLocales();
        languageList = new ArrayList<>();
        for(int i = 0; i < languages.length; i++){
            languageList.add(languages[i].getDisplayName());
        }
        spinnerLanguages = findViewById(R.id.spinnerLanguagesAddBook);
        setEditTextTypes();
    }

    private void setEditTextTypes(){
        editTextBookTitle.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        editTextLanguage2.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
    }

    private void populateSpinner(){
        Collections.sort(languageList);
        ArrayList<String> actList = new ArrayList<>();
        actList.addAll(languageList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, actList);
        spinnerLanguages.setAdapter(adapter);
    }

    public void addBook(View view){
        if (editTextLanguage2.getText().toString().equals("") ||
        editTextBookTitle.getText().toString().equals(""))
            Toast.makeText(getBaseContext(), getString(R.string.all_fields_are_required_error), Toast.LENGTH_LONG).show();
        else {
            dbLite.addBook(editTextBookTitle.getText().toString(),
                    spinnerLanguages.getSelectedItem().toString(),
                    editTextLanguage2.getText().toString());
            finish();
        }
    }
}
