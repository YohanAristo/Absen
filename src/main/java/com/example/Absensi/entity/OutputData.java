package com.example.Absensi.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OutputData extends BaseResponse{
    private String outputMfc;
    private String outputUserId;
    private String outputDate;
    private String outputTimeIn;
    private String outputTimeOut;
    private String outputStatus;
    private String outputDesc;


    public String getOutputMfc() {
        return outputMfc;
    }

    public void setOutputMfc(String outputMfc) {
        this.outputMfc = outputMfc;
    }

    public String getOutputUserId() {
        return outputUserId;
    }

    public void setOutputUserId(String outputUserId) {
        this.outputUserId = outputUserId;
    }

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

    public String getOutputStatus() {
        return outputStatus;
    }

    public void setOutputStatus(String outputStatus) {
        this.outputStatus = outputStatus;
    }

    public String getOutputDesc() {
        return outputDesc;
    }

    public void setOutputDesc(String outputDesc) {
        this.outputDesc = outputDesc;
    }
}
