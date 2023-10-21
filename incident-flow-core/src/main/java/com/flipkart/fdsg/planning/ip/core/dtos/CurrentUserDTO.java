package com.flipkart.fdsg.planning.ip.core.dtos;

import com.flipkart.fdsg.planning.ip.core.entities.CurrentUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserDTO {
    private Long id;
    private OncallTrackerDTO oncallTracker;
    private String userId;

    public static CurrentUserDTO map(CurrentUser currentUser) {
        return CurrentUserDTO.builder()
                .id(currentUser.getId())
                .oncallTracker(OncallTrackerDTO.map(currentUser.getOncallTracker()))
                .userId(currentUser.getUserId())
                .build();
    }
}
