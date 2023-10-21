package com.flipkart.fdsg.planning.ip.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "current_users")
@NamedQueries({
        @NamedQuery(name = "CurrentUser.findAll", query = "SELECT cu FROM CurrentUser cu"),
        @NamedQuery(name = "CurrentUser.findByOncallTrackerId",
                query = "SELECT cu FROM CurrentUser cu WHERE cu.oncallTracker.id = :oncallTrackerId")
})
public class CurrentUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "oncall_tracker_id")
    private OncallTracker oncallTracker;

    @Column(name = "user_id")
    private String userId;
}
