package com.example.smith.arcs2018;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.CycleInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.smith.arcs2018.ModelClasses.Login.Login;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    EditText username;
    EditText password;
    TextInputLayout passwordLayout, usernameLayout;
    RelativeLayout mLayout;
    ProgressDialog progress;
    SharedPreferences prefs;
    SharedPreferences.Editor mEditor;
    private ImageView mImageView;
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        mImageView = (ImageView)findViewById(R.id.logoSplash);

        mLayout = (RelativeLayout) findViewById(R.id.login_layout);
        initialiseSharedPrefs();

        initialiseLayout();
    }

    public void initialiseLayout(){

        //logo starts vibrating for 1 sec
        mImageView.animate()
                .scaleYBy(.1f)
                .scaleXBy(.1f)
                .setDuration(1500)
                .setInterpolator(new CycleInterpolator(6));

        //log scales up for 1 sec after 1 sec
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mImageView.animate()
                        .scaleYBy(100f)
                        .scaleXBy(100f)
                        .alpha(0)
                        .setDuration(1000)
                        .setInterpolator(new AnticipateOvershootInterpolator(.4f));
            }
        },1400);

        //layout fades in for 1 sec after 1 sec
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLayout.animate()
                        .alpha(1)
                        .setDuration(2000);
            }
        },1500);
    }

    public void login_button(View veiw) {
        setDialog(true);
        usernameLayout = (TextInputLayout) findViewById(R.id.username_layout);
        passwordLayout = (TextInputLayout) findViewById(R.id.password_layout);
        passwordLayout.setErrorEnabled(false);
        usernameLayout.setErrorEnabled(false);
        username = (EditText) findViewById(R.id.username_edit_text);
        password = (EditText) findViewById(R.id.passord_edit_text);

        APIRequestMethods APIRequestMethods = new APIRequestMethods();
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();

        APIRequestMethods.login(usernameText, passwordText, new APIRequestMethods.OnCompleteListener() {
            @Override
            public void onSuccess(String auth_token, Login loginResponse) {
                setDialog(false);
                mEditor.putString("auth",auth_token);
                mEditor.commit();
                Intent intent = new Intent(LoginActivity.this, HackathonActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(String msg) {
                setDialog(false);
                if (msg.equals("User not present")) {
                    usernameLayout.setError("User not present");
                } else if (msg.equals("Incorrect Password")) {
                    passwordLayout.setError("Incorrect password");
                } else {
                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setDialog(boolean value) {
        if (value) {
            progress.setMessage("Please wait ...");
            progress.setTitle("Authenticating");
            progress.show();
        } else {
            progress.cancel();
        }
    }

    public void initialiseSharedPrefs() {
        prefs = this.getSharedPreferences("MyPrefs", 0);
        mEditor = prefs.edit();
    }
}
