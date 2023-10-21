package com.flipkart.fdsg.planning.ip.core.dtos;

import com.flipkart.fdsg.planning.ip.core.entities.OncallSuggestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OncallSuggestionDTO {
    private Long id;
    private OncallTrackerDTO oncallTracker;
    private String suggestion;

    public static OncallSuggestionDTO map(OncallSuggestion oncallSuggestion) {
        return OncallSuggestionDTO.builder()
                .id(oncallSuggestion.getId())
                .oncallTracker(OncallTrackerDTO.map(oncallSuggestion.getOncallTracker()))
                .suggestion(oncallSuggestion.getSuggestion())
                .build();
    }
}
