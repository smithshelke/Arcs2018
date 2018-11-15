package com.example.smith.arcs2018;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smith.arcs2018.ModelClasses.TeamMembers.MemberData;
import com.example.smith.arcs2018.ModelClasses.TeamMembers.Members;
import com.example.smith.arcs2018.ModelClasses.TeamModel.TeamData;
import com.example.smith.arcs2018.ModelClasses.TeamModel.TeamModel;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.lang.reflect.Member;
import java.util.ArrayList;

public class HackathonActivity extends AppCompatActivity {
    private APIRequestMethods mAPIRequestMethods;
    private SharedPreferences mSharedPreferences;
    private String authToken;
    private AppBarLayout mAppBarLayout;
    private RelativeLayout mCoordinatorLayout;
    private CountDownTimer timer;
    private Members mMembers;
    private static final String TAG = "HackathonActivity";
    LayoutInflater inflater;
    LinearLayout ll, ml;
    View teamLayout;
    View rowCreateTeam;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hackathon);
        ll = (LinearLayout) findViewById(R.id.hackathon_views);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mAPIRequestMethods = new APIRequestMethods();
        mSharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        authToken = mSharedPreferences.getString("auth", null);
        inflateRespectedViews();
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_hackathon);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.gradientLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        //mCoordinatorLayout = (RelativeLayout) findViewById(R.id.appbar_main);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    //inflates either createTeamLayout or teamDetailsLayout
    public void inflateRespectedViews() {
        if (mSharedPreferences.getString("team", null) == null) {
            Log.d(TAG, "inflateRespectedViews: inflateCreateTeam called");
            inflateCreateTeam();
        } else {
            Log.d(TAG, "inflateRespectedViews: infalteTeamLayout called");
            TeamModel team = (TeamModel) retrieveObjectFromSharedPrefs("team", TeamModel.class);
            inflateTeamLayout(team);
        }
    }

    public void onBack(View view) {
        onBackPressed();
    }

    private void inflateCreateTeam() {
        rowCreateTeam = inflater.inflate(R.layout.layout_create_team, null, false);
        ll.addView(rowCreateTeam, 0);
    }

    public void inflateTeamLayout(TeamModel teamModel) {
        //FrameLayout chooseTeamLayout = (FrameLayout) findViewById(R.id.chooseTeamLayout);
        //ll.removeView(chooseTeamLayout);
        if (rowCreateTeam != null) {
            ll.removeView(rowCreateTeam);
        }
        teamLayout = inflater.inflate(R.layout.team_layout_exp, null, false);
        ((TextView) teamLayout.findViewById(R.id.teamSubtitle)).setText(teamModel.getTeamData().getName());
        String teamID = teamModel.getTeamData().getTeamId();
        ((TextView) teamLayout.findViewById(R.id.team_id)).setText("#" + teamID);
        ((TextView) teamLayout.findViewById(R.id.submission_status)).setText(teamModel.getTeamData().getSubmissionStatus().toString());
        ll.addView(teamLayout, 0);
        saveObjectInSharedPrefs("team", teamModel);
        setupMemberViews(teamID);
        Toast.makeText(HackathonActivity.this, "Success", Toast.LENGTH_SHORT).show();
    }

    public void createTeamButton(View view) {
        EditText editText = (EditText) findViewById(R.id.team_name);
        String teamName = editText.getText().toString();
        mAPIRequestMethods.createTeam(authToken, teamName, new APIRequestMethods.OnCompleteListener() {
            @Override
            public void onTeamCreated(TeamModel teamModel) {
                super.onTeamCreated(teamModel);
                inflateTeamLayout(teamModel);

            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                Toast.makeText(HackathonActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void joinTeamButton(View view) {
        EditText teamIdEditText = (EditText) findViewById(R.id.team_name);
        String teamNameText = teamIdEditText.getText().toString();
        mAPIRequestMethods.joinTeam(authToken, teamNameText, new APIRequestMethods.OnCompleteListener() {
            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                Toast.makeText(HackathonActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onJoinTeamSuccess(TeamModel teamModel) {
                Toast.makeText(HackathonActivity.this, "success", Toast.LENGTH_SHORT).show();

                //****************should inflate teamLayout****************//
                inflateTeamLayout(teamModel);
            }
        });
    }

    public void leaveTeamButton(View view) {
        /*mAPIRequestMethods.getProfile(authToken, new APIRequestMethods.OnCompleteListener() {
            @Override
            public void onProfileReceived(Profile profile) {
                super.onProfileReceived(profile);
            }
        });*/
        mAPIRequestMethods.leaveTeam(authToken, new APIRequestMethods.OnCompleteListener() {

            @Override
            public void onTeamLeaveSuccess() {
                super.onTeamLeaveSuccess();
                Toast.makeText(HackathonActivity.this, "Success", Toast.LENGTH_SHORT).show();
                mSharedPreferences.edit().remove("team").apply();
                inflateCreateTeam();
                ll.removeView(teamLayout);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                Toast.makeText(HackathonActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fabAction(View view) {
        Intent intent = new Intent(this, RefreshmentsActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(HackathonActivity.this,
                        mAppBarLayout,
                        ViewCompat.getTransitionName(mAppBarLayout));
        startActivity(intent, options.toBundle());
    }

    public void teamPopupMenuPressed(View view) {

        PopupMenu teamMenu = new PopupMenu(this, teamLayout.findViewById(R.id.team_menu));
        teamMenu.inflate(R.menu.menu_team);
        teamMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        teamMenu.show();

    }

    private void countdownStart() {
        final TextView countdownText = (TextView) findViewById(R.id.countdownTimer);
        final DateTime end = new DateTime(2018, 2, 22, 18, 16, 0, 0);
        long mili = end.getMillis();

        timer = new CountDownTimer(mili, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                DateTime start = new DateTime();
                Period period = new Period(start, end, PeriodType.yearMonthDayTime());
                int days = period.getDays();
                int hours = period.getHours();
                int minute = period.getMinutes();
                final int second = period.getSeconds();
                if (days > 1) {
                    countdownText.setText(days + " days left");
                } else if (hours > 1) {
                    countdownText.setText(hours + " hours left");
                } else if (minute > 0) {
                    countdownText.setText(minute + " minutes left");
                } else if (minute < 1 && hours < 1) {
                    countdownText.setText(second + "");

                }
            }

            @Override
            public void onFinish() {
                timer.cancel();
            }
        }.start();
    }

    //saves POJOs into strings
    private void saveObjectInSharedPrefs(String key, Object objectToBeStored) {
        Gson gson = new Gson();
        String json = gson.toJson(objectToBeStored);
        mSharedPreferences.edit().putString(key, json).apply();
        Log.d(TAG, "saveObjectInSharedPrefs: object saved: " + json);
    }

    //converts String into generic POJOs;
    private Object retrieveObjectFromSharedPrefs(String key, Class<?> cls) {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString(key, "");
        Object obj = gson.fromJson(json, cls);
        Log.d(TAG, "retrieveObjectFromSharedPrefs: object retrieved: " + obj.toString());
        return obj;
    }

    //sets up the member dots :/
    private void setupMemberViews(String teamID) {
        Log.d(TAG, "setupMemberViews: " + teamID);
        mAPIRequestMethods.getMembers(authToken, teamID, new APIRequestMethods.OnCompleteListener() {
            @Override
            public void onGetMembersSuccess(final Members members) {
                super.onGetMembersSuccess(members);
                mMembers = members;
                ml = (LinearLayout) findViewById(R.id.member_layout);
                if (members != null) {
                    String[] stateLists = {"#FF4081", "#26A69A", "#303F9F", "#FFEB3B", "#8BC34A"};
                    int i = 0;
                    for (MemberData member : members.getData()) {
//                        ImageView memberImageView = (ImageView) inflater.inflate(R.layout.imageview_member, null, false);
//                        memberImageView.setImageTintList(ColorStateList.valueOf(Color.parseColor(stateLists[i])));
                        FrameLayout memberImageView = (FrameLayout) inflater.inflate(R.layout.imageview_member, null, false);
                        ((ImageView)memberImageView.findViewById(R.id.memberImage)).setImageTintList(ColorStateList.valueOf(Color.parseColor(stateLists[i])));
                        ml.addView(memberImageView);
                        memberImageView.setTag(i);
                        i++;
                    }
                    /*memberImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = Integer.parseInt(v.getTag().toString());
                            MemberData memberData = members.getData().get(position);
                            Toast.makeText(HackathonActivity.this, memberData.getName(), Toast.LENGTH_SHORT).show();
                        }
                    });*/
                } else {
                    Toast.makeText(HackathonActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
            }

        });
    }

    //OnClickListener for the member dots
    public void viewMember(View view) {
        int i = Integer.parseInt(view.getTag().toString());
       // ((TextView) view.findViewById(R.id.member_name)).setText(mMembers.getData().get(i).getName());
       // ((TextView)view).setText(mMembers.getData().get(i).getName());
       // Toast.makeText(this, String.valueOf(view.getId()), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, mMembers.getData().get(i).getName(), Toast.LENGTH_SHORT).show();
    }
}
