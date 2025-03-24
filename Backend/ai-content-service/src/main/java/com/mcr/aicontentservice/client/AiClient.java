package com.mcr.aicontentservice.client;

import com.mcr.aicontentservice.config.FeignClientConfig;
import com.mcr.aicontentservice.model.AiRequest;
import com.mcr.aicontentservice.model.AiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="aiClient",
        url="${api.url}",
        configuration= FeignClientConfig.class)
public interface AiClient {

    @PostMapping("/v1/chat/completions")
    AiResponse generateDescription(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody AiRequest request);
}
