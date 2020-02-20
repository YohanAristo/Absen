package com.example.Absensi.entity.overrideEntity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PostOverrideReqMainFrame {
    private InputOverride inputOverride;

    public InputOverride getInputOverride() {
        return inputOverride;
    }

    public void setInputOverride(InputOverride inputOverride) {
        this.inputOverride = inputOverride;
    }
}
