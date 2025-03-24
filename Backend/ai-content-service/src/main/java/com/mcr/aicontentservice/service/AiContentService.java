package com.mcr.aicontentservice.service;

import com.mcr.aicontentservice.client.AiClient;
import com.mcr.aicontentservice.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AiContentService {
    private final AiClient aiClient;

    @Value("${api.key}")
    private String apiKey;

    public String generateDescription(String productName, String category) {

        AiRequest aiRequest = new AiRequest();
        aiRequest.setModel("jamba-large");

        aiRequest.setMessages(Arrays.asList(
                new Message("system", "You're a professional content writer specializing in crafting detailed, SEO-friendly product descriptions in a conversational tone. Avoid headings, markdown symbols, or hashtags. Focus on writing engaging paragraphs relevant to the product."),
                new Message("user", "Generate an SEO-friendly product description for a " +
                        productName + " in the " + category + " category."),
                new Message("assistant", "Here is you description.")));
        aiRequest.setStop(List.of("\n"));

        AiResponse aiResponse = aiClient.generateDescription("Bearer " + apiKey, aiRequest);

        return aiResponse.getChoices().get(0).getMessage().getContent();
    }
}
