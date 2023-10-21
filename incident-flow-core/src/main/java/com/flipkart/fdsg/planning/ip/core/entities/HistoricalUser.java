package com.flipkart.fdsg.planning.ip.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "historical_users")
@NamedQueries({
        @NamedQuery(name = "HistoricalUsers.findAll", query = "SELECT hu FROM HistoricalUser hu"),
        @NamedQuery(name = "HistoricalUsers.findByOncallTrackerId",
                query = "SELECT hu FROM HistoricalUser hu WHERE hu.oncallTracker.id = :oncallTrackerId")

})
public class HistoricalUser extends BaseEntity {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "oncall_tracker_id")
    private OncallTracker oncallTracker;

    @Column(name = "user_id")
    private String userId;
}
