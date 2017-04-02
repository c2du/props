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
    Button button2;

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
    }
}
