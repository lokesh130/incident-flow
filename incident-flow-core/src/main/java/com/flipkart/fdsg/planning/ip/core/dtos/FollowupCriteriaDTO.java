package com.flipkart.fdsg.planning.ip.core.dtos;

import com.flipkart.fdsg.planning.ip.core.entities.FollowupCriteria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowupCriteriaDTO {
    private Long id;
    private Long expectedDuration;

    public static FollowupCriteriaDTO map(FollowupCriteria followupCriteria) {
        return FollowupCriteriaDTO.builder()
                .id(followupCriteria.getId())
                .expectedDuration(followupCriteria.getExpectedDuration())
                .build();
    }

    public FollowupCriteria mapToEntity() {
        FollowupCriteria followupCriteria = new FollowupCriteria();
        followupCriteria.setId(this.id);
        followupCriteria.setExpectedDuration(this.expectedDuration);
        return followupCriteria;
    }
}
