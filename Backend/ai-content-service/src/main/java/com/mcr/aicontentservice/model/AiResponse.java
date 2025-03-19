package com.mcr.aicontentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiResponse {
    private String id;
    private String model;
    private List<Choice> choices;
    private Usage usage;
}
