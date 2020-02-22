package com.example.Absensi.entity.overrideEntity;

import com.example.Absensi.entity.BaseResponse;
import com.example.Absensi.model.Override;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetOverrideRespList extends BaseResponse {
    private List<Override> overrideList;

    public List<Override> getOverrideList() {
        return overrideList;
    }

    public void setOverrideList(List<Override> overrideList) {
        this.overrideList = overrideList;
    }
}
