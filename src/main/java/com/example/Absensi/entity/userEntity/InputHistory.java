package com.example.Absensi.entity.userEntity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InputHistory {
    private String inputTrans;
    private String inputAction;
    private String inputUserId;
    private String inputMm;
    private String inputYyyy;

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

    public String getInputMm() {
        return inputMm;
    }

    public void setInputMm(String inputMm) {
        this.inputMm = inputMm;
    }

    public String getInputYyyy() {
        return inputYyyy;
    }

    public void setInputYyyy(String inputYyyy) {
        this.inputYyyy = inputYyyy;
    }
}
