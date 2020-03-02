package com.example.Absensi.entity.userEntity;

import com.example.Absensi.entity.BaseResponse;
import com.example.Absensi.model.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetUserResp extends BaseResponse {
   /*private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/

    private String userId;
    private String name;
    private String password;
    private String role;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
