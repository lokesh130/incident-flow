package com.flipkart.fdsg.planning.ip.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
        @NamedQuery(name = "Emails.findByToList",
                query = "SELECT emails FROM RawEmail emails " +
                        "WHERE emails.toList = :toList " +
                        "ORDER BY emails.startDate DESC"
        ),
        @NamedQuery(
                name = "Emails.findByFromList",
                query = "SELECT emails FROM RawEmail emails " +
                        "WHERE emails.fromList = :fromList "+
                        "ORDER BY emails.startDate DESC"
        ),
        @NamedQuery(
                name = "Emails.findByThreadId",
                query = "SELECT emails FROM RawEmail emails " +
                        "WHERE emails.threadId = :threadId "+
                        "ORDER BY emails.startDate DESC"
        ),
        @NamedQuery(
                name = "Emails.findByMessageId",
                query = "SELECT emails FROM RawEmail emails " +
                        "WHERE emails.messageId = :messageId "+
                        "ORDER BY emails.startDate DESC"
        )

})
public class RawEmail extends BaseEntity {
    @Id
    private Long id;

    @Column
    private String subject;

    @Column(name="to_list")
    private String toList;

    @Column(name = "from_list")
    private String fromList;

    @Column
    private String body;

    @Column(name = "message_id")
    private String messageId;

    @Column(name = "thread_id")
    private String threadId;

    @Column(name = "message_order")
    private String messageOrder;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;
}