package com.nzartofiq.battelship;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class LastActivity extends AppCompatActivity {

    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        setContentView(R.layout.content_last);
        Intent intent = getIntent();
        name = intent.getStringExtra(StartActivity.NAME_EXTRA);
        setupTxtMsg();
    }

    private void setupTxtMsg(){
        TextView nameText = (TextView)findViewById(R.id.goodByMsg);
        nameText.setText("Goodbye "+name);
        Context context = getApplicationContext();
        Toast.makeText(context, "Game Over", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(LastActivity.this, LastActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
