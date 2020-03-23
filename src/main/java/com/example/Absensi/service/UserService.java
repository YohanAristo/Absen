package com.example.Absensi.service;

import com.example.Absensi.dao.UserDao;
import com.example.Absensi.entity.BaseResponse;
import com.example.Absensi.entity.userEntity.*;
import com.example.Absensi.model.User;
import com.example.Absensi.util.ConverterString;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    ConverterString converterString;

    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    public RestTemplate restTemplate()
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
        return new RestTemplate(requestFactory);
    }

    //ADMIN
    public BaseResponse postUser(PostUserReq user){
        BaseResponse response = new BaseResponse();
        User user1 = new User();

        if(converterString.countChar(user.getName()) > 30)
        {
            response.setErrorCode("99");
            response.setErrorMessage("Name Cannot Be Longer than 30 Characters");
            return response;
        }

        if(converterString.countChar(user.getUserId()) > 7)
        {
            response.setErrorCode("99");
            response.setErrorMessage("User Id Cannot Be Longer than 7 Characters");
            return response;
        }

        user1.setRole("member");
        user1.setName(user.getName());
        user1.setPassword(user.getPassword());
        user1.setUserId(user.getUserId());

        User saveUser = userDao.save(user1);

        if(saveUser == null) {
            response.setErrorCode("99");
            response.setErrorMessage("Failed to Add User");
            return response;
        }
        else {
            response.setErrorCode("00");
            response.setErrorMessage("Successfully Add User");
            return response;
        }
    }

    public BaseResponse editUser(String id, PutUserReq input){
        BaseResponse response = new BaseResponse();

        if(!userDao.existsById(id))
        {
            response.setErrorCode("99");
            response.setErrorMessage("User Not Found");
            return response;
        }

        User userFound = userDao.findById(id).get();
        if(input.getPassword()!=null)
            userFound.setPassword(input.getPassword());

        User editUser = userDao.save(userFound);

        if(editUser == null) {
            response.setErrorCode("99");
            response.setErrorMessage("Failed to Edit User Data");
            return response;
        }
        else {
            response.setErrorCode("00");
            response.setErrorMessage("Successfully Edit User Data");
            return response;
        }
    }



    public GetUserRespList getUserList(){
        GetUserRespList respList = new GetUserRespList();
        List<UserList> myList = new ArrayList<>();

        List<User> users = userDao.findAll();

        if(users.isEmpty())
        {
            respList.setUserList(myList);
            respList.setErrorCode("00");
            respList.setErrorMessage("User Data is Empty");
            return respList;
        }

        for(User data : users){
           UserList temp = new UserList();
           temp.setUserId(data.getUserId());
           temp.setName(data.getName());
           myList.add(temp);
        }

        respList.setUserList(myList);
        respList.setErrorCode("00");
        respList.setErrorMessage("Successfully Show User");
        return respList;
    }

    public BaseResponse deleteUser(String id){
        BaseResponse response = new BaseResponse();

        if(!userDao.existsById(id))
        {
            response.setErrorCode("99");
            response.setErrorMessage("User Not Found");
            return response;
        }

        userDao.deleteById(id);
        response.setErrorCode("00");
        response.setErrorMessage("Successfully Delete User");
        return response;
    }

    public GetUserResp getUser(String id){
        GetUserResp resp = new GetUserResp();

        if(!userDao.existsById(id))
        {
            resp.setErrorCode("99");
            resp.setErrorMessage("User Not Found");
            return resp;
        }

        User user = userDao.findById(id).get();

        //resp.setUser(user);
        resp.setUserId(user.getUserId());
        resp.setName(user.getName());
        resp.setPassword(user.getPassword());
        resp.setRole(user.getRole());
        resp.setErrorCode("99");
        resp.setErrorMessage("Successfully Get User");
        return resp;
    }

    public GetUserResp loginProcess(PostUserLogin input){
        GetUserResp resp = new GetUserResp();

        if(!userDao.existsById(input.getUserId()))
        {
            resp.setErrorCode("99");
            resp.setErrorMessage("User Not Found");
            return resp;
        }

        User user = userDao.findById(input.getUserId()).get();
        if(user.getPassword().equalsIgnoreCase(input.getPassword())) {
            resp.setUserId(user.getUserId());
            resp.setName(user.getName());
            resp.setPassword(user.getPassword());
            resp.setRole(user.getRole());
            resp.setErrorCode("00");
            resp.setErrorMessage("Login Successfully");
            return resp;
        }
        else {
            resp.setErrorCode("99");
            resp.setErrorMessage("Login Failed");
            return resp;
        }
    }

    public String getName(String id){
        User user = userDao.findById(id).get();

        return user.getName();
    }



    // Post data check ke MF/////////////
    public BaseResponse checkState(PostCheckReq check) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        BaseResponse response = new BaseResponse();

        RestTemplate restTemplate = restTemplate();

        //RestTemplate restTemplate = new RestTemplate();
        InputData input = new InputData();
        PostCheckReqMainFrame checkReq = new PostCheckReqMainFrame();

        input.setInputUserId(check.getUserId());
        input.setInputAction(check.getState());
        input.setInputName(getName(check.getUserId()));
        input.setInputTrans("");

        checkReq.setInputData(input);

        System.out.println(gson.toJson(checkReq));

        //String destionationURL = "http://www.mocky.io/v2/5e548eea3100006000eb31ea";// Diisi API MF.///////////////////////
        String destionationURL = "https://10.20.218.9:9079/absentmg-in-out/absent";
        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(checkReq));
        ResponseEntity<String> responseEntity = restTemplate.exchange(destionationURL, HttpMethod.POST, entity, String.class);
        String text = responseEntity.getBody();
        //System.out.println(text);
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
        input.setInputTrans("");

        resp.setInputHistory(input);

        System.out.println(gson.toJson(resp));

        //String destionationURL = "http://www.mocky.io/v2/5e54899e3100002900eb31d5";// Diisi API MF.
        String destionationURL = "https://10.20.218.9:9079/absentmg-history/history";
        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(resp));
        ResponseEntity<String> responseEntity = restTemplate.exchange(destionationURL, HttpMethod.POST, entity, String.class);
        String text = responseEntity.getBody();

        if(responseEntity.getStatusCode()== HttpStatus.OK) {

            GetUserHistoryRespMainFrame response1 = gson.fromJson(text, GetUserHistoryRespMainFrame.class);

            List<HistData> list = response1.getOutputHistory().getHistData();

            if(list.isEmpty())
            {
                response.setHistData(list);
                response.setErrorCode("99");
                response.setErrorMessage("History Data is Empty");
            }

            for (HistData data : list) {
                data.setOutputTimeIn(converterString.convertTime(data.getOutputTimeIn()));
                data.setOutputTimeOut(converterString.convertTime(data.getOutputTimeOut()));
                data.setOutputDate(converterString.convertDate(data.getOutputDate()));
            }

            response.setOutputUserId(response1.getOutputHistory().getOutputUserId());
            response.setOutputMm(response1.getOutputHistory().getOutputMm());
            response.setOutputYyyy(response1.getOutputHistory().getOutputYyyy());
            response.setOutputAttend(String.valueOf(response1.getOutputHistory().getOutputAttend()));
            response.setHistData(list);
            response.setErrorCode(response1.getOutputHistory().getErrorCode());
            response.setErrorMessage(response1.getOutputHistory().getErrorMessage());
        }
        else
        {
            response.setErrorCode("99");
            response.setErrorMessage("Failed");
        }

        return response;
    }

    public long countUser(){
        return userDao.count();
    }

    /*
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
        String result, day, mon, monFormat, year;
        String[] split = input.split("-");

        year = split[0];
        mon = getMonth(Integer.parseInt(split[1]));
        monFormat = mon.substring(0,2);
        day = split[2];

        result = day.concat(" ").concat(monFormat).concat(" ").concat(year);

        return result;
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

    public int countChar(String input){
        return input.length();
    }

     */
}
