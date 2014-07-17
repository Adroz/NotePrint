package com.workshoporange.android.noteprint;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends ActionBarActivity {

    private String email;
    private String password;

    public String[] lines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView text = (TextView) findViewById(R.id.editText);

        Intent receivedIntent = getIntent();
        String receivedAction = receivedIntent.getAction();
        String receivedType = receivedIntent.getType();


        if (receivedAction.equals(Intent.ACTION_SEND)) {
            if (receivedType.startsWith("text/")) {
                String receivedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
                if (receivedText != null) {
                    receivedText = receivedText.replace("\nShared from Google Keep", "");
                    text.setText(receivedText);
                    lines = receivedText.split(System.getProperty("line.separator"));
                }
            }
        } else if (receivedAction.equals(Intent.ACTION_MAIN)) {
            text.setText("Nothing has been shared.");
        }





        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Output", text.getText().toString());

                for (String line : lines) {
                    Intent intent = new Intent(getApplicationContext(), ConnectionService.class)
                            .putExtra("deviceId", "51ff65065067545726380687")
                            .putExtra("accessToken", "73dfe7f5a6b77e74e52610462d20dcd5f10fbe42")
                            .putExtra("functionOrVariableName", "print")
                            .putExtra("argString", line);
//                        .setAction(ConnectionService.ACTION_LOGIN);
                    getApplicationContext().startService(intent);
                }



//                cloudRequest("51ff65065067545726380687", "73dfe7f5a6b77e74e52610462d20dcd5f10fbe42", "print", "Hello Mr. Spark, I am Mr. Android :)");
            }
        });

//        email = "nicholas.k.moores@gmail.com";
//        password = "";
//
//        Intent intent = new Intent(getApplicationContext(),ConnectionService.class)
//                .putExtra("username", email)
//                .putExtra("password", password)
//                .setAction(ConnectionService.ACTION_LOGIN);
//        getApplicationContext().startService(intent);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
