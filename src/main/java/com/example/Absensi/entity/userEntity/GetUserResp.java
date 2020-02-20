package com.example.Absensi.entity.userEntity;

import com.example.Absensi.entity.userEntity.BaseResponse;
import com.example.Absensi.model.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetUserResp extends BaseResponse {
   private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
