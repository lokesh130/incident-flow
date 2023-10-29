package com.flipkart.fdsg.planning.ip.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "followup_criteria")
@NamedQueries({
    @NamedQuery(name = "FollowupCriteria.findById", query = "SELECT f FROM FollowupCriteria f WHERE f.id = :id"),
    @NamedQuery(name = "FollowupCriteria.findAll", query = "SELECT f FROM FollowupCriteria f"),
        @NamedQuery(name = "FollowupCriteria.findLatest", query = "SELECT f FROM FollowupCriteria f ORDER BY f.createdAt DESC")

})
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowupCriteria extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "expected_duration")
    private Long expectedDuration;
}
