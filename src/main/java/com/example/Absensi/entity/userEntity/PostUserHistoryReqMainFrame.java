package com.example.Absensi.entity.userEntity;

import com.example.Absensi.entity.userEntity.InputHistory;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PostUserHistoryReqMainFrame {
    private InputHistory inputHistory;

    public InputHistory getInputHistory() {
        return inputHistory;
    }

    public void setInputHistory(InputHistory inputHistory) {
        this.inputHistory = inputHistory;
    }
}
