package com.flipkart.fdsg.planning.ip.core.dtos;

import com.flipkart.fdsg.planning.ip.core.entities.OncallSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OncallSummaryDTO {
    private Long id;
    private OncallTrackerDTO oncallTracker;
    private String suggestion;
    private String threadDate;

    public static OncallSummaryDTO map(OncallSummary oncallSummary) {
        return OncallSummaryDTO.builder()
                .id(oncallSummary.getId())
                .oncallTracker(OncallTrackerDTO.map(oncallSummary.getOncallTracker()))
                .suggestion(oncallSummary.getSuggestion())
                .threadDate(oncallSummary.getThreadDate().toString())
                .build();
    }
}
