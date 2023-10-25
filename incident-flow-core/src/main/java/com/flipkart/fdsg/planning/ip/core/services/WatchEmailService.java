package com.flipkart.fdsg.planning.ip.core.services;

import com.flipkart.fdsg.planning.ip.core.entities.WatchEmail;

import java.util.List;

public interface WatchEmailService {
    List<WatchEmail> getAllEmails();
    void addEmail(String email);

    void deleteEmail(String email);
}
