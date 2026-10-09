package com.example.controller;

import cn.hutool.json.JSONUtil;
import com.example.common.Result;
import com.example.common.AIconfig.DeepSeekClient;
import com.example.common.AIconfig.DeepSeekResponse;
import com.example.entity.TestPaper;
import com.example.entity.Topic;
import com.example.service.TestPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

@Controller
@RequestMapping("/ai")
public class AIController {
    @Autowired
    private DeepSeekClient deepSeekClient;

    @Autowired
    private TestPaperService testPaperService;

    @GetMapping(value = "/sendMessageStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sendMessage(@RequestParam("userAnswerList") List<String> userAnswerList, @RequestParam("testPaperId") Integer testPaperId) {
        SseEmitter emitter = new SseEmitter();
        String prompt = this.prompt(userAnswerList, testPaperId);
        ResponseExtractor<Void> responseExtractor = response -> {
            try (InputStream body = response.getBody();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(body, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        try {
                            if (line.contains("[DONE]")) {
                                emitter.complete();
                                return null;
                            }
                            line = line.split("data: ")[1];
                            DeepSeekResponse bean = JSONUtil.toBean(line, DeepSeekResponse.class);
                            List<DeepSeekResponse.Choice> choices = bean.getChoices();
                            if (choices == null || choices.isEmpty()) {
                                emitter.complete();
                                return null;
                            }
                            String content = choices.get(0).getDelta().getContent();
                            if (content == null) {
                                continue;
                            }
                            content = content.replaceAll("\n", "<br>");
                            System.out.println(content);
                            emitter.send(content);
                        } catch (Exception e) {
                            System.out.println("当前返回值：" + line);
                            throw new RuntimeException("出错了。错误原因：" + e.getMessage());
                        }
                    }
                }
                emitter.complete();
            } catch (Exception e) {
                System.out.println("出错了。错误原因：" + e.getMessage());
                emitter.completeWithError(e);
            }
            return null;
        };

        Consumer<Exception> errorHandle = e -> {
            try {
                emitter.send("data: Error calling DeepSeek API: " + e.getMessage() + "\n\n");
            } catch (Exception ignored) {
                System.out.println("出错了111。错误原因：" + e.getMessage());
            }
            emitter.completeWithError(e);
        };
        deepSeekClient.sendMessageStream(prompt, responseExtractor, errorHandle);
        return emitter;
    }

    @GetMapping("aiResult")
    public Result getAiResult(@RequestParam("userAnswerList") List<String> userAnswerList, @RequestParam("testPaperId") Integer testPaperId) {
        String prompt = this.prompt(userAnswerList, testPaperId);
        String result = deepSeekClient.sendMessage(prompt);
        System.out.println(result);
        return Result.success(result);
    }

    private String prompt(List<String> userAnswerList, Integer testPaperId) {
        TestPaper testPaper = testPaperService.selectById(testPaperId);
        List<Topic> topicList = testPaper.getTopicList();
        StringBuilder question = new StringBuilder("以下是我的一个问卷调查，包含题目以及用户给出的答案，请你根据这份问卷的问题，分析填写者的心理状态。请尽量简化逐题分析的环节：");
        question.append("\n");
        question.append("问题");
        question.append("\t");
        question.append("选项A");
        question.append("\t");
        question.append("A分数");
        question.append("\t");
        question.append("选项B");
        question.append("\t");
        question.append("B分数");
        question.append("\t");
        question.append("选项C");
        question.append("\t");
        question.append("C分数");
        question.append("\t");
        question.append("选项D");
        question.append("\t");
        question.append("D分数");
        question.append("\t");
        question.append("用户选择");
        question.append("\t");
        for (int i = 0; i < topicList.size(); i++) {
            Topic topic = topicList.get(i);
            question.append("\n");
            question.append(topic.getTitle());
            question.append("\t");
            question.append(topic.getaName());
            question.append("\t");
            question.append(topic.getaScore());
            question.append("\t");
            question.append(topic.getbName());
            question.append("\t");
            question.append(topic.getbScore());
            question.append("\t");
            question.append(topic.getcName());
            question.append("\t");
            question.append(topic.getcScore());
            question.append("\t");
            question.append(topic.getdName());
            question.append("\t");
            question.append(topic.getdScore());
            question.append("\t");
            question.append(userAnswerList.get(i));
            question.append("\t");
        }
        return question.toString();
    }

}
