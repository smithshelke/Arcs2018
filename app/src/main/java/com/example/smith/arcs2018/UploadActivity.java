package com.example.smith.arcs2018;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UploadActivity extends AppCompatActivity {

    public String authToken;
    private TextView linkText,statusText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        authToken = getSharedPreferences("MyPrefs",0).getString("auth",null);
        linkText = (TextView) findViewById(R.id.linkText);
        statusText = (TextView)findViewById(R.id.statusText);
    }
    public void onBack(View view){
        onBackPressed();
    }

    public void onSubmit(View view){
        MyCustomDialog dialog = new MyCustomDialog();
        dialog.getLink(new MyCustomDialog.OnDialogInputListener() {
            @Override
            public void inputReceived(String msg) {
                statusText.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                linkText.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                statusText.setText("Uploaded");
                linkText.setText(msg);
            }
        });
        dialog.show(getFragmentManager(),"dialog");
    }
}
