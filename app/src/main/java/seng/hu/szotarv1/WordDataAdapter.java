package seng.hu.szotarv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WordDataAdapter extends ArrayAdapter<WordData> {

    private final String TAG = "WordDataAdapter";
    private Context context;
    private int resources;
    private ArrayList<WordData> objects;

    public WordDataAdapter(@NonNull Context context, int resource, @NonNull ArrayList<WordData> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resources = resource;
        this.objects = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String word = getItem(position).getWord();
        String meaning = getItem(position).getMeaning();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resources, parent, false);

        TextView textViewWord = convertView.findViewById(R.id.textView_left);
        TextView textViewMeaning = convertView.findViewById(R.id.textView_right);

        textViewWord.setText(word);
        textViewMeaning.setText(meaning);

        return convertView;
    }
}
