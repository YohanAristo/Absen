package com.example.Absensi.entity.overrideEntity;

import com.example.Absensi.entity.BaseResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OutputOverride extends BaseResponse {
    private String outputMfc;

    public String getOutputMfc() {
        return outputMfc;
    }

    public void setOutputMfc(String outputMfc) {
        this.outputMfc = outputMfc;
    }
}
