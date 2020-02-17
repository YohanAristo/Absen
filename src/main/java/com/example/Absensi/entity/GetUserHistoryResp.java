package com.example.Absensi.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetUserHistoryResp extends BaseResponse{
    private String outputUserId;
    private String outputMm;
    private String outputYyyy;
    private int outputAttend;
    private List<HistData> histData;

    public String getOutputUserId() {
        return outputUserId;
    }

    public void setOutputUserId(String outputUserId) {
        this.outputUserId = outputUserId;
    }

    public String getOutputMm() {
        return outputMm;
    }

    public void setOutputMm(String outputMm) {
        this.outputMm = outputMm;
    }

    public String getOutputYyyy() {
        return outputYyyy;
    }

    public void setOutputYyyy(String outputYyyy) {
        this.outputYyyy = outputYyyy;
    }

    public int getOutputAttend() {
        return outputAttend;
    }

    public void setOutputAttend(int outputAttend) {
        this.outputAttend = outputAttend;
    }

    public List<HistData> getHistData() {
        return histData;
    }

    public void setHistData(List<HistData> histData) {
        this.histData = histData;
    }
}
