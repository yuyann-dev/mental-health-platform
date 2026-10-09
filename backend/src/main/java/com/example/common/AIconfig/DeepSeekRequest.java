package com.example.common.AIconfig;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DeepSeekRequest {
    private String model;

    private List<Message> messages = new ArrayList<>();

    private boolean stream = true;

    private StreamOptions stream_options = new StreamOptions(true);



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Message {
        private String role;

        private String content;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class StreamOptions {
        private boolean include_usage;
    }

}
