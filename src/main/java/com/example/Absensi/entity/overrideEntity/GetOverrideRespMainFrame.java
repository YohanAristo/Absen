package com.example.Absensi.entity.overrideEntity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetOverrideRespMainFrame {
    private OutputOverride outputOverride;

    public OutputOverride getOutputOverride() {
        return outputOverride;
    }

    public void setOutputOverride(OutputOverride outputOverride) {
        this.outputOverride = outputOverride;
    }
}
