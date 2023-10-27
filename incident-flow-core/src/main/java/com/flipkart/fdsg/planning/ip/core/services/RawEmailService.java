package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.dtos.OncallTrackerDTO;
import com.flipkart.fdsg.planning.ip.core.dtos.RawEmailDTO;
import com.flipkart.fdsg.planning.ip.core.entities.RawEmail;

import java.util.List;

public interface RawEmailService {
    List<RawEmail> findAll();

    List<RawEmail> findBySubject(String subject);

    List<RawEmail> findByThreadId(String threadId);

    List<RawEmail> findByMessageId(String messageId);

    List<RawEmail> findById(Long id);

    void addRawEmails(List<RawEmailDTO> rawEmailDTOList);

    OncallTrackerDTO getUpdatedOncallTracker(OncallTrackerDTO oncallTrackerDTO, RawEmailDTO rawEmailDTO);
}
