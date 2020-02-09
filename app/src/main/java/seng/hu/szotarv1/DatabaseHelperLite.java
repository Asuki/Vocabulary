package seng.hu.szotarv1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelperLite extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelperLite";
    private static final String DATABASE_NAME = "vocabulary_db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE = " create table if not exists ";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    private static final String ID_TYPE = " integer primary key autoincrement";
    private static final String STRING_TYPE_50 = " varchar(50)";
    private static final String STRING_TYPE_100 = " varchar(100)";
    private static final String STRING_TYPE_200 = " varchar(200)";
    private static final String STRING_TYPE_500 = " varchar(500)";
    private static final String INT_TYPE = " integer";
    private static final String UNIQUE_MODIFIER = " unique";
    private static final String COMMA_SEPARATOR = ", ";
    private static final String DELET_ALL_FROM_TABLE = "delete from ";
    private static final String SELECT_ALL_ROW_FROM_TABLE = "select * from ";

    public static final String LANGUAGE_TABLE = "languages";
    private static final String ID = "id";
    private static final String LANGUAGE_NAME = "language_name";
    private static final String CREATE_LANGUAGE_ID = ID + ID_TYPE;
    private static final String CREATE_LANGUAGE_NAME = LANGUAGE_NAME + STRING_TYPE_50;
    private static final String CREATE_LANGUAGE_TABLE = CREATE_TABLE + LANGUAGE_TABLE + " (" +
            CREATE_LANGUAGE_ID + COMMA_SEPARATOR +
            CREATE_LANGUAGE_NAME +  ");";

    public static final String BOOK_TABLE = "books";
    private static final String BOOK_TITLE = "book_title";
    private static final String FIRST_LANGUAGE_ID_F = "language_id_1";
    private static final String SECOND_LANGUAGE_ID_F = "language_id_2";
    private static final String CREATE_BOOK_ID = ID + ID_TYPE;
    private static final String CREATE_BOOK_TITLE = BOOK_TITLE + STRING_TYPE_50;
    private static final String CREATE_FIRST_LANGUAGE_ID_F = FIRST_LANGUAGE_ID_F + ID_TYPE;
    private static final String CREATE_SECOND_LANGUAGE_ID_F = SECOND_LANGUAGE_ID_F + ID_TYPE;
    private static final String CREATE_BOOK_TABLE = CREATE_TABLE + BOOK_TABLE + " (" +
            CREATE_BOOK_ID + COMMA_SEPARATOR +
            CREATE_BOOK_TITLE + COMMA_SEPARATOR +
            CREATE_FIRST_LANGUAGE_ID_F + COMMA_SEPARATOR +
            CREATE_SECOND_LANGUAGE_ID_F + ");";

    public static final String LESSON_TABLE = "lesson";
    private static final String BOOK_ID_F = "book_id";
    private static final String FIRST_LANGUAGE_WORD = "first_language_word";
    private static final String SECOND_LANGUAGE_MEANING = "second_language_meaning";
    private static final String KNOWLEDGE_PERCENT = "knowledge_percent";
    private static final String EXAMPLE_SENTENCE = "example_sentence";
    private static final String CREATE_LESSON_ID = ID + ID_TYPE;
    private static final String CREATE_BOOK_ID_F = BOOK_ID_F + ID_TYPE;
    private static final String CREATE_FIRST_LANGUAGE_WORD = FIRST_LANGUAGE_WORD + STRING_TYPE_100;
    private static final String CREATE_SECOND_LANGUAGE_MEANING = SECOND_LANGUAGE_MEANING + STRING_TYPE_200;
    private static final String CREATE_KNOWLEDGE_PERCENT = KNOWLEDGE_PERCENT + INT_TYPE;
    private static final String CREATE_EXAMPLE_SENTENCE = EXAMPLE_SENTENCE + STRING_TYPE_200;
    private static final String CREATE_LESSON_TABLE = CREATE_TABLE + LESSON_TABLE + " (" +
            CREATE_LESSON_ID + COMMA_SEPARATOR +
            CREATE_BOOK_ID_F + COMMA_SEPARATOR +
            CREATE_FIRST_LANGUAGE_WORD + COMMA_SEPARATOR +
            CREATE_SECOND_LANGUAGE_MEANING + COMMA_SEPARATOR +
            CREATE_KNOWLEDGE_PERCENT + COMMA_SEPARATOR +
            CREATE_EXAMPLE_SENTENCE + ");";


    public DatabaseHelperLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_LANGUAGE_TABLE);
        sqLiteDatabase.execSQL(CREATE_BOOK_TABLE);
        sqLiteDatabase.execSQL(CREATE_LESSON_TABLE);
        Log.d(TAG, "onCreate: create " + LANGUAGE_TABLE + " table + " + CREATE_LANGUAGE_TABLE);
        Log.d(TAG, "onCreate: create " + BOOK_TABLE + " table + " + CREATE_BOOK_TABLE);
        Log.d(TAG, "onCreate: create " + LESSON_TABLE + " table + " + CREATE_LESSON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public boolean addWord(String title, String firstLanguageWord, String meaning, String example){
        // ToDO: check this
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_TITLE, title);
        contentValues.put(FIRST_LANGUAGE_WORD, firstLanguageWord);
        contentValues.put(SECOND_LANGUAGE_MEANING, meaning);
        contentValues.put(KNOWLEDGE_PERCENT, 0);
        contentValues.put(EXAMPLE_SENTENCE, example);

        Log.d(TAG, "addWord: {title: " + title + ", word: " +
                firstLanguageWord + ", meaning: " + meaning +
                ", example: " + example);

        long result = db.insert(LESSON_TABLE, null, contentValues);
        if (-1 == result)
            return  false;
        return  true;
    }

}
