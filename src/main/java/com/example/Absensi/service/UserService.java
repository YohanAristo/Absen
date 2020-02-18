package com.example.Absensi.service;

import com.example.Absensi.dao.UserDao;
import com.example.Absensi.entity.*;
import com.example.Absensi.model.User;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormatSymbols;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    //ADMIN
    public BaseResponse postUser(User user){
        BaseResponse response = new BaseResponse();
        User user1 = new User();

        user1.setRole(user.getRole());
        user1.setName(user.getName());
        user1.setPassword(user.getPassword());
        user1.setUserId(user.getUserId());

        User saveUser = userDao.save(user1);

        if(saveUser == null) {
            response.setErrorCode("99");
            response.setErrorMessage("User Gagal Ditambahkan");
            return response;
        }
        else {
            response.setErrorCode("00");
            response.setErrorMessage("User Berhasil Ditambahkan");
            return response;
        }
    }

    public BaseResponse editUser(String id, PutUserReq input){
        BaseResponse response = new BaseResponse();

        if(!userDao.existsById(id))
        {
            response.setErrorCode("99");
            response.setErrorMessage("User Tidak Ditemukan");
            return response;
        }

        User userFound = userDao.findById(id).get();
//        if(input.getName()!=null)
//            userFound.setName(input.getName());
        if(input.getPassword()!=null)
            userFound.setPassword(input.getPassword());

        User editUser = userDao.save(userFound);

        if(editUser == null) {
            response.setErrorCode("99");
            response.setErrorMessage("User Gagal Diedit");
            return response;
        }
        else {
            response.setErrorCode("00");
            response.setErrorMessage("User Berhasil Diedit");
            return response;
        }
    }

    public GetUserRespList getUserList(){
        GetUserRespList respList = new GetUserRespList();

        List<User> users = userDao.findAll();
        respList.setUserList(users);
        respList.setErrorCode("00");
        respList.setErrorMessage("Berhasil Tampilkan User");
        return respList;
    }

    public BaseResponse deleteUser(String id){
        BaseResponse response = new BaseResponse();

        if(!userDao.existsById(id))
        {
            response.setErrorCode("99");
            response.setErrorMessage("User Tidak Ditemukan");
            return response;
        }

        userDao.deleteById(id);
        response.setErrorCode("00");
        response.setErrorMessage("User Berhasil Dihapus");
        return response;
    }

    public GetUserResp getUser(String id){
        GetUserResp resp = new GetUserResp();

        if(!userDao.existsById(id))
        {
            resp.setErrorCode("99");
            resp.setErrorMessage("User Tidak Ditemukan");
            return resp;
        }

        User user = userDao.findById(id).get();

        resp.setUser(user);
        resp.setErrorCode("99");
        resp.setErrorMessage("User Ditemukan");
        return resp;
    }

    public GetUserResp loginProcess(PostUserLogin input){
        GetUserResp resp = new GetUserResp();

        if(!userDao.existsById(input.getUserId()))
        {
            resp.setErrorCode("99");
            resp.setErrorMessage("User Tidak Ditemukan");
            return resp;
        }

        User user = userDao.findById(input.getUserId()).get();
        if(user.getPassword().equalsIgnoreCase(input.getPassword())) {
            resp.setUser(user);
            resp.setErrorCode("00");
            resp.setErrorMessage("Berhasil Login");
            return resp;
        }
        else {
            resp.setErrorCode("99");
            resp.setErrorMessage("Gagal Login");
            return resp;
        }
    }
    // Post data check ke MF/////////////
    public BaseResponse checkState(PostCheckReq check){
        BaseResponse response = new BaseResponse();
        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
        InputData input = new InputData();
        PostCheckReqMainFrame checkReq = new PostCheckReqMainFrame();

//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        input.setInputUserId(check.getUserId());
        input.setInputAction(check.getState());

        checkReq.setInputData(input);

        //String destionationURL = "http://localhost:8081/";// Diisi API MF.///////////////////////
        String destionationURL = "https://10.20.218.9:9079/absentmg-in-out/absent";
        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(checkReq));
        ResponseEntity<String> responseEntity = restTemplate.exchange(destionationURL, HttpMethod.POST, entity, String.class);
        String text = responseEntity.getBody();

        if(responseEntity.getStatusCode()==HttpStatus.OK) {
            GetCheckRespMainFrame respGet = gson.fromJson(text, GetCheckRespMainFrame.class);
            response.setErrorMessage(respGet.getOutputData().getErrorMessage());
            response.setErrorCode(respGet.getOutputData().getErrorCode());
        }
        else{
            response.setErrorCode("99");
            response.setErrorMessage("Failed");
        }

        return response;
    }


    //////////////////////Ambil req data histori dari android lalu kirim ke MF lalu return hasil ke android
    public GetUserHistoryResp userHistory(PostUserHistoryReq user){
        GetUserHistoryResp response = new GetUserHistoryResp();
        RestTemplate restTemplate = new RestTemplate();
        PostUserHistoryReqMainFrame resp = new PostUserHistoryReqMainFrame();
        InputHistory input = new InputHistory();

        input.setInputUserId(user.getUserId());
        input.setInputMm(user.getMonth());
        input.setInputYyyy(user.getYear());
        input.setInputAction("H");

        resp.setInputHistory(input);

        //String destionationURL = "http://www.mocky.io/v2/5e465de03300002d410260c0";// Diisi API MF.
        String destionationURL = "https://10.20.218.9:9079/history-absentmg/history";
        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(resp));
        ResponseEntity<String> responseEntity = restTemplate.exchange(destionationURL, HttpMethod.POST, entity, String.class);
        String text = responseEntity.getBody();

        GetUserHistoryRespMainFrame response1 = gson.fromJson(text, GetUserHistoryRespMainFrame.class);

        List<HistData> list = response1.getOutputHistory().getHistData();

        for(HistData data : list){
            data.setOutputTimeIn(convertTime(data.getOutputTimeIn()));
            data.setOutputTimeOut(convertTime(data.getOutputTimeOut()));
            data.setOutputDate(convertDate(data.getOutputDate()));
        }

        response.setOutputUserId(response1.getOutputHistory().getOutputUserId());
        response.setOutputMm(response1.getOutputHistory().getOutputMm());
        response.setOutputYyyy(response1.getOutputHistory().getOutputYyyy());
        response.setOutputAttend(response1.getOutputHistory().getOutputAttend());
        response.setHistData(list);
        response.setErrorCode(response1.getOutputHistory().getErrorCode());
        response.setErrorMessage(response1.getOutputHistory().getErrorMessage());

        return response;
    }

    public String convertTime(String input){
        String result, hh, mm, ss;
        String[] splits = input.split("\\.");

        hh = splits[0];
        mm = splits[1];
        ss = splits[2];

        result = hh.concat(":").concat(mm).concat(":").concat(ss);

        return result;
    }

    public String convertDate(String input){
        String result, day, mon, month, year;
        String[] split = input.split("-");

        year = split[0];
        mon = getMonth(Integer.parseInt(split[1]));
        day = split[2];

        result = day.concat(" ").concat(mon).concat(" ").concat(year);

        return result;
    }

    ///////////////////////////////////////////////////////////////////////////
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

    /////////////////////////////////////////////////////////////////////////
    public BaseResponse test(){
        //GetCheckRespMainFrame resp;
        BaseResponse resp1 = new BaseResponse();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String resourceURL = "http://5e3a83908d7e1300149cdbd9.mockapi.io/history/check/1";// Di isi API MF//////////////////
        HttpEntity<String> entityGet = new HttpEntity<String>(headers);
        ResponseEntity<String> responseGet = restTemplate.exchange(resourceURL, HttpMethod.GET, entityGet, String.class);
        String text = responseGet.getBody();

        if(responseGet.getStatusCode()==HttpStatus.OK) {
            GetCheckRespMainFrame resp = gson.fromJson(text, GetCheckRespMainFrame.class);
            resp1.setErrorCode(resp.getOutputData().getErrorCode());
            resp1.setErrorMessage(resp.getOutputData().getErrorMessage());
        }

        return resp1;
    }

    public String testMonth(int month){
        String temp = getMonth(month);

        return temp;
    }
}
