package com.flipkart.fdsg.planning.ip.core.dtos;

import com.flipkart.fdsg.planning.ip.core.entities.Followup;
import com.flipkart.fdsg.planning.ip.core.entities.Priority;
import lombok.Data;

@Data
public class FollowupDTO {
    private int id;
    private String oncallTitle;
    private OncallTrackerDTO oncallTrackerDTO;
    private String message;
    private Priority priority;

    public static FollowupDTO map(Followup followup) {
        FollowupDTO dto = new FollowupDTO();
        dto.setId(followup.getId());
        dto.setMessage(followup.getMessage());
        dto.setPriority(followup.getPriority());
        dto.setOncallTitle(followup.getOncallTracker().getTitle());
        dto.setOncallTrackerDTO(OncallTrackerDTO.map(followup.getOncallTracker()));
        return dto;
    }
}
