package com.flipkart.fdsg.planning.ip.core.dtos;

import com.flipkart.fdsg.planning.ip.core.entities.OncallTracker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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
    private String threadId;
    private String summary;

    public static OncallTrackerDTO map(OncallTracker oncallTracker) {
        if(Objects.isNull(oncallTracker)) {
            return null;
        }

        return OncallTrackerDTO.builder()
                .id(oncallTracker.getId())
                .title(oncallTracker.getTitle())
                .description(oncallTracker.getDescription())
                .activeOncallGroup(ActiveOncallGroupDTO.map(oncallTracker.getActiveOncallGroup()))
                .oncallStatus(oncallTracker.getOncallStatus())
                .status(oncallTracker.getStatus())
                .priority(oncallTracker.getPriority())
                .rcaDoc(oncallTracker.getRcaDoc())
                .threadId(oncallTracker.getThreadId())
                .summary(oncallTracker.getSummary())
                .build();
    }

    public static OncallTracker mapToEntity(OncallTrackerDTO dto) {
        return OncallTracker.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .activeOncallGroup(ActiveOncallGroupDTO.mapToEntity(dto.getActiveOncallGroup()))
                .oncallStatus(dto.getOncallStatus())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .rcaDoc(dto.getRcaDoc())
                .threadId(dto.getThreadId())
                .summary(dto.getSummary())
                .build();
    }
}
