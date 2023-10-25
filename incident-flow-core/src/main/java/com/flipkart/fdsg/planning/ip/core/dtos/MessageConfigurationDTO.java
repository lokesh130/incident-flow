package com.flipkart.fdsg.planning.ip.core.dtos;

import com.flipkart.fdsg.planning.ip.core.entities.MessageConfiguration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageConfigurationDTO {

    private Long id;
    private String subjectRegex;
    private Integer frequency;

    public static MessageConfigurationDTO map(MessageConfiguration messageConfiguration) {
        return MessageConfigurationDTO.builder()
                .id(messageConfiguration.getId())
                .subjectRegex(messageConfiguration.getSubjectRegex())
                .frequency(messageConfiguration.getFrequency())
                .build();
    }
}
