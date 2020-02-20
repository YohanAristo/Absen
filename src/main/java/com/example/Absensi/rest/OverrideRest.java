package com.example.Absensi.rest;

import com.example.Absensi.entity.overrideEntity.GetOverrideRespList;
import com.example.Absensi.entity.overrideEntity.GetOverrideRespMainFrame;
import com.example.Absensi.entity.overrideEntity.PostOverrideReq;
import com.example.Absensi.entity.BaseResponse;
import com.example.Absensi.model.Override;
import com.example.Absensi.service.OverrideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OverrideRest {

    @Autowired
    OverrideService overrideService;

    @PostMapping(value = "absentmg-in-out/override")
    public BaseResponse createOverride(@RequestBody PostOverrideReq overrideReq){
        return overrideService.postOverride(overrideReq);
    }

    @DeleteMapping(value = "absentmg-in-out/override/reject")
    public BaseResponse deleteOverride(@RequestBody Override override){
        return overrideService.deleteOverride(override);
    }

    @PostMapping(value = "absentmg-in-out/override/accept")
    public BaseResponse approvalOverride(@RequestBody Override override){
        return overrideService.approvalOverride(override);
    }

    @GetMapping(value = "absentmg-in-out/override/list")
    public GetOverrideRespList overrideList(){
        return overrideService.getOverrideList();
    }

}
