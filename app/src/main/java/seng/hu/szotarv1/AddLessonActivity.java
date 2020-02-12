package seng.hu.szotarv1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddLessonActivity extends AppCompatActivity {

    ArrayList<String> bookList;
    DatabaseHelperLite dbLite;
    Spinner spinnerBook;
    EditText editTextLessonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);
        initItems();
        fillSpinner();
    }

    private void initItems(){
        bookList = new ArrayList<>();
        dbLite = new DatabaseHelperLite(getBaseContext());
        spinnerBook = findViewById(R.id.spinnerBookLesson);
        editTextLessonName = findViewById(R.id.editTextLessonNameLesson);
    }

    private void fillSpinner(){
        Cursor data = dbLite.getBooks();
        while(data.moveToNext()){
            bookList.add(data.getString(DatabaseHelperLite.NAME_POSITION));
        }
        if (0 == bookList.size()){
            Toast.makeText(getBaseContext(), getString(R.string.add_book_first_error), Toast.LENGTH_LONG).show();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookList);
        spinnerBook.setAdapter(adapter);
    }

    public void addLesson(View view){
        if (editTextLessonName.getText().toString().equals(""))
            Toast.makeText(getBaseContext(), getString(R.string.all_fields_are_required_error), Toast.LENGTH_LONG).show();
        else {
            //Toast.makeText(getBaseContext(), spinnerBook.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            dbLite.addLesson(editTextLessonName.getText().toString(), spinnerBook.getSelectedItem().toString());
            finish();
        }
    }
}
