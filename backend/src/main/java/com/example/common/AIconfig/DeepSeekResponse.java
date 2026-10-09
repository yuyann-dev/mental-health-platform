package com.example.common.AIconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class DeepSeekResponse {

    private String model;

    private List<Choice> choices;

    private String id;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Choice {
        private Delta delta;
        private Integer index;
        private String finishReason;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Delta {
        private String content;
    }

}
