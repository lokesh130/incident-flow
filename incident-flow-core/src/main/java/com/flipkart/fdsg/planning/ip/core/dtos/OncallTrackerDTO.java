package com.flipkart.fdsg.planning.ip.core.dtos;

import com.flipkart.fdsg.planning.ip.core.entities.OncallTracker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OncallTrackerDTO {
    private Long id;
    private String title;
    private String description;
    private ActiveOncallGroupDTO activeOncallGroup;
    private String oncallStatus;
    private String status;
    private OncallTracker.Priority priority;
    private String rcaDoc;

    public static OncallTrackerDTO map(OncallTracker oncallTracker) {
        return OncallTrackerDTO.builder()
                .id(oncallTracker.getId())
                .title(oncallTracker.getTitle())
                .description(oncallTracker.getDescription())
                .activeOncallGroup(ActiveOncallGroupDTO.map(oncallTracker.getActiveOncallGroup()))
                .oncallStatus(oncallTracker.getOncallStatus())
                .status(oncallTracker.getStatus())
                .priority(oncallTracker.getPriority())
                .rcaDoc(oncallTracker.getRcaDoc())
                .build();
    }
}
