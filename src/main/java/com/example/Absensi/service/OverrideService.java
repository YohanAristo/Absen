package com.example.Absensi.service;

import com.example.Absensi.dao.OverrideDao;
import com.example.Absensi.entity.overrideEntity.*;
import com.example.Absensi.entity.BaseResponse;
import com.example.Absensi.model.Override;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OverrideService {

    @Autowired
    OverrideDao overrideDao;

    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    public BaseResponse postOverride(PostOverrideReq override){
        BaseResponse response = new BaseResponse();
        Override override1 = new Override();

        override1.setUserId(override.getUserId());
        override1.setDate(override.getDate());
        override1.setTime(override.getTime());
        override1.setAction(override.getAction());

        Override saveOverride = overrideDao.save(override1);

        if(saveOverride == null) {
            response.setErrorCode("99");
            response.setErrorMessage("Failed to Request Override");
            return response;
        }
        else {
            response.setErrorCode("00");
            response.setErrorMessage("Successfully Request Override");
            return response;
        }
    }

    public BaseResponse deleteOverride(Override override){
        BaseResponse response = new BaseResponse();

        if(!overrideDao.existsById(override.getId()))
        {
            response.setErrorCode("99");
            response.setErrorMessage("Override Request Not Found");
            return response;
        }

        overrideDao.deleteById(override.getId());
        response.setErrorCode("00");
        response.setErrorMessage("Successfully Delete Override");
        return response;
    }

    public GetOverrideRespList getOverrideList(){
        GetOverrideRespList respList = new GetOverrideRespList();

        List<Override> overrides = overrideDao.findAll();

        respList.setOverrideList(overrides);
        respList.setErrorCode("00");
        respList.setErrorMessage("Successfully Show User");
        return respList;
    }

    public BaseResponse approvalOverride(Override override){
        BaseResponse response = new BaseResponse();
        RestTemplate restTemplate = new RestTemplate();
        PostOverrideReqMainFrame postOverrideReq = new PostOverrideReqMainFrame();
        InputOverride inputOverride = new InputOverride();

        if(override.getAction().equalsIgnoreCase("I"))
        {
            inputOverride.setInputAction("V");
            inputOverride.setInputUserId(override.getUserId());
            inputOverride.setInputDate(override.getDate());
            inputOverride.setInputTimeIn(override.getTime());
            postOverrideReq.setInputOverride(inputOverride);
        }
        else
        {
            inputOverride.setInputAction("V");
            inputOverride.setInputUserId(override.getUserId());
            inputOverride.setInputDate(override.getDate());
            inputOverride.setInputTimeOut(convertTimeAndroid(override.getTime()));
            postOverrideReq.setInputOverride(inputOverride);
        }

//        postOverrideReq.setAction(override.getAction());
//        postOverrideReq.setDate(override.getDate());
//        postOverrideReq.setTime(convertTimeAndroid(override.getTime()));
//        postOverrideReq.setUserId(override.getUserId());

        String destionationURL = "https://10.20.218.9:9079/history-absentmg/history";//////////////////////////
        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(postOverrideReq));
        ResponseEntity<String> responseEntity = restTemplate.exchange(destionationURL, HttpMethod.POST, entity, String.class);
        String text = responseEntity.getBody();

        if(responseEntity.getStatusCode()== HttpStatus.OK) {
            GetOverrideRespMainFrame response1 = gson.fromJson(text, GetOverrideRespMainFrame.class);

            if(!overrideDao.existsById(override.getId()))
            {
                response.setErrorCode("99");
                response.setErrorMessage("Override Approval Error");
                return response;
            }

            overrideDao.deleteById(override.getId());
            response.setErrorMessage(response1.getOutputOverride().getErrorMessage());
            response.setErrorCode(response1.getOutputOverride().getErrorCode());
        }
        else
        {
            response.setErrorCode("99");
            response.setErrorMessage("Failed");
        }

        return response;
    }

    public String convertTimeAndroid(String input){
        String result, hh, mm, ss="00";
        String[] splits = input.split("\\:");

        hh = splits[0];
        mm = splits[1];

        result = hh.concat(".").concat(mm).concat(".").concat(ss);

        return result;
    }
}
