package com.flipkart.fdsg.planning.ip.core.dtos;

import com.flipkart.fdsg.planning.ip.core.entities.ActiveOncallGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActiveOncallGroupDTO {
    private Long id;
    private OncallUserDTO primaryUser;
    private OncallUserDTO secondaryUser;
    private String startDate;
    private String endDate;

    public static ActiveOncallGroupDTO map(ActiveOncallGroup activeOncallGroup) {
        return ActiveOncallGroupDTO.builder()
                .id(activeOncallGroup.getId())
                .primaryUser(OncallUserDTO.map(activeOncallGroup.getPrimaryUser()))
                .secondaryUser(OncallUserDTO.map(activeOncallGroup.getSecondaryUser()))
                .startDate(activeOncallGroup.getStartDate().toString())
                .endDate(activeOncallGroup.getEndDate().toString())
                .build();
    }

    public static ActiveOncallGroup mapToEntity(ActiveOncallGroupDTO dto) {
        ActiveOncallGroup entity = new ActiveOncallGroup();
        entity.setId(dto.getId());
        entity.setPrimaryUser(OncallUserDTO.mapToEntity(dto.getPrimaryUser()));
        entity.setSecondaryUser(OncallUserDTO.mapToEntity(dto.getSecondaryUser()));
        entity.setStartDate(LocalDateTime.parse(dto.getStartDate()));
        entity.setEndDate(LocalDateTime.parse(dto.getEndDate()));
        return entity;
    }
}
