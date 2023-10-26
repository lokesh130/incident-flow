package com.flipkart.fdsg.planning.ip.core.clients;

import com.flipkart.fdsg.planning.ip.core.dtos.MessageDTO;

import javax.mail.Message;
import javax.mail.search.SearchTerm;
import java.util.Date;
import java.util.List;

public interface GmailClient {
    List<MessageDTO> getMessages(List<String> givenEmail, long timestamp);
}
