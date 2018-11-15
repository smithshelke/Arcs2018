package com.example.smith.arcs2018;

import android.util.Log;

import com.example.smith.arcs2018.Interface.ArcsApiInterface;
import com.example.smith.arcs2018.ModelClasses.Login.Login;
import com.example.smith.arcs2018.ModelClasses.Profile.Profile;
import com.example.smith.arcs2018.ModelClasses.TeamMembers.Members;
import com.example.smith.arcs2018.ModelClasses.TeamModel.TeamData;
import com.example.smith.arcs2018.ModelClasses.TeamModel.TeamModel;
import com.example.smith.arcs2018.Retrofit.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SMITH on 25-Jan-18.
 */

public class APIRequestMethods {
    private static final String TAG = "APIRequestMethods";
    private ArcsApiInterface apiInterface;

    public APIRequestMethods() {
        Log.d(TAG, "APIRequestMethods: called");
        apiInterface = Utils.getInterface();
    }

    public void login(String username, String password, final OnCompleteListener listener) {

        Log.d(TAG, "login: called");
        Call call = apiInterface.attemptLogin(username, password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                String auth_token = response.headers().get("auth-token");
                if (auth_token != null) {
                    listener.onSuccess(auth_token, response.body());
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String msg = jsonObject.get("message").toString();
                        listener.onFailure(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                listener.onFailure(t.getMessage());
            }
        });

    }

    public void getEvents() {

    }

    public void getUserId() {

    }

    public void createTeam(String auth, String teamName, final OnCompleteListener listener) {
        Call call = apiInterface.createTeam(auth, teamName);
        Log.d(TAG, "createTeam: called");
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.body()!=null) {
                    Log.d(TAG, "onResponse: Response not null");
                    TeamModel teamModel = (TeamModel) response.body();
                    if (teamModel.getSuccess()) {
                        listener.onTeamCreated(teamModel);
                    } else {
                        listener.onFailure("Team could not be created.");
                    }
                }
                else{
                    Log.d(TAG, "onResponse: response is null");
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Log.d(TAG, "onResponse: "+jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public void leaveTeam(String auth, final OnCompleteListener listener){
        Call call = apiInterface.leaveTeam(auth);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d(TAG, "onResponse: "+response.raw());
                try {
                    ResponseBody responseBody = (ResponseBody) response.body();
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    Log.d(TAG, "onResponse: "+jsonObject.toString());
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        listener.onTeamLeaveSuccess();
                    }
                    else{
                        listener.onFailure("fail");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public void joinTeam(String auth, String teamId,final OnCompleteListener listener){
        Call call = apiInterface.joinTeam(auth,teamId);
        call.enqueue(new Callback<TeamModel>() {
            @Override
            public void onResponse(Call<TeamModel> call, Response<TeamModel> response) {
                /*ResponseBody body = (ResponseBody)response.body();
                Log.d(TAG, "onResponse: joinResponse: "+response.body());
                if(response.body()!=null) {
                    try {
                        JSONObject jsonObject = new JSONObject(body.string());
                        boolean success = jsonObject.getBoolean("success");
                        String message = jsonObject.getString("message");
                        Log.d(TAG, "onResponse: " + jsonObject.toString());
                        if (success) {
                            listener.onJoinTeamSuccess();
                        } else {
                            listener.onFailure(message);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                if(response.body()!=null) {
                    TeamModel teamModel = response.body();
                    boolean success = teamModel.getSuccess();
                    if(success){
                        listener.onJoinTeamSuccess(teamModel);
                    }
                }
                else{
                    try {
                        JSONObject jsonObject = new JSONObject(((ResponseBody)response.errorBody()).string());
                        String message = jsonObject.getString("message");
                        listener.onFailure(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public void getRefreshments() {
    }

    public void getProfile(String authToken, final OnCompleteListener listener) {
        Log.d(TAG, "getProfile: auth token receieved : " + authToken);
        Call call = apiInterface.getProfile(authToken);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d(TAG, "onResponse: " + response.body());
                Profile profile = (Profile) response.body();
                listener.onProfileReceived(profile);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG, "onFailure: profile could not be parsed");
            }
        });
    }

    public void postLink(String authToken, String link, final OnCompleteListener listener) {
        Call call = apiInterface.postLink(authToken, link);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d(TAG, "onResponse: linkResponse" + jsonObject.getString("success"));
                    if (jsonObject.getBoolean("success")) {
                        listener.onLinkReceived();
                    } else {
                        listener.onFailure(jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onFailure(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public void postPitch(String authToken, String pitch, final OnCompleteListener listener) {
        Call call = apiInterface.postPitch(authToken, pitch);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                ResponseBody responseBody = (ResponseBody) response.body();
                try {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    if (jsonObject.getBoolean("success")) {
                        listener.onPitchReceived();
                    } else {
                        listener.onFailure(jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public void getMembers(String authToken, String teamID, final OnCompleteListener listener){
        Log.d(TAG, "getMembers: "+teamID);
        Call call = apiInterface.getMembers(authToken);
        call.enqueue(new Callback<Members>() {
            @Override
            public void onResponse(Call<Members> call, Response<Members> response) {
                Members members = response.body();
                Log.d(TAG, "onResponse: "+response.raw());
                Log.d(TAG, "onResponse: "+members.getData().get(0).getName());
                listener.onGetMembersSuccess(members);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public abstract static class OnCompleteListener {
        public void onSuccess(String auth_token, Login loginResponse) {
        }

        public void onFailure(String msg) {}

        public void onProfileReceived(Profile profile) {}

        public void onLinkReceived() {
        }

        public void onPitchReceived() {
        }

        public void onTeamCreated(TeamModel teamModel){}

        public void onJoinTeamSuccess(TeamModel teamModel){}

        public void onTeamLeaveSuccess(){}

        public void onGetMembersSuccess(Members members){}
    }

}

