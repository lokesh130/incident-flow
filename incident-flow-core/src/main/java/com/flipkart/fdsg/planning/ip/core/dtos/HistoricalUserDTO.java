package com.flipkart.fdsg.planning.ip.core.dtos;

import com.flipkart.fdsg.planning.ip.core.entities.HistoricalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoricalUserDTO {
    private Long id;
    private OncallTrackerDTO oncallTracker;
    private String userId;

    public static HistoricalUserDTO map(HistoricalUser historicalUser) {
        return HistoricalUserDTO.builder()
                .id(historicalUser.getId())
                .oncallTracker(OncallTrackerDTO.map(historicalUser.getOncallTracker()))
                .userId(historicalUser.getUserId())
                .build();
    }
}
