package com.nzartofiq.battleship;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {
    public static final String NAME_EXTRA = "com.nzartofiq.StartActivity.NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_start);
    }

    public void startGame(View view){
        Intent intent = new Intent(this,MainActivity.class);
        EditText editText  = (EditText)findViewById(R.id.editName);
        String message = editText.getText().toString();
        intent.putExtra(NAME_EXTRA, message);
        startActivity(intent);
    }
}
