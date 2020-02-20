package com.example.Absensi.entity.overrideEntity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InputOverride {
    private String inputTrans;
    private String inputAction;
    private String inputUserId;
    private String inputDate;
    private String inputTimeIn;
    private String inputTimeOut;

    public String getInputTrans() {
        return inputTrans;
    }

    public void setInputTrans(String inputTrans) {
        this.inputTrans = inputTrans;
    }

    public String getInputAction() {
        return inputAction;
    }

    public void setInputAction(String inputAction) {
        this.inputAction = inputAction;
    }

    public String getInputUserId() {
        return inputUserId;
    }

    public void setInputUserId(String inputUserId) {
        this.inputUserId = inputUserId;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getInputTimeIn() {
        return inputTimeIn;
    }

    public void setInputTimeIn(String inputTimeIn) {
        this.inputTimeIn = inputTimeIn;
    }

    public String getInputTimeOut() {
        return inputTimeOut;
    }

    public void setInputTimeOut(String inputTimeOut) {
        this.inputTimeOut = inputTimeOut;
    }
}
