package seng.hu.szotarv1.AddingElements;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import seng.hu.szotarv1.DatabaseHelperLite;
import seng.hu.szotarv1.R;

public class AddBookActivity extends AppCompatActivity {

    EditText editTextBookTitle;
    EditText editTextLanguage1;
    EditText editTextLanguage2;
    Button buttonReady;
    DatabaseHelperLite dbLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        initItems();
    }

    private void initItems(){
        editTextBookTitle = findViewById(R.id.editTextBookTitleEdit);
        editTextLanguage1 = findViewById(R.id.editTextBookLanguage1Edit);
        editTextLanguage2 = findViewById(R.id.editTextBookLanguage2Edit);
        buttonReady = findViewById(R.id.buttonReadyEdit);
        dbLite = new DatabaseHelperLite(getBaseContext());
    }

    public void addBook(View view){
        if (editTextLanguage1.getText().toString().equals("") ||
        editTextLanguage2.getText().toString().equals("") ||
        editTextBookTitle.getText().toString().equals(""))
            Toast.makeText(getBaseContext(), getString(R.string.all_fields_are_required_error), Toast.LENGTH_LONG).show();
        else {
            dbLite.addBook(editTextBookTitle.getText().toString(),
                    editTextLanguage1.getText().toString(),
                    editTextLanguage2.getText().toString());
            finish();
        }
    }
}
