package com.example.Absensi.entity.userEntity;

import com.example.Absensi.entity.BaseResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetUserRespList extends BaseResponse {
    private List<UserList> userList;

    public List<UserList> getUserList() {
        return userList;
    }

    public void setUserList(List<UserList> userList) {
        this.userList = userList;
    }
}
