package com.example.Absensi.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetUserHistoryRespMainFrame{
   private OutputHistory outputHistory;

    public OutputHistory getOutputHistory() {
        return outputHistory;
    }

    public void setOutputHistory(OutputHistory outputHistory) {
        this.outputHistory = outputHistory;
    }
}
