package com.cielyang.android.customedittext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cielyang.android.clearableedittext.ClearableAutoCompleteTextView;
import com.cielyang.android.clearableedittext.ClearableEditText;
import com.cielyang.android.clearableedittext.OnClearListener;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((ClearableEditText) findViewById(R.id.edit_text)).setOnClearListener(new OnClearListener() {
            @Override public void onCleared() {
                Toast.makeText(MainActivity.this, "EditText cleared", Toast.LENGTH_SHORT).show();
            }
        });

        ((ClearableAutoCompleteTextView) findViewById(R.id.autocomplete_text_view)).setOnClearListener(new OnClearListener() {
            @Override public void onCleared() {
                Toast.makeText(MainActivity.this, "AutoCompleteTextView cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
