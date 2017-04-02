package com.lahacks.props;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by kimboslice on 4/1/17. Doesn't do anything yet!
 */

public class SelectCoinActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.lahacks.props.MESSAGE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcoinactivity);

        Button btn2 = (Button)findViewById(R.id.button2);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectCoinActivity.this, confirmCoinChoice.class);
                Button b = (Button) v;
                intent.putExtra(EXTRA_MESSAGE, b.getText().toString());
                startActivity(intent);
            }
        });

        Button btn3 = (Button)findViewById(R.id.button3);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectCoinActivity.this, confirmCoinChoice.class);
                Button b = (Button) v;
                intent.putExtra(EXTRA_MESSAGE, b.getText().toString());
                startActivity(intent);
            }
        });

        Button btn4 = (Button)findViewById(R.id.button4);

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectCoinActivity.this, confirmCoinChoice.class);
                Button b = (Button) v;
                intent.putExtra(EXTRA_MESSAGE, b.getText().toString());
                startActivity(intent);
            }
        });

        Button btn5 = (Button)findViewById(R.id.button5);

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectCoinActivity.this, confirmCoinChoice.class);
                Button b = (Button) v;
                intent.putExtra(EXTRA_MESSAGE, b.getText().toString());
                startActivity(intent);
            }
        });

       Button btn6 = (Button)findViewById(R.id.button6);

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectCoinActivity.this, CustomListAdapter.class);
                startActivity(intent);
            }
        });


    }
}
