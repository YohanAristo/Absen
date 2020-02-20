package com.example.Absensi.entity.userEntity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class HistData {
    private String outputDate;
    private String outputTimeIn;
    private String outputTimeOut;
    private String outputDesc;

    public String getOutputDate() {
        return outputDate;
    }

    public void setOutputDate(String outputDate) {
        this.outputDate = outputDate;
    }

    public String getOutputTimeIn() {
        return outputTimeIn;
    }

    public void setOutputTimeIn(String outputTimeIn) {
        this.outputTimeIn = outputTimeIn;
    }

    public String getOutputTimeOut() {
        return outputTimeOut;
    }

    public void setOutputTimeOut(String outputTimeOut) {
        this.outputTimeOut = outputTimeOut;
    }

    public String getOutputDesc() {
        return outputDesc;
    }

    public void setOutputDesc(String outputDesc) {
        this.outputDesc = outputDesc;
    }
}
