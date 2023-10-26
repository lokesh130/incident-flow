package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.daos.RawEmailDAO;
import com.flipkart.fdsg.planning.ip.core.dtos.RawEmailDTO;
import com.flipkart.fdsg.planning.ip.core.entities.RawEmail;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class DefaultRawEmailService implements RawEmailService {

    private final RawEmailDAO rawEmailDAO;

    @Inject
    public DefaultRawEmailService(RawEmailDAO rawEmailDAO) {
        this.rawEmailDAO = rawEmailDAO;
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
}
