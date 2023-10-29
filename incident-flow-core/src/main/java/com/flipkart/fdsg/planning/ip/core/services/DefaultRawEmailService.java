package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.RawEmailDAO;
import com.flipkart.fdsg.planning.ip.core.dtos.ActiveOncallGroupDTO;
import com.flipkart.fdsg.planning.ip.core.dtos.OncallTrackerDTO;
import com.flipkart.fdsg.planning.ip.core.dtos.RawEmailDTO;
import com.flipkart.fdsg.planning.ip.core.entities.OncallTracker;
import com.flipkart.fdsg.planning.ip.core.entities.RawEmail;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
public class DefaultRawEmailService implements RawEmailService {

    private final RawEmailDAO rawEmailDAO;
    private final ActiveOncallGroupService activeOncallGroupService;

    private final ChatGptService chatGptService;

    @Inject
    public DefaultRawEmailService(RawEmailDAO rawEmailDAO, ActiveOncallGroupService activeOncallGroupService, ChatGptService chatGptService) {
        this.rawEmailDAO = rawEmailDAO;
        this.activeOncallGroupService = activeOncallGroupService;
        this.chatGptService = chatGptService;
    }

    @Override
    @Transactional
    public List<RawEmail> findAll() {
        return rawEmailDAO.findAll();
    }

    @Override
    @Transactional
    public List<RawEmail> findBySubject(String subject) {
        return rawEmailDAO.findBySubject(subject);
    }

    @Override
    @Transactional
    public List<RawEmail> findByThreadId(String threadId) {
        return rawEmailDAO.findByThreadId(threadId);
    }

    @Override
    @Transactional
    public List<RawEmail> findByMessageId(String messageId) {
        return rawEmailDAO.findByMessageId(messageId);
    }

    @Override
    @Transactional
    public List<RawEmail> findById(Long id) {
        return rawEmailDAO.findById(id);
    }

    @Transactional
    public void addRawEmails(List<RawEmailDTO> rawEmailDTOList) {
        for (RawEmailDTO rawEmailDTO : rawEmailDTOList) {
            rawEmailDAO.addRawEmail(rawEmailDTO);
        }
    }

    @Override
    public OncallTrackerDTO getUpdatedOncallTracker(OncallTrackerDTO oncallTrackerDTO, RawEmailDTO rawEmailDTO) {
        if (oncallTrackerDTO == null) {
            log.info("Creating new OncallTracker from RawEmailDTO...");

            oncallTrackerDTO = OncallTrackerDTO.builder()
                    .title(rawEmailDTO.getSubject())
                    .description(chatGptService.getDescription(rawEmailDTO.getBody()))
                    .activeOncallGroup(fetchLatestActiveOncallGroup())
                    .oncallStatus(chatGptService.getOncallStatus(true, rawEmailDTO.getBody()))
                    .status(chatGptService.getStatus(rawEmailDTO.getBody()))
                    .priority(OncallTracker.Priority.valueOf(chatGptService.getPriority(rawEmailDTO.getBody())))
                    .rcaDoc(null)
                    .threadId(rawEmailDTO.getThreadId())
                    .summary(chatGptService.getSummary(rawEmailDTO.getBody()))
                    .build();
        } else {
            log.info("Updating existing OncallTracker from RawEmailDTO...");

            oncallTrackerDTO.setOncallStatus(chatGptService.getOncallStatus(false, rawEmailDTO.getBody()));
            oncallTrackerDTO.setSummary(chatGptService.getSummary(rawEmailDTO.getBody()));
            oncallTrackerDTO.setStatus(chatGptService.getStatus(rawEmailDTO.getBody()));
        }

        return oncallTrackerDTO;
    }


    private String getSubstringBeforeFirstPeriod(String input) {
        int index = input.indexOf('.');
        return index != -1 ? input.substring(0, index) : input;
    }


    private ActiveOncallGroupDTO fetchLatestActiveOncallGroup() {
        log.info("Fetching latest ActiveOncallGroup...");
        return activeOncallGroupService.findLatestActiveGroup()
                .map(ActiveOncallGroupDTO::map)
                .orElse(null);
    }

}
