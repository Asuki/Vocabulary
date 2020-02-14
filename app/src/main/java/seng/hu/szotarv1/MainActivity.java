package seng.hu.szotarv1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.github.clans.fab.FloatingActionButton;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import seng.hu.szotarv1.AddingElements.AddBookActivity;
import seng.hu.szotarv1.AddingElements.AddLessonActivity;
import seng.hu.szotarv1.AddingElements.AddNewWordActivity;
import seng.hu.szotarv1.Listing.LessonsActivity;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
//    ListView listView;
//    ArrayList<String> bookList;
    DatabaseHelperLite dbLite;

    private boolean fabExpanded = false;
    private FloatingActionButton fabSettings;
    private LinearLayout layoutFb;
    private LinearLayout layoutCall;
    private LinearLayout layoutWhatsApp;
    public static final String BOOK_TITLE = "book title";
    private static final int EDIT_REQUEST_CODE = 0;
    public static final String ADD_WORD_MODE = "add_word_mode";
    public static final String WORD_MODE_ONE = "one";
    public static final String WORD_MODE_ALL = "all";
    private boolean editMode;

    FloatingActionButton fabEditMode;
    FloatingActionButton fabAddBook;
    FloatingActionButton fabAddLesson;
    FloatingActionButton fabAddWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.menu_books));
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home
//                R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_send
        )
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        editMode = false;

//        listView = findViewById(R.id.listViewBooks);
//        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (!editMode) {
//                    Intent intentLessonList = new Intent(MainActivity.this, LessonsActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString(BOOK_TITLE, adapterView.getItemAtPosition(i).toString());
//                    intentLessonList.putExtras(bundle);
//                    startActivityForResult(intentLessonList, EDIT_REQUEST_CODE);
//                } else {
//                    Intent intentBookEdit = new Intent(MainActivity.this, BookEditorActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString(BOOK_TITLE, adapterView.getItemAtPosition(i).toString());
//                    intentBookEdit.putExtras(bundle);
//                    startActivityForResult(intentBookEdit, EDIT_REQUEST_CODE);
//                    fabEditMode.setColorNormal(getColor(R.color.colorPrimary));
//                    listView.setBackgroundColor(getColor(R.color.white));
//                    editMode = false;
//                }
//            }
//        });
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                return false;
//            }
//        });
        init();


        fabAddWord = findViewById(R.id.fabAddWord);
        fabAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddWord = new Intent(MainActivity.this, AddNewWordActivity.class);
                intentAddWord.putExtra(ADD_WORD_MODE, WORD_MODE_ALL);
                startActivity(intentAddWord);
            }
        });

        fabAddLesson = findViewById(R.id.fabAddLesson);
        fabAddLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddLesson = new Intent(MainActivity.this, AddLessonActivity.class);
                startActivityForResult(intentAddLesson, EDIT_REQUEST_CODE);
            }
        });

        fabAddBook = findViewById(R.id.fabAddBook);
        fabAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddBook = new Intent(MainActivity.this, AddBookActivity.class);
                startActivityForResult(intentAddBook, EDIT_REQUEST_CODE);
            }
        });

        fabEditMode = findViewById(R.id.fabEditMode);
        fabEditMode.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (editMode) {
                    editMode = false;
                    fabEditMode.setColorNormal(getColor(R.color.colorPrimary));
//                    listView.setBackgroundColor(getColor(R.color.white));
//                    populateListView();
                }
                else {
                    editMode = true;
                    fabEditMode.setColorNormal(getColor(R.color.colorAccent));
//                    listView.setBackgroundColor(getColor(R.color.light_orange));
//                    populateListView();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        bookList.clear();
//        loadBooks();
//        populateListView();
    }

    private void init(){
//        bookList = new ArrayList<>();
        dbLite = new DatabaseHelperLite(this.getBaseContext());
//        loadData();
//        populateListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

//    private void populateListView(){
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, bookList);
//        listView.setAdapter(arrayAdapter);
//    }
}
