package com.flipkart.fdsg.planning.ip.core.dtos;

import com.flipkart.fdsg.planning.ip.core.entities.WatchEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WatchEmailDTO {
    private String email;

    public static WatchEmailDTO map(WatchEmail email) {
        WatchEmailDTO watchEmailDTO = new WatchEmailDTO();
        watchEmailDTO.setEmail(email.getEmail());
        return watchEmailDTO;
    }
}
