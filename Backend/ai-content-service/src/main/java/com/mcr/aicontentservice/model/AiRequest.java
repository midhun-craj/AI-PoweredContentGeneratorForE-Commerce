package com.mcr.aicontentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiRequest {
   private String model;
   private List<Message> messages;
   private int max_tokens = 100;
   private double temperature = 0.7;
   private double top_p = 1.0;
   private List<String> stop;
   private int n = 1;
}
