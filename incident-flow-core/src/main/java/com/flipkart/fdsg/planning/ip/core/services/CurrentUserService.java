package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.dtos.CurrentUserDTO;
import com.flipkart.fdsg.planning.ip.core.entities.CurrentUser;

import java.util.List;

public interface CurrentUserService {
    List<CurrentUser> findAll();

    List<CurrentUser> findByOncallTrackerId(Long oncallTrackerId);

    void add(List<CurrentUserDTO> currentUserDTOs);
}
