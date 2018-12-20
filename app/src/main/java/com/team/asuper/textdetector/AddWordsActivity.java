package com.team.asuper.textdetector;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddWordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);
        this.showWords();
    }

    public void addWord(View view) {
        EditText edit = findViewById(R.id.editText);
        String newWord = edit.getText().toString();
        // save newWord to the end of file (or other method of storage)
        MainActivity.targetWords.add(newWord);

        StringBuilder csvList = new StringBuilder();
        for (String s: MainActivity.targetWords) {
            csvList.append(s);
            csvList.append(",");
        }

        SharedPreferences sharedPref = MainActivity.context.getSharedPreferences("targetWordList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("targetWordList", csvList.toString());
        editor.commit();

        // update list of words
        showWords();
    }

    public void showWords() {
        String words = "";
        for (String s: MainActivity.targetWords) {
            words += s + "\n";
        }

        // add values to view editText2
        TextView edit2 = findViewById(R.id.textView2);
        edit2.setText(words);
    }
}