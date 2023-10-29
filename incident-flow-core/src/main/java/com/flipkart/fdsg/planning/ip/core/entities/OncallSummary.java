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
@Table(name = "oncall_suggestions")
@NamedQueries({
        @NamedQuery(name = "OncallSuggestions.findAll", query = "SELECT os FROM OncallSummary os"),
        @NamedQuery(name = "OncallSuggestions.findByOncallTrackerId",
                query = "SELECT os FROM OncallSummary os WHERE os.oncallTracker.id = :oncallTrackerId")

})
public class OncallSummary extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "oncall_tracker_id")
    private OncallTracker oncallTracker;

    @Column(name = "suggestion")
    private String suggestion;

    @Column(name = "thread_date")
    private LocalDateTime threadDate;
}
