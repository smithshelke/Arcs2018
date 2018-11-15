
package com.example.smith.arcs2018.ModelClasses.Profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("team_id")
    @Expose
    private String teamId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("submissionStatus")
    @Expose
    private Boolean submissionStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Boolean getSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(Boolean submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

}
