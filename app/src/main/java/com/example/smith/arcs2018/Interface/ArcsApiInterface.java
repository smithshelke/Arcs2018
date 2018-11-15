package com.example.smith.arcs2018.Interface;

import com.example.smith.arcs2018.ModelClasses.Login.Login;
import com.example.smith.arcs2018.ModelClasses.Profile.Profile;
import com.example.smith.arcs2018.ModelClasses.Profile.Team;
import com.example.smith.arcs2018.ModelClasses.TeamMembers.Members;
import com.example.smith.arcs2018.ModelClasses.TeamModel.TeamModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by SMITH on 28-Jan-18.
 */

public interface ArcsApiInterface {

    @POST("api/login")
    @FormUrlEncoded
    Call<Login> attemptLogin(@Field("email") String email, @Field("password") String password);

    @GET("api/profile")
    Call<Profile> getProfile(@Header("auth-token") String auth);

    @GET("api/profile/attendence")
    Call<Void> getAttendence(@Header("auth-token") String auth);

    @POST("api/team/create")
    @FormUrlEncoded
    Call<TeamModel> createTeam(@Header("auth-token") String auth, @Field("teamName") String teamName);

    @POST("api/team/join")
    @FormUrlEncoded
    Call<TeamModel> joinTeam(@Header("auth-token") String auth, @Field("team_id")String teamId);

    @POST("api/team/leave")
    Call<ResponseBody> leaveTeam(@Header("auth-token") String auth);

    @POST("api/refreshment/view")
    @FormUrlEncoded
    Call<ResponseBody> getFreshments(@Header("auth-token") String auth);

    @POST("api/profile/submit")
    @FormUrlEncoded
    Call<ResponseBody> postLink(@Header("auth-token") String auth, @Field("link") String link);


    @POST("api/profile/pitch")
    @FormUrlEncoded
    Call<ResponseBody> postPitch(@Header("auth-token") String auth, @Field("pitch") String pitch );

    @GET("api/team/members")
    Call<Members> getMembers(@Header("auth-token") String auth);

}
