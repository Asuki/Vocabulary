package seng.hu.szotarv1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import seng.hu.szotarv1.ui.languageList.LanguageList;

public class LanguageWordList extends AppCompatActivity {

    private static final String TAG ="LanguageWordList" ;
    Intent intent;
    ListView listViewLanguageWordList;
    DatabaseHelperLite dbLite;
    ArrayList<WordData> wordList;
    TextToSpeech textToSpeech;
    TextToSpeech textToSpeechLocal;
    FloatingActionButton floatingActionButton;
    Locale[] languages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list2);

        initItems();
        populateListView();
    }

    private void initItems(){
//        intent = getIntent();
        listViewLanguageWordList = findViewById(R.id.listViewLanguageWordList);
        dbLite = new DatabaseHelperLite(getBaseContext());

        languages = Locale.getAvailableLocales();

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(languages[0]);
                    for (int i = 0; i < languages.length; i++){
                        if(languages[i].toLanguageTag().equals("en")){
                            ttsLang = textToSpeech.setLanguage(languages[i]);
                            Log.d(TAG, "onInit: Selected speech language: " + languages[i].getDisplayName());
                            break;
                        }
                        Log.d(TAG, "onInit: Current speech language: " + languages[i].getDisplayName());
                    }

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textToSpeechLocal = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < wordList.size(); i++){
                    while (textToSpeech.isSpeaking() || textToSpeechLocal.isSpeaking())
                        ;
                    textToSpeech.speak( wordList.get(i).getWord(), TextToSpeech.QUEUE_FLUSH, null, null);
                    textToSpeechLocal.speak(wordList.get(i).getMeaning(), TextToSpeech.QUEUE_FLUSH, null, null);
                    Log.d(TAG, "readWordsLanguageWordList: i = " + i);
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        floatingActionButton = findViewById(R.id.floatingActionButtonReadLanguageWords);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Thread thread = new Thread(runnable);
                thread.start();
            }
        });
    }

//    public void readWordsLanguageWordList(View view){
//        for (int i = 0; i < wordList.size(); i++){
//            textToSpeech.speak( wordList.get(i).getWord(), TextToSpeech.QUEUE_FLUSH, null, null);
//            textToSpeechLocal.speak(wordList.get(i).getMeaning(), TextToSpeech.QUEUE_FLUSH, null, null);
//            Log.d(TAG, "readWordsLanguageWordList: i = " + i);
//        }
//    }

    private void populateListView(){
        intent = getIntent();

        wordList = new ArrayList<>();
        Log.d(TAG, "populateListView: selected language: " + intent.getStringExtra(LanguageList.WORDS_OF_LANGUAGE));
        Cursor data = dbLite.getAllWords(intent.getStringExtra(LanguageList.WORDS_OF_LANGUAGE));
        while (data.moveToNext()){
            String wordTmp = data.getString(DatabaseHelperLite.WORD_POSITION);
            String meaningTmp = data.getString(DatabaseHelperLite.MEANING_POSITION);
            // ToDo Add id to delete word from full list
            wordList.add(new WordData("0", wordTmp, meaningTmp));
        }
        ArrayAdapter adapter = new WordDataAdapter(this.getBaseContext(), R.layout.adapter_view_double_col, wordList);
        listViewLanguageWordList.setAdapter(adapter);
    }
}
