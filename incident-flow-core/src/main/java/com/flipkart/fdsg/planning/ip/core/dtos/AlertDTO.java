package com.flipkart.fdsg.planning.ip.core.dtos;

import com.flipkart.fdsg.planning.ip.core.entities.Alert;
import com.flipkart.fdsg.planning.ip.core.entities.Priority;
import lombok.Data;

@Data
public class AlertDTO {
    private int id;
    private String title;
    private String message;
    private Priority priority;

    public static AlertDTO map(Alert alert) {
        AlertDTO dto = new AlertDTO();
        dto.setId(alert.getId());
        dto.setTitle(alert.getTitle());
        dto.setMessage(alert.getMessage());
        dto.setPriority(alert.getPriority());
        return dto;
    }
}
