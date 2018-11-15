
package com.example.smith.arcs2018.ModelClasses.TeamModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private TeamData mTeamData;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public TeamData getTeamData() {
        return mTeamData;
    }

    public void setTeamData(TeamData teamData) {
        this.mTeamData = teamData;
    }

}
