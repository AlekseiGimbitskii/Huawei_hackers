package com.team.asuper.textdetector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AddWordsActivity extends AppCompatActivity {
    private ArrayList<String> targetWords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);
        try {
            this.showWords();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addWord(View view) throws FileNotFoundException {
        EditText edit = findViewById(R.id.editText);
        String newWord = edit.getText().toString();
        // save newWord to the end of file (or other method of storage)
        targetWords.set(0, newWord);
        // update list of words
        this.showWords();
    }

    public void showWords() throws FileNotFoundException {
        // get list from storage
//        File file = new File("words.txt");
//        ArrayList<String> words = new ArrayList<>();
//        Scanner in = new Scanner(file);
//        while (in.hasNextLine()){
//            words.add(in.nextLine());
//        }

        targetWords.add("hello");
//        targetWords.add("MiCrosOft");
//        targetWords.add("peAnuT");
//        targetWords.add("huawei");

        // add values to view editText2
        TextView edit2 = findViewById(R.id.textView2);
        edit2.setText((CharSequence) targetWords.get(0));
    }
}