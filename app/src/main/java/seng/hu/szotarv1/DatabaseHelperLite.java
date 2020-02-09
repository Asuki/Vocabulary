package seng.hu.szotarv1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelperLite extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelperLite";
    private static final String DATABASE_NAME = "vocabulary_db";
    private static final int DATABASE_VERSION = 1;
    public static final int ID_POSITION = 0;
    public static final int NAME_POSITION = 1;

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

    // Storing languages.
    public static final String LANGUAGE_TABLE = "languages";
    private static final String ID = "id";
    private static final String LANGUAGE_NAME = "language_name";
    private static final String CREATE_LANGUAGE_ID = ID + ID_TYPE;
    private static final String CREATE_LANGUAGE_NAME = LANGUAGE_NAME + STRING_TYPE_50;
    private static final String CREATE_LANGUAGE_TABLE = CREATE_TABLE + LANGUAGE_TABLE + " (" +
            CREATE_LANGUAGE_ID + COMMA_SEPARATOR +
            CREATE_LANGUAGE_NAME + UNIQUE_MODIFIER + ");";

    // Storing books.
    public static final String BOOK_TABLE = "books";
    private static final String BOOK_TITLE = "book_title";
    private static final String FIRST_LANGUAGE = "first_language_name";
    private static final String SECOND_LANGUAGE = "second_language_name";
    private static final String CREATE_BOOK_ID = ID + ID_TYPE;
    private static final String CREATE_BOOK_TITLE = BOOK_TITLE + STRING_TYPE_50;
    private static final String CREATE_FIRST_LANGUAGE_ID_F = FIRST_LANGUAGE + STRING_TYPE_50;
    private static final String CREATE_SECOND_LANGUAGE_ID_F = SECOND_LANGUAGE + STRING_TYPE_50;
    private static final String CREATE_BOOK_TABLE = CREATE_TABLE + BOOK_TABLE + " (" +
            CREATE_BOOK_ID + COMMA_SEPARATOR +
            CREATE_BOOK_TITLE + COMMA_SEPARATOR +
            CREATE_FIRST_LANGUAGE_ID_F + COMMA_SEPARATOR +
            CREATE_SECOND_LANGUAGE_ID_F + ");";

    // Storing lessons of books.
    public static final String LESSON_TABLE = "lesson";
    private static final String LESSON_NAME = "name";
    private static final String BOOK_ID_F = "book_id";
    private static final String CREATE_LESSON_ID = ID + ID_TYPE;
    private static final String CREATE_LESSON_NAME = LESSON_NAME + STRING_TYPE_50;
    private static final String CREATE_BOOK_ID_F = BOOK_ID_F + STRING_TYPE_50;
    private static final String CREATE_LESSON_TABLE = CREATE_TABLE + LESSON_TABLE + " (" +
            CREATE_LESSON_ID + COMMA_SEPARATOR +
            CREATE_LESSON_NAME + COMMA_SEPARATOR +
            CREATE_BOOK_ID_F + ");";

    public static final String WORD_TABLE = "words";
    private static final String WORD  = "word";
    private static final String LANGUAGE_NAME_F  = "language_name";
    private static final String MEANING = "meaning";
    private static final String LESSON_ID_F = "lesson_id";
    private static final String KNOWLEDGE = "knowledge";
    private static final String EXAMPLE_SENTENCE = "example_sentence";
    private static final String CREATE_WORD_ID = ID + ID_TYPE;
    private static final String CREATE_WORD = WORD + STRING_TYPE_100;
    private static final String CREATE_LANGUAGE_NAME_F = LANGUAGE_NAME_F + STRING_TYPE_50;
    private static final String CREATE_MEANING = MEANING + STRING_TYPE_200;
    private static final String CREATE_LESSON_ID_F = LESSON_ID_F + STRING_TYPE_50;
    private static final String CREATE_KNOWLEDGE = KNOWLEDGE + INT_TYPE;
    private static final String CREATE_EXAMPLE_SENTENCE = EXAMPLE_SENTENCE + STRING_TYPE_200;
    private static final String CREATE_WORDS_TABLE = CREATE_TABLE + WORD_TABLE + " (" +
            CREATE_WORD_ID + COMMA_SEPARATOR +
            CREATE_WORD + COMMA_SEPARATOR +
            CREATE_LANGUAGE_NAME_F + COMMA_SEPARATOR +
            CREATE_MEANING + COMMA_SEPARATOR +
            CREATE_LESSON_ID_F + COMMA_SEPARATOR +
            CREATE_KNOWLEDGE + COMMA_SEPARATOR +
            CREATE_EXAMPLE_SENTENCE + " );";


    public DatabaseHelperLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creating the database and its tables
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_LANGUAGE_TABLE);
        sqLiteDatabase.execSQL(CREATE_BOOK_TABLE);
        sqLiteDatabase.execSQL(CREATE_LESSON_TABLE);
        sqLiteDatabase.execSQL(CREATE_WORDS_TABLE);
        Log.d(TAG, "onCreate: create " + LANGUAGE_TABLE + " table + " + CREATE_LANGUAGE_TABLE);
        Log.d(TAG, "onCreate: create " + BOOK_TABLE + " table + " + CREATE_BOOK_TABLE);
        Log.d(TAG, "onCreate: create " + LESSON_TABLE + " table + " + CREATE_LESSON_TABLE);
        Log.d(TAG, "onCreate: create " + WORD_TABLE + " table + " + CREATE_WORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    /**
     * Adding new language
     * @param language: the language what you want to add
     * @return
     */
    private boolean addLanguage(String language){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LANGUAGE_NAME, language);

        long result = db.insert(LANGUAGE_TABLE, null, contentValues);
        if(-1 == result)
            return false;
        return true;
    }

    /**
     * Add new book. It also add the languages if them not exists
     * @param title The title of the book
     * @param languageFirst The first language. Mainly the language of the book
     * @param languageSecond The second language. Mainly the users mother language
     * @return True: if the new value have been inserted. False others.
     */
    public boolean addBook(String title, String languageFirst, String languageSecond){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_TITLE, title);
        contentValues.put(FIRST_LANGUAGE, languageFirst);
        contentValues.put(SECOND_LANGUAGE, languageSecond);

        // Trying to add the languages to languages table
        addLanguage(languageFirst);
        addLanguage(languageSecond);

        long result = db.insert(BOOK_TABLE, null, contentValues);
        if (-1 == result){
            return false;
        }
        return true;
    }

    /**
     * Adding new lesson to a book
     * @param name Name of the lesson
     * @param bookId ID of the book
     * @return true if the adding was successful false otherwise.
     */
    public boolean addLesson(String name, int bookId){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LESSON_NAME, name);
        contentValues.put(BOOK_ID_F, bookId);

        long result = db.insert(LESSON_TABLE, null, contentValues);
        if (-1 == result)
            return false;
        return true;
    }

    /**
     * Adding new word for a lesson.
     * @param word The new word, what we want to ad.
     * @param meaning The meanings of the word.
     * @param example An example sentence what describes the word.
     * @param lessonId The ID of the lesson where you want to add the word.
     * @return True if the inserting was successful false otherwise.
     */
    public boolean addWord(String word, String meaning, String example, Integer lessonId, String languageName){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORD, word);
        contentValues.put(MEANING, meaning);
        contentValues.put(EXAMPLE_SENTENCE, example);
        contentValues.put(LESSON_ID_F, lessonId);
        contentValues.put(KNOWLEDGE, 0);
        contentValues.put(LANGUAGE_NAME_F, languageName);

        long result = db.insert(WORD_TABLE, null, contentValues);
        if (result == -1)
            return false;
        return true;
    }

    /**
     * Getting all words of a language.
     * @param language The languages what's words we want to get.
     * @return The cursor of the words.
     */
    public Cursor getAllWords(String language) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "select " + WORD + ", " + MEANING +
                " from " + WORD_TABLE +
                " where " + LANGUAGE_NAME_F + " = " + language;
        Log.d(TAG, "getAllWords: query: " + query);
        Cursor data = db.rawQuery(query, null);
        Log.d(TAG, "getAllWords: query run successfully");
        return data;
    }

    /**
     * Getting all words of a lesson.
     * @param lessonId The ID of a lesson what's words we want to get.
     * @return The cursor of the words.
     */
    public Cursor getAllWordsOfLesson(Integer lessonId) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "select " + WORD + ", " + MEANING +
                " from " + WORD_TABLE +
                " where " + LESSON_ID_F + " = '" + lessonId + "'";
        Log.d(TAG, "getAllWordsOfLesson: query: " + query);
        Cursor data = db.rawQuery(query, null);
        Log.d(TAG, "getAllWordsOfLesson: query run successfully");
        return data;
    }

    /**
     * Getting all words of a book.
     * @param bookId The ID of the book what's words we want to get.
     * @return The cursor of the words.
     */
    public Cursor getAllWordsOfBook(Integer bookId) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "select " + WORD + ", " + MEANING +
                " from " + WORD_TABLE + " as w " +
                " join " + LESSON_TABLE + " as l on w." + LESSON_ID_F + " = l." + ID +
                " where l." + BOOK_ID_F + " = '" + bookId + "'";
        Log.d(TAG, "getAllWordsOfBook: query: " + query);
        Cursor data = db.rawQuery(query, null);
        Log.d(TAG, "getAllWordsOfBook: query run successfully");
        return data;
    }

    /**
     * Getting all data from books table.
     * @return A cursor what contains the data.
     */
    public Cursor getBooks(){
        SQLiteDatabase db = getWritableDatabase();
        String query = SELECT_ALL_ROW_FROM_TABLE + BOOK_TABLE;
        Log.d(TAG, "getBooks. query: " + query);
        Cursor data = db.rawQuery(query, null);
        Log.d(TAG, "getBooks: query was successful");
        return data;
    }
}
