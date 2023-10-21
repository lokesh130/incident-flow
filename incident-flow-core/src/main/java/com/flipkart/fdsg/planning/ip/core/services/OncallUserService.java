package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.entities.OncallUser;

import java.util.List;
import java.util.Optional;

public interface OncallUserService {
    List<OncallUser> findAll();

    Optional<String> findNextUserIdById(Long currentId);
}
