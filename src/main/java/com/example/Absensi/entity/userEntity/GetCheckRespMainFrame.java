package com.example.Absensi.entity.userEntity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetCheckRespMainFrame{
    private OutputData outputData;

    public OutputData getOutputData() {
        return outputData;
    }

    public void setOutputData(OutputData outputData) {
        this.outputData = outputData;
    }
}
