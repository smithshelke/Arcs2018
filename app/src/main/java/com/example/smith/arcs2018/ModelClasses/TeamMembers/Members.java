package com.example.smith.arcs2018.ModelClasses.TeamMembers;

/**
 * Created by SMITH on 16-Feb-18.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Members {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<MemberData> data = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<MemberData> getData() {
        return data;
    }

    public void setData(List<MemberData> data) {
        this.data = data;
    }

}