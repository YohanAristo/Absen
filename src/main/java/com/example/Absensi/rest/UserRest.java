package com.example.Absensi.rest;

import com.example.Absensi.entity.BaseResponse;
import com.example.Absensi.entity.userEntity.*;
import com.example.Absensi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@RestController
public class UserRest {

    @Autowired
    UserService userService;

    @PostMapping(value = "interns")
    public BaseResponse createUser(@RequestBody PostUserReq user){
        return userService.postUser(user);
    }

    @PutMapping(value = "interns/{id}")
    public BaseResponse editUser(@PathVariable(value = "id") String id, @RequestBody PutUserReq user){
        return userService.editUser(id, user);
    }

    @GetMapping(value = "interns/{id}")
    public GetUserResp getUser(@PathVariable(value = "id") String id){
        return userService.getUser(id);
    }

    @GetMapping(value = "interns")
    public GetUserRespList userList(){
        return userService.getUserList();
    }

    @DeleteMapping(value = "interns/{id}")
    public BaseResponse deleteUser(@PathVariable(value = "id") String id){
        return userService.deleteUser(id);
    }

    @PostMapping(value = "interns/login")
    public GetUserResp loginProcess(@RequestBody PostUserLogin userLogin){
        return userService.loginProcess(userLogin);
    }

    @PostMapping(value = "absentmg-in-out/history")
    public GetUserHistoryResp historyList(@RequestBody PostUserHistoryReq user){
        return userService.userHistory(user);
    }

    @PostMapping(value = "absentmg-in-out/absent")
    public BaseResponse checkState(@RequestBody PostCheckReq check) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return userService.checkState(check);
    }

    @GetMapping(value = "interns/user")
    public long countUser(){
        return userService.countUser();
    }

}
