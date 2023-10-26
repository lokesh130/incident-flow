package com.flipkart.fdsg.planning.ip.core.dtos;

import com.flipkart.fdsg.planning.ip.core.entities.RawEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RawEmailDTO {
    private Long id;
    private String subject;
    private String fromList;
    private String toList;
    private String body;
    private String messageId;
    private String threadId;
    private String messageOrder;
    private String startDate;
    private String endDate;

    public static RawEmailDTO map(RawEmail rawEmail) {
        return RawEmailDTO.builder()
                .id(rawEmail.getId())
                .subject(rawEmail.getSubject())
                .fromList(rawEmail.getFromList())
                .toList(rawEmail.getToList())
                .body(rawEmail.getBody())
                .messageId(rawEmail.getMessageId())
                .threadId(rawEmail.getThreadId())
                .messageOrder(rawEmail.getMessageOrder())
                .startDate(rawEmail.getStartDate().toString())
                .endDate(rawEmail.getEndDate().toString())
                .build();
    }
}