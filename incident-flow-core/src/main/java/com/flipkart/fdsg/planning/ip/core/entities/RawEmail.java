package com.flipkart.fdsg.planning.ip.core.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "raw_emails")
@NamedQueries({
        @NamedQuery(name = "Emails.findAll", query = "SELECT emails FROM RawEmail emails"),
        @NamedQuery(name = "Emails.findBySubject", query = "SELECT emails FROM RawEmail emails "+
                "WHERE emails.subject = :subject"),
        @NamedQuery(name = "Emails.findByThreadId", query = "SELECT emails FROM RawEmail emails "+
                "WHERE emails.threadId = :threadId"),
        @NamedQuery(name = "Emails.findByMessageId", query = "SELECT emails FROM RawEmail emails "+
                "WHERE emails.messageId = :messageId"),
        @NamedQuery(name = "Emails.findById", query = "SELECT emails FROM RawEmail emails "+
                "WHERE emails.id = :id")
})
public class RawEmail extends BaseEntity {
    @Id
    private Long id;

    @Column
    private String subject;

    @Column(name="to_list")
    private String toList;

    @Column(name = "from")
    private String from;

    @Column
    private String body;

    @Column(name = "message_id")
    private String messageId;

    @Column(name = "thread_id")
    private String threadId;

    @Column(name = "message_order")
    private String messageOrder;

    // Custom getter for toList
    public List<String> getToList() {
        return Arrays.asList(toList.split(","));
    }

    // Custom setter for toList
    public void setToList(List<String> toList) {
        this.toList = String.join(",", toList);
    }
}