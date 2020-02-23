package seng.hu.szotarv1.Editors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import seng.hu.szotarv1.DatabaseHelperLite;
import seng.hu.szotarv1.Listing.LessonsActivity;
import seng.hu.szotarv1.MainActivity;
import seng.hu.szotarv1.R;

public class EditLessonActivity extends AppCompatActivity {

    private Intent intent;
    private DatabaseHelperLite databaseHelperLite;

    EditText editTextLessonName;
    String lessonName;
    String bookTittle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lesson);
        initItems();
    }

    private void initItems(){
        editTextLessonName = findViewById(R.id.editTextLessonNameEditLesson);
        intent = getIntent();
        lessonName = intent.getStringExtra(LessonsActivity.LESSON_NAME);
        bookTittle = intent.getStringExtra(MainActivity.BOOK_TITLE);
        editTextLessonName.setText(lessonName);
        databaseHelperLite = new DatabaseHelperLite(getBaseContext());
    }

    public void modifyLesson(View view){
        if (editTextLessonName.getText().toString().equals("")){
            Toast.makeText(getBaseContext(),
                    getString(R.string.all_fields_are_required_error),
                    Toast.LENGTH_LONG);
        } else {
            databaseHelperLite.updateLessonData(getBaseContext(),
                    lessonName, editTextLessonName.getText().toString(), bookTittle);
            finish();
        }
    }
}
