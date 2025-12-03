package com.example.act07;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button boto;
    TextView resultats;
    String text = "";
    int contador = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boto = findViewById(R.id.btnLlençarChild);
        resultats = findViewById(R.id.resultats);

        boto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChildActivity.class);
                intent.putExtra("child_id", contador);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                int id = data.getIntExtra("child_id", 0);
                String res = data.getStringExtra("result_text");

                if (!text.isEmpty()) {
                    text += "\n";
                }
                text += "Activity #" + id + ": " + res;
                resultats.setText(text);
                contador++;
            }
        }
    }
}