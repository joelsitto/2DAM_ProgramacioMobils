package com.example.act07;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChildActivity extends Activity {

    TextView titol;
    EditText camp;
    Button boto;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        id = getIntent().getIntExtra("child_id", 0);

        titol = findViewById(R.id.idChild);
        camp = findViewById(R.id.txtResultat);
        boto = findViewById(R.id.btnReturnResult);

        titol.setText(getString(R.string.activity_filla) + id);

        boto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = camp.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("child_id", id);
                intent.putExtra("result_text", text);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}

