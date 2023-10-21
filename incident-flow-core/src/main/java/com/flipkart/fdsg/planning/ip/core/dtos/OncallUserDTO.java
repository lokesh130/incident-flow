package com.flipkart.fdsg.planning.ip.core.dtos;

import com.flipkart.fdsg.planning.ip.core.entities.OncallUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OncallUserDTO {
    private Long id;
    private String userId;
    private String name;

    public static OncallUserDTO map(OncallUser oncallUser) {
        return OncallUserDTO.builder()
                .id(oncallUser.getId())
                .userId(oncallUser.getUserId())
                .name(oncallUser.getName())
                .build();
    }
}
