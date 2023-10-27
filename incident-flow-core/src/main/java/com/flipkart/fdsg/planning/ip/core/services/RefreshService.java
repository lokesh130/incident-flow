package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.dtos.RawEmailDTO;

import java.util.List;

public interface RefreshService {
    List<RawEmailDTO> refreshEmails();
}

