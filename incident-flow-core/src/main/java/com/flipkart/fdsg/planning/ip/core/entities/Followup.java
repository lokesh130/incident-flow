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
@Table(name = "followups")
@NamedQueries({
        @NamedQuery(
                name = "Followup.find.all",
                query = "SELECT f FROM Followup f"
        ),
        @NamedQuery(
                name = "Followup.find.by.id",
                query = "SELECT f FROM Followup f WHERE f.id = :followupId"
        )
})
public class Followup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "oncall_tracker_id")
    private OncallTracker oncallTracker;

    private String message;

    @Enumerated(EnumType.STRING)
    private Priority priority;
}
