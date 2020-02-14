package seng.hu.szotarv1.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import seng.hu.szotarv1.BookEditorActivity;
import seng.hu.szotarv1.DatabaseHelperLite;
import seng.hu.szotarv1.Listing.LessonsActivity;
import seng.hu.szotarv1.MainActivity;
import seng.hu.szotarv1.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView listView;
    private boolean editMode;
    private static final int EDIT_REQUEST_CODE = 0;
    DatabaseHelperLite dbLite;
    ArrayList<String> bookList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

//        homeViewModel.getText().observe(this, new Observer<String>() {
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        initItems();

        listView = root.findViewById(R.id.listViewBooksBooks);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!editMode) {
                    Intent intentLessonList = new Intent(getActivity(), LessonsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(MainActivity.BOOK_TITLE, adapterView.getItemAtPosition(i).toString());
                    intentLessonList.putExtras(bundle);
                    startActivityForResult(intentLessonList, EDIT_REQUEST_CODE);
                } else {
                    Intent intentBookEdit = new Intent(getActivity(), BookEditorActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(MainActivity.BOOK_TITLE, adapterView.getItemAtPosition(i).toString());
                    intentBookEdit.putExtras(bundle);
                    startActivityForResult(intentBookEdit, EDIT_REQUEST_CODE);
//                    fabEditMode.setColorNormal(getActivity().getColor(R.color.colorPrimary));
                    listView.setBackgroundColor(getActivity().getColor(R.color.white));
                    editMode = false;
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });

        populateListView();
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        populateListView();
    }

    private void initItems(){
        bookList = new ArrayList<>();
        editMode = false;
        dbLite = new DatabaseHelperLite(getActivity());
    }

    private void populateListView(){
        bookList = new ArrayList<>();
        Cursor data = dbLite.getBooks();
        while (data.moveToNext()){
            bookList.add(data.getString(DatabaseHelperLite.NAME_POSITION));
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, bookList);
        listView.setAdapter(arrayAdapter);
    }
}