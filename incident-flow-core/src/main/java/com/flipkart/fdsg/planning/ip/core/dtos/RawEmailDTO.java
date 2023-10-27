package com.flipkart.fdsg.planning.ip.core.dtos;

import com.flipkart.fdsg.planning.ip.core.entities.RawEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class RawEmailDTO {
    private Long id;
    private String subject;
    private String from;
    private List<String> toList;
    private String body;
    private String messageId;
    private String threadId;
    private Integer messageOrder;

    public static RawEmailDTO map(RawEmail rawEmail) {
        return RawEmailDTO.builder()
                .id(rawEmail.getId())
                .subject(rawEmail.getSubject())
                .from(rawEmail.getFrom())
                .toList(rawEmail.getToList())
                .body(rawEmail.getBody())
                .messageId(rawEmail.getMessageId())
                .threadId(rawEmail.getThreadId())
                .messageOrder(rawEmail.getMessageOrder())
                .build();
    }

    public static RawEmail mapToEntity(RawEmailDTO rawEmailDTO) {
        RawEmail rawEmail = RawEmail.builder()
                .subject(rawEmailDTO.getSubject())
                .from(rawEmailDTO.getFrom())
                .body(rawEmailDTO.getBody())
                .messageId(rawEmailDTO.getMessageId())
                .threadId(rawEmailDTO.getThreadId())
                .messageOrder(rawEmailDTO.getMessageOrder())
                .build();

        rawEmail.setToList(rawEmailDTO.getToList());
        return rawEmail;
    }
}