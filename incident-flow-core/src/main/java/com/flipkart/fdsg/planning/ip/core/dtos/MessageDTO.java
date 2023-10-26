package com.flipkart.fdsg.planning.ip.core.dtos;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private List<String> toList;
    private String from;
    private String subject;
    private String content;
    private String messageId;
    private String threadId;
}