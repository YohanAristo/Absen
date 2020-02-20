package com.example.Absensi.service;

import com.example.Absensi.dao.OverrideDao;
import com.example.Absensi.entity.overrideEntity.PostOverrideReq;
import com.example.Absensi.entity.userEntity.BaseResponse;
import com.example.Absensi.model.Override;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OverrideService {

    @Autowired
    OverrideDao overrideDao;

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

    
}
