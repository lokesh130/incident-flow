package com.flipkart.fdsg.planning.ip.core.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "oncall_tracker")
@NamedQueries({
        @NamedQuery(name = "OncallTracker.findAll", query = "SELECT ot FROM OncallTracker ot"),
        @NamedQuery(name = "OncallTracker.findByPrimaryUserId",
                query = "SELECT ot FROM OncallTracker ot WHERE ot.activeOncallGroup.primaryUser.id = :primaryUserId"),
        @NamedQuery(name = "OncallTracker.findBySecondaryUserId",
                query = "SELECT ot FROM OncallTracker ot WHERE ot.activeOncallGroup.secondaryUser.id = :secondaryUserId"),
        @NamedQuery(name = "OncallTracker.findByPrimaryAndSecondaryUserId",
                query = "SELECT ot FROM OncallTracker ot WHERE ot.activeOncallGroup.primaryUser.id = :primaryUserId " +
                        "AND ot.activeOncallGroup.secondaryUser.id = :secondaryUserId"),
        @NamedQuery(name = "OncallTracker.findByOncallGroupId",
                query = "SELECT ot FROM OncallTracker ot WHERE ot.activeOncallGroup.id = :oncallGroupId")
})
public class OncallTracker extends BaseEntity {
    @Id
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "active_oncall_group_id")
    private ActiveOncallGroup activeOncallGroup;

    @Column(name = "oncall_status")
    private String oncallStatus;

    @Column(name = "status")
    private String status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Column(name = "rca_doc")
    private String rcaDoc;

    public enum Priority {
        P0, P1, P2, P3;
    }
}
