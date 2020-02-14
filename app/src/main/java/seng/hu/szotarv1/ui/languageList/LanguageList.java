package seng.hu.szotarv1.ui.languageList;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import seng.hu.szotarv1.DatabaseHelperLite;
import seng.hu.szotarv1.R;
import seng.hu.szotarv1.ui.home.HomeViewModel;

public class LanguageList extends Fragment {

    private LanguageListViewModel mViewModel;
    private LanguageListViewModel languageListViewModel;
    private ListView listView;
    private static final String TAG = "LanguageListFragment";
    DatabaseHelperLite dbLite;
    ArrayList<String> arrayListLanguages;

    public static LanguageList newInstance() {
        return new LanguageList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        languageListViewModel =
                ViewModelProviders.of(this).get(LanguageListViewModel.class);
        View root = inflater.inflate(R.layout.language_list_fragment, container, false);

        listView = root.findViewById(R.id.listViewLanguages);
        dbLite = new DatabaseHelperLite(getActivity());
        populateListView();

        return root;
    }

    private void populateListView() {
        Cursor data = dbLite.getLanguages();
        arrayListLanguages = new ArrayList<>();
        while (data.moveToNext()){
            arrayListLanguages.add(data.getString(DatabaseHelperLite.NAME_POSITION));
        }

        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayListLanguages);
        listView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LanguageListViewModel.class);
        // TODO: Use the ViewModel
    }

}
