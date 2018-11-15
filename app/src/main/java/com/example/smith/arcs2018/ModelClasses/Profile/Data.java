
package com.example.smith.arcs2018.ModelClasses.Profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("regNo")
    @Expose
    private String regNo;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("university")
    @Expose
    private String university;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("team")
    @Expose
    private Team team;
    @SerializedName("refreshment")
    @Expose
    private Integer refreshment;
    @SerializedName("attendance")
    @Expose
    private Boolean attendance;
    @SerializedName("role")
    @Expose
    private String role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getRefreshment() {
        return refreshment;
    }

    public void setRefreshment(Integer refreshment) {
        this.refreshment = refreshment;
    }

    public Boolean getAttendance() {
        return attendance;
    }

    public void setAttendance(Boolean attendance) {
        this.attendance = attendance;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "TeamData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", regNo='" + regNo + '\'' +
                ", gender='" + gender + '\'' +
                ", university='" + university + '\'' +
                ", v=" + v +
                ", team=" + team +
                ", refreshment=" + refreshment +
                ", attendance=" + attendance +
                ", role='" + role + '\'' +
                '}';
    }

}
